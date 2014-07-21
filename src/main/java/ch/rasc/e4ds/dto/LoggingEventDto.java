package ch.rasc.e4ds.dto;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import ch.rasc.e4ds.entity.LoggingEventException;
import ch.rasc.e4ds.entity.LoggingEventProperty;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@Model(value = "E4ds.model.LoggingEvent", readMethod = "loggingEventService.read",
		paging = true)
public class LoggingEventDto {
	private final long id;

	@ModelField(dateFormat = "Y-m-d H:i:s")
	private final ZonedDateTime dateTime;

	private final String message;

	private final String level;

	private final String callerClass;

	private final String callerLine;

	private String ip;

	private String stacktrace;

	public LoggingEventDto(ch.rasc.e4ds.entity.LoggingEvent event) {
		this.id = event.getEventId();
		this.dateTime = ZonedDateTime.ofInstant(
				Instant.ofEpochMilli(event.getTimestmp().longValue()), ZoneOffset.UTC);
		this.message = event.getFormattedMessage();
		this.level = event.getLevelString();
		this.callerClass = event.getCallerClass();
		this.callerLine = event.getCallerLine();

		Set<LoggingEventProperty> properties = event.getLoggingEventProperty();

		for (LoggingEventProperty prop : properties) {
			if ("ip".equals(prop.getId().getMappedKey())) {
				this.ip = prop.getMappedValue();
				break;
			}
		}

		StringBuilder sb = new StringBuilder();

		List<LoggingEventException> exceptionList;

		Set<LoggingEventException> exceptions = event.getLoggingEventException();
		if (exceptions != null) {
			exceptionList = new ArrayList<>(exceptions);
			Collections.sort(exceptionList, (o1, o2) -> o1.getId().getI()
					- o2.getId().getI());
		}
		else {
			exceptionList = Collections.emptyList();
		}

		for (LoggingEventException line : exceptionList) {
			sb.append(line.getTraceLine());
			sb.append("<br />");
		}

		this.stacktrace = sb.toString();

	}

	public ZonedDateTime getDateTime() {
		return dateTime;
	}

	public String getMessage() {
		return message;
	}

	public String getLevel() {
		return level;
	}

	public String getCallerClass() {
		return callerClass;
	}

	public String getCallerLine() {
		return callerLine;
	}

	public String getIp() {
		return ip;
	}

	public String getStacktrace() {
		return stacktrace;
	}

	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}

	public long getId() {
		return id;
	}

}
