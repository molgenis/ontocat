/*
 * 
 */
package uk.ac.ebi.ontoapi.file;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLOntology;

import uk.ac.ebi.ontoapi.Ontology;
import uk.ac.ebi.ontoapi.OntologyService;
import uk.ac.ebi.ontoapi.OntologyServiceException;
import uk.ac.ebi.ontoapi.OntologyTerm;

// TODO: Auto-generated Javadoc
/**
 * The Class FileOntologyService. This works on both OBO and OWL files.
 */
public final class FileOntologyService implements OntologyService {

	/** The ontology. */
	private final OWLOntology ontology;

	/** The slots. */
	private final FieldDescriptor slots;

	/**
	 * Instantiates a new file ontology service.
	 * 
	 * @param uriOntology
	 *            where to load the ontology from, can be local file or URL
	 * @param fdesc
	 *            FieldDescriptor describing which annotations hold synonyms,
	 *            definitions and labels
	 */
	public FileOntologyService(URI uriOntology, FieldDescriptor fdesc) {
		ontology = new OntologyLoader(uriOntology).getOntology();
		slots = fdesc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#getAnnotations(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Map<String, String[]> getAnnotations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return getTerm(termAccession).getAnnotations();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#getChildren(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getChildren(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return getTerm(termAccession).getChildren();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#getOntologies()
	 */
	@Override
	public List<Ontology> getOntologies() throws OntologyServiceException {
		throw new UnsupportedOperationException(
				"Not implemented. Only one ontology available");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#getOntology(java.lang.String)
	 */
	@Override
	public Ontology getOntology(String ontologyAccession)
			throws OntologyServiceException {
		throw new UnsupportedOperationException("Not implemented.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#getParents(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getParents(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return getTerm(termAccession).getParents();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#getRelations(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Map<String, String[]> getRelations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		throw new UnsupportedOperationException("Not implemented.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#getRootTerms(java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getRootTerms(String ontologyAccession)
			throws OntologyServiceException {
		List<OntologyTerm> rootTerms = new ArrayList<OntologyTerm>();
		for (OWLClass cls : ontology.getReferencedClasses()){
			// class without parents, looks like root
			// TODO: add reasoner, otherwise fails for defined classes
			// TODO: test if reasoner works with OBO ontologies
			if (cls.getSuperClasses(ontology).size() == 0) {
				rootTerms.add(new FileOntologyTerm(cls, ontology, slots));
			}
		}
		return rootTerms;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#getSynonyms(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String[] getSynonyms(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		return getTerm(termAccession).getSynonyms();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#getTerm(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public OntologyTerm getTerm(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		return getTerm(termAccession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#getTerm(java.lang.String)
	 */
	@Override
	public OntologyTerm getTerm(String termAccession)
			throws OntologyServiceException {
		// TODO: implement as a lookup table, memory issues?
		for (OWLClass cls : ontology.getReferencedClasses()) {
			if (cls.getURI().toString().endsWith(termAccession)) {
				return new FileOntologyTerm(cls, ontology, slots);
			}
		}
		throw new OntologyServiceException(termAccession
				+ " was not found in the ontology");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#getTermPath(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return getTerm(termAccession).getTermPath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontoapi.OntologyService#makeLookupHyperlink(java.lang.String)
	 */
	@Override
	public String makeLookupHyperlink(String termAccession) {
		try {
			return getTerm(termAccession) + termAccession;
		} catch (OntologyServiceException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#searchAll(java.lang.String)
	 */
	@Override
	public List<OntologyTerm> searchAll(String keyword)
			throws OntologyServiceException {

		HashSet<OntologyTerm> terms = new HashSet<OntologyTerm>();
		// iterate through all classes annotations
		// TODO: lucene index?
		for (OWLClass cls : ontology.getReferencedClasses()){
			for (OWLAnnotation annot : cls.getAnnotations(ontology)) {
				if (annot.getAnnotationValueAsConstant().getLiteral().contains(
						keyword))
					terms.add(new FileOntologyTerm(cls, ontology, slots));
			}
		}

		return new ArrayList<OntologyTerm>(terms);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#searchOntology(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> searchOntology(String ontologyAccession,
			String keyword) throws OntologyServiceException {
		return searchAll(keyword);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontoapi.OntologyService#getDefinitions(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String[] getDefinitions(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return getTerm(termAccession).getDefinitions();
	}

}
