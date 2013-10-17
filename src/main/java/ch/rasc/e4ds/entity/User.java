package ch.rasc.e4ds.entity;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import ch.ralscha.extdirectspring.generator.Model;
import ch.ralscha.extdirectspring.generator.ModelAssociation;
import ch.ralscha.extdirectspring.generator.ModelAssociationType;
import ch.ralscha.extdirectspring.generator.ModelField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

@Entity
@Table(name = "AppUser")
@Model(value = "E4ds.model.User", readMethod = "userService.read", createMethod = "userService.create", updateMethod = "userService.update", destroyMethod = "userService.destroy", paging = true)
public class User extends AbstractPersistable {

	@NotEmpty(message = "{user_missing_username}")
	@Size(max = 100)
	@Column(unique = true)
	private String userName;

	@Size(max = 255)
	private String name;

	@Size(max = 255)
	private String firstName;

	@Email(message = "{user_missing_email}")
	@Size(max = 255)
	@NotEmpty(message = "{user_missing_email}")
	@Column(unique = true)
	private String email;

	@Size(max = 255)
	@JsonIgnore
	private String passwordHash;

	@Transient
	private String passwordNew;

	@Transient
	private String passwordNewConfirm;

	@Transient
	private String oldPassword;

	@Size(max = 8)
	private String locale;

	private boolean enabled;

	@ModelField
	@Transient
	private Collection<Long> roleIds;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "AppUserRoles", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
	@ModelAssociation(value = ModelAssociationType.HAS_MANY, model = Role.class, foreignKey = "user_id", autoLoad = false)
	private Set<Role> roles;

	@JsonIgnore
	private Integer failedLogins;

	@JsonIgnore
	@Convert(converter = DateTimeConverter.class)
	private DateTime lockedOut;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Integer getFailedLogins() {
		return failedLogins;
	}

	public void setFailedLogins(Integer failedLogins) {
		this.failedLogins = failedLogins;
	}

	public DateTime getLockedOut() {
		return lockedOut;
	}

	public void setLockedOut(DateTime lockedOut) {
		this.lockedOut = lockedOut;
	}

	public String getPasswordNew() {
		return passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getPasswordNewConfirm() {
		return passwordNewConfirm;
	}

	public void setPasswordNewConfirm(String passwordNewConfirm) {
		this.passwordNewConfirm = passwordNewConfirm;
	}

	public Collection<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Collection<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@PostLoad
	@PostPersist
	@PostUpdate
	private void populate() {

		roleIds = Collections2.transform(roles, new Function<Role, Long>() {
			@Override
			public Long apply(Role input) {
				return input.getId();
			}
		});

	}

}
