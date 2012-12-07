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
package uk.ac.ebi.ontocat.examples;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;

public class Sandbox {

	private static final Logger log = Logger.getLogger(Sandbox.class);

	/**
	 * @param args
	 * @throws OntologyServiceException
	 */
	public static void main(String[] args) throws OntologyServiceException {

		OntologyService os = new OlsOntologyService();
		Map<String, List<String>> annotations = os.getAnnotations("DOID",
				"DOID:0050120");
		System.out.println(annotations.get("xref_analog"));

	}
}
