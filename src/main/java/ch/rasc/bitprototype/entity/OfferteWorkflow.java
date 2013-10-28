package ch.rasc.bitprototype.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.joda.time.DateTime;

import ch.ralscha.extdirectspring.generator.Model;
import ch.ralscha.extdirectspring.generator.ModelField;
import ch.ralscha.extdirectspring.generator.ModelType;
import ch.rasc.bitprototype.service.OfferteStatus;
import ch.rasc.bitprototype.util.ISO8601DateTimeSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Model(value = "BitP.model.OfferteWorkflow", readMethod = "workflowService.readOfferte")
public class OfferteWorkflow extends AbstractPersistable {

	@ModelField(dateFormat = "c")
	@Convert(converter = DateTimeConverter.class)
	private DateTime createDate;

	@ManyToOne
	@JoinColumn(name = "offerteId")
	private Offerte offerte;

	@ModelField(type = ModelType.STRING)
	private OfferteStatus lastStatus;

	@ModelField(type = ModelType.STRING)
	private OfferteStatus nextStatus;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	private String notes;

	@Transient
	@ModelField(persist = false)
	private String lastName;

	@Transient
	@ModelField(persist = false)
	private String firstName;

	@JsonSerialize(using = ISO8601DateTimeSerializer.class)
	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}

	public Offerte getOfferte() {
		return offerte;
	}

	public void setOfferte(Offerte offerte) {
		this.offerte = offerte;
	}

	public OfferteStatus getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(OfferteStatus lastStatus) {
		this.lastStatus = lastStatus;
	}

	public OfferteStatus getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(OfferteStatus nextStatus) {
		this.nextStatus = nextStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@PostLoad
	private void populate() {
		if (user != null) {
			firstName = user.getFirstName();
			lastName = user.getName();
		} else {
			firstName = null;
			lastName = null;
		}

	}

}
