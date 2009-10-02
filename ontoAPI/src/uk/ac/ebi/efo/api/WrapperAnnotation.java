/**
 * 
 */
package uk.ac.ebi.efo.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.semanticweb.owl.model.OWLAnnotation;

/**
 * Collection of methods providing extra functionality in manipulating
 * annotations.
 * 
 * @author $Id: WrapperAnnotation.java 9019 2009-09-22 12:39:01Z tomasz $
 */
public class WrapperAnnotation {
	@SuppressWarnings("unchecked")
	private final OWLAnnotation _annotation;

	/**
	 * Default constructor
	 * 
	 * @param value
	 *            annotation to be processed
	 */
	@SuppressWarnings("unchecked")
	public WrapperAnnotation(OWLAnnotation value) {
		_annotation = value;
	}

	/**
	 * @return text value stored in the annotation
	 */
	public String getComparableValue() {
		String s = _annotation.getAnnotationValueAsConstant().getLiteral();
		return StripAndSort(dropSourceAndDate(dropQuotationMarks(s)));
	}

	public String getValue() {
		return _annotation.getAnnotationValueAsConstant().getLiteral();
	}

	/**
	 * This is specifically implemented to drop extra information from synonym
	 * annotations, e.g. "Osteogenic Sarcoma" EXACT [NCI2004_11_17:C9145] If
	 * there are no quotation marks the string remains unaltered
	 * 
	 * @return string enclosed in quotation marks.
	 */
	private String dropQuotationMarks(String s) {
		// drops the [] from synonyms
		Pattern pattern = Pattern.compile("\"(.*)\"");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			s = matcher.group(1);
		}
		return s;
	}

	public String StripAndSort(String s) {
		// lower case
		s = s.toLowerCase();
		// fix for badly loaded NCIt annotations
		s = s.replace("&amp;#39;", "'");
		// split on non alfanumeric
		String[] arr = s.split("[^a-z0-9]");
		// sort
		java.util.Arrays.sort(arr);
		// join
		String result = "";
		for (String t : arr)
			result += t;
		return result;
	}

	/**
	 * This is to remove source and date from annotations for comparisons
	 * 
	 * @return string without source and date
	 */
	public String dropSourceAndDate(String s) {
		// drops the [accessedResource: .*] and [accessDate: .*] from
		// annotations
		Pattern pResource = Pattern.compile("\\[accessedResource: .*\\]");
		Pattern pDate = Pattern.compile("\\[accessDate: .*\\]");

		Matcher matcher = pResource.matcher(s);
		if (matcher.find())
			s = matcher.replaceAll("");
		matcher = pDate.matcher(s);
		if (matcher.find())
			s = matcher.replaceAll("");

		return s;
	}

	public String getSource() {
		String s = _annotation.getAnnotationValueAsConstant().getLiteral();
		Pattern pResource = Pattern.compile("\\[accessedResource: (.*?)\\]");

		Matcher matcher = pResource.matcher(s);
		if (matcher.find())
			return matcher.group(1);

		return "";
	}
}
