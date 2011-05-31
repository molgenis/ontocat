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
package uk.ac.ebi.ontocat.file;

import java.net.URI;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.ebi.ontocat.OntologyServiceException;

/**
 * Contains loading related methods.
 * 
 * @author Tomasz Adamusiak
 */
public final class OntologyLoader {

	/** The Constant log. */
	private static final Logger log = Logger.getLogger(OntologyLoader.class
			.getName());

	/** The Constant manager. */
	// NOTE: this does no longer use the singleton, as it would complain
	// on reloading the ontology
	// The ontology could not be created: Ontology already exists.
	// <http://www.ebi.ac.uk/efo/efo.owl>
	// private static final OWLOntologyManager manager =
	// OntologyManagerSingleton.INSTANCE;
	private OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

	/**
	 * @return the manager
	 */
	public OWLOntologyManager getManager() {
		return manager;
	}

	/** The ontology to be loaded. */
	private OWLOntology ontology;

	/**
	 * The Constructor.
	 * 
	 * @param uriOntology
	 *            ontology to be loaded
	 * @throws OntologyServiceException
	 */
	public OntologyLoader(URI uriOntology) throws OntologyServiceException {
		try {
			// Manager has to be synchronised otherwise parser will
			// complain: Parsers should load imported ontologies using
			// the makeImportLoadRequest method.
			synchronized (manager) {
				// Very important otherwise RDFXMLParser
				// fails with SAXParseException: The parser has encountered more
				// / than "64,000" entity expansions
				System.setProperty("entityExpansionLimit", "100000000");
				IRI iri = IRI.create(uriOntology);
				try {
					ontology = manager.loadOntologyFromOntologyDocument(iri);
				} catch (OWLOntologyCreationException e) {
					log.warn("Ontology could not be created. Waiting 30s and retrying once. " + uriOntology.toString() + e.getMessage());
					Thread.sleep(30000);
					ontology = manager.loadOntologyFromOntologyDocument(iri);
				}

			}
		} catch (InterruptedException e) {
			throw new OntologyServiceException(e);
		} catch (OWLOntologyCreationException e) {
			throw new OntologyServiceException(
					"Ontology could not be created for " + uriOntology.toString() + e.getMessage());
		} catch (java.lang.OutOfMemoryError e) {
			log.fatal("Ran out of memory. Try a bigger heap size, e.g. VM arguments -Xms512M -Xmx512M");
			throw new OntologyServiceException(e.getMessage());
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
