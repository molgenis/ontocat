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
package uk.ac.ebi.ontocat.examples;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.CompositeDecorator;
import uk.ac.ebi.ook.web.services.Query;
import uk.ac.ebi.ook.web.services.QueryServiceLocator;

public class ServiceComparison {
	private static final Logger log = Logger.getLogger(ServiceComparison.class);

	public static void main(String[] args) throws OntologyServiceException,
	XPathExpressionException, IOException, ParserConfigurationException, SAXException,
	ServiceException {
		queryOLS();
		queryBioportal();
		queryOntoCAT();

	}

	private static void queryBioportal() throws IOException, ParserConfigurationException,
	SAXException, XPathExpressionException {
		String query = "thymus";
		// Create a REST URL
		URL urlQuery = new URL("http://rest.bioontology.org/bioportal/search/" + query
				+ "/?isexactmatch=0" + "&includeproperties=1" + "&maxnumhits=10000000"
				+ "&email=ontocat-svn@lists.sourceforge.net");

		// Prepare XML parser
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Submit and parse query
		Document doc = builder.parse(urlQuery.openStream());
		XPathFactory XPfactory = XPathFactory.newInstance();
		XPath xpath = XPfactory.newXPath();
		XPathExpression expr = xpath.compile("//searchResultList/searchBean");
		NodeList terms = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

		// Iterate over result set
		for (int i = 0; i < terms.getLength(); i++) {
			String ontologyAccession = (String) xpath.evaluate("ontologyId", terms.item(i),
					XPathConstants.STRING);
			String termAccession = (String) xpath.evaluate("conceptIdShort", terms.item(i),
					XPathConstants.STRING);
			String label = (String) xpath.evaluate("preferredName", terms.item(i),
					XPathConstants.STRING);
			// System.out.println("termAccession: " + termAccession +
			// "\tontologyAccession: "
			// + ontologyAccession + "\tlabel: " + label);
		}

		log.info(terms.getLength() + " BP terms");
	}

	private static void queryOLS() throws RemoteException, ServiceException {
		// Instantiate OLS client
		Query qs = null;
		qs = new QueryServiceLocator().getOntologyQuery();

		// Search for terms containing thymus
		String query = "thymus";
		Set<Map.Entry<String, String>> terms = qs.getPrefixedTermsByName(query, false).entrySet();

		// Iterate over result set
		for (Map.Entry<String, String> entry : terms) {
			String termAccession = entry.getKey();
			String ontologyAccession = entry.getValue().split(":")[0];
			String label = entry.getValue().split(":")[1];
			// System.out.println("termAccession: " + termAccession +
			// "\tontologyAccession: "
			// + ontologyAccession + "\tlabel: " + label);
		}

		log.info(terms.size() + " OLS terms");
	}

	private static void queryOntoCAT() throws OntologyServiceException {
		String query = "thymus";
		// Instantiate composite OntologyService consisting of
		// both BioPortal and OLS
		List<OntologyService> lOntologies = new ArrayList<OntologyService>();
		lOntologies.add(new BioportalOntologyService());
		lOntologies.add(new OlsOntologyService());
		OntologyService os = CompositeDecorator.getService(lOntologies);

		// Search for terms containing thymus
		List<OntologyTerm> terms = os.searchAll(query, SearchOptions.INCLUDE_PROPERTIES);

		// Iterate over result set
		for (OntologyTerm ot : terms) {
			// System.out.println("termAccession: " + ot.getAccession() +
			// "\tontologyAccession: "
			// + ot.getOntologyAccession() + "\tlabel: " + ot.getLabel());
		}
		log.info(terms.size() + " CAT terms");
	}
}
