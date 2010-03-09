/*
 * 
 */
package uk.ac.ebi.ontocat.file;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLOntology;

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

// TODO: Auto-generated Javadoc
// OWLClass decorator
/**
 * The Class FileOntologyTerm.
 */
public final class FileOntologyTerm extends OntologyTerm {
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger(FileOntologyTerm.class
			.getName());

	/**
	 * Instantiates a new file ontology term.
	 * 
	 * @param owl_cls
	 *            the owl_cls
	 * @param owl_ontology
	 *            the owl_ontology
	 * @param fieldDesc
	 *            the field desc
	 * @throws OntologyServiceException
	 */
	public FileOntologyTerm(FileOntologyService fos, OWLClass cls,
			OWLOntology ontology, FieldDescriptor slots)
			throws OntologyServiceException {
		// termAccession
		setAccession(getFragment(cls.getURI()));
		// termLabel
		setLabel(fos.getAnnotations(this).get(slots.labelSlot).get(0));
		// ontologyAccession
		Pattern pattern = Pattern.compile("^.*[//#]{1}");
		Matcher matcher = pattern.matcher(cls.getURI().toString());
		if (matcher.find())
			setOntologyAccession(matcher.group());
	}
	
	/**
	 * Gets the fragment.
	 * 
	 * @param uri
	 *            the uri
	 * 
	 * @return the fragment
	 */
	private String getFragment(URI uri)
	{
		// can't use URI.getFragment() as it's not always delimited with # (see
		// EFO)
		Pattern pattern = Pattern.compile("[^//#]*$");
		Matcher matcher = pattern.matcher(uri.toString());
		if (matcher.find())
			return matcher.group();
		return null;
	}
}
