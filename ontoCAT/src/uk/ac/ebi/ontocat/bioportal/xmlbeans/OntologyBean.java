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
package uk.ac.ebi.ontocat.bioportal.xmlbeans;

import java.util.ArrayList;

import uk.ac.ebi.ontocat.Ontology;

/**
 * Wraps for the Ontology representation of BioPortal and maps it to the
 * OntologyEntity interface.
 * 
 * @author Tomasz Adamusiak
 * 
 */
@SuppressWarnings("unused")
public class OntologyBean extends Ontology {
	private static final long serialVersionUID = 1L;
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
	private String synonymSlot;
	private String targetTerminologies;
	private String preferredNameSlot;
	private ArrayList<String> categoryIds;
	private ArrayList<String> filenames;
	private ArrayList<Integer> groupIds;
	private ArrayList<Integer> userIds;
	private String filePath;
	private String viewDefinition;
	private String viewDefinitionLanguage;
	private String downloadLocation;
	private String authorSlot;
	private String isMetadataOnly;
	private String userAcl;
	private String isFlat;
	private String viewGenerationEngine;
	private String obsoleteParent;

	public OntologyBean(String ontologyAccession) {
		super(ontologyAccession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.xmlbeans.OntologyEntity#getSynonymSlot()
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.xmlbeans.OntologyEntity#getVersionNumber()
	 */
	@Override
	public String getVersionNumber() {
		return versionNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.xmlbeans.OntologyEntity#getDateReleased()
	 */
	@Override
	public String getDateReleased() {
		return dateReleased;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.xmlbeans.OntologyEntity#getDisplayLabel()
	 */
	@Override
	public String getLabel() {
		return displayLabel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.xmlbeans.OntologyEntity#getAbbreviation()
	 */
	@Override
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
		return description;
	}
}
