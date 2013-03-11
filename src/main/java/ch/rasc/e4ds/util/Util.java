package ch.rasc.e4ds.util;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.SortDirection;
import ch.ralscha.extdirectspring.bean.SortInfo;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.PathBuilder;

public class Util {

	private Util() {
		// do not instantiate this class
	}

	public static void addPagingAndSorting(JPQLQuery query, ExtDirectStoreReadRequest request, Class<?> clazz,
			EntityPathBase<?> entityPathBase) {
		addPagingAndSorting(query, request, clazz, entityPathBase, Collections.<String, String> emptyMap(),
				Collections.<String> emptySet());
	}

	public static void addSorting(JPQLQuery query, ExtDirectStoreReadRequest request, Class<?> clazz,
			EntityPathBase<?> entityPathBase) {
		addSorting(query, request, clazz, entityPathBase, Collections.<String, String> emptyMap(),
				Collections.<String> emptySet());
	}

	public static void addPagingAndSorting(JPQLQuery query, ExtDirectStoreReadRequest request, Class<?> clazz,
			EntityPathBase<?> entityPathBase, Map<String, String> mapGuiColumn2Dbfield, Set<String> sortIgnoreProperties) {

		if (request.getStart() != null) {
			query.offset(request.getStart()).limit(request.getLimit());
		}

		addSorting(query, request, clazz, entityPathBase, mapGuiColumn2Dbfield, sortIgnoreProperties);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addSorting(JPQLQuery query, ExtDirectStoreReadRequest request, Class<?> clazz,
			EntityPathBase<?> entityPathBase, Map<String, String> mapGuiColumn2Dbfield, Set<String> sortIgnoreProperties) {
		if (!request.getSorters().isEmpty()) {
			PathBuilder<?> entityPath = new PathBuilder<>(clazz, entityPathBase.getMetadata());
			for (SortInfo sortInfo : request.getSorters()) {

				if (!sortIgnoreProperties.contains(sortInfo.getProperty())) {
					Order order;
					if (sortInfo.getDirection() == SortDirection.ASCENDING) {
						order = Order.ASC;
					} else {
						order = Order.DESC;
					}

					String property = mapGuiColumn2Dbfield.get(sortInfo.getProperty());
					if (property == null) {
						property = sortInfo.getProperty();
					}

					query.orderBy(new OrderSpecifier(order, entityPath.get(property)));
				}
			}
		}
	}
	/*
	 * public static JpaUserDetails getLoggedInUser() { Object principal =
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); if
	 * (principal instanceof JpaUserDetails) { return (JpaUserDetails)
	 * principal; } return null; }
	 * 
	 * public static boolean hasRole(String role) { // get security context from
	 * thread local SecurityContext context =
	 * SecurityContextHolder.getContext(); if (context == null) { return false;
	 * }
	 * 
	 * Authentication authentication = context.getAuthentication(); if
	 * (authentication == null) { return false; }
	 * 
	 * for (GrantedAuthority auth : authentication.getAuthorities()) { if
	 * (role.equals(auth.getAuthority())) { return true; } }
	 * 
	 * return false; }
	 */
}
