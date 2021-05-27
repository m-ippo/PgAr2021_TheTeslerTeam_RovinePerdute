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

import java.util.ArrayList;
import java.util.Iterator;
import ttt.rovineperdute.contents.graph.Node;

/**
 *
 * @author gabri
 */
public class StreamHandler {

    private volatile ArrayList<StreamElement> crawlers = new ArrayList<>();
    private volatile ArrayList<Thread> threads = new ArrayList<>();

    private final Node start;
    private final Node end;

    public StreamHandler(Node start, Node end) {
        this.start = start;
        this.end = end;
        init();
    }

    private void init() {
        addCrawler(new GraphPath(), start);
    }

    public ArrayList<StreamElement> getCrawlers() {
        return crawlers;
    }

    public synchronized void addCrawler(GraphPath gp, Node option) {
        StreamElement sm = new StreamElement(gp, option, this);
        crawlers.add(sm);
        sm.startFall();
    }

    public synchronized void addService(Thread t) {
        threads.add(t);
    }

    public synchronized void removeService(Thread t) {
        threads.remove(t);
    }

    public boolean finished() {
        return threads.isEmpty();
    }

    public void cleanup() {
        Iterator<StreamElement> iterator = crawlers.iterator();
        while (iterator.hasNext()) {
            StreamElement next = iterator.next();
            GraphPath finale = next.getFinale();
            if (!finale.contains(end)) {
                iterator.remove();
            }
        }
    }

    public synchronized void removeCrawler(StreamElement to_remove) {
        crawlers.remove(to_remove);
    }

}
