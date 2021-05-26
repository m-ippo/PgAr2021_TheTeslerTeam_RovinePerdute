package ttt.rovineperdute.graph;

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

    public void addNode (Node n){
        if(n != null) {
            links.add(n);
        }
    }

    public LinkedList<Node> getLinks(){
        return links;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node[").append(city.getName()).append("]");
        return sb.toString();
    }
}
