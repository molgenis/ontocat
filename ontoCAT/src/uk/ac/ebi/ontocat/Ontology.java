package uk.ac.ebi.ontocat;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ontology")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Ontology implements Serializable {
	private String abbreviation;
	private String dateReleased;
	private String description;
	private String label;
	private String ontologyAccession;
	private String synonymSlot;
	private String versionNumber;

	public Ontology() {

	}

	public Ontology(String ontologyAccession) {
		this.ontologyAccession = ontologyAccession;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getDateReleased() throws OntologyServiceException {
		return dateReleased;
	}

	public void setDateReleased(String dateReleased) {
		this.dateReleased = dateReleased;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() throws OntologyServiceException {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getOntologyAccession() {
		return ontologyAccession;
	}

	public void setOntologyAccession(String ontologyAccession) {
		this.ontologyAccession = ontologyAccession;
	}

	public String getSynonymSlot() {
		return synonymSlot;
	}

	public void setSynonymSlot(String synonymSlot) {
		this.synonymSlot = synonymSlot;
	}

	public String getVersionNumber() throws OntologyServiceException {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String toString() {
		try {
			return String
					.format(
							"Ontology(abbreviation=%s, label=%s, ontologyAccession=%s,  dateReleased=%s, synonymSlot=%s, versionNumber=%s, description=%s)",
							getAbbreviation(), getLabel(),
							getOntologyAccession(), getDateReleased(),
							getSynonymSlot(), getVersionNumber(),
							getDescription());
		} catch (OntologyServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
}
