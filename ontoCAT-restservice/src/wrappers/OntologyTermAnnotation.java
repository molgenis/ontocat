package wrappers;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="annotation")
@XmlAccessorType(XmlAccessType.FIELD)
public class OntologyTermAnnotation
{
	public OntologyTermAnnotation()
	{
		
	}
	
	public OntologyTermAnnotation(String type, String termAccession)
	{
		this.type = type;
		this.value = termAccession;
	}
	
	public String type;
	public String value;
}
