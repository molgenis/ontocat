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
package uk.ac.ebi.ontocat.utils;


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
		 if (this == obj) {
			 return true;
		 }
		 if (obj == null) {
			 return false;
		 }
		 if (getClass() != obj.getClass()) {
			 return false;
		 }
		 OntologyTermFeature other = (OntologyTermFeature) obj;
		 if (feature == null) {
			 if (other.feature != null) {
				 return false;
			 }
		 } else if (!feature.equals(other.feature)) {
			 return false;
		 }
		 if (type != other.type) {
			 return false;
		 }
		 return true;
	 }

	 @Override
	 public String toString() {
		 return getType() + " " + getFeature();
	 }
 }
