// Sort and rank searches within ontology
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

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.CompositeServiceNoThreads;
import uk.ac.ebi.ontocat.virtual.SortedSubsetDecorator;

/**
 * Example 4
 * 
 * Shows how to sort and rank searches
 * 
 */
public class Example4 {
	public static void main(String[] args) throws OntologyServiceException {
		// Instantiate an ontology service across BioPortal and OLS
		OntologyService composite = CompositeServiceNoThreads.getService(new OlsOntologyService(),
				new BioportalOntologyService());

		// Create a list of ontologies to be included in the query
		// HP and MP only
		List<String> subset = new ArrayList<String>();
		subset.add("HP");
		subset.add("MP");
		subset.add("1136"); // EFO in BioPortal

		// Add a filtering layer to the ontology service
		OntologyService os = SortedSubsetDecorator.getService(composite, subset);

		// Test that results are only returned form EFO, HP and MP
		for (OntologyTerm ot : os.searchAll("thymus")) {
			System.out.println(ot);
			System.out.println("\tsimilarity to query: " + ot.getContext().getSimilarityScore()
					+ "%");
			System.out.println("\tterm source: " + ot.getContext().getServiceType());
		}
		// But should be none for CHEBI
		if (os.searchOntology("CHEBI", "nicotine") == null) {
			System.out.println("No results found for CHEBI");
		}
	}

}
