package ttt.rovineperdute.io;

import ttt.rovineperdute.graph.Node;
import ttt.rovineperdute.io.elements.City;
import ttt.rovineperdute.io.elements.Link;
import ttt.rovineperdute.io.elements.Map;
import ttt.utils.console.output.GeneralFormatter;
import ttt.utils.xml.document.XMLDocument;
import ttt.utils.xml.engine.XMLEngine;
import ttt.utils.xml.engine.interfaces.IXMLElement;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ReadXML {

    private XMLDocument doc;
    private File file;
    private HashMap<Integer, Node> gia_letti = new HashMap<>();
    private List<IXMLElement> city;

    public ReadXML(File file){
        this.file = file;
        doc = new XMLDocument(null);
        readDocument();
    }

    private void readDocument(){
        try { // lettura xml
            XMLEngine engine = new XMLEngine(file, Map.class, City.class, Link.class);
            engine.morph(doc);
            city = doc.getRoot().getElements();
        } catch (IOException e) {
            GeneralFormatter.printOut("Non è stato possibile leggere il file...", true, true);
            e.printStackTrace();
        }
    }

    public Node putCityInGraph(){
        City campo_base = (City) city.get(0);
        Node first = new Node(campo_base);
        generateNode(first);
        return first;
    }

    private void generateNode(Node n){
        gia_letti.put(n.getCity().getId(), n);
        for(IXMLElement e : n.getCity().getElements()){
            Link l = (Link) e;
            if(gia_letti.containsKey(l.getId())){
                n.addNode(gia_letti.get(l.getId()));
            } else {
                Node m = new Node((City) city.get(l.getId()));
                generateNode(m);
                n.addNode(m);
            }
        }
    }

    public XMLDocument getDocument(){
        if(doc != null){
            return doc;
        }
        return null;
    }
}
