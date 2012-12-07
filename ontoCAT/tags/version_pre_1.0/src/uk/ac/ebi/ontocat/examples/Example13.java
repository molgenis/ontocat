// Get functional roots in EFO and build hierarchy
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
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;

/**
 * Example 13
 * 
 * Shows how to get functional roots in EFO and build hierarchy
 */
public class Example13 {
	private static final Logger log = Logger.getLogger(Example13.class);

	public static void main(String[] args) throws Exception {
		// Instantiate a FileOntologyService
		FileOntologyService os = new FileOntologyService(
				new URI(
				"http://efo.svn.sourceforge.net/viewvc/efo/trunk/src/efoinowl/InferredEFOOWLview/EFO_inferred.owl"),
		"EFO");

		// Use a non-SKOS annotation for synonyms
		os.setSynonymSlot("alternative_term");

		// Get functional roots in EFO
		Boolean functionalRootFound = false;
		for (OntologyTerm t : os.getAllTerms("EFO")) {
			if (isFunctionalRoot(os, t)) {
				log.info("Functional root is " + t);
				functionalRootFound = true;
			}
		}
		// Or alternatively just regular roots
		if (functionalRootFound != true) {
			log.info(os.getRootTerms("EFO"));
		}

		// Get hierarchy for a term
		OntologyTerm term = os.getTerm("EFO_0003085");
		// All unsorted parents
		for (OntologyTerm t : os.getAllParents(term)) {
			log.info(t);
		}
		// Paths to root
		log.info(getClassPathToRoot(os, term));
	}

	private static Boolean isFunctionalRoot(OntologyService os, OntologyTerm t)
	throws OntologyServiceException {
		List<String> propBranchClass = os.getAnnotations(t).get("branch_class");
		if (propBranchClass != null && propBranchClass.size() > 0
				&& propBranchClass.get(0).equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static Stack<Stack<OntologyTerm>> getClassPathToRoot(OntologyService os,
			OntologyTerm term) throws Exception {
		Stack<Stack<OntologyTerm>> tempStack = new Stack<Stack<OntologyTerm>>();
		Stack<Stack<OntologyTerm>> resultsStack = new Stack<Stack<OntologyTerm>>();

		// Seed the queue with first element
		Stack<OntologyTerm> seed = new Stack<OntologyTerm>();
		seed.add(term);
		tempStack.push(seed);

		do {
			// Pop some path from stack
			Stack<OntologyTerm> path = tempStack.pop();
			if (path.size() > 50) {
				throw new Exception("Circular path encountered in " + term);
			}
			// Go through all the parents on top of the stack
			List<OntologyTerm> parents = os.getParents(path.peek());
			if (parents.size() != 0) {
				for (OntologyTerm parent : parents) {
					// Stop at functional roots
					if (isFunctionalRoot(os, parent)) {
						path.push(parent);
						path.trimToSize();
						resultsStack.push(path);
					} else {
						// Create new path for every parent and add to tempStack
						Stack<OntologyTerm> newPath = (Stack<OntologyTerm>) path.clone();
						newPath.push(parent);
						tempStack.push(newPath);
					}
				}
			}
			// Push the path back if reached root
			else {
				path.trimToSize();
				resultsStack.push(path);
			}
		} while (!tempStack.empty());

		resultsStack.trimToSize();
		return resultsStack;
	}
}