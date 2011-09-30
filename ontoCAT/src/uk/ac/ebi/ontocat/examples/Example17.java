// Work with term paths
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
import java.util.Set;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;

/**
 * Example 17
 * 
 * Shows how to access term paths
 */
public class Example17 {
	public static void main(String[] args) throws OntologyServiceException {
		OntologyService os = new BioportalOntologyService();

		// querying NCBI Taxonomy
		OntologyTerm ot = os.getTerm("1132", "Rattus");
		System.out.println("Found " + ot);

		List<OntologyTerm> path = os.getTermPath("1132", ot.getAccession());
		// term accession is NCBITaxon:10114
		for (OntologyTerm term : path) {
			System.out.println("\t" + term.getLabel());
		}

		// querying CHEBI
		ot = os.getTerm("1007", "hexose");
		System.out.println("\nFound " + ot);
		// term's accession is CHEBI:18133
		path = os.getTermPath("1007", ot.getAccession());

		System.out.println(path);
		for (OntologyTerm term : path) {
			if (term != null) {
				System.out.println("\t" + term.getLabel());
			} else {
				// CHEBI:33243 is not resolvable for this term's path
				// > 0.9.9 versions will throw an exception at getTermPath() 
				System.out.println("An unresolvable term found in path");
			}
		}

		// alternatively to see term's neighbourhood try
		System.out.println("Children are");
		Set<OntologyTerm> sAllChildren = os.getAllChildren(
				ot.getOntologyAccession(), ot.getAccession());
		if (sAllChildren.size() == 0) {
			System.out.println("There are no child terms");
		}
		for (OntologyTerm term : sAllChildren) {
			System.out.println("\t" + term.getLabel());
		}
		
		System.out.println("Parents are");
		Set<OntologyTerm> sAllParents = os.getAllParents(
				ot.getOntologyAccession(), ot.getAccession());
		for (OntologyTerm term : sAllParents) {
			System.out.println("\t" + term.getLabel());
		}
	}
}