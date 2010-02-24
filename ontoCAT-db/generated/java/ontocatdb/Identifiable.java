
/* File:        ontocatdb/model/Identifiable.java
 * Copyright:   GBIC 2000-2,010, all rights reserved
 * Date:        February 24, 2010
 * Generator:   org.molgenis.generators.DataTypeGen 3.3.2-testing
 *
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */

package ontocatdb;


/**
 * Identifiable:  (For implementation purposes) The Identifiable interface
				provides its sub-classes with a unique numeric identifier within the scope
				of one database. This class maps to FuGE::Identifiable (together with
				Nameable interface)
.
 * @version February 24, 2010 
 * @author MOLGENIS generator
 */
public interface Identifiable extends org.molgenis.util.Entity
{
	public Integer getId();
	public void setId(Integer _id);

}

