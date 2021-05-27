package ttt.rovineperdute.io;

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
import java.util.Stack;
import ttt.rovineperdute.contents.graph.Node;

public class ReadXML {

    private final XMLDocument doc;
    private final File file;
    private final HashMap<Integer, Node> gia_letti = new HashMap<>();
    private List<IXMLElement> city;

    private Node end;

    public ReadXML(File file) {
        this.file = file;
        doc = new XMLDocument(null);
        readDocument();
    }

    /**
     * Legge il documento XML e lo salva in #doc.
     */
    private void readDocument() {
        try { // lettura xml
            XMLEngine engine = new XMLEngine(file, ttt.rovineperdute.io.elements.Map.class, City.class, Link.class);
            engine.morph(doc);
            city = doc.getRoot().getElements();
        } catch (IOException e) {
            GeneralFormatter.printOut("Non è stato possibile leggere il file...", true, true);
        }
    }

    /**
     * Inizializza la prima città della mappa.
     * @return Nodo del "Campo Base".
     */
    public Node putCityInGraph() {
        City campo_base = (City) city.get(0);
        end = new Node((City) city.get(city.size() - 1));
        Node first = new Node(campo_base);
        generateNode(first);
        return first;
    }

    /**
     * Genera i nodi tra le città.
     * @param n Il nodo di partenza
     */
    private void generateNode(Node n) {
        Stack<Node> da_leggere = new Stack<>();
        da_leggere.addElement(n);
        while (!da_leggere.isEmpty()) {
            Node v = da_leggere.pop();
            for (IXMLElement e : v.getCity().getElements()) {
                Link l = (Link) e;
                if (gia_letti.containsKey(l.getId())) {
                    v.addNode(gia_letti.get(l.getId()));
                } else {
                    Node m = new Node((City) city.get(l.getId()));
                    v.addNode(m);
                    da_leggere.push(m);
                }
            }
            gia_letti.put(v.getCity().getId(), v);
        }
    }

    @Deprecated
    private void generateNodeRecursion(Node n) {
        Stack<Node> da_leggere = new Stack<>();
        da_leggere.addElement(n);
        for (IXMLElement e : n.getCity().getElements()) {
            Link l = (Link) e;
            if (gia_letti.containsKey(l.getId())) {
                n.addNode(gia_letti.get(l.getId()));
            } else {
                Node m = new Node((City) city.get(l.getId()));
                da_leggere.addElement(m);
                n.addNode(m);
            }
        }
    }

    /**
     * Ritorna il documento creato dalla lettura del file.
     * @return Documento creato dalla lettura del file.
     */
    public XMLDocument getDocument() {
        if (doc != null) {
            return doc;
        }
        return null;
    }

    /**
     * Ritorna il nodo finale (le rovine perdute).
     * @return Nodo finale.
     */
    public Node getEnd() {
        return end;
    }

    /**
     * Ritorna la mappa ID-nodi letti dal file XML.
     * @return Mappa ID-nodi letti dal file XML.
     */
    public Map<Integer, Node> getNodes() {
        return Collections.unmodifiableMap(gia_letti);
    }

}
