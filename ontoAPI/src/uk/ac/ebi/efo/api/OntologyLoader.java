/**
 * 
 */
package uk.ac.ebi.efo.api;

import java.net.URI;

import org.apache.log4j.Logger;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;

/**
 * Contains loading related methods.
 * 
 * @author $Id: OntologyLoader.java 8454 2009-08-17 07:52:50Z Tomasz $
 */
public final class OntologyLoader {
	private static final Logger _log = Logger.getLogger(OntologyLoader.class.getName());
	private static final OWLOntologyManager _manager = OntologyManagerSingleton.INSTANCE;
	private OWLOntology _ontLoaded;

	/**
	 * @param uriOntology
	 *            ontology to be loaded
	 */
	public OntologyLoader(URI uriOntology) {
		try {
			// Try to remove this ontology first
			// For some reason obo ontologies get to be duplicated
			// hogging memory, though documentation says otherwise
			_manager.removeOntology(uriOntology);
			_ontLoaded = _manager.loadOntologyFromPhysicalURI(uriOntology);
		} catch (OWLOntologyCreationException e) {
			_log.fatal("The ontology could not be created: " + e.getMessage());
			System.exit(1);
		} catch (java.lang.OutOfMemoryError e) {
			_log.error("Try a bigger heap size, e.g. VM arguments -Xms512M -Xmx512M");
			_log.fatal(e);
			System.exit(1);
		}
	}

	/**
	 * @return ontology loaded in the constructor
	 */
	public OWLOntology getOntology() {
		return _ontLoaded;
	}
}
