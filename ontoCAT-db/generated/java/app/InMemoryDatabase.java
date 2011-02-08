/* File:        ontocatdb/model/JDBCDatabase
 * Copyright:   GBIC 2000-2,011, all rights reserved
 * Date:        February 8, 2011
 * 
 * generator:   org.molgenis.generators.db.InMemoryDatabaseGen 3.3.2-testing
 *
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */

package app;

import org.molgenis.framework.db.DatabaseException;
import org.molgenis.model.elements.Model;

//import ontocatdb.JDBCMetaDatabase;
import app.JDBCMetaDatabase;

public class InMemoryDatabase extends org.molgenis.framework.db.inmemory.InMemoryDatabase
{
	public InMemoryDatabase() 
	{
		//naieve impl, much todo
	}
	
	@Override
	public Model getMetaData() throws DatabaseException
	{
		return new JDBCMetaDatabase();
	}
}