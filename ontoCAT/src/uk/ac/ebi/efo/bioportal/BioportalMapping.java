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
package uk.ac.ebi.efo.bioportal;

import java.util.regex.Pattern;

import uk.ac.ebi.ontocat.OntologyIdMapping;

// TODO: Auto-generated Javadoc
/**
 * The Class BioportalMapping.
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 */
public class BioportalMapping extends OntologyIdMapping {

	/**
	 * Instantiates a new bioportal mapping.
	 * 
	 */
	public BioportalMapping(String sMatchingPattern, String sExtractPattern,
			String externalOntologyAccession, String sTestingCode) {
		super();
		pConfirmMatch = Pattern.compile(sMatchingPattern);
		pExtractID = Pattern.compile(sExtractPattern);
		this.externalOntologyAccession = externalOntologyAccession;
		testingCode = sTestingCode;
	}

	/**
	 * Instantiates a new bioportal mapping. Default extract pattern to match
	 * only numbers.
	 * 
	 */
	public BioportalMapping(String sMatchingPattern,
			String externalOntologyAccession, String sTestingCode) {
		super();
		pConfirmMatch = Pattern.compile(sMatchingPattern);
		pExtractID = Pattern.compile(": *(\\d+)");
		this.externalOntologyAccession = externalOntologyAccession;
		testingCode = sTestingCode;
	}

}
