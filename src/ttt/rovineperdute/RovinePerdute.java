/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.rovineperdute;

import ttt.rovineperdute.contents.graph.Node;
import ttt.rovineperdute.io.ReadXML;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Stack;
import ttt.rovineperdute.contents.waterfall.GraphPath;
import ttt.rovineperdute.contents.waterfall.GraphPathComparator;
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
        long t1 = new Date().getTime();
        File f = new File("PgAr_Map_10000.xml");
        ReadXML r = new ReadXML(f);
        Node n = r.putCityInGraph();
        TrackFinder t = new TrackFinder(n, r.getEnd(), r);
        Stack<GraphPath> findBestTrack = t.findBestTrack();
        ArrayList<GraphPath> computed = new ArrayList<>();

        while (!findBestTrack.empty()) {
            GraphPath gp = findBestTrack.pop();
            gp.compute(r.getEnd());
            computed.add(gp);
        }
        Collections.sort(computed, new GraphPathComparator());

        System.out.println(new Date().getTime() - t1);
        computed.stream().forEachOrdered(gp -> {
            System.out.println(gp);
            System.out.println("GP: \tcosto: " + gp.compute(null) + " \tdim:" + gp.getPath().size());
        });
        
//        StreamHandler sm = new StreamHandler(n,r.getNodes().get(49));
//        System.out.println(memoryUsed() / 1000000 + "MB");
//        while(!sm.finished()){}
//        System.out.println(memoryUsed() / 1000000 + "MB");
//        System.out.println(sm.getCrawlers().size());
//        sm.cleanup();
//        System.out.println(sm.getCrawlers().size());
//        System.out.println(memoryUsed() / 1000000 + "MB");
//        System.gc();
//        System.out.println(memoryUsed() / 1000000 + "MB");
    }

}
