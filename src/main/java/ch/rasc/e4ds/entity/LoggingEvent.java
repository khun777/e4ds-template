package ch.rasc.e4ds.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Sets;

@Entity
@Table(name = "logging_event")
public class LoggingEvent {

	@Column(name = "timestmp", nullable = false)
	private BigDecimal timestmp;

	@Column(name = "formatted_message", nullable = false)
	private String formattedMessage;

	@Column(name = "logger_name", nullable = false)
	private String loggerName;

	@Column(name = "level_string", nullable = false)
	private String levelString;

	@Column(name = "thread_name")
	private String threadName;

	@Column(name = "reference_flag")
	private Short referenceFlag;

	private String arg0;

	private String arg1;

	private String arg2;

	private String arg3;

	@Column(name = "caller_filename", nullable = false)
	private String callerFilename;

	@Column(name = "caller_class", nullable = false)
	private String callerClass;

	@Column(name = "caller_method", nullable = false)
	private String callerMethod;

	@Column(name = "caller_line", nullable = false)
	private String callerLine;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "event_id", unique = true, nullable = false)
	private Long eventId;

	@OneToMany(mappedBy = "eventId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<LoggingEventException> loggingEventException = Sets.newHashSet();

	@OneToMany(mappedBy = "eventId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<LoggingEventProperty> loggingEventProperty = Sets.newHashSet();

	public BigDecimal getTimestmp() {
		return this.timestmp;
	}

	public void setTimestmp(final BigDecimal timestmp) {
		this.timestmp = timestmp;
	}

	public String getFormattedMessage() {
		return this.formattedMessage;
	}

	public void setFormattedMessage(final String formattedMessage) {
		this.formattedMessage = formattedMessage;
	}

	public String getLoggerName() {
		return this.loggerName;
	}

	public void setLoggerName(final String loggerName) {
		this.loggerName = loggerName;
	}

	public String getLevelString() {
		return this.levelString;
	}

	public void setLevelString(final String levelString) {
		this.levelString = levelString;
	}

	public String getThreadName() {
		return this.threadName;
	}

	public void setThreadName(final String threadName) {
		this.threadName = threadName;
	}

	public Short getReferenceFlag() {
		return this.referenceFlag;
	}

	public void setReferenceFlag(final Short referenceFlag) {
		this.referenceFlag = referenceFlag;
	}

	public String getArg0() {
		return this.arg0;
	}

	public void setArg0(final String arg0) {
		this.arg0 = arg0;
	}

	public String getArg1() {
		return this.arg1;
	}

	public void setArg1(final String arg1) {
		this.arg1 = arg1;
	}

	public String getArg2() {
		return this.arg2;
	}

	public void setArg2(final String arg2) {
		this.arg2 = arg2;
	}

	public String getArg3() {
		return this.arg3;
	}

	public void setArg3(final String arg3) {
		this.arg3 = arg3;
	}

	public String getCallerFilename() {
		return this.callerFilename;
	}

	public void setCallerFilename(final String callerFilename) {
		this.callerFilename = callerFilename;
	}

	public String getCallerClass() {
		return this.callerClass;
	}

	public void setCallerClass(final String callerClass) {
		this.callerClass = callerClass;
	}

	public String getCallerMethod() {
		return this.callerMethod;
	}

	public void setCallerMethod(final String callerMethod) {
		this.callerMethod = callerMethod;
	}

	public String getCallerLine() {
		return this.callerLine;
	}

	public void setCallerLine(final String callerLine) {
		this.callerLine = callerLine;
	}

	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(final Long eventId) {
		this.eventId = eventId;
	}

	public Set<LoggingEventException> getLoggingEventException() {
		return this.loggingEventException;
	}

	public void setLoggingEventException(final Set<LoggingEventException> loggingEventException) {
		this.loggingEventException = loggingEventException;
	}

	public Set<LoggingEventProperty> getLoggingEventProperty() {
		return this.loggingEventProperty;
	}

	public void setLoggingEventProperty(final Set<LoggingEventProperty> loggingEventProperty) {
		this.loggingEventProperty = loggingEventProperty;
	}

}
