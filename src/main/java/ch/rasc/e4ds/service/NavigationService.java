package ch.rasc.e4ds.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.TREE_LOAD;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

@Service
public class NavigationService {

	@Autowired
	private MessageSource messageSource;

	private final MenuNode root;

	public NavigationService() {
		root = new MenuNode("root");

		MenuNode businessNode = new MenuNode("navigation_business", "/resources/images/Shape18-16.png", true);
		businessNode.addChild(new MenuNode("chart_title", "/resources/images/Real-Time-16.png", "E4ds.view.poll.PollChart",
				Roles.ROLE_USER, Roles.ROLE_ADMIN));
		root.addChild(businessNode);

		MenuNode administrationNode = new MenuNode("navigation_administration", "/resources/images/Shape18-16.png",
				true);
		administrationNode.addChild(new MenuNode("user_users", "/resources/images/User-Group-16.png", "E4ds.view.user.List",
				Roles.ROLE_ADMIN));
		root.addChild(administrationNode);

		MenuNode systemNode = new MenuNode("navigation_system", "/resources/images/Shape18-16.png", true);
		systemNode.addChild(new MenuNode("accesslog", "/resources/images/Books-16.png", "E4ds.view.accesslog.List",
				Roles.ROLE_ADMIN));
		systemNode.addChild(new MenuNode("logevents", "/resources/images/Books-16.png",
				"E4ds.view.loggingevent.List", Roles.ROLE_ADMIN));
		systemNode.addChild(new MenuNode("config", "/resources/images/Customize-02-16.png", "E4ds.view.config.Edit",
				Roles.ROLE_ADMIN));
		root.addChild(systemNode);
	}

	@ExtDirectMethod(TREE_LOAD)
	@PreAuthorize("isAuthenticated()")
	public MenuNode getNavigation(Locale locale, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return MenuNode
				.copyOf(root, authentication.getAuthorities(), new MutableInt(0), locale, messageSource, request);
	}

}
