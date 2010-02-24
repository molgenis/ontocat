
/* File:        ontocatdb/model/Nameable.java
 * Copyright:   GBIC 2000-2,010, all rights reserved
 * Date:        February 24, 2010
 * Generator:   org.molgenis.generators.DataTypeGen 3.3.2-testing
 *
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */

package ontocatdb;


/**
 * Nameable:  (For modeling purposes) The Nameable interface provides its
				sub-classes a meaningful name that need not be unique. This class maps to
				FuGE::Identifiable (together with Identifiable interface)
.
 * @version February 24, 2010 
 * @author MOLGENIS generator
 */
public interface Nameable extends org.molgenis.util.Entity
{
	public String getName();
	public void setName(String _name);

}

