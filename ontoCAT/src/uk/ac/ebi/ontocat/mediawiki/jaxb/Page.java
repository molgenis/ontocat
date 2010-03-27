package uk.ac.ebi.ontocat.mediawiki.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="p")
public class Page
{
	@XmlAttribute
	public String pageid;
	@XmlAttribute
	public String title;
	
	public String toString()
	{
		return String.format("Page(pageid=%s, title=%s)",pageid,title);
	}
}
