package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.DatabaseException;

import app.JDBCDatabase;
import example.Sample;
import example.SpecialSample;

/**
 * Servlet implementation class StaticMethods
 */
public class StaticMethods
{
	public static void main(String[] args) throws DatabaseException, FileNotFoundException, IOException, ParseException
	{
		Database db = new JDBCDatabase("molgenis.properties");

		List<Sample> result = SpecialSample.find(db);
		for(Sample s: result) System.out.println(result);
		
		List<SpecialSample> result2 = Sample.query(db).equals("name","Test").find();
		for(Sample s: result2) System.out.println(s);
	}
}