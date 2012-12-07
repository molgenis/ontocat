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

 import java.net.URI;

 import org.apache.log4j.Logger;
 import org.junit.Before;
 import org.junit.BeforeClass;
 import org.junit.Test;

 import uk.ac.ebi.ontocat.OntologyService;
 import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
 import uk.ac.ebi.ontocat.OntologyServiceException;
 import uk.ac.ebi.ontocat.OntologyTerm;
 import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
 import uk.ac.ebi.ontocat.file.FileOntologyService;
 import uk.ac.ebi.ontocat.ols.OlsOntologyService;
 import uk.ac.ebi.ontocat.virtual.CompositeDecorator;

 public class OntologyTermContextTest {
	 protected static OntologyService os;
	 protected static final Logger log = Logger.getLogger(OntologyTermContextTest.class);

	 @BeforeClass
	 public static void setUpBeforeClass() throws Exception {
		 OntologyService ols = new OlsOntologyService();
		 OntologyService bioportal = new BioportalOntologyService();
		 OntologyService efoowl = new FileOntologyService(new URI("http://www.ebi.ac.uk/efo"));

		 os = CompositeDecorator.getService(ols, bioportal, efoowl);
	 }

	 @Before
	 public void setUp() throws Exception {
	 }

	 @Test
	 public void testGetContext() throws OntologyServiceException {
		 for (OntologyTerm ot : os.searchAll("thymus", SearchOptions.INCLUDE_PROPERTIES
		 )) {
			 log.info(ot);
			 log.info(ot.getContext());
		 }
	 }
 }
