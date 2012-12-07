// Xref FMAIDs in HPO to FMA ontology and retrieve all the children and parent terms
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

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.virtual.CompositeDecorator;

// TODO: Auto-generated Javadoc
/**
 * Example 9
 * 
 * Shows how to xref FMAIDs in HPO to FMA ontology and retrieve all the children
 * and parent terms.
 */
public class Example9 {

	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// These need to point to ontologies either locally or on the web
		URI uriHPO = new File("human-phenotype-ontology_v1208.obo").toURI();
		URI uriFMA = new File("fma2_obo.obo").toURI();
		// Load FMA
		FileOntologyService osFMA = new FileOntologyService(uriFMA, "FMA");

		// Load HPO
		FileOntologyService osHPO = new FileOntologyService(uriHPO, "HPO");

		// Single entry point
		OntologyService os = CompositeDecorator.getService(osFMA, osHPO);
		// Load all HPO terms into a set
		Set<OntologyTerm> termsHPO = os.getAllTerms("HPO");
		System.out.println("Loaded " + termsHPO.size() + " HPO terms");

		// Regex to extract FMA xrefs from HPO definitions
		Pattern p = Pattern.compile("(FMA:\\d+)");
		Matcher m = p.matcher("");

		// Iterate through HPO terms
		for (OntologyTerm termHPO : termsHPO) {
			// Iterate through definitions
			for (String def : os.getDefinitions(termHPO)) {
				m.reset(def);
				// For each FMA xref found
				while (m.find()) {
					// note: replacing : with _ in accessions
					// as OWLAPI changes them on loading
					String idFMA = m.group().replace(":", "_");
					// Fetch the full term from FMA
					OntologyTerm termFMA = os.getTerm(idFMA);
					// Check if null as some terms may not be
					// resolvable due to versioning, etc.
					if (termFMA != null) {
						System.out.println("Found " + termFMA + " in " + termHPO);
						System.out.println("\tFetching children...");
						System.out.println(osFMA.getAllChildren(termFMA));
						System.out.println("\tFetching parents...");
						System.out.println(osFMA.getAllParents(termFMA));
					} else {
						System.out.println("ERROR could not resolve " + idFMA);
					}

				}
			}
		}
	}

}
