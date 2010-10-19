/**
 * 
 */
package uk.ac.ebi.ontocat.file;

import java.net.URI;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

// TODO: Auto-generated Javadoc
/**
 * Contains loading related methods.
 * 
 * @author $Id: OntologyLoader.java 8454 2009-08-17 07:52:50Z Tomasz $
 */
public final class OntologyLoader {

	/** The Constant log. */
	private static final Logger log = Logger.getLogger(OntologyLoader.class.getName());

	/** The Constant manager. */
	private static final OWLOntologyManager manager = OntologyManagerSingleton.INSTANCE;

	/** The ontology to be loaded. */
	private OWLOntology ontology;

	/**
	 * The Constructor.
	 * 
	 * @param uriOntology
	 *            ontology to be loaded
	 */
	public OntologyLoader(URI uriOntology) {
		try {
			// Manager has to be synchronised otherwise parser will
			// complain: Parsers should load imported ontologies using
			// the makeImportLoadRequest method.
			synchronized (manager) {
				// Try to remove this ontology first
				// For some reason obo ontologies are duplicated
				// hogging memory, though documentation says otherwise
				// ----------
				// have not tested it in OWL API 3
				// possibly not needed anymore
				// also cannot remove ontology by URI anymore
				// manager.removeOntology(uriOntology);
				IRI iri = IRI.create(uriOntology);
				ontology = manager.loadOntologyFromOntologyDocument(iri);
			}
		} catch (OWLOntologyCreationException e) {
			log.fatal("The ontology could not be created: " + e.getMessage());
			System.exit(1);
		} catch (java.lang.OutOfMemoryError e) {
			log.error("Try a bigger heap size, e.g. VM arguments -Xms512M -Xmx512M");
			log.fatal(e);
			System.exit(1);
		}
	}

	/**
	 * Gets the ontology.
	 * 
	 * @return ontology loaded in the constructor
	 */
	public OWLOntology getOntology() {
		return ontology;
	}
}
