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
package ttt.rovineperdute.contents.trash.waterfall;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import ttt.rovineperdute.contents.graph.Node;
import ttt.rovineperdute.trackresearch.TrackFinder;

/**
 *
 * @author gabri
 */
public class GraphPath {

    private final LinkedList<Node> path;
    private boolean dead_end;

    public GraphPath() {
        path = new LinkedList<>();
        dead_end = false;
    }

    private GraphPath(List<Node> percorso_padre) {
        this();
        percorso_padre.stream().forEachOrdered(n -> path.add(n));
    }

    public GraphPath addNode(Node bot) {
        path.add(bot);
        return this;
    }

    public boolean contains(Node bot) {
        return path.contains(bot);
    }

    public LinkedList<Node> getPath() {
        return path;
    }

    public GraphPath split() {
        return new GraphPath(Collections.unmodifiableList(path));
    }

    public void die() {
        dead_end = true;
    }

    public boolean isDeadEnd() {
        return dead_end;
    }

    Double cost = Double.POSITIVE_INFINITY;

    public Double compute(Node end) {
        if (cost != Double.POSITIVE_INFINITY) {
            return cost;
        }
        Iterator<Node> iterator = path.iterator();
        if (iterator.hasNext()) {
            Node previous = iterator.next();
            if (iterator.hasNext()) {
                Node current = iterator.next();
                cost = calcDist(previous, current, 0);
                while (iterator.hasNext()) {
                    previous = current;
                    current = iterator.next();
                    cost += calcDist(previous, current, 0);
                    if(current == end){
                        break;
                    }
                }
                return cost;
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    public static double calcDist(Node to, Node from, int team) {
        if(team == 0){
            return Math.sqrt(Math.pow(from.getCity().getX() - to.getCity().getX(), 2) + Math.pow(from.getCity().getY() - to.getCity().getY(), 2));
        } else {
            return Math.abs(to.getCity().getH() - from.getCity().getH());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        path.stream().forEachOrdered(l -> {
            sb.append("\n>").append(l);
        });
        return sb.toString();
    }

}
