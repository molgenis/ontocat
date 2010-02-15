/**
 * 
 */
package uk.ac.ebi.ontocat.file;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLOntologyManager;

/**
 * Employs singleton pattern to serve a static reference to OWLOntologyManager.
 * This fixes some issues with out of heap memory exceptions.
 * 
 * @author $Id: OntologyManagerSingleton.java 8273 2009-07-29 17:10:50Z tomasz $
 */
public class OntologyManagerSingleton {
	
	/** Instance of the OntologyManager to be shared among all the classes in the package. */
	public final static OWLOntologyManager INSTANCE = OWLManager.createOWLOntologyManager();

	/**
	 * Instantiates a new ontology manager singleton.
	 */
	protected OntologyManagerSingleton() {
		// Exists only to defeat instantiation.
	}
}
