/**
 * 
 */
package uk.ac.ebi.efo.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLEntityAnnotationAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyAnnotationAxiom;
import org.semanticweb.owl.model.OWLOntologyChangeException;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.model.OWLTypedConstant;
import org.semanticweb.owl.model.RemoveAxiom;

/**
 * Collection of methods providing extra functionality in manipulating
 * ontologies.
 * 
 * @author $Id: WrapperOntology.java 9019 2009-09-22 12:39:01Z tomasz $
 */
public class WrapperOntology {
	private static final Logger _log = Logger.getLogger(WrapperOntology.class.getName());
	private static final OWLOntologyManager _manager = OntologyManagerSingleton.INSTANCE;
	private static final OWLDataFactory _factory = _manager.getOWLDataFactory();
	private final OWLOntology _ontology;

	public WrapperOntology(OWLOntology value) throws AnnotationFragmentNotFoundException {
		_ontology = value;
	}

	/**
	 * Adds a new annotation to a class in this ontology. Due to the internal
	 * implementation of annotations collections as sets it is impossible to
	 * duplicate an annotation in an ontology.
	 * 
	 * @param cls
	 *            class to add the new annotation
	 * @param uriAnnot
	 *            uri of the new annotation
	 * @param annotNew
	 *            annotation to be added
	 * @throws OWLOntologyChangeException
	 * @see #addEditor
	 * @see BioportalImporter
	 */
	public void addAnnotation(OWLClass cls, URI uriAnnot, String text) throws OWLOntologyChangeException {
		// Fix badly loaded NCIt annotation
		text = text.replace("&amp;#39;", "'");
		text = text.replace("&#39;", "'");
		// Create the axiom to be added, also first get a type string constant
		OWLTypedConstant anno = _factory.getOWLTypedConstant(text);
		OWLEntityAnnotationAxiom axiom = _factory.getOWLEntityAnnotationAxiom(cls, uriAnnot, anno);
		// Link axiom to ontology
		AddAxiom addAxiom = new AddAxiom(_ontology, axiom);
		_manager.applyChange(addAxiom);
	}

	public void addCommentAnnotation(String text) throws OWLOntologyChangeException {
		// Create the axiom to be added, also first get a type string constant
		OWLOntologyAnnotationAxiom axiom = _factory.getOWLOntologyAnnotationAxiom(_ontology, _factory
				.getCommentAnnotation(text));
		// Link axiom to ontology
		AddAxiom addAxiom = new AddAxiom(_ontology, axiom);
		_manager.applyChange(addAxiom);
	}

	@SuppressWarnings("unchecked")
	public void removeAllAnnotationsBySource(OWLClass cls, URI uriAnnot, String Source, ProgressCounter counter)
			throws OWLOntologyChangeException {
		for (OWLAnnotation annot : cls.getAnnotations(_ontology, uriAnnot)) {
			if (new WrapperAnnotation(annot).getSource().endsWith(Source)) {
				// _log.debug("Removing " + annot);
				removeAnnotation(cls, annot);
				counter.incrementUpdated();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public String getClassLabel(OWLClass cls) {
		return new WrapperAnnotation(getClassLabelAnnotation(cls)).getValue();
	}

	public OWLAnnotation getClassLabelAnnotation(OWLClass cls) {
		try {
			TreeSet<OWLAnnotation> labelSet = new TreeSet<OWLAnnotation>(cls.getAnnotations(_ontology, new URI(
					"http://www.w3.org/2000/01/rdf-schema#label")));
			if (labelSet.size() > 1)
				_log.warn("Multiple labels on " + cls);
			return labelSet.first();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public void removeDuplicateAnnotations(OWLClass cls, URI uriAnnot, ProgressCounter counter)
			throws OWLOntologyChangeException {
		Map<String, String> map = new HashMap();
		map.put(new WrapperAnnotation(getClassLabelAnnotation(cls)).getComparableValue(), "LABEL");

		for (OWLAnnotation annot : cls.getAnnotations(_ontology, uriAnnot)) {
			String comparableValue = new WrapperAnnotation(annot).getComparableValue();
			if (map.containsKey(comparableValue)) {
				if (map.get(comparableValue) != null && map.get(comparableValue).equals("LABEL")) {
					_log.debug("DEL DUPE SAME AS LABEL " + annot + " - hash value: " + comparableValue);
				} else
					_log.debug("DEL DUPE " + annot + " - hash value: " + comparableValue);
				removeAnnotation(cls, annot);
				counter.incrementUpdated();
			}
			// do not remove obsolete annotations from obsoleted classes
			if (!getClassLabel(cls).contains("obsolete")
					&& (comparableValue.contains("obsolete") || comparableValue.contains("retired"))) {
				_log.warn("DEL RETIRED|OBSOLETE " + annot);
				removeAnnotation(cls, annot);
				counter.incrementUpdated();
			}
			map.put(comparableValue, null);
		}
	}

	public void removeAnnotation(OWLClass cls, OWLAnnotation annot) throws OWLOntologyChangeException {
		OWLEntityAnnotationAxiom axiom = _factory.getOWLEntityAnnotationAxiom(cls, annot.getAnnotationURI(), annot
				.getAnnotationValueAsConstant());
		removeAxiom(axiom);
	}

	public void removeAxiom(OWLAxiom ax) throws OWLOntologyChangeException {
		RemoveAxiom remov = new RemoveAxiom(_ontology, ax);
		_manager.applyChange(remov);
	}

	/**
	 * Same as {@link #addAnnotation}, only the annotation is the
	 * definition_editor and contains
	 * "OntologyMappingImporter following strExtra"
	 * 
	 * @param cls
	 *            class to be annotated
	 * @param strExtra
	 *            this will be the name of the source ontology
	 * @throws OWLOntologyChangeException
	 */
	public void addEditor(OWLClass cls) throws OWLOntologyChangeException {
		// Create the axiom to be added, also first get a type string constant
		OWLTypedConstant anno = _factory.getOWLTypedConstant("BioportalImporter v.1.1");
		URI uri = null;
		try {
			// TODO possibly unnecessary calculated here every time anew
			uri = getUriFromFrag("definition_editor");
		} catch (AnnotationFragmentNotFoundException e) {
			_log.fatal("Could not find definition_editor in the supplied ontology to infer its uri automatically");
			System.exit(1);
			e.printStackTrace();
		}
		OWLEntityAnnotationAxiom axiom = _factory.getOWLEntityAnnotationAxiom(cls, uri, anno);
		// Link axiom to ontology
		AddAxiom addAxiom = new AddAxiom(_ontology, axiom);

		_manager.applyChange(addAxiom);
	}

	public boolean removeEditor(OWLClass cls, String strAnnot) throws OWLOntologyChangeException,
			AnnotationFragmentNotFoundException {
		for (OWLAnnotation annot : cls.getAnnotations(_ontology, getUriFromFrag("definition_editor"))) {
			if (new WrapperAnnotation(annot).getValue().startsWith(strAnnot)) {
				removeAnnotation(cls, annot);
				return true;
			}
		}
		return false;
	}

	/**
	 * Thrown when the given URI fragment wasn't found in any of the annotations
	 */
	public class AnnotationFragmentNotFoundException extends Exception {
		private static final long serialVersionUID = 1659429351877052302L;
	}

	/**
	 * Walks the ontology until it finds an annotation of a given fragment in
	 * its URI. It then returns this URI. So basically what we're looking for is
	 * ontology URI + specific delimiter (# or /) plus the fragment itself
	 * 
	 * @param frag
	 *            uri fragment of the annotation
	 * @return complete uri of the annotation inferred from a fragment
	 * @throws AnnotationFragmentNotFoundException
	 * @see BioportalImporter#OntologyImporter(URI, URI, URI, String, String,
	 *      String)
	 */
	@SuppressWarnings("unchecked")
	public URI getUriFromFrag(String frag) throws AnnotationFragmentNotFoundException {
		// pattern to match the fragment at the end of the uri behind a slash or
		// hash
		Pattern pattern = Pattern.compile("[//#]{1}" + frag + "$");

		for (URI uri : _ontology.getAnnotationURIs()) {
			Matcher matcher = pattern.matcher(uri.toString());
			if (matcher.find()) {
				return uri;
			}
		}
		throw new AnnotationFragmentNotFoundException();
	}

	/**
	 * Walks the ontology until it finds a class uri of a given class id in its
	 * URI. It then returns this URI. So basically what we're looking for is
	 * ontology URI + specific delimiter (# or /) plus the class id
	 * 
	 * @param frag
	 *            class id fragment of the annotation
	 * @return complete uri of the class of a given class id
	 * @throws AnnotationFragmentNotFoundException
	 * @see BioportalImporter#OntologyImporter(URI, URI, URI, String, String,
	 *      String)
	 */
	@SuppressWarnings("unchecked")
	public URI getUriFromClassID(String clsId) throws AnnotationFragmentNotFoundException {
		// pattern to match the fragment at the end of the uri behind a slash or
		// hash
		return getClassForID(clsId).getURI();
	}

	/**
	 * Returns paths to root.
	 * 
	 * @throws AnnotationFragmentNotFoundException
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Stack<Stack<OWLClass>> getClassPathToRoot(String clsID) throws AnnotationFragmentNotFoundException {
		Stack<Stack<OWLClass>> tempStack = new Stack<Stack<OWLClass>>();
		Stack<Stack<OWLClass>> resultsStack = new Stack<Stack<OWLClass>>();

		// Seed the que with first element
		Stack<OWLClass> seed = new Stack<OWLClass>();
		seed.add(getClassForID(clsID));
		tempStack.push(seed);

		do {
			// Pop some path from stack
			Stack<OWLClass> path = tempStack.pop();
			// Go through all the parents on top of the stack
			Set<OWLDescription> parents = path.peek().getSuperClasses(_ontology);
			if (parents.size() != 0) {
				for (OWLDescription cls : parents) {
					if (cls.isAnonymous() == true)
						continue;
					// Create new path for every parent and add to tempStack
					Stack<OWLClass> newPath = (Stack<OWLClass>) path.clone();
					newPath.push(cls.asOWLClass());
					tempStack.push(newPath);
				}
			}
			// Push the path back if reached root
			else {
				resultsStack.push(path);
			}
		} while (!tempStack.empty());

		return resultsStack;
	}

	public OWLClass getClassForID(String clsID) throws AnnotationFragmentNotFoundException {
		// pattern to match the fragment at the end of the uri behind a slash or
		// hash
		Pattern pattern = Pattern.compile("[//#]{1}" + clsID + "$");

		for (OWLClass cls : _ontology.getReferencedClasses()) {
			Matcher matcher = pattern.matcher(cls.getURI().toString());
			if (matcher.find()) {
				return cls;
			}
		}

		throw new AnnotationFragmentNotFoundException();
	}
}