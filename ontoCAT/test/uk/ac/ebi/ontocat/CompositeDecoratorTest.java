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

 import static org.junit.Assert.assertEquals;

 import java.net.URI;
 import java.net.URISyntaxException;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Set;
 import java.util.concurrent.Callable;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;

 import org.junit.BeforeClass;
 import org.junit.Ignore;
 import org.junit.Test;

 import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
 import uk.ac.ebi.ontocat.file.FileOntologyService;
 import uk.ac.ebi.ontocat.ols.OlsOntologyService;
 import uk.ac.ebi.ontocat.special.AbstractOntologyServiceTest;
 import uk.ac.ebi.ontocat.virtual.CompositeDecorator;

 public class CompositeDecoratorTest extends AbstractOntologyServiceTest {

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

		 os = CompositeDecorator.getService(osOLS, osBP, osFile, osFile2);

		 // Testing with EFO
		 // Note the efo accession is not stable
		 ONTOLOGY_ACCESSION = "http://www.ebi.ac.uk/efo/";
		 TERM_ACCESSION = "EFO_0000400";
	 }

	 @Test
	 @Ignore("Tested equals() on OntologyTerm, fixed now")
	 public void testHashCollapse() throws OntologyServiceException {
		 List<OntologyTerm> l = os.searchAll("thymus");
		 log.info("List size " + l.size());
		 Set<OntologyTerm> h = new HashSet<OntologyTerm>(l);
		 log.info("Hash size " + h.size());
		 assertEquals(l.size(), h.size());
		 // for (OntologyTerm o : l) log.info(o);
	 }

	 @Test
	 @Ignore("Fixed concurrency issues in OWLmanager")
	 public void testConcurrentRequests() throws OntologyServiceException, URISyntaxException {
		 ExecutorService ec = Executors.newFixedThreadPool(50);
		 Collection<RequestTask> tasks = new ArrayList<RequestTask>();
		 // Create tasks
		 for (int i = 0; i < 50; i++) {
			 tasks.add(new RequestTask());
		 }
		 try {
			 ec.invokeAll(tasks);
		 } catch (InterruptedException e) {
			 e.printStackTrace();
		 }
	 }

	 private class RequestTask implements Callable<Object> {
		 @Override
		 public Object call() throws Exception {
			 new FileOntologyService(new URI("http://www.ebi.ac.uk/efo"));
			 return null;
		 }

	 }
 }
