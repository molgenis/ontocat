package uk.ac.ebi.ontocat;

import java.util.List;
import java.util.Map;

/**
 * Helper class for all methods that take OntologyTerm.
 * @author Morris Swertz
 *
 */
public abstract class AbstractOntologyService implements OntologyService
{

	@Override
	public Map<String, List<String>> getAnnotations(OntologyTerm term) throws OntologyServiceException
	{
		return getAnnotations(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getChildren(OntologyTerm term) throws OntologyServiceException
	{
		return getChildren(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<String> getDefinitions(OntologyTerm term) throws OntologyServiceException
	{
		return getDefinitions(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getParents(OntologyTerm term) throws OntologyServiceException
	{
		return getParents(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public Map<String, List<String>> getRelations(OntologyTerm term) throws OntologyServiceException
	{
		return getRelations(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getRootTerms(Ontology ontology) throws OntologyServiceException
	{
		return getRootTerms(ontology.getOntologyAccession());
	}

	@Override
	public List<String> getSynonyms(OntologyTerm term) throws OntologyServiceException
	{
		return getSynonyms(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getTermPath(OntologyTerm term) throws OntologyServiceException
	{
		return getTermPath(term.getOntologyAccession(), term.getAccession());
	}
}
