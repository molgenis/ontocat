package uk.ac.ebi.ontocat;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

@XmlRootElement(name = "term")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class OntologyTerm implements Serializable {
	private String accession;
	private String label;
	private String ontologyAccession;
	private static final Logger log = Logger.getLogger(OntologyTerm.class);

	public OntologyTerm(String ontologyAccession, String termAccession,
			String label) {
		this.setAccession(termAccession);
		this.setLabel(label);
		this.setOntologyAccession(ontologyAccession);
	}

	public String getAccession() {
		return accession;
	}

	public void setAccession(String accession) {
		this.accession = accession;
	}

	public String getLabel() {
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

	@Override
	public String toString() {
		return String
				.format(
						"OntologyTerm(ontologyAccession=%s, termAccession=%s, label=%s)",
						getOntologyAccession(), getAccession(), getLabel());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getAccession() == null) ? 0 : getAccession().hashCode());
		result = prime * result
				+ ((getLabel() == null) ? 0 : getLabel().hashCode());
		result = prime
				* result
				+ ((getOntologyAccession() == null) ? 0
						: getOntologyAccession()
						.hashCode());
		return result;
	}

	/*
	 * Note using getters, as BioPortal beans are instantiated from XML, and
	 * will not have default properties set.
	 * 
	 * Getters are overridden in BioPortal beans.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OntologyTerm other = (OntologyTerm) obj;
		if (getAccession() == null) {
			if (other.getAccession() != null)
				return false;
		} else if (!getAccession().equals(other.getAccession()))
			return false;
		if (getLabel() == null) {
			if (other.getLabel() != null)
				return false;
		} else if (!getLabel().equals(other.getLabel()))
			return false;
		if (getOntologyAccession() == null) {
			if (other.getOntologyAccession() != null)
				return false;
		} else if (!getOntologyAccession().equals(other.getOntologyAccession()))
			return false;
		return true;
	}

}
