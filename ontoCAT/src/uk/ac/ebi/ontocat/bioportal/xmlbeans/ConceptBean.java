/**
 * 
 */
package uk.ac.ebi.ontocat.bioportal.xmlbeans;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;

/**
 * Wraps the Concept representation of BioPortal and maps it to the OntologyTerm
 * interface.
 * 
 * @author $Id: ConceptBean.java 9019 2009-09-22 12:39:01Z tomasz $
 */
public class ConceptBean extends OntologyTerm {
	/** The service that produced it */
	BioportalOntologyService bioportal;
	private static final Logger log = Logger.getLogger(ConceptBean.class.getName());

	/** The label. */
	private String label;

	/** The id. */
	private String id;

	/** The full id. */
	private String fullId;

	private String ontAccession;

	private String ontologyVersionId;

	private String[] synonyms;
	private List<String> definitions;
	private String[] authors;

	/** The relations. */
	private EntryBean[] relations;

	private String type;

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

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
	 * @see plugin.OntologyBrowser.OntologyTermExt#getAccession()
	 */
	public String getAccession() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyTermExt#getMetadata()
	 */
	public Map<String, List<String>> getAnnotations() {
		Map<String, List<String>> metadata = new HashMap<String, List<String>>();
		for (EntryBean e : relations) {
			if (e.getList() != null)
				metadata.put(e.getLabel(), e.getList());
		}
		return metadata;
	}

	public List<OntologyTerm> getChildren() {
		for (EntryBean e : relations) {
			if (e.getLabel().equalsIgnoreCase("SubClass")) {
				return e.getConceptBeans();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyTerm#getSynonyms()
	 */
	public List<String> getSynonyms() {
		for (EntryBean e : relations) {
			if (e.getLabel().equalsIgnoreCase("exact synonym") || e.getLabel().equalsIgnoreCase("synonym")
					|| e.getLabel().equalsIgnoreCase("FULL_SYN") || e.getLabel().equalsIgnoreCase("SYNONYM Full Form")) {
				return e.getList();
			}
		}
		return null;
	}

	public String getPathString() {
		// special case will only work if the bean was returned from path
		return relations[0].getPath();
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
				if (e.getLabel().toLowerCase().startsWith("def") && !e.getLabel().equalsIgnoreCase("definition source")
						&& !e.getLabel().equalsIgnoreCase("definition editor")) {
					log.warn("BP returned empty definitions list though there is some");
					return e.getList();
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyTerm#getOntologyAccession()
	 */
	public String getOntologyAccession() {
		return ontAccession;
	}

	/**
	 * @param ontAccession
	 *            the ontAccession to set
	 */
	public void setOntologyAccession(String ontAccession) {
		this.ontAccession = ontAccession;
	}

//	@Override
//	public Map<String, String[]> getRelations() throws OntologyServiceException {
//		Map<String, String[]> result = new LinkedHashMap<String, String[]>();
//		for (EntryBean e : this.relations) {
//			result.put(e.getLabel(), e.getList());
//		}
//		return result;
//	}

	// helper function
	public void setBioportalService(BioportalOntologyService bioportal) {
		// used for lazy loading
		this.bioportal = bioportal;
	}
}
