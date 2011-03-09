package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;


/**
 * Example 13
 * 
 * Shows how to instantiate the reasoned file service and build partonomy
 */
public class Example14 {
	private static final Logger log = Logger.getLogger(Example14.class);
	private static ReasonedFileOntologyService os;

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		// Instantiate a ReasonedFileOntologyService
		// Note behind the scenes that uses the HermiT reasoner
		// http://hermit-reasoner.com/
		os = new ReasonedFileOntologyService(new URI(
		"http://www.ebi.ac.uk/efo/efo.owl"), "efo");

		// Get parents of atrial myocardium (EFO_0003087)
		// This is a defined class and it would not work
		// otherwise
		log.info(os.getParents("efo", "EFO_0003087"));

		// Build partonomy - to make it quicker we'll focus
		// on the skeleton structure branch (EFO_0000806)
		// to compute whole partonomy try organism part (EFO_0000635)
		OntologyTerm startNode = os.getTerm("EFO_0000806");

		// But first lets see all the possible relations for this term
		Map<String, Set<OntologyTerm>> result = os.getRelations(startNode);

		for (Entry<String, Set<OntologyTerm>> entry : result.entrySet()) {
			System.out.println("\n\t" + entry.getKey());
			for (OntologyTerm ot : entry.getValue()) {
				System.out.println(ot.getLabel());
			}
		}

		// Proceed with building the partonomy
		// Note that other axes are also possible
		// and we're using a specific getRelationsShortcut()
		// to only compute the has_part relations
		Set<OntologyTerm> branch = os.getAllChildren(startNode);
		branch.add(startNode);
		log.info("Processing partonomy for " + branch.size() + " classes");

		// Visualise recursively
		System.out.println(startNode.getLabel());
		visualise(startNode, "    ");

	}

	/**
	 * Simple recursive visualisation from the top node
	 * 
	 * @param currentNode
	 *            the current node
	 * @param tab
	 *            the tab
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private static void visualise(OntologyTerm currentNode, String tab)
	throws OntologyServiceException {
		String partPadding = "|---has_part--- ";
		String isaPadding = "|-------------- ";
		String newTab = tab
		+ String.format("%1$-" + partPadding.length() + "s", "");

		Set<OntologyTerm> isa_children = new HashSet<OntologyTerm>(
				os.getChildren(currentNode));
		Set<OntologyTerm> part_children = new HashSet<OntologyTerm>(os
				.getRelationsShortcut(currentNode.getOntologyAccession(),
						currentNode.getAccession(), "has_part").get("has_part"));

		// remove has_part children from the asserted isa set
		isa_children.removeAll(part_children);

		for (OntologyTerm term : isa_children) {
			System.out.println(tab + isaPadding + term.getLabel());
			visualise(term, newTab);
		}
		for (OntologyTerm term : part_children) {
			System.out.println(tab + partPadding + term.getLabel());
			visualise(term, newTab);
		}
	}
}