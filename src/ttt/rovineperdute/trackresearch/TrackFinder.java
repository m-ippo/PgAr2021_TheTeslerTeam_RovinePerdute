package ttt.rovineperdute.trackresearch;

import ttt.rovineperdute.graph.Node;

import java.util.HashMap;
import java.util.LinkedList;

public class TrackFinder {

    private final Node start_node;
    private final HashMap<Node, Double> valori;
    private final LinkedList<Node> da_collegare;
    private final LinkedList<Node> precedenti;

    public TrackFinder(Node node) {
        this.start_node = node;
        da_collegare = new LinkedList<>();
        valori = new HashMap<>();
        precedenti = new LinkedList<>();
        init();
    }

    private void init() {
        for (Node n : start_node.getLinks()) {
            valori.put(n, -1.0); // -1 perchè non possono essere negativi
            da_collegare.add(n);
        }
    }

    public Node findBestTrack() {
        Node n = new Node(start_node.getCity());
        valori.put(n, 0.0);
        precedenti.add(start_node);
        da_collegare.remove(start_node);

        double distanza = 0;
        
        while (da_collegare.size() > 0) {
            Node nuovo = findNearestNode(n);
            da_collegare.remove(nuovo);
            if (valori.get(nuovo) == -1) {
                break;
            }
            for (Node node : nuovo.getLinks()) {
                
            }
        }

        return n;
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
