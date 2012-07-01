package ch.rasc.e4ds.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.ralscha.extdirectspring.util.JsonHandler;

import com.google.common.collect.Maps;

@Controller
public class I18nMessageController implements InitializingBean {

	@Autowired(required = false)
	private JsonHandler jsonHandler;

	private final static String prefix = "var i18n = ";

	private final static String postfix = ";";

	@Override
	public void afterPropertiesSet() throws Exception {
		if (jsonHandler == null) {
			jsonHandler = new JsonHandler();
		}
	}

	@RequestMapping(value = "/i18n.js", method = RequestMethod.GET)
	public void i18n(HttpServletResponse response, Locale locale) throws JsonGenerationException, JsonMappingException,
			IOException {

		response.setContentType("application/x-javascript;charset=UTF-8");

		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);

		Map<String, String> messages = Maps.newHashMap();
		Enumeration<String> e = rb.getKeys();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			messages.put(key, rb.getString(key));
		}

		String output = prefix + jsonHandler.writeValueAsString(messages) + postfix;
		response.setContentLength(output.getBytes().length);

		ServletOutputStream out = response.getOutputStream();
		out.write(output.getBytes(StandardCharsets.UTF_8));
		out.flush();
	}

}
