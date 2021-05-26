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
package ttt.rovineperdute.contents.waterfall;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import ttt.rovineperdute.contents.graph.Node;

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

}
