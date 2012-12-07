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
 import java.util.regex.Pattern;

 /**
  * Ontology mapping interface.
  * 
  * @author Tomasz Adamusiak, Morris Swertz
  * @version $Id$
  */
 public class OntologyIdMapping implements Serializable {
	 private static final long serialVersionUID = 1L;
	 /** Confirms match to this particular ontology. */
	 protected Pattern pConfirmMatch;
	 /** Extracts bioportal compatible id from matched string. */
	 protected Pattern pExtractID;

	 protected String externalOntologyAccession;
	 protected String localOntologyAccession;

	 protected String testingCode;

	 public OntologyIdMapping() {
	 }

	 /**
	  * A regular expression pattern which confirms that a particular concept
	  * string represents OntologyTerm encapsulated by this mapping.
	  * 
	  * @return pattern
	  */
	 public Pattern getConfirmMatchPattern(){
		 return pConfirmMatch;
	 }

	 /**
	  * A regular expression pattern which can convert a concept string confirmed
	  * with MatchPattern to a format acceptable by the OntologyService.
	  * 
	  * @return pattern
	  */
	 public Pattern getExtractIDPattern() {
		 return pExtractID;
	 }

	 /**
	  * Gets the external ontology accession
	  * 
	  * @return the ont id
	  */
	 public String getExternalOntologyAccession(){
		 return externalOntologyAccession;
	 }

	 public void setExternalOntologyAccession(String value) {
		 externalOntologyAccession = value;
	 }

	 /**
	  * Gets the local ontology accession
	  * 
	  * @return the ont id
	  */
	 public String getLocalOntologyAccession(){
		 return localOntologyAccession;
	 }

	 public void setLocalOntologyAccession(String value) {
		 localOntologyAccession = value;
	 }

	 /**
	  * Testing ontology term code used to confirm that the service can resolve
	  * the mappings correctly.
	  * 
	  * @return the test code
	  */
	 public String getTestCode(){
		 return testingCode;
	 }

 }