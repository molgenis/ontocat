package uk.ac.ebi.ontocat.virtual;

import java.util.List;
import java.util.Map;

import uk.ac.ebi.ontocat.AbstractOntologyService;
import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

public class CachedOntologyService extends AbstractOntologyService implements
		OntologyService {

	OntologyService os;

	public CachedOntologyService(OntologyService os) {
		this.os = os;
	}

	@Override
	public Map<String, List<String>> getAnnotations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		// check parameters if exist
		// return from cache
		// if not
		return os.getAnnotations(ontologyAccession, termAccession);
	}

	@Override
	public List<OntologyTerm> getChildren(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getDefinitions(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ontology> getOntologies() throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ontology getOntology(String ontologyAccession)
			throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OntologyTerm> getParents(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<String>> getRelations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OntologyTerm> getRootTerms(String ontologyAccession)
			throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getSynonyms(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OntologyTerm getTerm(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OntologyTerm getTerm(String termAccession)
			throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String makeLookupHyperlink(String termAccession) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OntologyTerm> searchAll(String keywords)
			throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OntologyTerm> searchOntology(String ontologyAccession,
			String keywords) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
