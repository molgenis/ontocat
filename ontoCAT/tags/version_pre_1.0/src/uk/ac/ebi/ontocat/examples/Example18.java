// Work with namespaces in local ontologies
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

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;

/**
 * Example 18
 * 
 * The tricky namespaces
 */
public class Example18 {
	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// OWL API arbitrary assigns one to OBO ontologies as they are not supported 
		// natively by OBO format 
		// OWL ontologies can have multiple namespaces if they import terms directly (e.g. EFO). 
		// In practice anything that comes up in the file minus URI fragment can be used as 
		// ontology accession, but it is good practice to specify one yourself in alternative
		// constructor, see below:

		// Instantiate a FileOntologyService(URI, ontologyAccession)
		FileOntologyService os = new FileOntologyService(new URI("http://www.co-ode.org/ontologies/pizza/2007/02/12/pizza.owl"), 
				"http://pizza/namespace/");
	
		// Try to find a term
		List<OntologyTerm> list = os.searchAll("Cajun");

		System.out.println(list.get(0).getOntologyAccession());
		// http://www.co-ode.org/ontologies/pizza/pizza.owl#

	    System.out.println(os.getOntologies().get(0).getOntologyAccession());
	    // http://www.co-ode.org/ontologies/pizza/pizza.owl

	    // Those work
	   	System.out.println("\nThose work:");
	    System.out.println(os.getAllTerms("http://www.co-ode.org/ontologies/pizza/pizza.owl#").size());
	    System.out.println(os.getAllTerms("http://www.co-ode.org/ontologies/pizza/pizza.owl").size());
	    System.out.println(os.getAllTerms("http://pizza/namespace/").size());
	    
	    System.out.println(os.getRootTerms("http://www.co-ode.org/ontologies/pizza/pizza.owl#").size());
	    System.out.println(os.getRootTerms("http://www.co-ode.org/ontologies/pizza/pizza.owl").size());
	    System.out.println(os.getRootTerms("http://pizza/namespace/").size());
	    
	    // Those don't
	    System.out.println("\nThose don't:");
	    System.out.println(os.getAllTerms("some/random/namespace").size());
	    System.out.println(os.getRootTerms("some/random/namespace").size());
	}
}