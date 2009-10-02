package uk.ac.ebi.efo.test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.semanticweb.owl.model.OWLAnnotation;

import uk.ac.ebi.efo.api.WrapperAnnotation;

/**
 * @author $Id$
 * 
 */

public class WrapperAnnotationTest {

	@Test
	public void testGetValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testDropSourceAndDate() {
		OWLAnnotation annot = null;
		System.out
				.println(new WrapperAnnotation(annot)
						.dropSourceAndDate("HL[accessedResource: /bioportal/virtual/1009/DOID:8567][accessDate: 2009-07-29 08:11:12.388 PDT]"));
	}

	@Test
	public void testStripAndSort() {
		OWLAnnotation annot = null;
		System.out.println(new WrapperAnnotation(annot)
				.StripAndSort("Osteogenic Sarcoma\" EXACT [NCI2004_11_17:C9145]\""));

	}
}
