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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import ttt.rovineperdute.graph.Node;

/**
 *
 * @author gabri
 */
public class GraphPath {

    private final HashSet<Node> percorso;
    private boolean dead;

    public GraphPath() {
        percorso = new HashSet<>();
        dead = false;
    }

    private GraphPath(Set<Node> percorso_padre) {
        this();
        percorso_padre.stream().forEachOrdered(n -> percorso.add(n));
    }

    public boolean addNode(Node bot) {
        return percorso.add(bot);
    }

    public boolean contains(Node bot) {
        return percorso.contains(bot);
    }

    public GraphPath split() {
        return new GraphPath(Collections.unmodifiableSet(percorso));
    }
    
    public void die(){
        dead = true;
    }
    
    public boolean isDeadEnd(){
        return dead;
    }

}
