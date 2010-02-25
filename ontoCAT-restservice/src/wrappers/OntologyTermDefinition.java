package wrappers;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "definition")
@XmlAccessorType(XmlAccessType.FIELD)
public class OntologyTermDefinition
{
	public OntologyTermDefinition()
	{

	}

	public OntologyTermDefinition(List<String> definitions)
	{
		//System.out.println("definitions " + definitions);
		for (String definition : definitions)
			this.definition += definition;
	}

	@XmlValue
	public String definition = "";

}
