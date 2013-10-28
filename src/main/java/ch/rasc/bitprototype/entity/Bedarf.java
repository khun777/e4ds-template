package ch.rasc.bitprototype.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.joda.time.LocalDate;

import ch.ralscha.extdirectspring.generator.Model;
import ch.ralscha.extdirectspring.generator.ModelField;
import ch.ralscha.extdirectspring.generator.ModelType;
import ch.rasc.bitprototype.service.BedarfStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;

@Entity
@Model(value = "BitP.model.Bedarf", readMethod = "bedarfService.read", createMethod = "bedarfService.create", updateMethod = "bedarfService.update", destroyMethod = "bedarfService.destroy", paging = true)
public class Bedarf extends AbstractPersistable {

	private String titel;

	private String anforderungen;

	@Convert(converter = DateTimeConverter.class)
	@ModelField(dateFormat = "c")
	private LocalDate arbeitsdauerBeginn;

	@Convert(converter = DateTimeConverter.class)
	@ModelField(dateFormat = "c")
	private LocalDate arbeitsdauerEnde;

	private String arbeitsOrt;

	private BigDecimal kostendach;

	@Convert(converter = DateTimeConverter.class)
	@ModelField(dateFormat = "c")
	private LocalDate einreichefrist;

	@ModelField(type = ModelType.STRING)
	private BedarfStatus status;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false, updatable = false)
	private User owner;

	@JsonIgnore
	@Convert(converter = DateTimeConverter.class)
	private LocalDate abgeschlossen;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bedarf", orphanRemoval = true)
	private Set<BedarfAttachment> attachments = Sets.newHashSet();

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getAnforderungen() {
		return anforderungen;
	}

	public void setAnforderungen(String anforderungen) {
		this.anforderungen = anforderungen;
	}

	public LocalDate getArbeitsdauerBeginn() {
		return arbeitsdauerBeginn;
	}

	public void setArbeitsdauerBeginn(LocalDate arbeitsdauerBeginn) {
		this.arbeitsdauerBeginn = arbeitsdauerBeginn;
	}

	public LocalDate getArbeitsdauerEnde() {
		return arbeitsdauerEnde;
	}

	public void setArbeitsdauerEnde(LocalDate arbeitsdauerEnde) {
		this.arbeitsdauerEnde = arbeitsdauerEnde;
	}

	public String getArbeitsOrt() {
		return arbeitsOrt;
	}

	public void setArbeitsOrt(String arbeitsOrt) {
		this.arbeitsOrt = arbeitsOrt;
	}

	public BigDecimal getKostendach() {
		return kostendach;
	}

	public void setKostendach(BigDecimal kostendach) {
		this.kostendach = kostendach;
	}

	public LocalDate getEinreichefrist() {
		return einreichefrist;
	}

	public void setEinreichefrist(LocalDate einreichefrist) {
		this.einreichefrist = einreichefrist;
	}

	public BedarfStatus getStatus() {
		return status;
	}

	public void setStatus(BedarfStatus status) {
		this.status = status;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public LocalDate getAbgeschlossen() {
		return abgeschlossen;
	}

	public void setAbgeschlossen(LocalDate abgeschlossen) {
		this.abgeschlossen = abgeschlossen;
	}

	public Set<BedarfAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<BedarfAttachment> attachments) {
		this.attachments = attachments;
	}

}
