package ttt.rovineperdute.trackresearch;

import ttt.rovineperdute.io.ReadXML;

import java.util.*;
import ttt.rovineperdute.contents.graph.Node;
import ttt.rovineperdute.trackresearch.interfaces.ICalculable;

public class TrackFinder {

    private final static double THRESHOLD = 0.00001;

    private final Node start_node;
    private final Node end_node;
    private final ReadXML reader;
    private final ICalculable calculator;

    private final HashMap<Node, Double> valori;
    private final LinkedList<Node> da_collegare;
    private final LinkedHashMap<Node, Node> precedenti;

    /**
     * Costruttore per trovare il cammino minimo tra i nodi della mappa.
     * @param start_node Il nodo di partenza.
     * @param end_node Il nodo di arrivo.
     * @param reader Il lettore per la lettura del file XML.
     * @param calculator L'operazione da eseguire per il calcolo del carburante.
     */
    public TrackFinder(Node start_node, Node end_node, ReadXML reader, ICalculable calculator) {
        this.start_node = start_node;
        this.end_node = end_node;
        this.reader = reader;
        this.calculator = calculator;
        da_collegare = new LinkedList<>();
        valori = new HashMap<>();
        precedenti = new LinkedHashMap<>();
        init();
    }

    /***
     * Inizializza le liste/mappe usate per la ricerca del cammino minimo.
     */
    private void init() {
        for (Node n : reader.getNodes().values()) {
            da_collegare.add(n);
            valori.put(n, Double.MAX_VALUE);
            precedenti.put(n, null);
        }
        valori.put(start_node, 0.0);
        da_collegare.remove(start_node);
    }

    /**
     * Trova il percorso dei cammini minimi tra tutti i nodi della mappa.
     */
    public void findTrack() {
        for (Node n : start_node.getLinks()) {
            precedenti.put(n, start_node);
            valori.put(n, calculator.calcDistance(n, start_node));
        }
        while (!da_collegare.isEmpty()) {
            Node piu_vicino = null;
            double min = Double.MAX_VALUE;
            for (Node n : da_collegare) {
                double dist = valori.get(n);
                if (dist - min < THRESHOLD) {
                    min = dist;
                    piu_vicino = n;
                }
            }
            double dist = valori.get(piu_vicino);
            for (Node n : piu_vicino.getLinks()) {
                double ricalc = dist + calculator.calcDistance(n, piu_vicino);
                if (ricalc - valori.get(n) < THRESHOLD) {
                    precedenti.put(n, piu_vicino);
                    valori.put(n, ricalc);
                }
            }
            da_collegare.remove(piu_vicino);
        }
    }

    /**
     * Ritorna la distanza totale dalle rovine perdute.
     * @return La distanza totale dalle rovine perdute.
     */
    public double getFinalDistance(){
        return valori.get(end_node);
    }

    /**
     * Ritorna la lista ordinata con i nodi del percorso per
     * arrivare alle rovine perdute.
     * @return Lista del percorso.
     */
    public ArrayList<Node> getTrack(){
        ArrayList<Node> track = new ArrayList<>();
        Node attuale = end_node;
        while(attuale != null){
            track.add(attuale);
            attuale = precedenti.get(attuale);
        }
        Collections.reverse(track);
        return track;
    }

}
