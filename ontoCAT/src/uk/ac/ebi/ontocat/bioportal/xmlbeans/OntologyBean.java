/**
 * 
 */
package uk.ac.ebi.ontocat.bioportal.xmlbeans;

import java.util.ArrayList;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyServiceException;


/**
 * Wraps for the Ontology representation of BioPortal and maps
 * it to the OntologyEntity interface.
 * 
 * @author $Id: ConceptBean.java 8301 2009-07-31 13:41:14Z tomasz $
 * 
 */
public class OntologyBean extends Ontology {
	private String id;
	private String ontologyId;
	private String virtualViewIds;
	private String isView;
	private String hasViews;
	private String viewOnOntologyVersionId;
	private String internalVersionNumber;
	private String userId;
	private String versionNumber;
	private String versionStatus;
	private String isRemote;
	private String isReviewed;
	private String statusId;
	private String dateCreated;
	private String dateReleased;
	private String isManual;
	private String displayLabel;
	private String description;
	private String abbreviation;
	private String format;
	private String contactName;
	private String contactEmail;
	private String homepage;
	private String documentation;
	private String urn;
	private String isFoundry;
	private String oboFoundryId;
	private String codingScheme;
	private String publication;
	private String documentationSlot;

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.xmlbeans.OntologyEntity#getSynonymSlot()
	 */
	public String getSynonymSlot() {
		return synonymSlot;
	}

	public String getTargetTerminologies() {
		return targetTerminologies;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.efo.bioportal.xmlbeans.OntologyEntity#getPreferredNameSlot()
	 */
	public String getPreferredNameSlot() {
		return preferredNameSlot;
	}

	private String synonymSlot;
	private String targetTerminologies;
	private String preferredNameSlot;
	private ArrayList categoryIds;
	private ArrayList filenames;
	private ArrayList<Integer> groupIds;
	private String filePath;

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.xmlbeans.OntologyEntity#getVersionNumber()
	 */
	public String getVersionNumber() {
		return versionNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.xmlbeans.OntologyEntity#getDateReleased()
	 */
	public String getDateReleased() {
		return dateReleased.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.xmlbeans.OntologyEntity#getDisplayLabel()
	 */
	public String getLabel() {
		return displayLabel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.xmlbeans.OntologyEntity#getAbbreviation()
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.xmlbeans.OntologyEntity#getMetaAnnotation()
	 */
	public String getMetaAnnotation() {
		return "Bioportal mappings to " + displayLabel + " (" + abbreviation + ") ver" + versionNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyEntity#getOntologyID()
	 */
	@Override
	public String getOntologyAccession() {
		return ontologyId;
	}

	@Override
	public String getDescription()
	{
		throw new UnsupportedOperationException();
	}
}
