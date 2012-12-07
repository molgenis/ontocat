// Add caching to ontology browsing
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

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.virtual.CachedServiceDecorator;

/**
 * Example 5
 * 
 * Shows how to add cache functionality to an ontology service
 * 
 */
public class Example5 {
	public static void main(String[] args) throws OntologyServiceException {
		long t1, t2;
		// Instantiate an ontology service
		OntologyService osBP = new BioportalOntologyService();
		// Add a caching layer on top of it
		OntologyService os = CachedServiceDecorator.getService(osBP);

		// First search so no results in cache yet
		// NOTE: cache persists to disk
		t1 = System.nanoTime();
		os.searchAll("thymus");
		t2 = System.nanoTime();
		System.out.println("First search took " + ((t2 - t1) * 1e-6) + " ms");

		// Results of a now cached query should be quicker
		t1 = System.nanoTime();
		os.searchAll("thymus");
		t2 = System.nanoTime();
		System.out.println("Second search took " + ((t2 - t1) * 1e-6) + " ms");
	}

}
