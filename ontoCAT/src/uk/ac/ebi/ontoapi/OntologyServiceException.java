package uk.ac.ebi.ontoapi;

/**
 * @author Tomasz Adamusiak, Morris Swertz
 * @version $Id$
 */
public class OntologyServiceException extends Exception {

	public OntologyServiceException(String str)
	{
		super(str);
	}
	
	public OntologyServiceException(Exception e) {
		super(e);
	}
}
