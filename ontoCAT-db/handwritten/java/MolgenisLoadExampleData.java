import java.io.File;

import org.apache.log4j.Logger;
import org.molgenis.MolgenisOptions;
import org.molgenis.framework.db.Database;

import app.CsvImport;
import app.JDBCDatabase;


public class MolgenisLoadExampleData
{
	static Logger logger = Logger.getLogger(MolgenisLoadExampleData.class);
	
	public static void main(String[] args) throws Exception
	{		
		MolgenisOptions options = new MolgenisOptions();
		File directory = new File(options.example_data_dir);
		Database db = new JDBCDatabase("molgenis.properties");
		
		logger.debug("Trying to import example data from path '"+directory+"'");
		CsvImport.importAll(directory, db, null);
	}

}
