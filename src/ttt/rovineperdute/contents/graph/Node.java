package ttt.rovineperdute.contents.graph;

import ttt.rovineperdute.io.elements.City;

import java.util.*;

public class Node {

    private final LinkedList<Node> links = new LinkedList<>();
    private City city;

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

    @Override
    public boolean equals(Object o){
        if(o instanceof Node){
            return ((Node) o).getCity().getId() == city.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node[").append(city.getName()).append(",ID:").append(city.getId()).append("]");
        return sb.toString();
    }
}
