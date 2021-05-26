/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.rovineperdute;

import ttt.rovineperdute.contents.graph.Node;
import ttt.rovineperdute.io.ReadXML;

import java.io.File;
import ttt.rovineperdute.contents.waterfall.StreamHandler;

/**
 * @author TTT
 */
public class RovinePerdute {

    static long memoryUsed() {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    static final Runtime runtime = Runtime.getRuntime();

    public static void main(String[] args) {

        File f = new File("PgAr_Map_10000.xml");
        ReadXML r = new ReadXML(f);
        Node n = r.putCityInGraph();
        //Node trovato = findNode(n, 35);
        //TrackBot main_bot = new TrackBot(n, null, new GraphPath());
//        TrackFinder t = new TrackFinder(n, r);
//        t.findBestTrack();
//        n.stampaPercorsi("");
//        StreamHandler sm = new StreamHandler(n,r.getNodes().get(49));
//        System.out.println(memoryUsed() / 1000000 + "MB");
//        while(!sm.finished()){}
//        System.out.println(memoryUsed() / 1000000 + "MB");
//        System.out.println(sm.getCrawlers().size());
//        sm.cleanup();
//        System.out.println(sm.getCrawlers().size());
    }

}
