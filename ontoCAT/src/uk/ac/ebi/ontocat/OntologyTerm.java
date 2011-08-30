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
package uk.ac.ebi.ontocat;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
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
public class OntologyTerm implements Serializable, Comparable<OntologyTerm> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The accession. */
	private String accession;

	/** The label. */
	private String label;

	/** The ontology accession. */
	private String ontologyAccession;

	/** The term URI */
	private URI uri = null;

	/** The Constant log. */
	@SuppressWarnings("unused")
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

	public class OntologyTermContext implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "OntologyTermContext [SimilarityScore=" + SimilarityScore
			+ ", valueMatched="
			+ valueMatched
			+ ", searchOptions="
			+ Arrays.toString(searchOptions)
			+ ", serviceType="
			+ getServiceType()
			+ ", timestamp="
			+ timestamp
			+ "]";
		}

		/**
		 * The Enum OntologyServiceType.
		 */
		private Date timestamp = new Date();
		private Integer SimilarityScore = null;
		private SearchOptions[] searchOptions;
		private OntologyServiceType serviceType = OntologyServiceType.UNKNOWN;
		private String valueMatched = null;

		/**
		 * Gets the service type that the ontology term originated from.
		 * 
		 * @return the service type
		 * @throws OntologyServiceException
		 */
		public OntologyServiceType getServiceType() {
			return serviceType;
		}

		public void setServiceType(OntologyServiceType serviceType) {
			this.serviceType = serviceType;
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

		public Integer getSimilarityScore() {
			return SimilarityScore;
		}

		public String getValueMatched() {
			return valueMatched;
		}

		private void setValueMatched(String val) {
			valueMatched = val;
		}

		/**
		 * Gets the similarity based on Levenshtein distance between query and
		 * the text
		 * 
		 * @param query
		 *            the query
		 * @param text
		 *            the text
		 * 
		 */
		public void setSimilarityScore(String query, String text) {
			setValueMatched(text);
			if (query.equalsIgnoreCase(text)) {
				setSimilarityScore(100); // exact match
			} else {
				int LD = StringUtils.getLevenshteinDistance(
						getNormalised(text), getNormalised(query));
				int LDnorm = (int) (((query.length() - LD) / (float) query
						.length()) * 100);
				setSimilarityScore(LDnorm);
			}
		}

		private void setSimilarityScore(Integer similarityScore) {
			SimilarityScore = similarityScore;
		}

		/**
		 * Normalises the string by lowercasing, splitting it into words on
		 * non-alphanumeric characters and sorting them alphabetically
		 * 
		 * @param in
		 *            string to be normalised
		 * 
		 * @return the normalised string
		 */
		private String getNormalised(String in) {
			String[] words = in.split("[^a-z0-9]");
			Arrays.sort(words);
			StringBuilder builder = new StringBuilder();
			for (String s : words) {
				builder.append(s);
			}

			return builder.toString();
		}
	}

	/**
	 * Instantiates a new ontology term. Empty constructor. Now deprecated use
	 * the 4-argument one with URI.
	 */
	public OntologyTerm() {
	}

	/**
	 * Instantiates a new ontology term without URI
	 * 
	 * @param ontologyAccession
	 *            the ontology accession
	 * @param termAccession
	 *            the term accession
	 * @param label
	 *            the label
	 */
	@Deprecated
	public OntologyTerm(String ontologyAccession, String termAccession,
			String label) {
		this.setAccession(termAccession);
		this.setLabel(label);
		this.setOntologyAccession(ontologyAccession);
	}

	/**
	 * Instantiates a new ontology term without URI
	 * 
	 * @param ontologyAccession
	 *            the ontology accession
	 * @param termAccession
	 *            the term accession
	 * @param label
	 *            the label
	 */
	public OntologyTerm(String ontologyAccession, String termAccession,
			String label, URI uri) {
		this.setAccession(termAccession);
		this.setLabel(label);
		this.setOntologyAccession(ontologyAccession);
		this.setURI(uri);
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

	/**
	 * Gets the term URI
	 * 
	 * @return the term URI
	 */
	public URI getURI() {
		return uri;
	}

	/**
	 * Sets the term URI
	 * 
	 * @param uri
	 *            the term URI
	 */
	public void setURI(URI uri) {
		this.uri = uri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String
		.format("OntologyTerm(ontologyAccession=%s, termAccession=%s, termURI=%s, label=%s)",
				getOntologyAccession(), getAccession(), getURI(),
				getLabel());

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
		result = prime * result
		+ ((getAccession() == null) ? 0
				: getAccession().hashCode());
		result = prime * result
		+ ((getLabel() == null) ? 0
				: getLabel().hashCode());
		result = prime * result
		+ ((getOntologyAccession() == null) ? 0
				: getOntologyAccession().hashCode());
		result = prime * result
		+ ((getURI() == null) ? 0
				: getURI().hashCode());
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OntologyTerm other = (OntologyTerm) obj;
		if (getAccession() == null) {
			if (other.getAccession() != null) {
				return false;
			}
		} else if (!getAccession().equals(other.getAccession())) {
			return false;
		}
		if (getLabel() == null) {
			if (other.getLabel() != null) {
				return false;
			}
		} else if (!getLabel().equals(other.getLabel())) {
			return false;
		}
		if (getOntologyAccession() == null) {
			if (other.getOntologyAccession() != null) {
				return false;
			}
		} else if (!getOntologyAccession().equals(other.getOntologyAccession())) {
			return false;
		}
		if (getURI() == null) {
			if (other.getURI() != null) {
				return false;
			}
		} else if (!getURI().equals(other.getURI())) {
			return false;
		}
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
			OntologyService os = CompositeDecorator.getService(
					new OlsOntologyService(), new BioportalOntologyService());

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
			ontologyCache.put(getOntologyAccession(),
					new BioportalOntologyService()
			.getOntology(getOntologyAccession()));
			return ontologyCache.get(getOntologyAccession());
		}
		throw new OntologyServiceException("Could not infer ontology!");
	}

	public OntologyTermContext getContext() {
		// Instantiate the context for Bioportal
		// as xml beans are instantiated without the fields
		if (context == null) {
			context = new OntologyTermContext();
		}
		return context;
	}

	@Override
	public int compareTo(OntologyTerm o) {
		if (this.getContext().getSimilarityScore() > o.getContext()
				.getSimilarityScore()) {
			return -1;
		}
		if (this.getContext().getSimilarityScore() < o.getContext()
				.getSimilarityScore()) {
			return 1;
		}
		return 0;
	}
}
