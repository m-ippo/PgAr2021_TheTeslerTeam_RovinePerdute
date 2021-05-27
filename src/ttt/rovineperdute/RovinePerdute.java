/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.rovineperdute;

import ttt.rovineperdute.graph.Node;
import ttt.rovineperdute.io.ReadXML;

import java.io.File;
import ttt.rovineperdute.trackresearch.TrackFinder;

/**
 * @author TTT
 */
public class RovinePerdute {

    static long memoryUsed() {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    static final Runtime runtime = Runtime.getRuntime();

    public static void main(String[] args) {

        //////////////////////////////////////////////////////////////////////////
        //   se si va a ritroso dai precedenti esce giusto
        //////////////////////////////////////////////////////////////////////////

        File f = new File("PgAr_Map_10000.xml");
        ReadXML r = new ReadXML(f);
        Node n = r.putCityInGraph();
        //Node trovato = findNode(n, 35);
        //TrackBot main_bot = new TrackBot(n, null, new GraphPath());
        TrackFinder t = new TrackFinder(n, r);
        t.find3();
        //n.stampaPercorsi("");
        System.out.println(memoryUsed() / 1000000 +"MB");
    }

}
