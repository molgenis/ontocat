package uk.ac.ebi.ontocat;


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
