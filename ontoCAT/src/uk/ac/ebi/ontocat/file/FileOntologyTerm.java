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

	/** The cls. */
	private final OWLClass cls;
	
	/** The ontology. */
	private final OWLOntology ontology;
	
	/** The slots. */
	private final FieldDescriptor slots;

	private FileOntologyService fos;

	/**
	 * Gets the oWL class.
	 * 
	 * @return the oWL class
	 */
	public OWLClass getOWLClass() {
		return cls;
	}

	/**
	 * Instantiates a new file ontology term.
	 * 
	 * @param owl_cls the owl_cls
	 * @param owl_ontology the owl_ontology
	 * @param fieldDesc the field desc
	 */
	public FileOntologyTerm(FileOntologyService fos, OWLClass owl_cls,
			OWLOntology owl_ontology,
			FieldDescriptor fieldDesc) {
		this.fos = fos;
		cls = owl_cls;
		ontology = owl_ontology;
		slots = fieldDesc;
	}

	/* (non-Javadoc)
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getAccession()
	 */
	@Override
	public String getAccession() {
		return getFragment(cls.getURI());
	}


	/* (non-Javadoc)
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getLabel()
	 */
	@Override
	public String getLabel() throws OntologyServiceException {
		// in theory there can be multiple labels but return first
		return fos.getAnnotations(this).get(slots.labelSlot).get(0);
	}

	/* (non-Javadoc)
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getOntologyAccession()
	 */
	@Override
	public String getOntologyAccession() {
		// ontology.getURI() returns the uri it was loaded from
		// so can't be used here
		Pattern pattern = Pattern.compile("^.*[//#]{1}");
		Matcher matcher = pattern.matcher(cls.getURI().toString());
		if (matcher.find())
			return matcher.group();
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getTermPath()
	 */
//	@Override
//	public List<OntologyTerm> getTermPath() throws OntologyServiceException {
//		ArrayList<OntologyTerm> termPath = new ArrayList<OntologyTerm>();
//		int iteration = 0;
//		// seed the list with this term
//		List<OntologyTerm> parents = this.getParents();
//		OntologyTerm term = this;
//		termPath.add(term);
//		// iterate its first parents recursively
//		while (parents.size() != 0) {
//			term = parents.get(0);
//			termPath.add(term);
//			parents = term.getParents();
//			// safety break for circular relations
//			if (iteration++ > 100) {
//				log.error("getTermPath(): TOO MANY ITERATIONS (" + iteration
//						+ "x)");
//				break;
//			}
//		}
//		// reverse to have root as first
//		Collections.reverse(termPath);
//		return termPath;
//	}
	
	/* (non-Javadoc)
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getChildren()
	 */
//	@Override
//	public List<OntologyTerm> getChildren() throws OntologyServiceException {
//		ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
//		for (OWLDescription desc : cls.getSubClasses(ontology)) {
//			if (!desc.isAnonymous()) {
//				list.add(new FileOntologyTerm(desc.asOWLClass(), ontology,
//						slots));
//			}
//		}
//		return list;
//	}
	
	/* (non-Javadoc)
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getParents()
	 */
//	@Override
//	public List<OntologyTerm> getParents() throws OntologyServiceException {
//		ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
//		for (OWLDescription desc : cls.getSuperClasses(ontology)) {
//			if (!desc.isAnonymous()) {
//				list.add(new FileOntologyTerm(desc.asOWLClass(), ontology,
//						slots));
//			}
//		}
//		return list;
//	}
	
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
