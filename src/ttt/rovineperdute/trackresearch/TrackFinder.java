package ttt.rovineperdute.trackresearch;

import java.util.ArrayList;
import ttt.rovineperdute.contents.graph.Node;
import ttt.rovineperdute.io.ReadXML;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Stack;
import ttt.rovineperdute.contents.waterfall.GraphPath;

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
        da_collegare.remove(start_node);
//        end_node.getLinks().clear();
        valori.put(start_node, Double.MIN_VALUE);
    }

    public Stack<GraphPath> findBestTrack() {
        Node attuale = start_node;

        Stack<GraphPath> backups = new Stack<>();
        Stack<GraphPath> ends = new Stack<>();
        GraphPath current_path = new GraphPath().addNode(start_node);
        backups.push(current_path);
        while (!da_collegare.isEmpty()) {
            Node piu_vicino = null;
            Double min = Double.MAX_VALUE;
            for (Node n : attuale.getLinks()) {
                if (da_collegare.contains(n)) {
                    double dist = calcDist(n, attuale);
                    if (dist < min) {
                        min = dist;
                        piu_vicino = n;
                    }
                }
            }
            da_collegare.remove(piu_vicino);
            if (piu_vicino == null) {
                if (current_path.contains(end_node)) {
                    ends.push(current_path);
                }
                current_path = backups.pop();
                attuale = current_path.getPath().getLast();
                continue;
            }
            piu_vicino.removeNode(attuale);

            double fino_ad_ora = min + getTotalDistance(attuale);
            valori.put(piu_vicino, fino_ad_ora);
            precedenti.put(piu_vicino, attuale);

            //attuale.addDijkstraNode(piu_vicino);
            backups.push(current_path.split());
            current_path.addNode(piu_vicino);
            attuale = piu_vicino;

            /*double dist;
            for (Node n : piu_vicino.getLinks()) {
                if (da_collegare.contains(n)) {
                    dist = fino_ad_ora + calcDist(piu_vicino, n);
                    double valore_attuale = valori.get(n);
                    boolean isAlreadyLinked = valore_attuale != -1;
                    if (!isAlreadyLinked) {
                        valori.put(n, dist);
                        //piu_vicino.addDijkstraNode(n);
                        precedenti.put(n, piu_vicino);
                    } else if (Math.abs(dist - valore_attuale) < THRESHOLD) {
                        valori.put(n, dist);
                        //Node node = precedenti.get(n);
                        //node.removeDijkstraNode(n);
                        //removeFromAll(n);
                        //piu_vicino.addDijkstraNode(n);
                        precedenti.put(n, piu_vicino);
                    }
                }
            }*/
        }
        return ends;
    }

    private void removeFromAll(Node d) {
        for (Node n : reader.getNodes().values()) {
            n.removeDijkstraNode(d);
        }
    }

    private long countLeftBack(Node n) {
        return da_collegare.stream().filter((t) -> {
            return n.getLinks().contains(t);
        }).count();
    }

    private double getTotalDistance(Node n) {
        Node precedente = precedenti.get(n);
        double dist = 0;
        while (precedente != null) {
            dist += calcDist(precedente, n);
            n = precedente;
            precedente = precedenti.get(precedente);
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

    public static double calcDist(Node to, Node from) {
        return Math.sqrt(Math.pow(from.getCity().getX() - to.getCity().getX(), 2) + Math.pow(from.getCity().getY() - to.getCity().getY(), 2));
    }

}
