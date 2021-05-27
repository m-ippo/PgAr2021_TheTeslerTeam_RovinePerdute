package ttt.rovineperdute.contents.graph;

import ttt.rovineperdute.io.elements.City;

import java.util.*;

public class Node {

    private final LinkedList<Node> links = new LinkedList<>();
    //private final HashMap<Node, Double> link_values = new HashMap<>();
    private final City city;
    private final LinkedHashSet<Node> dijkstra = new LinkedHashSet<>();

    public Node(City city) {
        this.city = city;
    }

    /**
     * Ritorna il nome del nodo.
     *
     * @return Il nome.
     */
    public String getName() {
        return city.getName();
    }

    /**
     * Ritorna il valore associato a questo nodo.
     *
     * @return Il valore precedentemente associato.
     */
    public City getCity() {
        return city;
    }

    public void addNode(Node n) {
        if (n != null) {
            links.add(n);
        }
    }

    public void removeNode(Node n) {
        links.remove(n);
    }

    public LinkedList<Node> getLinks() {
        return links;
    }

    public Optional<Node> lookupNode(Node parent, int id) {
        for (Node n : links) {
            if (n != parent) {
                Optional<Node> lookupNode = lookupNode(this, id);
                if (lookupNode.isPresent()) {
                    return lookupNode;
                }
            }
        }
        return Optional.empty();
    }

    public void addDijkstraNode(Node n) {
         dijkstra.add(n);
    }

    public void removeDijkstraNode(Node n) {
        /* boolean remove =*/ dijkstra.remove(n);
        //System.out.println("Rimosso con successo? " + remove);
    }

    public LinkedHashSet<Node> getDijkstra() {
        return  dijkstra;
    }

    public void stampaPercorsi(String tabs) {
        dijkstra.forEach(n -> {
            System.out.println(tabs + n.toString());
            n.stampaPercorsi(tabs + "\t");
        });
    }

//    public void computeCalcs() {
//        links.forEach(l -> {
//            link_values.put(l, calcDist(l, this));
//        });
//    }
//
//    private double calcDist(Node to, Node from) {
//        return Math.sqrt(Math.pow(from.getCity().getX() - to.getCity().getX(), 2) + Math.pow(from.getCity().getY() - to.getCity().getY(), 2));
//    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node[").append(city.getName()).append(",ID:").append(city.getId()).append("]");
        return sb.toString();
    }
}
