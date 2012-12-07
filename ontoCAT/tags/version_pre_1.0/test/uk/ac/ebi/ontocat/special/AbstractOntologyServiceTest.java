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
package uk.ac.ebi.ontocat.special;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

public abstract class AbstractOntologyServiceTest {
	protected static OntologyService os;
	protected static String ONTOLOGY_ACCESSION;
	protected static String TERM_ACCESSION = "GO:0043227";
	// GO:0005623 not working for getChildren() as subclasses are partOf
	protected static final Logger log = Logger
	.getLogger(AbstractOntologyServiceTest.class);

	@Test
	public final void testGetOntologies() throws OntologyServiceException {
		printCurrentTest();
		List<Ontology> list = os.getOntologies();
		assertNotSame("Empty list returned!", 0, list.size());
		for (Ontology oe : list) {
			println(oe);
		}
	}

	@Test
	public final void testGetOntology() throws OntologyServiceException {
		printCurrentTest();
		Ontology oe = os.getOntology(ONTOLOGY_ACCESSION);
		assertNotNull("Null returned!", oe);
		println(oe);
	}

	@Test
	public final void testSearchOntology() throws OntologyServiceException {
		printCurrentTest();
		List<OntologyTerm> list = os.searchOntology(ONTOLOGY_ACCESSION, "gene");
		assertNotSame("Empty list returned!", 0, list.size());
		for (OntologyTerm ot : list) {
			println(ot);
		}
		log.info(list.size());
	}

	@Test
	public void testSearchAll() throws OntologyServiceException {
		printCurrentTest();
		List<OntologyTerm> list = os.searchAll("nephroblastoma",
				SearchOptions.EXACT, SearchOptions.INCLUDE_PROPERTIES);
		assertNotSame("Empty list returned!", 0, list.size());
		for (OntologyTerm ot : list) {
			println(ot);
		}
	}

	@Test
	public final void testGetRootTerms() throws OntologyServiceException {
		printCurrentTest();
		List<OntologyTerm> list = os.getRootTerms(ONTOLOGY_ACCESSION);
		assertNotSame("Empty list returned!", 0, list.size());
		// in an uninferred view defined classes come up as root terms
		// Assert.assertTrue("Too many root terms returned", list.size() < 4);
		assertNotSame("Empty list returned!", 0, list.size());
		for (OntologyTerm ot : list) {
			println(ot);
		}
	}

	@Test
	public final void testGetTerm() throws OntologyServiceException {
		printCurrentTest();
		OntologyTerm ot = os.getTerm(ONTOLOGY_ACCESSION, TERM_ACCESSION);
		assertNotNull("Null returned", ot);
		println(ot);
		// println(ot.getOntology());
	}

	@Test
	public final void testGetTermByAccessionOnly()
	throws OntologyServiceException {
		printCurrentTest();
		OntologyTerm ot = os.getTerm(TERM_ACCESSION);
		assertNotNull("Null returned", ot);
		println(ot);
	}

	@Test
	public final void testGetTermPath() throws OntologyServiceException {
		printCurrentTest();
		List<OntologyTerm> list = os.getTermPath(ONTOLOGY_ACCESSION,
				TERM_ACCESSION);
		Assert.assertTrue("No path found!", list.size() > 1);
		for (OntologyTerm ot : list) {
			println(ot);
		}
	}

	@Test
	public void testGetChildren() throws OntologyServiceException {
		printCurrentTest();
		List<OntologyTerm> list = os.getChildren(ONTOLOGY_ACCESSION,
				TERM_ACCESSION);
		assertNotSame("Empty list returned!", 0, list.size());
		for (OntologyTerm ot : list) {
			println(ot);
		}
	}

	@Test
	@Ignore("This runs for more than 10 minutes on a remote service")
	public final void testGetAllChildren() throws OntologyServiceException {
		printCurrentTest();
		Set<OntologyTerm> set = os.getAllChildren(ONTOLOGY_ACCESSION,
				TERM_ACCESSION);
		assertNotSame("Empty set returned!", 0, set.size());
		for (OntologyTerm ot : set) {
			println(ot);
		}
	}

	@Test
	public final void testGetParents() throws OntologyServiceException {
		printCurrentTest();
		List<OntologyTerm> list = os.getParents(ONTOLOGY_ACCESSION,
				TERM_ACCESSION);
		assertNotSame("Empty list returned!", 0, list.size());
		for (OntologyTerm ot : list) {
			println(ot);
		}
	}

	@Test
	public final void testGetAllParents() throws OntologyServiceException {
		printCurrentTest();
		Set<OntologyTerm> set = os.getAllParents(ONTOLOGY_ACCESSION,
				TERM_ACCESSION);
		assertNotSame("Empty set returned!", 0, set.size());
		// this is not really working for obo test, where part_of
		// is automatically inferred as isa
		// assertTrue("More parents expected!", set.size() > 1);
		for (OntologyTerm ot : set) {
			println(ot);
		}
	}

	@Test
	public final void testGetAnnotations() throws OntologyServiceException {
		printCurrentTest();
		Map<String, List<String>> map = os.getAnnotations(ONTOLOGY_ACCESSION,
				TERM_ACCESSION);
		assertNotSame("Empty map returned!", 0, map.size());
		for (Entry<String, List<String>> e : map.entrySet()) {
			println(e.getKey());
			for (String s : e.getValue()) {
				println("\t" + s);
			}
		}
	}

	@Test
	public final void testGetDefinitions() throws OntologyServiceException {
		printCurrentTest();
		println(ONTOLOGY_ACCESSION + " "
				+ TERM_ACCESSION);
		List<String> list = os.getDefinitions(ONTOLOGY_ACCESSION,
				TERM_ACCESSION);
		assertNotSame("Empty list returned!", 0, list.size());
		println(list.get(0));
	}

	@Test
	public final void testGetSynonyms() throws OntologyServiceException {
		printCurrentTest();
		List<String> list = os.getSynonyms(ONTOLOGY_ACCESSION, TERM_ACCESSION);
		assertNotSame("Empty list returned!", 0, list.size());
		println(list.get(0));
	}

	protected static void printCurrentTest() {
		println();
		println("**************************");
		println(Thread.currentThread().getStackTrace()[2].getMethodName()
				.substring(4));
		println("*************************");
		println();
	}

	private static void println() {
		System.out.println();
	}

	protected static void println(Object o) {
		System.out.println(o);
	}
}
