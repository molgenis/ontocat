/**
 * 
 */
package uk.ac.ebi.ontocat.file;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
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

		// We'll now create an instance of an OWLReasoner (the implementation
		// being provided by HermiT as we're using the HermiT reasoner factory).
		// The are two categories of
		// reasoner, Buffering and NonBuffering. In our case, we'll create the
		// buffering reasoner, which
		// is the default kind of reasoner. We'll also attach a progress monitor
		// to the reasoner. To do this we
		// set up a configuration that knows about a progress monitor.

		// Create a console progress monitor. This will print the reasoner
		// progress out to the console.
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		// Specify the progress monitor via a configuration. We could also
		// specify other setup parameters in the configuration,
		// and different reasoners may accept their own
		// defined parameters this way.
		OWLReasonerConfiguration config = new SimpleConfiguration(
				progressMonitor);
		// Create a reasoner that will reason over our ontology and its imports
		// closure. Pass in the configuration.
		reasoner = reasonerFactory.createReasoner(ontology, config);

		// Ask the reasoner to do all the necessary work now
		reasoner.precomputeInferences();

		// Throw an exception if the ontology is inconsistent
		if (!reasoner.isConsistent())
			throw new OntologyServiceException(
					"Inconsistent ontology according to HermiT reasoner - "
							+ uriOntology.toString());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontocat.file.FileOntologyService#getRelations(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Map<String, List<String>> getRelations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return super.getRelations(ontologyAccession, termAccession);
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
			for (OWLClass cls : reasoner.getSubClasses(ent.asOWLClass(),
					true).getFlattened()) {
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
			for (OWLClass cls : reasoner.getSuperClasses(ent.asOWLClass(),
					true).getFlattened()) {
				list.add(getTerm(cls));
			}
		}
		if (ent.isOWLNamedIndividual()) {
			for (OWLClass cls : reasoner.getTypes(
					ent.asOWLNamedIndividual(), true).getFlattened()) {
				list.add(getTerm(cls));
			}
		}
		return list;
	}
}
