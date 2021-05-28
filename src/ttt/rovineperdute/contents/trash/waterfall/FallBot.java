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

import java.util.Iterator;
import ttt.rovineperdute.contents.graph.Node;

/**
 *
 * @author gabri
 */
public class FallBot {

    private final GraphPath gp;
    private final StreamHandler sm;

    public FallBot(GraphPath gp, StreamHandler sm) {
        this.gp = gp;
        this.sm = sm;
    }

    public void startFall() {
        Node last_reached = gp.getPath().getLast();
        fall(last_reached);
    }

    private void fall(Node next) {
        Iterator<Node> iterator = next.getLinks().iterator();
        if (iterator.hasNext()) {
            while (iterator.hasNext()) {
                Node l = iterator.next();
                if (!gp.contains(l)) {
                    gp.addNode(l);
                    fall(l);
                    break;
                }
            }
            while (iterator.hasNext()) {
                Node l = iterator.next();
                if (!gp.contains(l)) {
                    sm.addCrawler(gp.split(), l);
                }
            }
        } else {
            gp.die();
        }
    }

    public GraphPath getGraphPath() {
        return gp;
    }

}
