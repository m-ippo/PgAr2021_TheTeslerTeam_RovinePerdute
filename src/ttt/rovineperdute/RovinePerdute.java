/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.rovineperdute;

import ttt.rovineperdute.graph.Node;
import ttt.rovineperdute.io.ReadXML;

import java.io.File;
import java.util.ArrayList;
import ttt.rovineperdute.trackresearch.GraphPath;
import ttt.rovineperdute.trackresearch.TrackBot;

/**
 * @author TTT
 */
public class RovinePerdute {

    static long memoryUsed() {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    static final Runtime runtime = Runtime.getRuntime();
    static final ArrayList<Node> cercati = new ArrayList<>();

    public static Node findNode(Node parent, int ID) {
        cercati.add(parent);
        for (Node n : parent.getLinks()) {
            if (n.getCity().getId() == ID) {
                return n;
            } else {
                if (!cercati.contains(n)) {
                    return findNode(n, ID);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {

        File f = new File("PgAr_Map_50.xml");
        ReadXML r = new ReadXML(f);
        Node n = r.putCityInGraph();
        //Node trovato = findNode(n, 35);
        TrackBot main_bot = new TrackBot(n, null, new GraphPath());

        System.out.println(memoryUsed() / 1000000 +"Mb");
    }

}
