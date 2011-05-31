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


 import org.junit.After;
 import org.junit.Before;
 import org.junit.BeforeClass;

 import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
 import uk.ac.ebi.ontocat.special.AbstractOntologyServiceTest;
 import uk.ac.ebi.ontocat.virtual.CachedServiceDecorator;

 public class CachedDecoratorTest extends AbstractOntologyServiceTest {

	 @BeforeClass
	 public static void setUpBeforeClass() throws Exception {
		 CachedServiceDecorator.clearCache();
		 os = CachedServiceDecorator.getService(new BioportalOntologyService());
		 // GO accession
		 ONTOLOGY_ACCESSION = "1070";
	 }

	 private long t1;
	 private long t2;

	 @Before
	 public void runBeforeEveryTest() {
		 t1 = System.nanoTime();
	 }

	 @After
	 public void runAfterEveryTest() {
		 t2 = System.nanoTime();
		 log.info("Query took " + ((t2 - t1) * 1e-6) + " ms\t");
	 }

 }
