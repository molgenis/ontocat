package uk.ac.ebi.ontocat.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import uk.ac.ebi.ontocat.BioportalServiceTest;
import uk.ac.ebi.ontocat.FileOntologyServiceTest;
import uk.ac.ebi.ontocat.OlsOntologyServiceTest;

// based on example taken from:
// http://radio.javaranch.com/lasse/2006/07/27/1154024535662.html

@RunWith(Suite.class)
@Suite.SuiteClasses( { BioportalServiceTest.class,
		OlsOntologyServiceTest.class, FileOntologyServiceTest.class })
public class TestCoreServices {
	// the class remains completely empty,
	// being used only as a holder for the above annotations
}