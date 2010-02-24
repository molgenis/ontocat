import org.molgenis.model.JDBCModelExtractor;

/**
 * Extracts a MOLGENIS model from an existing database.
 * Set the molgenis.properties db* settings and run...
 */
public class MolgenisExtractModel
{
	public static void main(String[] args) throws Exception
	{
		JDBCModelExtractor.main(args);
	}
}
