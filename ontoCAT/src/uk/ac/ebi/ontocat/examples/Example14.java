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
		System.out.println("Parent of defined class atrial myocardium:");
		System.out.println(os.getParents("efo", "EFO_0003087"));

		// Build a tree of relations (including partonomy)
		// To make it quicker we'll focus
		// on the skeleton structure branch (EFO_0000806)
		// to compute a larger tree try organism part (EFO_0000635)
		OntologyTerm startNode = os.getTerm("EFO_0000806");

		// But first lets see all the possible relations for the starting term
		System.out.println("\nDirect relations for term: "
				+ startNode.getLabel() + "...");
		Map<String, Set<OntologyTerm>> result = os.getRelations(startNode);

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
		.println("\nBuilding exhaustive relations tree, estimated size more than "
				+ branch.size() + " classes\n");

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
		String isaPadding = pad("", "-");
		String newTab = pad(tab, " ");

		Set<OntologyTerm> isaChildren = new HashSet<OntologyTerm>(
				os.getChildren(currentNode));
		// Note you could use ReasonedOntologyService.getSpecificRelation
		// to focus only on a specific axis (e.g. has_part)
		Map<String, Set<OntologyTerm>> mOtherChildren = os.getRelations(
				currentNode.getOntologyAccession(), currentNode.getAccession());

		// remove isa children inferred by the reasoner
		// under a structure defined specifically to capture a relationship
		// example skeleton structure, or skeleton disease
		// these will be shown with the original relationship in the next step
		for (Set<OntologyTerm> sOtherChildren : mOtherChildren.values()) {
			isaChildren.removeAll(sOtherChildren);
		}

		// print isa children
		for (OntologyTerm term : isaChildren) {
			System.out.println(tab + isaPadding + term.getLabel());
			visualise(term, newTab);
		}
		// print other children
		for (Entry<String, Set<OntologyTerm>> e : mOtherChildren
				.entrySet()) {
			for (OntologyTerm ot : e.getValue()){
				System.out.println(tab + pad(e.getKey(), "-") + ot.getLabel());
				visualise(ot, newTab);
			}
		}
	}

	public static String pad(String str, String padChar) {
		StringBuilder padded = new StringBuilder(padChar + padChar + str);
		while (padded.length() < 27) {
			padded.append(padChar);
		}
		return padded.toString();
	}
}