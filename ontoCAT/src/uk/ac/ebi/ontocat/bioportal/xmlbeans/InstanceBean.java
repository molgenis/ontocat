/**
 * 
 */
package uk.ac.ebi.ontocat.bioportal.xmlbeans;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyTerm;

/**
 * Wraps the Concept representation of BioPortal and maps it to the OntologyTerm
 * interface.
 * 
 * @author $Id: ConceptBean.java 9019 2009-09-22 12:39:01Z tomasz $
 */
public class InstanceBean extends OntologyTerm {

	private static final Logger log = Logger.getLogger(InstanceBean.class.getName());


	/** The label. */
	private String label;

	/** The id. */
	private String id;

	/** The full id. */
	private String fullId;

	// <relations/>
	// <instanceType>
	// <list>
	// <classBean>
	// <id>owl:SomeValuesFromRestriction</id>
	// <fullId>http://www.w3.org/2002/07/owl#SomeValuesFromRestriction</fullId>
	// <label>owl:SomeValuesFromRestriction</label>
	// <type>Class</type>
	// <relations/>
	// </classBean>
	// </list>
	// </instanceType>



	/** The relations. */
	private EntryBean[] relations;




	/**
	 * Gets the full id.
	 * 
	 * @return the fullid
	 */
	public String getFullId() {
		return fullId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getAccession()
	 */
	@Override
	public String getAccession() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getLabel()
	 */
	@Override
	public String getLabel() {
		return this.label;
	}

}
