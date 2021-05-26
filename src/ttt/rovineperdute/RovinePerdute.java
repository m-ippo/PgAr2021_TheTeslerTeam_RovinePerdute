/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.rovineperdute;

import ttt.rovineperdute.graph.Node;
import ttt.rovineperdute.io.ReadXML;

import java.io.File;

/**
 * @author TTT
 */
public class RovinePerdute {

    public static void main(String[] args) {

        File f = new File("PgAr_Map_5.xml");
        ReadXML r = new ReadXML(f);
        Node n = r.putCityInGraph();
        System.out.println("huiogfe");
    }
    
}
