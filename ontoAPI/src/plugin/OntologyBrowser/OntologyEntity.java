/**
 * 
 */
package plugin.OntologyBrowser;

/**
 * Ontology interface, e.g. EFO, OBI
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 * @version $Id$
 */
public interface OntologyEntity {

	public String getSynonymSlot();

	public String getPreferredNameSlot();

	public String getVersionNumber();

	public String getDateReleased();

	public String getDisplayLabel();

	public String getAbbreviation();

	public String getOntologyAccession();

}