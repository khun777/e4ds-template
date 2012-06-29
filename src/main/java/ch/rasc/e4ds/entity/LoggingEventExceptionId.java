package ch.rasc.e4ds.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LoggingEventExceptionId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "event_id", nullable = false)
	private long eventId;

	@Column(name = "i", nullable = false)
	private short i;

	public LoggingEventExceptionId() {
		// no action
	}

	public LoggingEventExceptionId(final long eventId, final short i) {
		this.eventId = eventId;
		this.i = i;
	}

	public long getEventId() {
		return this.eventId;
	}

	public void setEventId(final long eventId) {
		this.eventId = eventId;
	}

	public short getI() {
		return this.i;
	}

	public void setI(final short i) {
		this.i = i;
	}

	@Override
	public boolean equals(final Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof LoggingEventExceptionId)) {
			return false;
		}
		LoggingEventExceptionId castOther = (LoggingEventExceptionId) other;

		return (this.getEventId() == castOther.getEventId()) && (this.getI() == castOther.getI());
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getEventId();
		result = 37 * result + this.getI();
		return result;
	}

}
