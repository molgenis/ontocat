import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.molgenis.MolgenisOptions;
import org.molgenis.framework.ui.html.FileInput;
import org.molgenis.framework.ui.html.SelectInput;
import org.molgenis.generators.doc.DotDocGen;
import org.molgenis.model.MolgenisModel;
import org.molgenis.model.elements.Model;
import org.molgenis.util.HttpServletRequestTuple;
import org.molgenis.util.Tuple;

public class GeneratorServlet extends HttpServlet
{
	static Logger logger = Logger.getLogger(GeneratorServlet.class);
	
	public enum Action
	{
		get_preview_image, get_preview_svg, get_molgenis_demo, get_molgenis_jar, get_molgenis_war;

		public static String[] stringValues()
		{
			String[] values = new String[Action.values().length];
			int i = 0;
			for (Action a : Action.values())
			{
				values[i++] = a.toString();
			}
			return values;
		}
	};
	
	static final String MOLGENIS_DB = "molgenis_db";

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		try
		{
			Tuple requestTuple = new HttpServletRequestTuple(request);
			logger.debug("handling request: "+requestTuple);
			if (requestTuple.getAction() != null)
			{
				Action action = Action.valueOf(requestTuple.getAction());
				switch (action)
				{
					case get_preview_image:
						// returns a png file
						getPreviewImage(requestTuple, response);
						break;
					case get_preview_svg:
						// returns a svg file
						break;
					case get_molgenis_demo:
						// links to an iframe that refreshes until the system is
						// ready
						break;
					case get_molgenis_jar:
						// links to an iframe that produces the system
						break;
					case get_molgenis_war:
						// links to an iframe that produces the system
						break;
				}
			}
			else
			{
				// show simple dialog to test the service
				PrintWriter out = response.getWriter();
				out.println("<form method=\"GET\" enctype=\"multipart/form-data\">");

				// choose the service
				SelectInput actionInput = new SelectInput("__action", null);
				actionInput.setOptions(Action.stringValues());
				out.print("Choose service:" + actionInput.toHtml() + "<br/>");

				// upload model files
				FileInput dbInput = new FileInput(MOLGENIS_DB, null);
				out.print("Upload db model file:" + dbInput.toHtml() + "<br/>");

				FileInput uiInput = new FileInput("molgenis_ui", null);
				out.print("Upload ui model file:" + uiInput.toHtml() + "<br/>");

				// submit button
				out.println("<input type=\"submit\" value=\"submit\">");

				out.println("</form>");

			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getPreviewImage(Tuple request, HttpServletResponse response) throws Exception
	{	
	    File tempDir = (File) getServletContext().
        getAttribute( "javax.servlet.context.tempdir" );
		
		File outputDir = File.createTempFile("molgenis_preview","",tempDir);
		outputDir.delete();
		outputDir.mkdirs();
		
		//relevant options
		MolgenisOptions options = new MolgenisOptions();
		options.setModel_database((request.getFile("filefor_"+MOLGENIS_DB).getAbsolutePath()));
		options.output_doc = outputDir.getAbsolutePath();
		
		//execute
		Model model = MolgenisModel.parse(options);
		new DotDocGen().generate(model, options,true);
		
		//generates a file named entity-uml-diagram.png
		File file = new File(outputDir.getCanonicalPath()+"/entity-uml-diagram.dot.png");
		logger.debug("serving file: '"+file+"' "+Arrays.asList(outputDir.list()));

		InputStream in = new BufferedInputStream(new FileInputStream(file));
		String mimetype = new MimetypesFileTypeMap().getContentType(file);
		logger.debug("mimetype for " + file + ": " + mimetype);
		response.setContentType(mimetype);

		OutputStream out = response.getOutputStream();
		byte[] buffer = new byte[2048];
		for( ;; )
		{
			int nBytes = in.read(buffer);
			if( nBytes <= 0 )
				break;
			out.write(buffer, 0, nBytes);
		}
		out.flush();
		out.close();
		
	}
}
