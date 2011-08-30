// List all ontologies available through OLS
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

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;

/**
 * Example 1
 * 
 * Shows how to list all ontologies available through OLS
 * 
 */
public class Example1 {
	public static void main(String[] args) throws OntologyServiceException {
		// Instantiate OLS service
		OntologyService os = new OlsOntologyService();

		// List all available ontologies
		for (Ontology o : os.getOntologies()) {
			System.out.println(o);
		}

		// Find all terms containing string thymus
		for (OntologyTerm ot : os.searchAll("thymus", SearchOptions.EXACT,
				SearchOptions.INCLUDE_PROPERTIES)) {
			System.out.println(ot);
		}
	}
}
