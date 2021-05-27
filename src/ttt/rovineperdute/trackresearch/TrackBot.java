/*
 * Copyright 2021 gabri.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ttt.rovineperdute.trackresearch;

import ttt.rovineperdute.contents.waterfall.GraphPath;
import java.util.LinkedHashMap;
import java.util.List;
import ttt.rovineperdute.graph.Node;

/**
 *
 * @author gabri
 */
public class TrackBot {

    private final Node main;
    private final TrackBot parent;
    private final LinkedHashMap<Node, TrackBot> bots = new LinkedHashMap<>();
    private final GraphPath fino_a_me;
//    private Node best;

    public TrackBot(Node n, TrackBot parent, GraphPath p) {
        main = n;
        this.parent = parent;
        fino_a_me = p;
        init();
    }

    private void init() {
        fino_a_me.addNode(this.main);
        /*if (main.getCity().getId() != ReadXML.getEnd().getCity().getId()) {
            end = findTracks();
        }*/
    }

    GraphPath end;

    private GraphPath findTracks() {
        //Creiamo tanti bot quante sono le uscite del nodo main
        System.out.println("Completando : " + main.getCity().getId() + " dal padre: " + (parent != null ? parent.getNode().getCity().getId() : "orfano"));
        List<Node> uscite = main.getLinks();
        int my_id = main.getCity().getId();
        if (this.main.getName().equals("Rovine Perdute")) {
            end = fino_a_me;
            return end;
        }
        if (uscite.isEmpty()) {
            fino_a_me.die();
        } else {
            if (parent != null) {
                for (Node da_calcolare : uscite) {
                    if (da_calcolare != parent.getNode() 
                            && da_calcolare.getCity().getId() > my_id 
                            && !fino_a_me.contains(da_calcolare) 
                            && !bots.containsKey(da_calcolare)) {
                        TrackBot b = new TrackBot(da_calcolare, this, fino_a_me.split());
                        if (!b.getPathToThisNode().isDeadEnd()) {
                            GraphPath findTracks = b.findTracks();
                            bots.put(da_calcolare, b);
                            if (findTracks != null) {
                                return findTracks;
                            }
                        }
                    }
                }
                fino_a_me.die();
                return fino_a_me;
            } else {
                for (Node da_calcolare : uscite) {
                    TrackBot b = new TrackBot(da_calcolare, this, fino_a_me.split());
                    GraphPath findTracks = b.findTracks();
                    if (findTracks != null) {
                        return findTracks;
                    }
                    //bots.put(da_calcolare, b);
                    //System.out.println("Completando : " + da_calcolare.getCity().getId() + " dal padre: " + main.getCity().getId());
                }
            }
        }
        return null;
    }

    private void findBestTrack() {
        //Trova il nodo migliore
    }

    public Double getWeight() {
        return null;
    }

    public Node getNode() {
        return main;
    }

    public GraphPath getPathToThisNode() {
        return fino_a_me;
    }

    private static double calcDist(Node to, Node from) {
        return Math.sqrt(Math.pow(from.getCity().getX() - to.getCity().getX(), 2) + Math.pow(from.getCity().getY() - to.getCity().getY(), 2));
    }
}
