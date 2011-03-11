package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;

/**
 * Example 13
 * 
 * Shows how to instantiate the reasoned file service and build partonomy
 */
public class Example14 {
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
		System.out.println("Parents of a defined class atrial myocardium:");
		System.out.println(os.getParents("efo", "EFO_0003087"));

		// Build a tree of relations (including partonomy)
		// To make it quicker we'll focus
		// on the skeleton structure branch (EFO_0000806)
		// to compute a larger tree try organism part (EFO_0000635)
		OntologyTerm startNode = os.getTerm("EFO_0000806");

		// But first lets see all the possible relations for the starting term
		Map<String, Set<OntologyTerm>> result = os.getRelations(startNode);

		System.out
		.println("Direct relations for term: " + startNode.getLabel());
		for (Entry<String, Set<OntologyTerm>> entry : result.entrySet()) {
			System.out.println("\n\t" + entry.getKey());
			for (OntologyTerm ot : entry.getValue()) {
				System.out.println(ot.getLabel());
			}
		}

		// Proceed with building the tree
		// Note you can also focus on a specific relation (e.g. has_part)
		// and it would be quicker
		Set<OntologyTerm> branch = os.getAllChildren(startNode);
		branch.add(startNode);
		System.out
		.println("Building exhaustive tree, estimated size more than "
				+ branch.size() + " classes");

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
		String partPadding = addPadding("has_part", "-");
		String isaPadding = addPadding("", "-");
		String newTab = addPadding(tab, "");

		Set<OntologyTerm> isa_children = new HashSet<OntologyTerm>(
				os.getChildren(currentNode));
		Set<OntologyTerm> part_children = new HashSet<OntologyTerm>(os
				.getSpecificRelation(currentNode.getOntologyAccession(),
						currentNode.getAccession(), "has_part"));

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

	private static String addPadding(String s, String string) {
		return string + string
		+ String.format("%1$-" + (20 - s.length()) + "s", string) + s
		+ string + string;
	}
}