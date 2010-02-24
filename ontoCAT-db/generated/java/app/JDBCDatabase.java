/* File:        ontocatdb/model/JDBCDatabase
 * Copyright:   GBIC 2000-2,010, all rights reserved
 * Date:        February 24, 2010
 * 
 * generator:   org.molgenis.generators.db.JDBCDatabaseGen 3.3.2-testing
 *
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */

package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.jdbc.DataSourceWrapper;
import org.molgenis.framework.db.jdbc.SimpleDataSourceWrapper;

public class JDBCDatabase extends org.molgenis.framework.db.jdbc.JDBCDatabase
{
	public JDBCDatabase(DataSource data_src, File file_source) throws DatabaseException
	{
		this(new SimpleDataSourceWrapper(data_src), file_source);
	}

	public JDBCDatabase(DataSourceWrapper data_src, File file_src) throws DatabaseException
	{
		super(data_src, file_src, new JDBCMetaDatabase());
		this.setup();
	}

	public JDBCDatabase(Properties p)
	{
		super(p);
		this.setup();
	}

	public JDBCDatabase(String propertiesFilePath) throws FileNotFoundException, IOException, DatabaseException
	{
		super(propertiesFilePath, new JDBCMetaDatabase());
		this.setup();
	}
	
	private void setup()
	{
		this.putMapper(ontocatdb.Investigation.class, new ontocatdb.db.InvestigationMapper(this));
		this.putMapper(ontocatdb.OntologySource.class, new ontocatdb.db.OntologySourceMapper(this));
		this.putMapper(ontocatdb.OntologyTerm.class, new ontocatdb.db.OntologyTermMapper(this));
		this.putMapper(ontocatdb.Sample.class, new ontocatdb.db.SampleMapper(this));
		this.putMapper(ontocatdb.Sample_annotations.class, new ontocatdb.db.Sample_annotationsMapper(this));
	}
}