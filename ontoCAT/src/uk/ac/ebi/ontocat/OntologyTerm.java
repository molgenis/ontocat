package uk.ac.ebi.ontocat;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.CompositeDecorator;

// TODO: Auto-generated Javadoc
/**
 * The Class OntologyTerm.
 */
@XmlRootElement(name = "term")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class OntologyTerm implements Serializable {

	/** The accession. */
	private String accession;

	/** The label. */
	private String label;

	/** The ontology accession. */
	private String ontologyAccession;

	/** The Constant log. */
	private static final Logger log = Logger.getLogger(OntologyTerm.class);

	private OntologyTermContext context = new OntologyTermContext();

	public enum OntologyServiceType {
		/** OlsOntologyService. */
		OLS,
		/** BioportalOntologyService. */
		BIOPORTAL,
		/** FileOntologyService */
		FILE,
		/** Anything else */
		UNKNOWN
	};

	public class OntologyTermContext {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "OntologyTermContext [SimilarityScore=" + SimilarityScore + ", searchOptions="
					+ Arrays.toString(searchOptions) + ", serviceType=" + getServiceType()
					+ ", timestamp=" + timestamp + "]";
		}

		/**
		 * The Enum OntologyServiceType.
		 */
		private Date timestamp = new Date();
		private Integer SimilarityScore = null;
		private SearchOptions[] searchOptions;

		/**
		 * Gets the service type that the ontology term origanted from.
		 * 
		 * @return the service type
		 * @throws OntologyServiceException
		 */
		public OntologyServiceType getServiceType() {
			// File will have http in accession
			// TODO: check if that's true for OBO
			if (getOntologyAccession().startsWith("http"))
				return OntologyServiceType.FILE;
			// OLS will be up to four letters
			if (getOntologyAccession().matches("[A-Z]{2,5}"))
				return OntologyServiceType.OLS;
			// BioPortal will be four digits
			if (getOntologyAccession().matches("\\d{4}"))
				return OntologyServiceType.BIOPORTAL;
			log.warn("Could not infer ServiceType!");
			return OntologyServiceType.UNKNOWN;
		}

		public Date getTimestamp() {
			return timestamp;
		}

		public void setSearchOptions(SearchOptions[] searchOptions) {
			this.searchOptions = searchOptions;
		}

		public SearchOptions[] getSearchOptions() {
			return searchOptions;
		}

		public void setSimilarityScore(Integer similarityScore) {
			SimilarityScore = similarityScore;
		}

		public Integer getSimilarityScore() {
			return SimilarityScore;
		}
	}

	/**
	 * Instantiates a new ontology term. Empty constructor.
	 */
	public OntologyTerm() {
	}

	/**
	 * Instantiates a new ontology term.
	 * 
	 * @param ontologyAccession
	 *            the ontology accession
	 * @param termAccession
	 *            the term accession
	 * @param label
	 *            the label
	 */
	public OntologyTerm(String ontologyAccession, String termAccession, String label) {
		this.setAccession(termAccession);
		this.setLabel(label);
		this.setOntologyAccession(ontologyAccession);
	}

	/**
	 * Gets the accession.
	 * 
	 * @return the accession
	 */
	public String getAccession() {
		return accession;
	}

	/**
	 * Sets the accession.
	 * 
	 * @param accession
	 *            the new accession
	 */
	public void setAccession(String accession) {
		this.accession = accession;
	}

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 * 
	 * @param label
	 *            the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the ontology accession.
	 * 
	 * @return the ontology accession
	 */
	public String getOntologyAccession() {
		return ontologyAccession;
	}

	/**
	 * Sets the ontology accession.
	 * 
	 * @param ontologyAccession
	 *            the new ontology accession
	 */
	public void setOntologyAccession(String ontologyAccession) {
		this.ontologyAccession = ontologyAccession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("OntologyTerm(ontologyAccession=%s, termAccession=%s, label=%s)",
				getOntologyAccession(), getAccession(), getLabel());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getAccession() == null) ? 0 : getAccession().hashCode());
		result = prime * result + ((getLabel() == null) ? 0 : getLabel().hashCode());
		result = prime * result
				+ ((getOntologyAccession() == null) ? 0 : getOntologyAccession().hashCode());
		return result;
	}

	/*
	 * Note using getters, as BioPortal beans are instantiated from XML, and
	 * will not have default properties set.
	 * 
	 * Getters are overridden in BioPortal beans.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OntologyTerm other = (OntologyTerm) obj;
		if (getAccession() == null) {
			if (other.getAccession() != null)
				return false;
		} else if (!getAccession().equals(other.getAccession()))
			return false;
		if (getLabel() == null) {
			if (other.getLabel() != null)
				return false;
		} else if (!getLabel().equals(other.getLabel()))
			return false;
		if (getOntologyAccession() == null) {
			if (other.getOntologyAccession() != null)
				return false;
		} else if (!getOntologyAccession().equals(other.getOntologyAccession()))
			return false;
		return true;
	}

	/**
	 * The ontology cache. Stores ontology lists from OLS and BP for
	 * {@link OntologyTerm#getOntology()}
	 */
	private static Map<String, Ontology> ontologyCache;

	/**
	 * Initalize ontology cache. {@link OntologyTerm#ontologyCache}
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private void initalizeOntologyCache() throws OntologyServiceException {
		if (ontologyCache == null) {
			ontologyCache = new HashMap<String, Ontology>();
			OntologyService os = CompositeDecorator.getService(new OlsOntologyService(),
					new BioportalOntologyService());

			for (Ontology o : os.getOntologies()) {
				ontologyCache.put(o.getOntologyAccession(), o);
			}
		}
	}

	/**
	 * Gets the ontology for the term. Acquires the object from cache
	 * {@link OntologyTerm#ontologyCache}
	 * 
	 * @return the ontology
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	public Ontology getOntology() throws OntologyServiceException {
		initalizeOntologyCache();
		if (ontologyCache.containsKey(getOntologyAccession())) {
			return ontologyCache.get(getOntologyAccession());
		} else if (getContext().getServiceType() == OntologyServiceType.FILE) {
			return new Ontology(getOntologyAccession());
		} else if (getContext().getServiceType() == OntologyServiceType.BIOPORTAL) {
			// possibly a view, fire an extra query and store in cache
			ontologyCache.put(getOntologyAccession(), new BioportalOntologyService()
					.getOntology(getOntologyAccession()));
			return ontologyCache.get(getOntologyAccession());
		}
		throw new OntologyServiceException("Could not infer ontology!");
	}

	public OntologyTermContext getContext() {
		// Instantiate the context for Bioportal
		// as xml beans are instantiated without the fields
		if (context == null)
			context = new OntologyTermContext();
		return context;
	}
}
