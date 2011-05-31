/**
 * Copyright (c) 2010 - 2011 European Molecular Biology Laboratory and University of Groningen
 *
 * Contact: ontocat-users@lists.sourceforge.net
 * 
 * This file is part of OntoCAT
 * 
 * OntoCAT is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * 
 * OntoCAT is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along
 * with OntoCAT. If not, see <http://www.gnu.org/licenses/>.
 */
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

	/**
	 * No-arg constructor for JAXB
	 */
	public Ontology()
	{

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

	public String getDateReleased() {
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

	public String getSynonymSlot() {
		return synonymSlot;
	}

	public void setSynonymSlot(String synonymSlot) {
		this.synonymSlot = synonymSlot;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	@Override
	public String toString() {
		return String.format(
				"Ontology(abbreviation=%s, label=%s, ontologyAccession=%s)",
				getAbbreviation(), getLabel(), getOntologyAccession());
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
		+ ((getLabel() == null) ? 0 : getLabel().hashCode());
		result = prime
		* result
		+ ((getOntologyAccession() == null) ? 0
				: getOntologyAccession()
				.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Ontology other = (Ontology) obj;
		if (getLabel() == null) {
			if (other.getLabel() != null) {
				return false;
			}
		} else if (!getLabel().equals(other.getLabel())) {
			return false;
		}
		if (getOntologyAccession() == null) {
			if (other.getOntologyAccession() != null) {
				return false;
			}
		} else if (!getOntologyAccession().equals(other.getOntologyAccession())) {
			return false;
		}
		return true;
	}

}
