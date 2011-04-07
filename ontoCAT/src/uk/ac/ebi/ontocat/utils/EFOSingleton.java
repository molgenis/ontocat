package uk.ac.ebi.ontocat.utils;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;
import uk.ac.ebi.ontocat.virtual.LocalisedFileService;

import java.net.URI;

/**
 * Singleton for EFO OntologyService to prevent more then one EFO instance.
 * <p/>
 * Author: Natalja Kurbatova
 * Date: 2011-03-16
 */
public final class EFOSingleton {

    private static EFOSingleton EFO = null;
    public OntologyService os;

    /**
     * A private Constructor prevents any other class from instantiating.
     */
    private EFOSingleton() {
        try {

            //System.out.println("FIRST TRY FROM URL");
            os = LocalisedFileService.getService(new ReasonedFileOntologyService(new URI(
                    "http://www.ebi.ac.uk/efo/efo.owl"), "efo"));


        } catch (Exception e) {

                System.out.println("Sorry, can't create Ontology object for EFO by using URL \"http://www.ebi.ac.uk/efo/efo.owl\" or local file.");
                //el.printStackTrace();

        }
    }

    public static synchronized EFOSingleton getEFO() {
        if (EFO == null) {
            EFO = new EFOSingleton();
        }

        return EFO;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
