/**
 * 
 */
package uk.ac.ebi.ontocat.file;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

// TODO: Auto-generated Javadoc
/**
 * The Class ReasonedFileOntologyService.
 * 
 * @author Tomasz Adamusiak
 */
public class ReasonedFileOntologyService extends FileOntologyService {

	/** The reasoner. */
	private OWLReasoner reasoner;

	/** The cache inverse property. */
	private Map<OWLObjectProperty, OWLObjectProperty> cacheInverseProperty = new HashMap<OWLObjectProperty, OWLObjectProperty>();

	/** The cache property label. */
	private Map<OWLObjectProperty, String> cachePropertyLabel = new HashMap<OWLObjectProperty, String>();

	/** The cache property by label. */
	private Map<String, OWLObjectProperty> cachePropertyByLabel = new HashMap<String, OWLObjectProperty>();

	/** The factory. */
	private OWLDataFactory factory;

	/** The Constant log. */
	private static final Logger log = Logger
	.getLogger(ReasonedFileOntologyService.class);

	/**
	 * Instantiates a new reasoned file ontology service.
	 * 
	 * @param uriOntology
	 *            the uri ontology
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	public ReasonedFileOntologyService(URI uriOntology)
	throws OntologyServiceException {
		super(uriOntology);
		instantiateReasoner(uriOntology);
	}

	/**
	 * Instantiates a new reasoned file ontology service.
	 * 
	 * @param uriOntology
	 *            the uri ontology
	 * @param ontologyAccession
	 *            the ontology accession
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	public ReasonedFileOntologyService(URI uriOntology, String ontologyAccession)
	throws OntologyServiceException {
		super(uriOntology, ontologyAccession);
		instantiateReasoner(uriOntology);
	}

	/**
	 * Instantiate reasoner.
	 * 
	 * @param uriOntology
	 *            the uri ontology
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private void instantiateReasoner(URI uriOntology)
	throws OntologyServiceException {
		// Create a reasoner factory.
		OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();

		// Create a console progress monitor. This will print the reasoner
		// progress out to the console.
		// ConsoleProgressMonitor progressMonitor = new
		// ConsoleProgressMonitor();
		// OWLReasonerConfiguration config = new SimpleConfiguration(
		// progressMonitor);

		// Create a HermiT OWLReasoner
		reasoner = reasonerFactory.createReasoner(ontology);

		// Ask the reasoner to do all the necessary work
		reasoner.precomputeInferences();

		// Throw an exception if the ontology is inconsistent
		if (!reasoner.isConsistent()) {
			throw new OntologyServiceException(
					"Inconsistent ontology according to HermiT reasoner - "
					+ uriOntology.toString());
		}
		log.info("Classified the ontology " + uriOntology.toString());

		// Cache the properties for relations
		factory = loader.getManager().getOWLDataFactory();
		for (OWLObjectProperty prop : ontology
				.getObjectPropertiesInSignature(false)) {
			OWLObjectProperty inverse = findInverseObjectProperty(prop);
			cacheInverseProperty.put(prop, inverse);
			cachePropertyLabel.put(prop, getLabel(prop));
			cachePropertyByLabel.put(getLabel(prop), prop);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontocat.file.FileOntologyService#getRelations(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Map<String, Set<OntologyTerm>> getRelations(
			String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		// this is only works for classes now (not individuals)
		OWLEntity ent = getOwlEntity(termAccession);
		if (!ent.isOWLClass()) {
			return Collections.emptyMap();
		}

		// initialise
		Map<String, Set<OntologyTerm>> result = new HashMap<String, Set<OntologyTerm>>();

		// iterate through properties
		for (OWLObjectProperty prop : cacheInverseProperty.keySet()) {

			Set<OntologyTerm> relatedTerms = getSpecificRelation(
					ent.asOWLClass(), prop);
			if (relatedTerms != null) { // null if no inverse found
				result.put(cachePropertyLabel.get(prop), relatedTerms);
			}
		}

		return result;
	}

	/**
	 * Gets the specific relation for a term. Works by asking the reasoner for
	 * children of an anonymous class defined as inverse(ObjectProperty) some
	 * classInQuestion
	 * 
	 * @param clsQueried
	 *            the cls queried
	 * @param prop
	 *            the prop
	 * @return the specific relation
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private Set<OntologyTerm> getSpecificRelation(OWLClass clsQueried,
			OWLObjectProperty prop) throws OntologyServiceException {
		OWLObjectProperty inverse = cacheInverseProperty.get(prop);
		// this really only works for inversable properties
		// so ignore other
		if (inverse == null) {
			return null;
		}

		// create class expression
		OWLClassExpression relationSomeObject = factory
		.getOWLObjectSomeValuesFrom(inverse, clsQueried);
		// and find all subclasses that fulfil it
		Set<OWLClass> sRelatedClasses = new HashSet<OWLClass>();
		sRelatedClasses.addAll(reasoner.getSubClasses(relationSomeObject, true)
				.getFlattened());

		// filter out anonymous classes
		Set<OntologyTerm> sFilteredResult = new HashSet<OntologyTerm>();
		for (OWLClass cls : sRelatedClasses) {
			if (cls.isBuiltIn()) {
				continue;
			}
			sFilteredResult.add(getTerm(cls));
		}

		return sFilteredResult;
	}

	/**
	 * Calls the previous method, but identifies the property by its name rather
	 * then OWLObjectProperty
	 * 
	 * @param ontologyAccession
	 *            the ontology accession
	 * @param termAccession
	 *            the term accession
	 * @param relation
	 *            the relation
	 * @return the specific relation
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	public Set<OntologyTerm> getSpecificRelation(String ontologyAccession,
			String termAccession, String relation)
			throws OntologyServiceException {
		// this is only works for classes now (not individuals)
		OWLEntity ent = getOwlEntity(termAccession);
		if (!ent.isOWLClass()) {
			return Collections.emptySet();
		}

		// find the relation
		OWLObjectProperty prop = cachePropertyByLabel.get(relation);
		if (prop == null) { // no such relation found
			return Collections.emptySet();
		}

		Set<OntologyTerm> relatedTerms = getSpecificRelation(ent.asOWLClass(),
				prop);
		if (relatedTerms != null) { // null if no inverse found
			return relatedTerms;
		}

		return Collections.emptySet();
	}

	/**
	 * Find inverse object property. This could potentially be a set, but most
	 * of the time only a single element one, so just return the first.
	 * 
	 * @param prop
	 *            the property to find inverse of
	 * @return the inverse of the object property passed in as parameter
	 */
	private OWLObjectProperty findInverseObjectProperty(OWLObjectProperty prop) {
		Set<OWLObjectPropertyExpression> inverse = reasoner
		.getInverseObjectProperties(prop).getEntities();
		for (OWLObjectPropertyExpression pe : inverse) {
			if (pe.isAnonymous()) {
				continue;
			}
			// only need the first one
			return pe.asOWLObjectProperty();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontocat.file.FileOntologyService#getChildren(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getChildren(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession)) {
			return Collections.emptyList();
		}
		ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
		OWLEntity ent = getOwlEntity(termAccession);
		if (ent == null) {
			return Collections.emptyList();
		}
		if (ent.isOWLClass()) {
			for (OWLClass cls : reasoner.getSubClasses(ent.asOWLClass(), true)
					.getFlattened()) {
				if (cls.isBuiltIn()) {
					continue;
				}
				list.add(getTerm(cls));
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontocat.file.FileOntologyService#getParents(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getParents(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession)) {
			return Collections.emptyList();
		}
		ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
		OWLEntity ent = getOwlEntity(termAccession);
		if (ent == null) {
			return Collections.emptyList();
		}
		if (ent.isOWLClass()) {
			for (OWLClass cls : reasoner
					.getSuperClasses(ent.asOWLClass(), true).getFlattened()) {
				if (cls.isBuiltIn()) {
					continue;
				}
				list.add(getTerm(cls));
			}
		}
		if (ent.isOWLNamedIndividual()) {
			for (OWLClass cls : reasoner.getTypes(ent.asOWLNamedIndividual(),
					true).getFlattened()) {
				list.add(getTerm(cls));
			}
		}
		return list;
	}
}
