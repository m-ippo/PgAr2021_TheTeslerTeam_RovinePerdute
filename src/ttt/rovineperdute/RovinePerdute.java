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
import java.util.Date;

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
        long t1 = new Date().getTime();
        File f = new File("PgAr_Map_5.xml");
        ReadXML r = new ReadXML(f);
        Node n = r.putCityInGraph();
        TrackFinder t = new TrackFinder(n,r.getEnd(), r);
        t.find3();
        System.out.println(new Date().getTime() - t1);

        System.out.println(t.getFinalDistance());
        ArrayList<Node> track = t.getTrack();
        System.out.println(memoryUsed() / 1000000 +"MB");
        System.gc();
        System.out.println(memoryUsed() / 1000000 +"MB");
    }

}
