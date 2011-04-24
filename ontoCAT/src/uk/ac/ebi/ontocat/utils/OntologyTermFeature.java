/**
 * 
 */
package uk.ac.ebi.ontocat.utils;

import org.semanticweb.owlapi.model.OWLAnnotation;

/**
 * Wrapper class for term characteristics used by the comparator in the
 * examples. Easily extendible to support new term features when they become
 * available.
 * 
 * @author Tomasz Adamusiak
 */
public class OntologyTermFeature {
	public enum OntologyTermFeatureType {
		ANNOTATION, PARENT
	}

	private String feature;
	private OntologyTermFeatureType type;

	/**
	 * @return the type
	 */
	public OntologyTermFeatureType getType() {
		return type;
	}

	/**
	 * @return the feature
	 */
	public String getFeature() {
		return feature;
	}

	public OntologyTermFeature(String feature, OntologyTermFeatureType type) {
		this.type = type;
		this.feature = feature;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((feature == null) ? 0 : feature.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OntologyTermFeature other = (OntologyTermFeature) obj;
		if (feature == null) {
			if (other.feature != null)
				return false;
		} else if (!feature.equals(other.feature))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getType() + " " + getFeature();
	}
}
