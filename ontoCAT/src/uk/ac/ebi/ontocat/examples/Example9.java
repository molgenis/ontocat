/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;

// TODO: Auto-generated Javadoc
/**
 * Example 9
 * 
 * Shows how to xref FMAIDs in HPO to FMA ontology and retrieve all the children
 * and parent terms.
 */
public class Example9 {

	@SuppressWarnings("serial")
	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// These need to point to ontologies either locally or on the web
		URI uriHPO = new File("human-phenotype-ontology_v1208.obo").toURI();
		URI uriFMA = new File("fma2_obo.obo").toURI();
		// Load FMA
		FileOntologyService osFMA = new FileOntologyService(uriFMA);

		// Load HPO
		FileOntologyService osHPO = new FileOntologyService(uriHPO);

		// Load all HPO terms into a set
		Set<OntologyTerm> termsHPO = osHPO.getAllTerms(null);
		System.out.println("Loaded " + termsHPO.size() + " HPO terms");

		// Regex to extract FMA xrefs from HPO definitions
		Pattern p = Pattern.compile("(FMA:\\d+)");
		Matcher m = p.matcher("");

		// Iterate through HPO terms
		for (OntologyTerm termHPO : termsHPO) {
			// Iterate through definitions
			for (String def : osHPO.getDefinitions(termHPO)) {
				m.reset(def);
				// For each FMA xref found
				while (m.find()) {
					// note: replacing : with _ in accessions
					// as OWLAPI changes them on loading
					String idFMA = m.group().replace(":", "_");
					// Fetch the full term from FMA
					OntologyTerm termFMA = osFMA.getTerm(idFMA);
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
