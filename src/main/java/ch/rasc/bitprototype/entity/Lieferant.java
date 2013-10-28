package ch.rasc.bitprototype.entity;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import ch.ralscha.extdirectspring.generator.Model;

@Entity
@Model(value = "BitP.model.Lieferant", readMethod = "lieferantService.read", createMethod = "lieferantService.create", updateMethod = "lieferantService.update", destroyMethod = "lieferantService.destroy", paging = true)
public class Lieferant extends AbstractPersistable {

	@NotEmpty(message = "{lieferant_missing_firma}")
	@Size(max = 255)
	private String firma;

	@Size(max = 255)
	private String zusatz;

	@Size(max = 255)
	private String strasse;

	@Size(max = 20)
	private String plz;

	@Size(max = 255)
	private String ort;

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getZusatz() {
		return zusatz;
	}

	public void setZusatz(String zusatz) {
		this.zusatz = zusatz;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

}
