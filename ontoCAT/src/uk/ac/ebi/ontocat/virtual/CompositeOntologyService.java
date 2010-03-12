package uk.ac.ebi.ontocat.virtual;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.ontocat.AbstractOntologyService;
import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

public class CompositeOntologyService extends AbstractOntologyService {

	private List<OntologyService> ontoServices;

	public CompositeOntologyService(List<OntologyService> ontoServices) {
		this.ontoServices = ontoServices;
	}

	// /////////////////////////////////////
	// pattern for Map<String, List<String>
	// /////////////////////////////////////
	@Override
	public Map<String, List<String>> getAnnotations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			Map<String, List<String>> result = os.getAnnotations(
					ontologyAccession, termAccession);
			if (result.size() != 0)
				return result;
		}
		return null;
	}

	@Override
	public Map<String, List<String>> getRelations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		throw new UnsupportedOperationException(
				"Not implemented. Only works for OLS.");
	}

	// /////////////////////////////////////
	// pattern for List<String>
	// /////////////////////////////////////

	@Override
	public List<String> getDefinitions(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<String> result = os.getDefinitions(ontologyAccession,
					termAccession);
			if (result != null && result.size() != 0)
				return result;
		}
		return null;
	}

	@Override
	public List<String> getSynonyms(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<String> result = os.getSynonyms(ontologyAccession,
					termAccession);
			if (result != null && result.size() != 0)
				return result;
		}
		return null;
	}

	// /////////////////////////////////////
	// pattern for List<OntologyTerm>
	// /////////////////////////////////////
	@Override
	public List<OntologyTerm> getChildren(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<OntologyTerm> result = os.getChildren(ontologyAccession,
					termAccession);
			if (result.size() != 0)
				return result;
		}
		return null;
	}

	@Override
	public List<OntologyTerm> getParents(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<OntologyTerm> result = os.getParents(ontologyAccession,
					termAccession);
			if (result.size() != 0)
				return result;
		}
		return null;
	}

	@Override
	public List<OntologyTerm> getRootTerms(String ontologyAccession)
			throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<OntologyTerm> result = os.getRootTerms(ontologyAccession);
			if (result.size() != 0)
				return result;
		}
		return null;
	}

	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<OntologyTerm> result = os.getTermPath(ontologyAccession,
					termAccession);
			if (result.size() != 0)
				return result;
		}
		return null;
	}

	@Override
	public List<OntologyTerm> searchOntology(String ontologyAccession,
			String keywords) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<OntologyTerm> result = os.searchOntology(ontologyAccession,
					keywords);
			if (result.size() != 0)
				return result;
		}
		return null;
	}

	// /////////////////////////////////////
	// pattern for combining results
	// /////////////////////////////////////

	@Override
	public List<Ontology> getOntologies() throws OntologyServiceException {
		List<Ontology> result = new ArrayList<Ontology>();
		for (OntologyService os : ontoServices) {
			result.addAll(os.getOntologies());
		}
		return result;
	}

	@Override
	public List<OntologyTerm> searchAll(String keywords)
			throws OntologyServiceException {
		List<OntologyTerm> result = new ArrayList<OntologyTerm>();
		for (OntologyService os : ontoServices) {
			result.addAll(os.searchAll(keywords));
		}
		return result;
	}

	// /////////////////////////////////////
	// All other patterns
	// /////////////////////////////////////

	// TODO: This is not working properly, as OLS instantiates an
	// empty ontology here for any parameter
	@Override
	public Ontology getOntology(String ontologyAccession)
			throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			Ontology result = os.getOntology(ontologyAccession);
			if (result != null)
				return result;
		}
		return null;
	}

	@Override
	public OntologyTerm getTerm(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			OntologyTerm result = os.getTerm(ontologyAccession, termAccession);
			if (result != null)
				return result;
		}
		return null;
	}

	@Override
	public String makeLookupHyperlink(String termAccession) {
		for (OntologyService os : ontoServices) {
			String result = os.makeLookupHyperlink(termAccession);
			if (result != null)
				return result;
		}
		return null;
	}

	@Override
	public String makeLookupHyperlink(String ontologyAccession,
			String termAccession) {
		for (OntologyService os : ontoServices) {
			String result = os.makeLookupHyperlink(ontologyAccession,
					termAccession);
			if (result != null)
				return result;
		}
		return null;
	}

	@Override
	public OntologyTerm getTerm(String termAccession)
			throws OntologyServiceException {
	for (OntologyService os : ontoServices) {
		OntologyTerm result = os.getTerm( termAccession);
		if (result != null)
			return result;
	}
	return null;
	}



}
