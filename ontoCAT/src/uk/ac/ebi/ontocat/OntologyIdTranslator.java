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
import java.util.ArrayList;

 /**
  * Ontology ID translation interface. Defines mappings and regular expressions
  * required to convert a given string to an ID searchable in an OntologyService.
  * 
  * @author Tomasz Adamusiak, Morris Swertz
  */
 public interface OntologyIdTranslator extends Serializable {
	 public ArrayList<OntologyIdMapping> getMappings();

	 public String getTranslatedOntologyAccession(String ontologyAccession)
	 throws OntologyServiceException;

	 public String getTranslatedTermAccession(String termAccession)
	 throws OntologyServiceException;

	 public String getOntologyAccFromTermAcc(String termAccession)
	 throws OntologyServiceException;
 }
