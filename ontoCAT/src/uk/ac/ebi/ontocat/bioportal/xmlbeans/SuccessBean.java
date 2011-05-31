/**
 * Copyright (c) 2010 - 2011 European Molecular Biology Laboratory and University of Groningen
 *
 * Contact: ontocat-users@lists.sourceforge.net
 * 
 * This file is part of OntoCAT
 * 
 * OntoCAT is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * 
 * OntoCAT is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along
 * with OntoCAT. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.ac.ebi.ontocat.bioportal.xmlbeans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tomasz Adamusiak
 * 
 */
public class SuccessBean {
	private String accessedResource;
	private String accessDate;
	private Integer numPages;
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

	public Integer getNumberOfPages() {
		return numPages;
	}
}
