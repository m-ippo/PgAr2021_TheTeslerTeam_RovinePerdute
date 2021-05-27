package ttt.rovineperdute.trackresearch;

import ttt.rovineperdute.graph.Node;

public interface ICalculable {

    /**
     * Ritorna la distanza tra due nodi.
     * @param to Nodo di partenza.
     * @param from Nodo di arrivo.
     * @return Distanza tra i due nodi.
     */
    public double calcDistance(Node to, Node from);
}
