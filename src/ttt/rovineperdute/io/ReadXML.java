package ttt.rovineperdute.io;

import ttt.rovineperdute.contents.graph.Node;
import ttt.rovineperdute.io.elements.City;
import ttt.rovineperdute.io.elements.Link;
import ttt.utils.console.output.GeneralFormatter;
import ttt.utils.xml.document.XMLDocument;
import ttt.utils.xml.engine.XMLEngine;
import ttt.utils.xml.engine.interfaces.IXMLElement;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadXML {

    private XMLDocument doc;
    private File file;
    private final HashMap<Integer, Node> gia_letti = new HashMap<>();
    private List<IXMLElement> city;

    private static Node end;

    public ReadXML(File file) {
        this.file = file;
        doc = new XMLDocument(null);
        readDocument();
    }

    private void readDocument() {
        try { // lettura xml
            XMLEngine engine = new XMLEngine(file, ttt.rovineperdute.io.elements.Map.class, City.class, Link.class);
            engine.morph(doc);
            city = doc.getRoot().getElements();
        } catch (IOException e) {
            GeneralFormatter.printOut("Non è stato possibile leggere il file...", true, true);
        }
    }

    public Node putCityInGraph() {
        City campo_base = (City) city.get(0);
        end = new Node((City) city.get(city.size() - 1));
        Node first = new Node(campo_base);
        generateNode(first);
        return first;
    }

    private void generateNode(Node n) {
        gia_letti.put(n.getCity().getId(), n);
        n.getCity().getElements().stream().forEachOrdered(e -> {
            Link l = (Link) e;
            if (gia_letti.containsKey(l.getId())) {
                n.addNode(gia_letti.get(l.getId()));
            } else {
                Node m = new Node((City) city.get(l.getId()));
                generateNode(m);
                n.addNode(m);
            }
        });
        /*for (IXMLElement e : n.getCity().getElements()) {
            Link l = (Link) e;
            if (gia_letti.containsKey(l.getId())) {
                n.addNode(gia_letti.get(l.getId()));
            } else {
                Node m = new Node((City) city.get(l.getId()));
                generateNode(m);
                n.addNode(m);
            }
        }*/
        //n.computeCalcs();
    }

    public XMLDocument getDocument() {
        if (doc != null) {
            return doc;
        }
        return null;
    }

    public static Node getEnd() {
        return end;
    }

    public Map<Integer, Node> getNodes() {
        return Collections.unmodifiableMap(gia_letti);
    }

}
