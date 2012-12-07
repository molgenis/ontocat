// Obtain hierarchy information for a term in an ontology
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
import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;

/**
 * Example 11
 * 
 * Shows how to obtain hierarchy information for a term in an ontology
 */
public class Example12 {
	private static final Logger log = Logger.getLogger(Example12.class);

	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// Instantiate a FileOntologyService
		FileOntologyService os = new FileOntologyService(new URI("http://www.ebi.ac.uk/efo"), "EFO");
		// Use a non-SKOS annotation for synonyms
		os.setSynonymSlot("alternative_term");

		String annotation = "thymus";

		// Try to match a term
		// Exact match, but include properties
		List<OntologyTerm> list = os.searchAll(annotation, SearchOptions.EXACT,
				SearchOptions.INCLUDE_PROPERTIES);

		if (list.size() > 1) {
			log.warn("Multiple terms (" + list.size() + ") matched <" + annotation + ">");
		}

		if (list.size() == 0) {
			log.warn("<" + annotation + "> could not be matched");
		}
		{
			// Lets get the first one
			OntologyTerm term = list.get(0);
			log.info("Matched <" + annotation + "> to " + term);
			// And walk through parents
			log.info("List immediate parents");
			for (OntologyTerm t : os.getParents(term)) {
				log.info("\t" + t.getLabel());
			}
			log.info("List all parents");
			for (OntologyTerm t : os.getAllParents(term)) {
				log.info("\t" + t.getLabel());
			}
		}
	}
}