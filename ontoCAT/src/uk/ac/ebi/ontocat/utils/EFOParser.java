package uk.ac.ebi.ontocat.utils;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;
import uk.ac.ebi.ontocat.virtual.LocalisedFileService;

import java.net.URI;

public final class EFOParser{

	private static EFOParser EFO;
    public OntologyService os;
	/** A private Constructor prevents any other class from instantiating. */
	private EFOParser() {
		try {

            os = LocalisedFileService.getService(new ReasonedFileOntologyService(new URI(
                    "http://www.ebi.ac.uk/efo/efo.owl"), "efo"));


            //((FileOntologyService) os).setSynonymSlot("alternative_term");
        } catch (Exception e) {
            System.out.println("Sorry, can't create Ontology object for EFO by using URL \"http://www.ebi.ac.uk/efo/efo.owl\".");

        }
	}

	public static synchronized EFOParser getEFO() {
		if (EFO == null) {
			EFO = new EFOParser();
		}

		return EFO;
	}
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
