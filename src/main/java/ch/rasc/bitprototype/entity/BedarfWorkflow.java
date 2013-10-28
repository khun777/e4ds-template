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
import ch.rasc.bitprototype.service.BedarfStatus;
import ch.rasc.bitprototype.util.ISO8601DateTimeSerializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Model(value = "BitP.model.BedarfWorkflow", readMethod = "workflowService.readBedarf")
public class BedarfWorkflow extends AbstractPersistable {

	@ModelField(dateFormat = "c")
	@Convert(converter = DateTimeConverter.class)
	private DateTime createDate;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "bedarfId")
	private Bedarf bedarf;

	@ModelField(type = ModelType.STRING)
	private BedarfStatus lastStatus;

	@ModelField(type = ModelType.STRING)
	private BedarfStatus nextStatus;

	@JsonIgnore
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

	public Bedarf getBedarf() {
		return bedarf;
	}

	public void setBedarf(Bedarf bedarf) {
		this.bedarf = bedarf;
	}

	public BedarfStatus getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(BedarfStatus lastStatus) {
		this.lastStatus = lastStatus;
	}

	public BedarfStatus getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(BedarfStatus nextStatus) {
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
