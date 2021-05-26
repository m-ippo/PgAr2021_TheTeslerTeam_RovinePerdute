package ttt.rovineperdute.trackresearch;

import ttt.rovineperdute.graph.Node;
import ttt.rovineperdute.io.ReadXML;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class TrackFinder {

    private final Node start_node;
    private final HashMap<Node, Double> valori;
    private final LinkedList<Node> da_collegare;
    private final LinkedHashMap<Node, Node> precedenti;
    private final ReadXML reader;
    public TrackFinder(Node start_node, ReadXML reader) {
        this.start_node = start_node;
        da_collegare = new LinkedList<>();
        valori = new HashMap<>();
        precedenti = new LinkedHashMap<>();
        this.reader = reader;
        init();
    }

    private void init() {
        for(Node n : reader.getNodes().values()){
            da_collegare.add(n);
            valori.put(n, -1.0); // -1 perchè non possono essere negativi
            precedenti.put(n, null);
        }
        da_collegare.remove(start_node);
        valori.put(start_node, Double.NEGATIVE_INFINITY);
    }

    public void findBestTrack() {
        Node attuale = start_node;
        while(!da_collegare.isEmpty()){
            Node piu_vicino = null;
            Double min = Double.POSITIVE_INFINITY;
            for(Node n : attuale.getLinks()) {
                if (da_collegare.contains(n)) {
                    double dist = calcDist(n, attuale);
                    if (dist < min) {
                        min = dist;
                        piu_vicino = n;
                    }
                }
            }
            if(piu_vicino == null){
                break;
            }
            piu_vicino.removeNode(attuale);

            double fino_ad_ora = min + getTotalDistance(attuale);
            valori.put(piu_vicino, fino_ad_ora);
            precedenti.put(piu_vicino, attuale);
            attuale.addDijkstraNode(piu_vicino);

            double dist;
            for(Node n : piu_vicino.getLinks()) {
                if (da_collegare.contains(n)) {
                    dist = fino_ad_ora + calcDist(piu_vicino, n);
                    double valore_attuale = valori.get(n);
                    boolean isAlreadyLinked = valore_attuale != -1;
                    if (!isAlreadyLinked || dist < valore_attuale) {
                        valori.put(n, dist);
                        if (isAlreadyLinked) {
                            Node node = precedenti.get(n);
                            node.removeDijkstraNode(n);
                        }
                        piu_vicino.addDijkstraNode(n);
                        precedenti.put(n, piu_vicino);
                    }
                }
            }
            da_collegare.remove(attuale);
            attuale = piu_vicino;
        }
    }

    private double getTotalDistance(Node n){
        Node precedente = precedenti.get(n);
        double dist = 0;
        long contatore = 0;
        while (precedente != null){
            dist += calcDist(precedente, n);
            n = precedente;
            precedente = precedenti.get(precedente);
            contatore++;
            if(contatore > 10000){
                System.out.println("uoegrt");
            }
        }
        return dist;
    }

    private Node findNearestNode(Node to) {
        Node to_ret = da_collegare.get(0);
        double min = calcDist(to, da_collegare.get(0));
        for (Node n : to.getLinks()) {
            if (da_collegare.contains(n)) {
                double distance = calcDist(to, n);
                if (distance < min) {
                    min = distance;
                    to_ret = n;
                }
            }
        }
        valori.put(to_ret, min);
        return to_ret;
    }

    private double calcDist(Node to, Node from) {
        return Math.sqrt(Math.pow(from.getCity().getX() - to.getCity().getX(), 2) + Math.pow(from.getCity().getY() - to.getCity().getY(), 2));
    }

}
