/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.rovineperdute;

import ttt.rovineperdute.io.ReadXML;

import java.io.File;

/**
 *
 * @author gabri
 */
public class RovinePerdute {

    public static void main(String[] args) {
        File f = new File("PgAr_Map_50.xml");
        ReadXML r = new ReadXML(f);
        System.out.println("huiogfe");
    }
    
}
