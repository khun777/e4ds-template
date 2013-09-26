package ch.rasc.e4ds.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.FORM_POST;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.e4ds.entity.QRole;
import ch.rasc.e4ds.entity.QUser;
import ch.rasc.e4ds.entity.Role;
import ch.rasc.e4ds.entity.User;
import ch.rasc.e4ds.security.JpaUserDetails;
import ch.rasc.e4ds.util.Util;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
@Lazy
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<User> read(ExtDirectStoreReadRequest request) {

		JPQLQuery query = new JPAQuery(entityManager).from(QUser.user);
		if (!request.getFilters().isEmpty()) {
			StringFilter filter = (StringFilter) request.getFilters().iterator().next();

			BooleanBuilder bb = new BooleanBuilder();
			bb.or(QUser.user.userName.contains(filter.getValue()));
			bb.or(QUser.user.name.contains(filter.getValue()));
			bb.or(QUser.user.firstName.contains(filter.getValue()));
			bb.or(QUser.user.email.contains(filter.getValue()));

			query.where(bb);
		}

		Util.addPagingAndSorting(query, request, User.class, QUser.user);
		SearchResults<User> searchResult = query.listResults(QUser.user);

		return new ExtDirectStoreResult<>(searchResult.getTotal(), searchResult.getResults());
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional
	public void destroy(User destroyUser) {
		if (!isLastAdmin(destroyUser)) {
			entityManager.remove(entityManager.find(User.class, destroyUser.getId()));
		}
	}

	@ExtDirectMethod(FORM_POST)
	@Transactional
	@PreAuthorize("isAuthenticated()")
	public ExtDirectFormPostResult userFormPost(Locale locale,
			@RequestParam(required = false, defaultValue = "false") final boolean options,
			@RequestParam(value = "id", required = false) final Long userId,
			@RequestParam(required = false) final String roleIds, @Valid final User modifiedUser,
			final BindingResult bindingResult) {

		// Check uniqueness of userName and email
		if (!bindingResult.hasErrors()) {
			if (!options) {
				BooleanBuilder bb = new BooleanBuilder(QUser.user.userName.equalsIgnoreCase(modifiedUser.getUserName()));
				if (userId != null) {
					bb.and(QUser.user.id.ne(userId));
				}
				if (new JPAQuery(entityManager).from(QUser.user).where(bb).exists()) {
					bindingResult.rejectValue("userName", null,
							messageSource.getMessage("user_usernametaken", null, locale));
				}
			}

			BooleanBuilder bb = new BooleanBuilder(QUser.user.email.equalsIgnoreCase(modifiedUser.getEmail()));
			if (userId != null && !options) {
				bb.and(QUser.user.id.ne(userId));
			} else if (options) {
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (principal instanceof JpaUserDetails) {
					bb.and(QUser.user.id.ne(((JpaUserDetails) principal).getUserDbId()));
				}
			}

			if (new JPAQuery(entityManager).from(QUser.user).where(bb).exists()) {
				bindingResult.rejectValue("email", null, messageSource.getMessage("user_emailtaken", null, locale));
			}
		}

		if (!bindingResult.hasErrors()) {

			if (StringUtils.hasText(modifiedUser.getPasswordHash())) {
				modifiedUser.setPasswordHash(passwordEncoder.encode(modifiedUser.getPasswordHash()));
			}

			if (!options) {
				Set<Role> roles = Sets.newHashSet();
				if (StringUtils.hasText(roleIds)) {
					Iterable<String> roleIdsIt = Splitter.on(",").split(roleIds);
					for (String roleId : roleIdsIt) {
						roles.add(entityManager.find(Role.class, Long.valueOf(roleId)));
					}
				}

				if (userId != null) {
					User dbUser = entityManager.find(User.class, userId);
					if (dbUser != null) {
						dbUser.getRoles().clear();
						dbUser.getRoles().addAll(roles);

						dbUser.setUserName(modifiedUser.getUserName());
						dbUser.setEnabled(modifiedUser.isEnabled());
						dbUser.setName(modifiedUser.getName());
						dbUser.setFirstName(modifiedUser.getFirstName());
						dbUser.setEmail(modifiedUser.getEmail());
						dbUser.setLocale(modifiedUser.getLocale());

						if (StringUtils.hasText(modifiedUser.getPasswordHash())) {
							dbUser.setPasswordHash(modifiedUser.getPasswordHash());
						}
					}
				} else {
					modifiedUser.setRoles(roles);
					entityManager.persist(modifiedUser);
				}
			} else {
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (principal instanceof JpaUserDetails) {
					User dbUser = entityManager.find(User.class, ((JpaUserDetails) principal).getUserDbId());
					if (dbUser != null) {
						dbUser.setName(modifiedUser.getName());
						dbUser.setFirstName(modifiedUser.getFirstName());
						dbUser.setEmail(modifiedUser.getEmail());
						dbUser.setLocale(modifiedUser.getLocale());
						if (StringUtils.hasText(modifiedUser.getPasswordHash())) {
							if (passwordEncoder.matches(modifiedUser.getOldPassword(), dbUser.getPasswordHash())) {
								dbUser.setPasswordHash(modifiedUser.getPasswordHash());
							} else {
								bindingResult.rejectValue("oldPassword", null,
										messageSource.getMessage("user_wrongpassword", null, locale));
							}
						}
					}
				}
			}
		}

		return new ExtDirectFormPostResult(bindingResult);
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public User getLoggedOnUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof JpaUserDetails) {
			return entityManager.find(User.class, ((JpaUserDetails) principal).getUserDbId());
		}
		return null;
	}

	private boolean isLastAdmin(User user) {
		Role role = new JPAQuery(entityManager).from(QRole.role).where(QRole.role.name.eq("ROLE_ADMIN"))
				.singleResult(QRole.role);
		JPQLQuery query = new JPAQuery(entityManager).from(QUser.user);
		query.where(QUser.user.ne(user).and(QUser.user.roles.contains(role)));
		return query.notExists();
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	public List<Role> readRoles() {
		return new JPAQuery(entityManager).from(QRole.role).orderBy(QRole.role.name.asc()).list(QRole.role);
	}

}
