/**
 * 
 */
package uk.ac.ebi.efo.api;

import java.net.URI;

import org.apache.log4j.Logger;
import org.semanticweb.owl.io.RDFXMLOntologyFormat;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.model.OWLOntologyStorageException;

/**
 * Contains saving related methods.
 * 
 * @author $Id: OntologySaver.java 8273 2009-07-29 17:10:50Z tomasz $
 */
public final class OntologySaver {
	private static final Logger _log = Logger.getLogger(OntologySaver.class.getName());
	private static final OWLOntologyManager _manager = OntologyManagerSingleton.INSTANCE;
	private final OWLOntology _ontologyToSave;

	/**
	 * @param value
	 *            ontology to be saved
	 */
	public OntologySaver(OWLOntology value) {
		_ontologyToSave = value;
	}

	/**
	 * Saves the ontology provided in the constructor
	 * 
	 * @param uri
	 *            path the ontology is to be saved to
	 */
	public void saveTo(URI uri) {
		try {
			_log.debug("Saving " + _ontologyToSave.getURI().toString() + " to " + uri);
			_manager.saveOntology(_ontologyToSave, new RDFXMLOntologyFormat(), uri);
		} catch (OWLOntologyStorageException e) {
			_log.fatal("The ontology could not be saved: " + e.getMessage());
			System.exit(1);
		}
	}
}