/**
 * 
 */
package uk.ac.ebi.efo.api;

import jargs.gnu.CmdLineParser;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.semanticweb.owl.model.OWLOntologyChangeException;

import plugin.OntologyBrowser.OntologyServiceException;
import uk.ac.ebi.efo.api.WrapperOntology.AnnotationFragmentNotFoundException;

/**
 * Command line tool employing {@link BioportalImporter}.
 * 
 * @see BioportalImporter
 * @author $Id: ScriptMain.java 8819 2009-09-07 16:06:43Z tomasz $
 */
public class ScriptMain {
	private static final Logger _log = Logger.getLogger(ScriptMain.class.getName());

	private static URI uriTargetOntology;
	private static URI uriOutputOntology;
	private static String email;

	/**
	 * Invokes BioportalImporter constructor.
	 * 
	 * @param args
	 *            command line arguments
	 * @throws TransformerException
	 * @throws IOException
	 * @throws AnnotationFragmentNotFoundException
	 * @throws InterruptedException
	 * @throws OWLOntologyChangeException
	 * @throws OntologyServiceException
	 */
	public static void main(String[] args) throws AnnotationFragmentNotFoundException, IOException,
			TransformerException, OWLOntologyChangeException, InterruptedException, OntologyServiceException {
		ParseCmdLine(args);

		BioportalImporter importer = new BioportalImporter(uriTargetOntology, uriOutputOntology, email);

		if (importer.processImport() == 0) {
			_log.error("Import failed. Nothing was imported!");
		} else {
			_log.info("IMPORT COMPLETED");
		}
	}

	/**
	 * Parses command line arguments
	 * 
	 * @param args
	 */
	private static void ParseCmdLine(String args[]) {
		_log.debug("Parsing command line arguments");
		CmdLineParser parser = new CmdLineParser();
		CmdLineParser.Option oTarget = parser.addStringOption("uriTarget");
		CmdLineParser.Option oOutput = parser.addStringOption("uriOutput");
		CmdLineParser.Option oEmail = parser.addStringOption("email");

		try {
			parser.parse(args);
		} catch (CmdLineParser.OptionException e) {
			_log.fatal(e.getMessage());
			print_usage();
		}

		try {
			uriTargetOntology = new File((String) parser.getOptionValue(oTarget)).toURI();
			uriOutputOntology = new File((String) parser.getOptionValue(oOutput)).toURI();
			email = parser.getOptionValue(oEmail).toString();

		} catch (Exception e) {
			print_usage();
		}
	}

	/**
	 * Prints the correct usage of the script
	 */
	public static void print_usage() {
		_log.fatal("Invalid usage");
		String s = "\nExample of correct usage:\n" + "java -jar OntologyMappingImporter.jar\n"
				+ "\t\t--uriTarget efo.owl\n" + "\t\t--uriOutput output.owl\n" + "\t\t--email example@email.com\n"
				+ "\rWhere *uri-* arguments point to respective ontology files on the file system\n"
				+ "and email string will be added to all Bioportal REST queries. \n";

		System.out.println(s);
		System.exit(2);
	}
}
