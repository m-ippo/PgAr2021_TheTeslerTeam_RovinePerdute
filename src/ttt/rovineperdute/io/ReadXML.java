package ttt.rovineperdute.io;

import ttt.rovineperdute.io.elements.City;
import ttt.rovineperdute.io.elements.Link;
import ttt.rovineperdute.io.elements.Map;
import ttt.utils.console.output.GeneralFormatter;
import ttt.utils.xml.document.XMLDocument;
import ttt.utils.xml.engine.XMLEngine;
import ttt.utils.xml.io.XMLReader;

import java.io.File;
import java.io.IOException;

public class ReadXML {

    private XMLDocument doc;
    private File file;

    public ReadXML(File file){
        this.file = file;
        doc = new XMLDocument(null);
        init();
    }

    private void init(){
        try { // lettura xml
            XMLEngine engine = new XMLEngine(file, Map.class, City.class, Link.class);
            engine.morph(doc);
        } catch (IOException e) {
            GeneralFormatter.printOut("Non è stato possibile leggere il file...", true, true);
            e.printStackTrace();
        }
    }
}
