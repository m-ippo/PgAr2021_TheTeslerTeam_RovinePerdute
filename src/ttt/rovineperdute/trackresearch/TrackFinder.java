package ttt.rovineperdute.trackresearch;

import ttt.rovineperdute.io.ReadXML;

import java.util.*;
import ttt.rovineperdute.graph.Node;

public class TrackFinder {

    private final static double THRESHOLD = 0.00001;

    private final Node start_node;
    private final Node end_node;
    private final HashMap<Node, Double> valori;
    private final LinkedList<Node> da_collegare;
    private final LinkedHashMap<Node, Node> precedenti;
    private final ReadXML reader;

    public TrackFinder(Node start_node, Node end_node, ReadXML reader) {
        this.start_node = start_node;
        this.end_node = end_node;
        da_collegare = new LinkedList<>();
        valori = new HashMap<>();
        precedenti = new LinkedHashMap<>();
        this.reader = reader;
        init();
    }

    private void init() {
        for (Node n : reader.getNodes().values()) {
            da_collegare.add(n);
            valori.put(n, Double.MAX_VALUE); // -1 perchè non possono essere negativi
            precedenti.put(n, null);
        }
        valori.put(start_node, 0.0);
        da_collegare.remove(start_node);
    }

    public void find3() {
        for (Node n : start_node.getLinks()) {
            precedenti.put(n, start_node);
            valori.put(n, calcDist(n, start_node));
        }

        while (!da_collegare.isEmpty()) {
            Node piu_vicino = null;
            double min = 0.0;
            for (Node n : da_collegare) {
                if(min == 0.0 && valori.get(n) != -1){
                    min = valori.get(n);
                }
                if (valori.get(n) != -1 && valori.get(n) - min < THRESHOLD) {
                    min = valori.get(n);
                    piu_vicino = n;
                }
            }
            if (piu_vicino == null) {
                break;
            }
            double dist = 0.0;
            for (Node n : piu_vicino.getLinks()) {
                dist = valori.get(piu_vicino) + calcDist(n, piu_vicino);
                if (valori.get(n) == -1 || dist - valori.get(n) < THRESHOLD) {
                    precedenti.put(n, piu_vicino);
                    valori.put(n, dist);
                }
            }
            da_collegare.remove(piu_vicino);
        }
    }


    public double getFinalDistance(){
        return valori.get(end_node);
    }

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

    public static double calcDist(Node to, Node from) {
        return Math.sqrt(Math.pow(from.getCity().getX() - to.getCity().getX(), 2) + Math.pow(from.getCity().getY() - to.getCity().getY(), 2));
    }

}
