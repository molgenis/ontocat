/**
 * 
 */
package uk.ac.ebi.ontoapi.bioportal.xmlbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.ontoapi.OntologyServiceException;
import uk.ac.ebi.ontoapi.OntologyTerm;
import uk.ac.ebi.ontoapi.bioportal.BioportalOntologyService;

/**
 * Wraps the Concept representation of BioPortal and maps
 * it to the OntologyTerm interface.
 * 
 * @author $Id: ConceptBean.java 9019 2009-09-22 12:39:01Z tomasz $
 */
public class ConceptBean implements OntologyTerm
{
	/** The service that produced it*/
	BioportalOntologyService bioportal;
	
	/** The label. */
	private String label;

	/** The id. */
	private String id;

	/** The full id. */
	private String fullId;

	private String ontAccession;

	private String ontologyVersionId;

	/** The relations. */
	private Entry[] relations;

	private String isBrowsable;

	/**
	 * The Class Entry.
	 */
	public class Entry
	{

		/** The string. */
		private String string;

		/** The list. */
		private ArrayList list;

		/** The Unmodifiable collection. */
		private ArrayList UnmodifiableCollection;

		/** The counter. */
		private Integer counter;

		/**
		 * Gets the label.
		 * 
		 * @return the label
		 */
		public String getLabel()
		{
			return string;
		}

		/**
		 * Gets the list.
		 * 
		 * @return the list
		 */
		@SuppressWarnings("unchecked")
		public String[] getList()
		{
			try
			{
				if (list != null && list.get(0) instanceof String)
				{
					return (String[]) list.toArray(new String[0]);
				}
				else
					return null;
			}
			catch (ArrayStoreException ex)
			{
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		public List<OntologyTerm> getConceptBeans()
		{
			if (list.get(0) instanceof ConceptBean)
			{
				return list;
			}
			else
				return null;
		}
	}

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * Gets the full id.
	 * 
	 * @return the fullid
	 */
	public String getFullId()
	{
		return fullId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyTermExt#getAccession()
	 */
	public String getAccession()
	{
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyTermExt#getMetadata()
	 */
	public Map<String, String[]> getAnnotations()
	{
		Map<String, String[]> metadata = new HashMap<String, String[]>();
		for (Entry e : relations)
		{
			if (e.getList() != null) metadata.put(e.getLabel(), e.getList());
		}
		return metadata;
	}

	public List<OntologyTerm> getChildren()
	{
		for (Entry e : relations)
		{
			if (e.getLabel().equalsIgnoreCase("SubClass"))
			{
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
	public String[] getSynonyms()
	{
		for (Entry e : relations)
		{
			if (e.getLabel().equalsIgnoreCase("exact synonym") || e.getLabel().equalsIgnoreCase("synonym")
					|| e.getLabel().equalsIgnoreCase("FULL_SYN") || e.getLabel().equalsIgnoreCase("SYNONYM Full Form"))
			{
				return e.getList();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyTerm#getDefinitions()
	 */
	public String[] getDefinitions()
	{
		for (Entry e : relations)
		{
			if (e.getLabel().toLowerCase().startsWith("def") && !e.getLabel().equalsIgnoreCase("definition source")
					&& !e.getLabel().equalsIgnoreCase("definition editor")) return e.getList();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyTerm#getOntologyAccession()
	 */
	public String getOntologyAccession()
	{
		return ontAccession;
	}

	/**
	 * @param ontAccession
	 *            the ontAccession to set
	 */
	public void setOntologyAccession(String ontAccession)
	{
		this.ontAccession = ontAccession;
	}

	@Override
	public List<OntologyTerm> getParents() throws OntologyServiceException
	{
		return bioportal.getParents(this.getOntologyAccession(), this.getAccession());
	}

	@Override
	public Map<String, String[]> getRelations() throws OntologyServiceException
	{
		Map<String,String[]> result = new LinkedHashMap<String,String[]>();
		for(Entry e: this.relations)
		{
			result.put(e.getLabel(), e.getList());
		}
		return result;
	}

	@Override
	public List<OntologyTerm> getTermPath() throws OntologyServiceException
	{
		return bioportal.getTermPath(this.getOntologyAccession(), this.getAccession());
	}

	//helper function
	public void setBioportalService(BioportalOntologyService bioportal)
	{
		//used for lazy loading
		this.bioportal = bioportal;
	}
}