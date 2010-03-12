package uk.ac.ebi.ontocat.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import uk.ac.ebi.ontocat.test.BioportalServiceTest;
import uk.ac.ebi.ontocat.test.FileOntologyServiceTest;
import uk.ac.ebi.ontocat.test.OlsOntologyServiceTest;

// based on example taken from:
// http://radio.javaranch.com/lasse/2006/07/27/1154024535662.html

@RunWith(Suite.class)
@Suite.SuiteClasses( { BioportalServiceTest.class,
		OlsOntologyServiceTest.class, FileOntologyServiceTest.class })
public class TestCoreServices {
	// the class remains completely empty,
	// being used only as a holder for the above annotations
}