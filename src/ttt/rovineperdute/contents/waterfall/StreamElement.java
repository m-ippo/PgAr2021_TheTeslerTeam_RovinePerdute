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

import ttt.rovineperdute.contents.graph.Node;

/**
 *
 * @author gabri
 */
public class StreamElement {

    private final GraphPath from;
    private final Node to;
    private final FallBot fb;
    private volatile GraphPath finale;
    private final StreamHandler sm;

    public StreamElement(GraphPath from, Node option, StreamHandler sm) {
        this.from = from;
        this.to = option;
        this.sm = sm;
        fb = new FallBot(from.addNode(to), sm);
    }

    public void startFall() {
        final StreamElement me = this;
        Thread t = new Thread() {
            @Override
            public void run() {
                fb.startFall();
                sm.removeService(this);
                finale = fb.getGraphPath();
                if (finale.isDeadEnd()) {
                    sm.removeCrawler(me);
                }
            }
        };
        sm.addService(t);
        t.start();
    }

    public GraphPath getFinale() {
        return finale;
    }

}
