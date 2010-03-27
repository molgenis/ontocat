package uk.ac.ebi.ontocat.mediawiki.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class Query
{
	@XmlElementWrapper(name="pages")
	@XmlElement(name="page")
	public List<Page> pages;
	
	public String toString()
	{
		String result = "";
		
		for(Page p: pages)
		{
			result +="\n"+p.toString();
		}
		
		return result;
	}
}
