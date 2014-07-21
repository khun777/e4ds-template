package ch.rasc.e4ds.web;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ch.rasc.e4ds.entity.LoggingEvent;
import ch.rasc.e4ds.entity.LoggingEventException;
import ch.rasc.e4ds.entity.LoggingEventProperty;
import ch.rasc.e4ds.entity.QLoggingEvent;

import com.mysema.query.jpa.impl.JPAQuery;

@Controller
public class LoggingEventExport {

	private final EntityManager entityManager;

	private final MessageSource messageSource;

	@Autowired
	public LoggingEventExport(MessageSource messageSource, EntityManager entityManager) {
		this.messageSource = messageSource;
		this.entityManager = entityManager;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/loggingEventExport.txt", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public void loggingEventExport(HttpServletResponse response, @RequestParam(
			required = false) final String level, Locale locale) throws Exception {

		response.setContentType("text/plain");
		response.addHeader("Content-disposition", "attachment;filename=logs.txt");

		String separator = new String(new char[140]).replace("\0", "=");

		try (OutputStream out = response.getOutputStream();
				PrintWriter pw = new PrintWriter(out)) {

			List<LoggingEvent> events;
			if (StringUtils.hasText(level)) {
				events = new JPAQuery(entityManager).from(QLoggingEvent.loggingEvent)
						.where(QLoggingEvent.loggingEvent.levelString.eq(level))
						.list(QLoggingEvent.loggingEvent);
			}
			else {
				events = new JPAQuery(entityManager).from(QLoggingEvent.loggingEvent)
						.list(QLoggingEvent.loggingEvent);
			}

			for (LoggingEvent event : events) {
				String userName = "";
				String ip = "";

				Set<LoggingEventProperty> properties = event.getLoggingEventProperty();
				for (LoggingEventProperty prop : properties) {
					if ("userName".equals(prop.getId().getMappedKey())) {
						userName = prop.getMappedValue();
					}
					else if ("ip".equals(prop.getId().getMappedKey())) {
						ip = prop.getMappedValue();
					}
				}

				pw.println(messageSource.getMessage("logevents_timestamp", null, locale)
						+ "   : "
						+ ZonedDateTime.ofInstant(
								Instant.ofEpochMilli(event.getTimestmp().longValue()),
								ZoneOffset.UTC));
				pw.println(messageSource.getMessage("user", null, locale) + "   : "
						+ userName);
				pw.println(messageSource.getMessage("logevents_ip", null, locale)
						+ "     : " + ip);
				pw.println(messageSource.getMessage("logevents_level", null, locale)
						+ "  : " + event.getLevelString());
				pw.println(messageSource.getMessage("logevents_class", null, locale)
						+ " : " + event.getCallerClass());
				pw.println(messageSource.getMessage("logevents_method", null, locale)
						+ ": " + event.getCallerMethod());
				pw.println(messageSource.getMessage("logevents_line", null, locale)
						+ "  : " + event.getCallerLine());
				pw.println(messageSource.getMessage("logevents_message", null, locale)
						+ ": " + event.getFormattedMessage());

				Set<LoggingEventException> stacktrace = event.getLoggingEventException();

				if (!stacktrace.isEmpty()) {
					StringBuilder sb = new StringBuilder(300);
					stacktrace.stream()
							.sorted(Comparator.comparing(e -> e.getId().getI()))
							.forEach(e -> sb.append(e.getTraceLine()).append("\n"));
					pw.println(sb);
				}
				pw.println(separator);
			}

		}

	}
}
