// Search a local OWL ontology
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
package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.net.URISyntaxException;

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;

/**
 * Example 3
 * 
 * Shows how to search an OWL Ontology
 * 
 */
public class Example3 {
	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// Instantiate a FileOntologyService
		FileOntologyService os = new FileOntologyService(new URI("http://www.ebi.ac.uk/efo"), "EFO");
		// Use a non-SKOS annotation for synonyms
		os.setSynonymSlot("alternative_term");

		// Find all terms containing string thymus
		for (OntologyTerm ot : os.searchAll("thymus")) {
			System.out.println(ot);
		}
	}
}
