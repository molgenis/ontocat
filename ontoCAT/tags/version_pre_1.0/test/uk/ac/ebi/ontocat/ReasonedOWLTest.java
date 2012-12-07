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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;
import uk.ac.ebi.ontocat.special.AbstractOntologyServiceTest;

public class ReasonedOWLTest extends
AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new ReasonedFileOntologyService(new URI(
		"http://www.ebi.ac.uk/efo/efo.owl"), "EFO");

		ONTOLOGY_ACCESSION = "EFO";
		TERM_ACCESSION = "EFO_0000326";
	}

	@Test
	public final void testGetRelations() throws OntologyServiceException {
		printCurrentTest();
		Map<String, Set<OntologyTerm>> map = os.getRelations(
				ONTOLOGY_ACCESSION, TERM_ACCESSION);
		assertNotSame("Empty map returned!", 0, map.size());
		assertFalse("No expected relations returned", map.get("inheres_in")
				.isEmpty());
		for (Entry<String, Set<OntologyTerm>> e : map.entrySet()) {
			System.out.println(e.getKey());
			for (OntologyTerm s : e.getValue()) {
				System.out.println("\t" + s.getLabel());
			}
		}
	}
}
