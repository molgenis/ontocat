/**
 * 
 */
package uk.ac.ebi.efo.bioportal.xmlbeans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author $Id$
 * 
 */
public class SuccessBean {
	private String accessedResource;
	private String accessDate;
	private static final String urlBASE = "http://rest.bioontology.org";

	/**
	 * @return the accessedResource
	 */
	public String getAccessedResource() {
		return urlBASE + accessedResource;
	}

	/**
	 * @return the accessDate
	 */
	public String getAccessDate() {
		Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
		Matcher matcher = pattern.matcher(accessDate);
		if (matcher.find()) {
			accessDate = matcher.group();
		}
		return accessDate;
	}
}
