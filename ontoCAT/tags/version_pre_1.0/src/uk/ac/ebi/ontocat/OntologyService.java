/**
 * Copyright (c) 2010 - 2011 European Molecular Biology Laboratory and University of Groningen
 *
 * Contact: ontocat-users@lists.sourceforge.net
 * 
 * This file is part of OntoCAT
 * 
 * OntoCAT is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * 
 * OntoCAT is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along
 * with OntoCAT. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.ac.ebi.ontocat;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Ontology service interface, e.g. Bioportal REST or OLS
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 */
public interface OntologyService {
	/**
	 * Enumeration for search options in searchAll and searchOntology. Without
	 * setting any options the search will be the most permissive, i.e. match
	 * substrings and in all properties.
	 * <p>
	 * EXACT - only exact matching of query. EXCLUE_PROPERTIES excludes -
	 * properties from search.
	 */
	public enum SearchOptions {
		EXACT, INCLUDE_PROPERTIES
	};

	/**
	 * Return a term.
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * 
	 * @return OntologyTerm or null if nothing was found
	 * @throws OntologyServiceException
	 */
	public OntologyTerm getTerm(String ontologyAccession, String termAccession)
	throws OntologyServiceException;

	/**
	 * Return a term using only its accession. This will only resolve correctly
	 * if the accession is unique within the service.
	 * 
	 * @param termAccession
	 *            E.g. GO:00001
	 * @return OntologyTerm or null if nothing was found
	 * @throws OntologyServiceException
	 */
	public OntologyTerm getTerm(String termAccession) throws OntologyServiceException;

	/**
	 * Return a path to the root of the ontology. Returns a list of terms,
	 * starting with the root and finishing with the term itself.
	 * 
	 * @param termAccession
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getTermPath(String ontologyAccession, String termAccession)
	throws OntologyServiceException;

	/**
	 * Return a path to the root of the ontology. Returns a list of terms,
	 * starting with the root and finishing with the term itself.
	 * 
	 * @param term
	 * @return List of OntologyTerms that has at least one element
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getTermPath(OntologyTerm term) throws OntologyServiceException;

	/**
	 * Return all available ontology accessions in a service.
	 * 
	 * @return List of Ontologies or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<Ontology> getOntologies() throws OntologyServiceException;

	/**
	 * Return terms matching query within a particular ontology. Without setting
	 * any options the search will be the most permissive, i.e. match substrings
	 * and in all properties.
	 * 
	 * @param ontologyAccession
	 * @param query
	 *            - query to be sent to the OntologyService, usually a keyword
	 *            to be searched for
	 * @param options
	 *            - EXACT - only exact matching of query. EXCLUDE_PROPERTIES
	 *            excludes - properties from search.
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> searchOntology(String ontologyAccession, String query,
			SearchOptions... options) throws OntologyServiceException;

	/**
	 * Return terms matching query. Without setting any options the search will
	 * match substrings but will not include properties.
	 * 
	 * @param query
	 *            - query to be sent to the OntologyService, usually a keyword
	 *            to be searched for
	 * @param options
	 *            - EXACT - only exact matching of query. INCLUDE_PROPERTIES -
	 *            includes properties in search.
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> searchAll(String query, SearchOptions... options)
	throws OntologyServiceException;

	/**
	 * Return the root terms for this ontology
	 * 
	 * @param ontologyAccession
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getRootTerms(String ontologyAccession)
	throws OntologyServiceException;

	/**
	 * Return the root terms for this ontology
	 * 
	 * @param ontology
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getRootTerms(Ontology ontology) throws OntologyServiceException;

	/**
	 * Return child concepts for this termAccession
	 * 
	 * @param termAccession
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getChildren(String ontologyAccession, String termAccession)
	throws OntologyServiceException;

	/**
	 * Return child concepts for this termAccession
	 * 
	 * @param term
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getChildren(OntologyTerm term) throws OntologyServiceException;

	/**
	 * Return parent concepts for this termAccession
	 * 
	 * @param termAccession
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getParents(String ontologyAccession, String termAccession)
	throws OntologyServiceException;

	/**
	 * Return parent concepts for a particular OntologyTerm
	 * 
	 * @param term
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getParents(OntologyTerm term) throws OntologyServiceException;

	/**
	 * Return annotations for this termAccession.
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * @return Map of OntologyTerms or an empty map if nothing was found
	 * @throws OntologyServiceException
	 */
	public Map<String, List<String>> getAnnotations(String ontologyAccession, String termAccession)
	throws OntologyServiceException;

	/**
	 * Return annotations for this termAccession.
	 * 
	 * @param term
	 * @return Map of OntologyTerms or an empty map if nothing was found
	 * @throws OntologyServiceException
	 */
	public Map<String, List<String>> getAnnotations(OntologyTerm term)
	throws OntologyServiceException;

	/**
	 * Return relations for this termAccession.
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * @return Map of OntologyTerms or an empty map if nothing was found
	 * @throws OntologyServiceException
	 */
	public Map<String, Set<OntologyTerm>> getRelations(String ontologyAccession, String termAccession)
	throws OntologyServiceException;

	/**
	 * Return relations for this termAccession.
	 * 
	 * @return Map of OntologyTerms or an empty map if nothing was found
	 * @throws OntologyServiceException
	 */
	public Map<String, Set<OntologyTerm>> getRelations(OntologyTerm term)
	throws OntologyServiceException;

	/**
	 * Generate a hyperlink to drill down to ontology source.
	 * 
	 * @param termAccession
	 * @return lookup hyperlink or null
	 */
	public String makeLookupHyperlink(String termAccession);

	/**
	 * Generate a hyperlink to drill down to ontology source.
	 * 
	 * @param termAccession
	 * @return lookup hyperlink or null
	 */
	public String makeLookupHyperlink(String ontologyAccession, String termAccession);

	/**
	 * Return an ontology from the service.
	 * 
	 * @param ontologyAccession
	 * @return Ontology or null if nothing was found
	 */
	public Ontology getOntology(String ontologyAccession) throws OntologyServiceException;

	/**
	 * Return term's synonyms.
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * @return List of synonyms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<String> getSynonyms(String ontologyAccession, String termAccession)
	throws OntologyServiceException;

	/**
	 * Return term's synonyms.
	 * 
	 * @param term
	 * @return List of synonyms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<String> getSynonyms(OntologyTerm term) throws OntologyServiceException;

	/**
	 * Return term's definitions.
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * @return List of definitions or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<String> getDefinitions(String ontologyAccession, String termAccession)
	throws OntologyServiceException;

	/**
	 * Return term's definitions.
	 * 
	 * @param term
	 * @return List of definitions or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<String> getDefinitions(OntologyTerm term) throws OntologyServiceException;

	/**
	 * Return all the terms in the ontology.
	 * 
	 * @param ontologyAccession
	 *            the ontology accession identifying the ontology in the
	 *            service. Can be null if the underlying service is of
	 *            FileOntologyService type. You can however use the base uri for
	 *            an owl ontology, or http://www.geneontology.org/go# for OBO
	 *            ontologies if you wish. Things get easily complicated here,
	 *            that's why null is also an accepted value.
	 * 
	 * @return A set as we want to collapse duplicate terms
	 * 
	 * @throws OntologyServiceException
	 */
	public Set<OntologyTerm> getAllTerms(String ontologyAccession) throws OntologyServiceException;

	/**
	 * Get all the children terms of a given term.
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * 
	 * @return all the child terms of the given term in is_a hierarchy or an
	 *         empty set if no parents were found
	 */
	public Set<OntologyTerm> getAllChildren(String ontologyAccession, String termAccession)
	throws OntologyServiceException;

	/**
	 * Get all the parent terms of a given term.
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * 
	 * @return all the parent terms of the given term in is_a hierarchy or an
	 *         empty set if no parents were found
	 */
	public Set<OntologyTerm> getAllParents(String ontologyAccession, String termAccession)
	throws OntologyServiceException;

}
