/**
 * 
 */
package uk.ac.ebi.efo.bioportal.xmlbeans;

import plugin.OntologyBrowser.OntologyTerm;

/**
 * @author $Id: SearchBean.java 8993 2009-09-18 17:14:46Z tomasz $
 * 
 */
public class SearchBean implements OntologyTerm {
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

}
