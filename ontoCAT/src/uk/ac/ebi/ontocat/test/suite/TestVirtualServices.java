package uk.ac.ebi.ontocat.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import uk.ac.ebi.ontocat.test.CachedDecoratorTest;
import uk.ac.ebi.ontocat.test.SortedSubsetTest;
import uk.ac.ebi.ontocat.test.TranslatedOntologyServiceTest;
import uk.ac.ebi.ontocat.virtual.CompositeDecorator;

// based on example taken from:
// http://radio.javaranch.com/lasse/2006/07/27/1154024535662.html

@RunWith(Suite.class)
@Suite.SuiteClasses( { CachedDecoratorTest.class, CompositeDecorator.class,
		SortedSubsetTest.class, TranslatedOntologyServiceTest.class })
public class TestVirtualServices {
	// the class remains completely empty,
	// being used only as a holder for the above annotations
}