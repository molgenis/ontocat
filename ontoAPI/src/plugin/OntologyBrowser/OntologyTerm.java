/**
 * 
 */
package plugin.OntologyBrowser;

/**
 * @author Tomasz Adamusiak, Morris Swertz
 * @version $Id$
 */
public interface OntologyTerm {
	/**
	 * Gets the accession.
	 * 
	 * @return the accession
	 */
	public String getAccession();

	/**
	 * Gets the ontology accession.
	 * 
	 * @return the ontology accession
	 */
	public String getOntologyAccession();

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel();
}
