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

import java.net.URI;

import org.junit.BeforeClass;

import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.special.AbstractOntologyServiceTest;
import uk.ac.ebi.ontocat.virtual.CompositeServiceNoThreads;

public class CompositeServiceNoThreadsTest extends AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final OntologyService osBP = new BioportalOntologyService();
		final OntologyService osOLS = new OlsOntologyService();
		final FileOntologyService osFile = new FileOntologyService(
				new URI(
				"http://efo.svn.sourceforge.net/viewvc/efo/trunk/src/efoinowl/efo.owl?revision=175"));
		final FileOntologyService osFile2 = new FileOntologyService(
				new URI(
				"http://diseaseontology.svn.sourceforge.net/svnroot/diseaseontology/trunk/HumanDO.obo"));

		os = CompositeServiceNoThreads.getService(osOLS, osBP, osFile, osFile2);

		// Testing with EFO
		ONTOLOGY_ACCESSION = "http://www.ebi.ac.uk/efo/";
		TERM_ACCESSION = "EFO_0000400";
	}

}
