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

 import java.io.IOException;
 import java.net.HttpURLConnection;
 import java.net.URL;

 import org.apache.log4j.Logger;
 import org.junit.Before;
 import org.junit.BeforeClass;
 import org.junit.Test;


 public class ResponseCodesTest {


	 protected static final Logger log = Logger.getLogger(AbstractOntologyServiceTest.class);

	 @BeforeClass
	 public static void setUpBeforeClass() throws Exception {
	 }

	 @Before
	 public void setUp() throws Exception {
	 }

	 @Test
	 public void testCodes() throws IOException {
		 URL url = new URL(
		 "http://rest.bioontology.org/bioportal/virtual/children/1070/GO:0043227?email=example@example.org");

		 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		 log.info(conn.getResponseCode());
		 log.info(conn.getResponseMessage());

	 }

 }
