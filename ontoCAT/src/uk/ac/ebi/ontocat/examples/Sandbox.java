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

import jargs.gnu.CmdLineParser.IllegalOptionValueException;
import jargs.gnu.CmdLineParser.UnknownOptionException;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;

public class Sandbox {

	private static final Logger log = Logger.getLogger(Sandbox.class);

	/**
	 * @param args
	 * @throws URISyntaxException
	 * @throws OntologyServiceException
	 * @throws UnknownOptionException
	 * @throws IllegalOptionValueException
	 */
	public static void main(String[] args) throws URISyntaxException,
	OntologyServiceException, IllegalOptionValueException,
	UnknownOptionException {

		OntologyService os = new ReasonedFileOntologyService(
				URI.create("http://www.geneontology.org/GO_slims/goslim_generic.obo"));

		log.info(os.getTerm("GO_0000003"));
		if (os.getTerm("GO:0000003") == null) {
			log.warn("NULL");
		}

	}


}
