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
package uk.ac.ebi.ontocat.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import uk.ac.ebi.ontocat.CachedDecoratorTest;
import uk.ac.ebi.ontocat.CompositeDecoratorTest;
import uk.ac.ebi.ontocat.SortedSubsetTest;
import uk.ac.ebi.ontocat.TranslatedOntologyServiceTest;

// based on example taken from:
// http://radio.javaranch.com/lasse/2006/07/27/1154024535662.html

@RunWith(Suite.class)
@Suite.SuiteClasses( { CachedDecoratorTest.class, CompositeDecoratorTest.class,
	SortedSubsetTest.class, TranslatedOntologyServiceTest.class })
	public class TestVirtualServices {
	// the class remains completely empty,
	// being used only as a holder for the above annotations
}