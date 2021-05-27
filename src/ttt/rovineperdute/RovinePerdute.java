/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.rovineperdute;

import ttt.rovineperdute.io.ReadXML;

import java.io.File;
import java.util.ArrayList;

import ttt.rovineperdute.graph.Node;
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

        teamTonatiuh();
        System.out.println(memoryUsed() / 1000000 + "MB");
    }

    public static void teamTonatiuh(){
        File f = new File("PgAr_Map_50.xml");
        ReadXML r = new ReadXML(f);
        Node n = r.putCityInGraph();
        TrackFinder t = new TrackFinder(n, r.getEnd(), r, (to, from) -> {
            return Math.sqrt(Math.pow(from.getCity().getX() - to.getCity().getX(), 2) + Math.pow(from.getCity().getY() - to.getCity().getY(), 2));
        });
        t.findTrack();
        ArrayList<Node> track = t.getTrack();
        printTrack(track);
        System.out.println("Total distance : " + t.getFinalDistance());
    }

    public static void teamMetztli(){
        File f = new File("PgAr_Map_50.xml");
        ReadXML r = new ReadXML(f);
        Node n = r.putCityInGraph();
        TrackFinder t = new TrackFinder(n, r.getEnd(), r, (to, from) -> {
            return Math.abs(to.getCity().getH() - from.getCity().getH());
        });
        t.findTrack();
        ArrayList<Node> track = t.getTrack();
        printTrack(track);
        System.out.println("Total distance : " + t.getFinalDistance());
    }

    public static void printTrack(ArrayList<Node> track){
        for(int i = 0; i < track.size(); i++){
            System.out.print(track.get(i).toString());
            if(i != track.size() - 1){
                System.out.println("--->");
            } else {
                System.out.println();
            }
        }
    }

}
