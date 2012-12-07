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


import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.efo.bioportal.EFOIDTranslator;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.bioportal.xmlbeans.OntologyBean;
import uk.ac.ebi.ontocat.virtual.TranslatedOntologyService;

/**
 * @author $Id: EFOIDTranslatorTest.java 9019 2009-09-22 12:39:01Z tomasz $
 * 
 */

public class TranslatedOntologyServiceTest {
	private static final TranslatedOntologyService os = new TranslatedOntologyService(
			new BioportalOntologyService(), new EFOIDTranslator());

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testMappings() throws OntologyServiceException {
		for (OntologyIdMapping BPmap : new EFOIDTranslator().getMappings()) {
			printResults(BPmap.getTestCode());
		}
	}

	@Test
	// Fails on term accession being a uri:
	// http://rest.bioontology.org/bioportal/virtual/ontology/1084/http://ontology.neuinfo.org/NIF/BiomaterialEntities/NIF-Molecule.owl#nifext_5168
	public void testFailedImport() throws OntologyServiceException {
		System.out.println(os.getTerm("NIFSTD:nifext_5168"));
	}

	@Test
	public void listResolvableOntologies() throws OntologyServiceException {
		for (OntologyIdMapping BPmap : new EFOIDTranslator().getMappings()) {
			try {
				OntologyBean ob = (OntologyBean) os.getOntology(BPmap
						.getTestCode());
				printMany(new String[] { ob.getMetaAnnotation(),
						"Preferred name: " + ob.getPreferredNameSlot(),
						"Synonym: " + ob.getSynonymSlot(),
						"Terminologies: " + ob.getTargetTerminologies() });
			} catch (OntologyServiceException e) {
				e.printStackTrace();
				fail(BPmap.getTestCode() + " "
						+ BPmap.getExternalOntologyAccession() + " NOT FOUND");

			}

		}

	}

	private void printResults(String testCode)
	throws OntologyServiceException {
		OntologyTerm extTerm = os.getTerm(testCode);
		System.out.println(testCode + " => " + extTerm);
		/*
		 * System.out.println("SYNONYMS ");
		 * 
		 * for (String syn : (os.getSynonyms(testCode) == null) ? new
		 * ArrayList<String>() : os.getSynonyms(testCode)) {
		 * System.out.println(syn); } System.out.print("DEFINITON "); for
		 * (String def : (os.getDefinitions(testCode) == null) ? new
		 * ArrayList<String>() : os.getDefinitions(testCode)) {
		 * System.out.println(def); }
		 * 
		 * System.out.println("SourceUrl " + os.makeLookupHyperlink(testCode));
		 */
		System.out.println();
	}

	private void printMany(String[] arr) {
		for (String str : arr) {
			System.out.println(str);
		}
		System.out.println("***********\n");
	}
}