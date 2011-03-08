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
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

/**
 * @author Tomasz Adamusiak
 * 
 */
public class ReasonedFileOntologyService extends FileOntologyService {

	private OWLReasoner reasoner;

	/** The Constant log. */
	private static final Logger log = Logger
			.getLogger(ReasonedFileOntologyService.class);

	/**
	 * @param uriOntology
	 * @throws OntologyServiceException
	 * 
	 */
	public ReasonedFileOntologyService(URI uriOntology)
			throws OntologyServiceException {
		super(uriOntology);
		instantiateReasoner(uriOntology);
	}

	public ReasonedFileOntologyService(URI uriOntology, String ontologyAccession)
			throws OntologyServiceException {
		super(uriOntology, ontologyAccession);
		instantiateReasoner(uriOntology);
	}

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
		if (!reasoner.isConsistent())
			throw new OntologyServiceException(
					"Inconsistent ontology according to HermiT reasoner - "
							+ uriOntology.toString());
		log.info("Classified the ontology " + uriOntology.toString());
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
		return getRelationsShortcut(ontologyAccession, termAccession, null);
	}

	/**
	 * Gets the relations, there's a shortcut that can limit the returned
	 * relations to a single type
	 * 
	 * @param ontologyAccession
	 *            the ontology accession
	 * @param termAccession
	 *            the term accession
	 * @param relation
	 *            the relation to limit the results to or null otherwise
	 * @return relations map for a particular term
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	public Map<String, Set<OntologyTerm>> getRelationsShortcut(
			String ontologyAccession, String termAccession, String relation)
			throws OntologyServiceException {
		// initialise
		Map<String, Set<OntologyTerm>> result = new HashMap<String, Set<OntologyTerm>>();
		OWLDataFactory factory = loader.getManager().getOWLDataFactory();
		Set<OWLObjectProperty> properties = ontology
				.getObjectPropertiesInSignature(false);
		OWLEntity ent = getOwlEntity(termAccession);
		// this is only works for all classes now (not individuals)
		if (!ent.isOWLClass())
			return Collections.emptyMap();

		// iterate through properties
		for (OWLObjectProperty prop : properties) {
			OWLObjectProperty inverse = findInverseObjectProperty(prop);
			// this really only works for inversable properties
			// so ignore other
			if (inverse == null)
				continue;

			// shortcut to return only a particular relation
			if (relation != null
					&& !getLabel(inverse).equalsIgnoreCase(relation))
				continue;

			// create class expression
			OWLClassExpression relationSomeObject = factory
					.getOWLObjectSomeValuesFrom(prop, ent.asOWLClass());
			// and find all subclasses that fulfill it
			Set<OWLClass> sRelatedClasses = new HashSet<OWLClass>();
			sRelatedClasses.addAll(reasoner.getSubClasses(relationSomeObject,
					true).getFlattened());

			// filter out anonymous classes
			Set<OntologyTerm> sFilteredResult = new HashSet<OntologyTerm>();
			for (OWLClass cls : sRelatedClasses) {
				if (cls.isBuiltIn())
					continue;
				sFilteredResult.add(getTerm(cls));
			}

			// inverse property provides a meaningful label
			result.put(getLabel(inverse), sFilteredResult);
		}

		return result;
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
			if (pe.isAnonymous())
				continue;
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
		if (!ontoAccessions.contains(ontologyAccession))
			return Collections.emptyList();
		ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
		OWLEntity ent = getOwlEntity(termAccession);
		if (ent == null)
			return Collections.emptyList();
		if (ent.isOWLClass()) {
			for (OWLClass cls : reasoner.getSubClasses(ent.asOWLClass(), true)
					.getFlattened()) {
				if (cls.isBuiltIn())
					continue;
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
		if (!ontoAccessions.contains(ontologyAccession))
			return Collections.emptyList();
		ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
		OWLEntity ent = getOwlEntity(termAccession);
		if (ent == null)
			return Collections.emptyList();
		if (ent.isOWLClass()) {
			for (OWLClass cls : reasoner
					.getSuperClasses(ent.asOWLClass(), true).getFlattened()) {
				if (cls.isBuiltIn())
					continue;
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
