// Get all the terms from an ontology
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
import java.util.Set;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.file.FileOntologyService;

//TODO: Auto-generated Javadoc
/**
 * Example 10
 * 
 * Shows how to load all the terms from an ontology
 */
public class Example10 {

	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// Create a reference to the ontology
		// Use new File().toURI() for local files
		URI uri = new URI("http://www.ebi.ac.uk/efo/efo.owl");

		// Load the ontology and set the accession for it
		OntologyService os = new FileOntologyService(uri, "EFO");

		// Load all classes into a set
		// Note: you must specify the ontologyAccession unless it's
		// FileOntologyService
		Set<OntologyTerm> terms = os.getAllTerms("EFO");

		// Iterate through all the terms
		for (OntologyTerm term : terms) {
			System.out.println(term.getAccession() + "\t" + term.getLabel());
		}

		System.out.println("Loaded " + terms.size() + " classes");

		// Alternatively get all terms through BioPortal
		os = new BioportalOntologyService();
		terms = os.getAllTerms("1136");
		System.out.println("Loaded " + terms.size() + " classes");

	}
}