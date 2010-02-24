package plugins.examples.helloworld;

public class HelloWorldModel  
{
	private String name = "noname";
	private String errorMessage = "";
	
	public String getHelloWorld()
	{
		return "Hello "+this.getName();
	}

	//Simply a name
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	//To show how errors can be shown
	public String getErrorMessage()
	{
		return errorMessage;
	}
	
	public void setErrorMessage(String message)
	{
		this.errorMessage = message;
		
	}		
}
