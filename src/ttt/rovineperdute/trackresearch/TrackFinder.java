package ttt.rovineperdute.trackresearch;

import ttt.rovineperdute.graph.Node;
import ttt.rovineperdute.io.ReadXML;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class TrackFinder {

    private final static double THRESHOLD = 0.00001;

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
        for (Node n : reader.getNodes().values()) {
            da_collegare.add(n);
            valori.put(n, -1.0); // -1 perchè non possono essere negativi
            precedenti.put(n, null);
        }
        valori.put(start_node, Double.NEGATIVE_INFINITY);
        da_collegare.remove(start_node);
    }

    public void findBestTrack() {
        Node attuale = start_node;
        while (!da_collegare.isEmpty()) {
            Node piu_vicino = null;
            Double min = Double.MAX_VALUE;
            double fino_ad_ora = getTotalDistance(attuale);

            for (Node n : attuale.getLinks()) {
                if (da_collegare.contains(n)) {
                    double dist = calcDist(n, attuale);
//                    if(min == 0.0){
//                        min = dist;
//                    }
                    if (dist < min){
                        min = dist;
                        piu_vicino = n;
                    }
                    dist += fino_ad_ora;
                    if(valori.get(n) == -1) {
                        precedenti.put(n, attuale);
                        valori.put(n, dist);
                        attuale.addDijkstraNode(n);
                    } else if(Math.abs(valori.get(n) - dist) < THRESHOLD){
                        Node precedente = precedenti.get(n);
                        precedente.removeDijkstraNode(n);
//                        removeFromAll(n);
                        precedenti.put(n, attuale);
                        valori.put(n, dist);
                        attuale.addDijkstraNode(n);
                    }
                }
            }
            if (piu_vicino == null) {
                break;
            }

            precedenti.put(piu_vicino, attuale);
            attuale.addDijkstraNode(piu_vicino);

            fino_ad_ora += min;
            valori.put(piu_vicino, fino_ad_ora);
            da_collegare.remove(piu_vicino);
            attuale = piu_vicino;

            double dist;
            for (Node n : piu_vicino.getLinks()) {
                if (da_collegare.contains(n)) {
                    dist = fino_ad_ora + calcDist(piu_vicino, n);
                    double valore_attuale = valori.get(n);
                    boolean isAlreadyLinked = valore_attuale != -1;
                    if (!isAlreadyLinked) {
                        valori.put(n, dist);
                        piu_vicino.addDijkstraNode(n);
                        precedenti.put(n, piu_vicino);
                    } else if (Math.abs(dist - valore_attuale) > THRESHOLD) {
                        valori.put(n, dist);
                        Node precedente = precedenti.get(n);
                        precedente.removeDijkstraNode(n);
//                        removeFromAll(n);
                        piu_vicino.addDijkstraNode(n);
                        precedenti.put(n, piu_vicino);
                    }
                }
            }
        }
    }

    public void find2(){
        for(Node n : start_node.getLinks()){
            precedenti.put(n, start_node);
            valori.put(n, calcDist(start_node, n));
        }
        while(!da_collegare.isEmpty()){
            Node piu_vicino = null;
            double min = valori.get(da_collegare.get(0));

            for(Node n : da_collegare){
                double dist = valori.get(n);
                if(valori.get(n) != -1 && dist - min < THRESHOLD){
                    min = dist;
                    piu_vicino = n;
                }
            }
            double dist_piu_vicino = valori.get(piu_vicino);
            for(Node n : piu_vicino.getLinks()){
                double dist = calcDist(n, piu_vicino) + dist_piu_vicino;
                if(dist == -1 ||  dist - valori.get(n) < THRESHOLD){
                    precedenti.put(n, piu_vicino);
                    valori.put(n, dist);
                }
            }
            da_collegare.remove(piu_vicino);
        }
    }

    public void find3() {
        for (Node n : start_node.getLinks()) {
            precedenti.put(n, start_node);
            valori.put(n, calcDist(n, start_node));
        }

        while (!da_collegare.isEmpty()) {
            Node piu_vicino = null;
            double min = valori.get(da_collegare.get(0));
            for (Node n : da_collegare) {
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

    private void removeFromAll(Node d) {
        for (Node n : reader.getNodes().values()) {
            n.removeDijkstraNode(d);
        }
    }

    private double getTotalDistance(Node n) {
        Node precedente = precedenti.get(n);
        double dist = 0;
        long contatore = 0;
        while (precedente != null) {
            dist += calcDist(precedente, n);
            n = precedente;
            precedente = precedenti.get(precedente);
            contatore++;
            if (contatore > 10000) {
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
