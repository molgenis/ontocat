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
package uk.ac.ebi.ontocat.bioportal.xmlbeans;

import java.net.URI;

import uk.ac.ebi.ontocat.OntologyTerm;

/**
 * @author Tomasz Adamusiak
 * 
 */
@SuppressWarnings("unused")
public class SearchBean extends OntologyTerm {
	private static final long serialVersionUID = -6977336688985745354L;

	private String ontologyVersionId;
	private String ontologyId;
	private String ontologyDisplayLabel;
	private String recordType;
	private String conceptId;
	private String conceptIdShort;
	private String preferredName;
	private String contents;
	private String objectType;
	private String isObsolete;

	/**
	 * @return the ontologyVersionId
	 */
	public String getOntologyVersionId() {
		return ontologyVersionId;
	}

	/**
	 * @return the ontologyDisplayLabel
	 */
	public String getOntologyDisplayLabel() {
		return ontologyDisplayLabel;
	}

	/**
	 * @return the recordType
	 */
	public String getRecordType() {
		return recordType;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getAccession()
	 */
	@Override
	public String getAccession() {
		return conceptIdShort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getLabel()
	 */
	@Override
	public String getLabel() {
		return preferredName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getOntologyAccession()
	 */
	@Override
	public String getOntologyAccession() {
		return ontologyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#setAccession(java.lang.String)
	 */
	@Override
	public void setAccession(String accession) {
		conceptIdShort = accession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#setLabel(java.lang.String)
	 */
	@Override
	public void setLabel(String label) {
		preferredName = label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontocat.OntologyTerm#setOntologyAccession(java.lang.String)
	 */
	@Override
	public void setOntologyAccession(String ontologyAccession) {
		ontologyId = ontologyAccession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getURI()
	 */
	@Override
	public URI getURI() {
		return URI.create(this.conceptId);
	}

}
