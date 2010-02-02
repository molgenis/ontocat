/**
 * 
 */
package uk.ac.ebi.ontoapi.bioportal.xmlbeans;

import java.util.List;
import java.util.Map;

import uk.ac.ebi.ontoapi.OntologyServiceException;
import uk.ac.ebi.ontoapi.OntologyTerm;
import uk.ac.ebi.ontoapi.bioportal.BioportalOntologyService;

/**
 *c @author $Id: SearchBean.java 8993 2009-09-18 17:14:46Z tomasz $
 * 
 */
public class SearchBean implements OntologyTerm {
	//handle to the service
	private BioportalOntologyService bioportal;
	
	private String ontologyVersionId;
	private String ontologyId;
	private String ontologyDisplayLabel;
	private String recordType;
	@SuppressWarnings("unused")
	private String conceptId;
	private String conceptIdShort;
	private String preferredName;
	private String contents;

	/**
	 * @return the ontologyVersionId
	 */
	final public String getOntologyVersionId() {
		return ontologyVersionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyTerm#getAccession()
	 */
	public String getAccession() {
		return conceptIdShort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyTerm#getLabel()
	 */
	public String getLabel() {
		return preferredName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * plugin.OntologyBrowser.OntologyTerm#getOntologyAccession()
	 */
	public String getOntologyAccession() {
		return ontologyId;
	}

	/**
	 * @return the ontologyDisplayLabel
	 */
	final public String getOntologyDisplayLabel() {
		return ontologyDisplayLabel;
	}

	/**
	 * @return the recordType
	 */
	final public String getRecordType() {
		return recordType;
	}

	/**
	 * @return the contents
	 */
	final public String getContents() {
		return contents;
	}

	@Override
	public String[] getDefinitions() throws OntologyServiceException
	{
		throw new OntologyServiceException("getDefinitions not implemented");
	}

	@Override
	public Map<String, String[]> getAnnotations() throws OntologyServiceException
	{
		return bioportal.getAnnotations(this.getOntologyAccession(), this.getAccession());
	}

	@Override
	public String[] getSynonyms() throws OntologyServiceException
	{
		return bioportal.getSynonyms(this.getOntologyAccession(), this.getAccession());
	}

	@Override
	public List<OntologyTerm> getChildren() throws OntologyServiceException
	{
		return bioportal.getChildren(this.getOntologyAccession(), this.getAccession());
	}

	@Override
	public List<OntologyTerm> getParents() throws OntologyServiceException
	{
		return bioportal.getParents(this.getOntologyAccession(), this.getAccession());
	}

	@Override
	public Map<String, String[]> getRelations() throws OntologyServiceException
	{
		return bioportal.getRelations(this.getOntologyAccession(), this.getAccession());
	}

	@Override
	public List<OntologyTerm> getTermPath() throws OntologyServiceException
	{
		return bioportal.getTermPath(this.getOntologyAccession(), this.getAccession());
	}

}
