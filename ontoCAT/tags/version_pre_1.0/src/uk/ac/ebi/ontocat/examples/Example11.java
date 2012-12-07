// Obtain UMLS CUIs for ontology terms through BioPortal
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

import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.virtual.CachedServiceDecorator;

/**
 * Example 11
 * 
 * Shows how to obtain UMLS CUIs for ontology terms through BioPortal
 */
public class Example11 {

	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// Instantiate BioPortal service with caching
		OntologyService os = CachedServiceDecorator.getService(new BioportalOntologyService());

		// Load all terms from an external ontology
		System.out.println("Processing " + os.getOntology("1116").getLabel());
		Set<OntologyTerm> terms = os.getAllTerms("1116");
		System.out.println("Loaded " + terms.size() + " classes");

		// And find their UMLS CUIs via NCI Thesaurus
		for (OntologyTerm term : terms) {
			String label = term.getLabel();
			System.out.println("Searching for " + term);
			List<OntologyTerm> searchResult = os.searchOntology("1032", label);
			// multiple results
			if (searchResult.size() > 1) {
				System.out.println("\tMultiple matches found");
			}
			// no results
			if (searchResult.size() == 0) {
				System.out.println("\tNo match found");
				// ok lets choose the first term from the result list
				// NOTE: this is not necessarily the most appropriate match
				// more advanced ontology matching tools are available
				// from http://www.ebi.ac.uk/efo/tools
			} else {
				OntologyTerm tNCI = searchResult.get(0);
				List<String> cuis = os.getAnnotations(tNCI).get("UMLS_CUI");
				if (cuis != null) {
					// there can be multiple CUIs on a term
					for (String cui : cuis)
					{
						System.out.println("\t" + cui + " " + tNCI.getLabel());
						// or none at all
					}
				} else {
					System.out.println("\tNo CUI on " + tNCI.getLabel());
				}
			}
		}
	}
}