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


 import java.util.List;

 import org.apache.log4j.Logger;
 import org.junit.Test;

 import uk.ac.ebi.ontocat.OntologyService;
 import uk.ac.ebi.ontocat.OntologyServiceException;
 import uk.ac.ebi.ontocat.OntologyTerm;
 import uk.ac.ebi.ontocat.conceptwiki.ConceptWikiOntologyService;

 public class MediaWikiOntologyServiceTest {

	 Logger logger = Logger.getLogger(MediaWikiOntologyServiceTest.class);

	 @Test
	 public void firstTest() throws OntologyServiceException
	 {
		 logger.info("testing searchAll");
		 OntologyService mediawiki = new ConceptWikiOntologyService("http://en.wikipedia.org");
		 List<OntologyTerm> result = mediawiki.searchAll("Flavono");

		 for(OntologyTerm t: result)
		 {
			 logger.debug("found: "+t);
		 }

		 logger.info("testing getTerm");
		 OntologyTerm t = mediawiki.getTerm("Flavonol");
		 logger.debug("found: "+t);

		 logger.info("testing getTerm");
		 t = mediawiki.getTerm("Flavono");
		 logger.debug("found: "+t);

		 logger.info("testing definitions");
		 for(String s: mediawiki.getDefinitions(null, "Albert_Einstein"))
		 {
			 logger.info("found: "+s);
		 }
	 }
 }
