// Search only a subtree within an ontology
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

import java.util.List;

import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;

/**
 * Example 8
 * 
 * Shows how to search a subtree of an ontology
 */
public class Example8 {

	public static void main(String[] args) throws OntologyServiceException {
		// Instantiate BioPortal service
		BioportalOntologyService bos = new BioportalOntologyService();

		// Find all terms containing string membrane located under GO:0043227
		List<OntologyTerm> result = bos.searchSubtree("1070", "GO:0043227", "membrane",
				SearchOptions.INCLUDE_PROPERTIES);

		// Warn if empty list
		if (result.size() == 0) {
			System.out.println("Nothing returned!");
		}

		// Print the terms
		for (OntologyTerm ot : result) {
			System.out.println(ot);
		}
	}
}
