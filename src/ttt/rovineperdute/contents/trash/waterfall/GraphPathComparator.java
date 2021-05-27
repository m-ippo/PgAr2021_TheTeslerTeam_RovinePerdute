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

import java.util.Comparator;

/**
 *
 * @author gabri
 */
public class GraphPathComparator implements Comparator<GraphPath> {

    @Override
    public int compare(GraphPath o1, GraphPath o2) {
        int compareTo = o1.compute(null).compareTo(o2.compute(null));
        if (compareTo == 0) {
            return ((Integer) o1.getPath().size()).compareTo(o2.getPath().size());
        }
        return compareTo;
    }

}
