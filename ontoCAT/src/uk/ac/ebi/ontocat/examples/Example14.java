package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;

/**
 * Example 14
 * 
 * Shows how to get all terms from an ontology under a specific parent term
 * TODO: need to fix this example
 */
public class Example14 {
	private static final Logger log = Logger.getLogger(Example14.class);

	public static void main(String[] args) throws Exception {
		// Instantiate an OntologyService
		FileOntologyService os = new FileOntologyService(
				new URI(
						"http://efo.svn.sourceforge.net/viewvc/efo/trunk/src/efoinowl/InferredEFOOWLview/EFO_inferred.owl"),
				"EFO");

		// Get all terms for C. elegans gross anatomy
		log.info("Fetching terms");
		Set<OntologyTerm> terms = os.getAllTerms("EFO");
		Set<OntologyTerm> diseaseChildren = new HashSet<OntologyTerm>();

		// Filter out anything that is not a child of cell
		OntologyTerm parent = os.getTerm("EFO_0000408");
		Map<Integer, Integer> parentsSize = new HashMap<Integer, Integer>();
		for (OntologyTerm t : terms) {
			Set<OntologyTerm> Allparents = os.getAllParents(t.getOntologyAccession(), t
					.getAccession());
			List<OntologyTerm> parents = os.getParents(t.getOntologyAccession(), t.getAccession());
			if (Allparents.contains(parent) && t.getAccession().contains("DO")) {
				diseaseChildren.add(t);
				int key = parents.size();
				parentsSize.put(key, parentsSize.containsKey(key) ? parentsSize.get(key) + 1 : 1);
			}
		}
		log
				.info("There are " + diseaseChildren.size() + " disease children out of "
 + terms.size()
				+ " total number of classes in the ontology");
		for (Entry<Integer, Integer> e : parentsSize.entrySet()) {
			log.info(e.getKey() + " parents => " + e.getValue() + " terms");
		}
	}

}