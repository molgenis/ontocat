/**
 * 
 */
package uk.ac.ebi.ontocat.bioportal.xmlbeans;

import uk.ac.ebi.ontocat.OntologyTerm;

/**
 *c @author $Id: SearchBean.java 8993 2009-09-18 17:14:46Z tomasz $
 * 
 */
public class SearchBean extends OntologyTerm {
	private static final long serialVersionUID = -6977336688985745354L;

	private String ontologyVersionId;
	private String ontologyId;
	private String ontologyDisplayLabel;
	private String recordType;
	@SuppressWarnings("unused")
	private String conceptId;
	private String conceptIdShort;
	private String preferredName;
	private String contents;

	public SearchBean(String ontologyAccession, String termAccession,
			String label) {
		super(ontologyAccession, termAccession, label);
	}

	/**
	 * @return the ontologyVersionId
	 */
	public String getOntologyVersionId() {
		return ontologyVersionId;
	}

	/**
	 * @return the ontologyDisplayLabel
	 */
	public String getOntologyDisplayLabel() {
		return ontologyDisplayLabel;
	}

	/**
	 * @return the recordType
	 */
	public String getRecordType() {
		return recordType;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getAccession()
	 */
	@Override
	public String getAccession() {
		return conceptIdShort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getLabel()
	 */
	@Override
	public String getLabel() {
		return preferredName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getOntologyAccession()
	 */
	@Override
	public String getOntologyAccession() {
		return ontologyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#setAccession(java.lang.String)
	 */
	@Override
	public void setAccession(String accession) {
		conceptIdShort = accession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#setLabel(java.lang.String)
	 */
	@Override
	public void setLabel(String label) {
		preferredName = label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontocat.OntologyTerm#setOntologyAccession(java.lang.String)
	 */
	@Override
	public void setOntologyAccession(String ontologyAccession) {
		ontologyId = ontologyAccession;
	}
	

}
