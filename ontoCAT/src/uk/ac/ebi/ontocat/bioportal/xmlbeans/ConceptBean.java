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

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyTerm;

/**
 * Wraps the Concept representation of BioPortal and maps it to the OntologyTerm
 * interface.
 * 
 * @author Tomasz Adamusiak
 */
@SuppressWarnings("unused")
public class ConceptBean extends OntologyTerm {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(ConceptBean.class.getName());

	/** The label. */
	private String label;

	/** The id. */
	private String id;

	/** The full id. */
	private String fullId;

	private String ontAccession;

	private String ontologyVersionId;

	private List<String> synonyms;
	private List<String> definitions;
	private String[] authors;

	/** The relations. */
	private EntryBean[] relations;

	private String[] instances;

	private String type;
	
	private String isObsolete;

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
	 * @see plugin.OntologyBrowser.OntologyTermExt#getMetadata()
	 */
	public Map<String, List<String>> getAnnotations() {
		Map<String, List<String>> metadata = new HashMap<String, List<String>>();
		for (EntryBean e : relations) {
			if (e.getList() != null) {
				metadata.put(e.getLabel(), e.getList());
			}
		}
		return metadata;
	}

	public List<OntologyTerm> getChildren() {
		for (EntryBean e : relations) {
			if (e.getLabel().equalsIgnoreCase("SubClass")) {
				return e.getConceptBeans();
			}
		}
		return Collections.emptyList();
	}

	public List<OntologyTerm> getParents() {
		for (EntryBean e : relations) {
			if (e.getLabel().equalsIgnoreCase("Superclass")) {
				return e.getConceptBeans();
			}
		}
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyTerm#getSynonyms()
	 */
	public List<String> getSynonyms() {
		if (synonyms != null) {
			return synonyms;
		} else {
			for (EntryBean e : relations) {
				if (e.getLabel().equalsIgnoreCase("exact synonym")
						|| e.getLabel().equalsIgnoreCase("synonym")
						|| e.getLabel().equalsIgnoreCase("FULL_SYN")
						|| e.getLabel().equalsIgnoreCase("altLabel")
						|| e.getLabel().equalsIgnoreCase("SYNONYM Full Form")
						|| e.getLabel().equalsIgnoreCase("fma:Synonym")
						|| e.getLabel().endsWith("alternative_term")) {
					log.debug("BP returned empty synonym list though there is some");
					return e.getList();
				}
			}
		}
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyTerm#getDefinitions()
	 */
	public List<String> getDefinitions() {
		if (definitions != null) {
			return definitions;
		} else {
			for (EntryBean e : relations) {
				if (e.getLabel().toLowerCase().startsWith("def")
						|| e.getLabel().endsWith("definition")
						&& !e.getLabel().equalsIgnoreCase("definition source")
						&& !e.getLabel().equalsIgnoreCase("definition editor")) {
					log.debug("BP returned empty definitions list though there is some");
					return e.getList();
				}
			}
		}
		return Collections.emptyList();
	}

	public String getPathString() {
		// special case will only work if the bean was returned from path
		return relations[0].getPath();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getOntologyAccession()
	 */
	@Override
	public String getOntologyAccession() {
		return this.ontAccession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#setAccession(java.lang.String)
	 */
	@Override
	public void setAccession(String accession) {
		this.id = accession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#setLabel(java.lang.String)
	 */
	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontocat.OntologyTerm#setOntologyAccession(java.lang.String)
	 */
	@Override
	public void setOntologyAccession(String ontologyAccession) {
		ontAccession = ontologyAccession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getURI()
	 */
	@Override
	public URI getURI() {
		return URI.create(this.fullId);
	}
}
