/**
 * 
 */
package uk.ac.ebi.ontocat.file;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.OWLEntityURIConverterStrategy;

/**
 * Strategy for fixing broken Object Properties URIs in OWL API 3.2.2
 * 
 * @author Tomasz Adamusiak
 */
public class FixBrokenObjectPropertiesIRIStrategy implements OWLEntityURIConverterStrategy {

	/** Logger */
	private static final Logger log = Logger.getLogger(FixBrokenObjectPropertiesIRIStrategy.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owl.util.OWLEntityURIConverterStrategy#getConvertedURI
	 * (org.semanticweb.owl.model.OWLEntity)
	 */
	@Override
	public IRI getConvertedIRI(OWLEntity ent) {
		IRI convertedIRI = ent.getIRI();
		if (ent.isOWLObjectProperty()) {
			convertedIRI = IRI.create(convertedIRI.toString().replace(
					"http_//purl.org/obo/owl:", ""));
		}

		return convertedIRI;
	}

}
