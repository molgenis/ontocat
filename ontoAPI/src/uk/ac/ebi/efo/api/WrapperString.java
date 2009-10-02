/**
 * 
 */
package uk.ac.ebi.efo.api;

import java.net.URI;

/**
 * Collection of methods providing extra functionality in manipulating strings.
 * 
 * @author $Id: WrapperString.java 8273 2009-07-29 17:10:50Z tomasz $
 */
public class WrapperString {
	private String _s;
	private URI _u;

	public WrapperString(String s) {
		_s = s;
	}

	public WrapperString(URI u) {
		_u = u;
	}

	/**
	 * This could be done with URI.getFragment() but EFO doesn't use # delimiter
	 * for the fragment, so the above method will fail.
	 * 
	 * @return uri fragment of an ontology
	 */
	public String getFragmentFromURI() {
		String[] arr = _u.toString().split("/|#");
		return arr[arr.length - 1];
	}

	/**
	 * @return returns the path part of an uri
	 */
	public String getPathFromUri() {
		String s = _u.toString();
		return s.substring(0, s.indexOf(getFragmentFromURI()) - 1);
	}

	/**
	 * @return id part of qualifier in source ontology, e.g. DOID from
	 *         DOID_000023
	 */
	public String getIDFromURI() {
		String[] arr = getFragmentFromURI().split(":|_");
		return arr[0];
	}

}
