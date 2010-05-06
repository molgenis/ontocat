package uk.ac.ebi.ontocat.examples;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
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

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ook.web.services.Query;
import uk.ac.ebi.ook.web.services.QueryServiceLocator;

public class ServiceComparison {
	public static void main(String[] args) throws OntologyServiceException,
			XPathExpressionException, IOException, ParserConfigurationException, SAXException {
		// queryOLS();
		queryBioportal();

	}

	private static void queryBioportal() throws IOException, ParserConfigurationException,
			SAXException, XPathExpressionException {
		// Create a REST URL
		String query = "thymus";
		URL urlQuery = new URL("http://rest.bioontology.org/bioportal/search/" + query
				+ "?email=ontocat-svn@lists.sourceforge.net&isexactmatch=0&includeproperties=1");

		// Parse the XML result
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(urlQuery.openStream());

		XPathFactory XPfactory = XPathFactory.newInstance();
		XPath xpath = XPfactory.newXPath();
		XPathExpression expr = xpath.compile("//searchResultList/searchBean");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;

		// Iterate over ontology term nodes
		for (int i = 0; i < nodes.getLength(); i++) {
			NodeList childNodes = nodes.item(i).getChildNodes();
			String ontologyAccession = null, termAccession = null, label = null;
			for (int ii = 0; ii < childNodes.getLength(); ii++) {
				Node currentNode = childNodes.item(ii);
				if (currentNode.getNodeName().equalsIgnoreCase("ontologyId")) 
					ontologyAccession = currentNode.getTextContent();
				if (currentNode.getNodeName().equalsIgnoreCase("conceptIdShort")) 
					termAccession = currentNode.getTextContent();
				if (currentNode.getNodeName().equalsIgnoreCase("preferredName"))
					label = currentNode.getTextContent();
			}
			System.out.println("termAccession: " + termAccession + "\tontologyAccession: "
					+ ontologyAccession + "\tlabel: " + label);

		}
	}

	private static void queryOLS() throws RemoteException, ServiceException {
		// Instantiate OLS client
		Query qs = null;

		qs = new QueryServiceLocator().getOntologyQuery();

		// Search for terms containing thymus
		Set<Map.Entry<String, String>> mTerms = null;

		mTerms = qs.getPrefixedTermsByName("thymus", false).entrySet();

		// Iterate over result set
		for (Map.Entry<String, String> entry : mTerms) {
			String termAccession = entry.getKey();
			String ontologyAccession = entry.getValue().split(":")[0];
			String label = entry.getValue().split(":")[1];
			System.out.println("termAccession: " + termAccession + "\tontologyAccession: "
					+ ontologyAccession + "\tlabel: " + label);
		}
	}

}
