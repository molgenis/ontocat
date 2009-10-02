package plugin.OntologyBrowser;

import java.util.Map;

/**
 * Interface for the OntologyTermExt, representing single concepts within ontology,
 * whether classes or individuals.
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 * @version $Id$
 */
public interface OntologyTermExt extends OntologyTerm {
	/**
	 * Gets the metadata.
	 * 
	 * @return the metadata
	 */
	public Map<String, String[]> getMetadata();

	/**
	 * Gets the synonyms.
	 * 
	 * @return the synonyms
	 */
	public String[] getSynonyms();

	/**
	 * Gets the definitions.
	 * 
	 * @return the definitions
	 */
	public String[] getDefinitions();
}