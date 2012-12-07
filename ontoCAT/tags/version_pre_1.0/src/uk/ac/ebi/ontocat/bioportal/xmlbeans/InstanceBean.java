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

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyTerm;

/**
 * Wraps the Concept representation of BioPortal and maps it to the OntologyTerm
 * interface.
 * 
 * @author Tomasz Adamusiak
 */
@SuppressWarnings("unused")
public class InstanceBean extends OntologyTerm {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(InstanceBean.class.getName());

	/** The label. */
	private String label;

	/** The id. */
	private String id;

	/** The full id. */
	private String fullId;

	// <relations/>
	// <instanceType>
	// <list>
	// <classBean>
	// <id>owl:SomeValuesFromRestriction</id>
	// <fullId>http://www.w3.org/2002/07/owl#SomeValuesFromRestriction</fullId>
	// <label>owl:SomeValuesFromRestriction</label>
	// <type>Class</type>
	// <relations/>
	// </classBean>
	// </list>
	// </instanceType>



	/** The relations. */
	private EntryBean[] relations;

	/**
	 * Gets the full id.
	 * 
	 * @return the fullid
	 */
	public String getFullId() {
		return fullId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getAccession()
	 */
	@Override
	public String getAccession() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getLabel()
	 */
	@Override
	public String getLabel() {
		return this.label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getURI()
	 */
	@Override
	public URI getURI() {
		return URI.create(this.fullId);
	}

}
