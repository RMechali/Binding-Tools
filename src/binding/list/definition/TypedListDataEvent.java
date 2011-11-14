/** 
 * This file is part of Binding Tools  project.
 *
 * Binding Tools  project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * Binding Tools  is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and GNU Lesser General Public License along with Binding Tools project.
 * If not, see <http://www.gnu.org/licenses/>.
 **/
package binding.list.definition;

import java.util.List;
import javax.swing.event.ListDataEvent;

/**
 * A typed list data event
 * 
 * Copyright 2011, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * @param T : list elements type
 */
public class TypedListDataEvent<T> extends ListDataEvent {

    /** List previous elements **/
    private final List<T> previousElements;

    /** List new elements **/
    private final List<T> newElements;

    /**
     * Constructor
     * @param source : source list
     * @param type : event type
     * @param index0 : first / last index 
     * @param index1 : first / last index 
     * @param previousElements : elements that were in list before event
     * @param newElements : corresponding elements that replaced previous elements after event
     */
    public TypedListDataEvent(Object source, int type, int index0, int index1,
                              List<T> previousElements, List<T> newElements) {
        super(source, type, index0, index1);
        this.previousElements = previousElements;
        this.newElements = newElements;
    }

    /**
     * New elements getter
     * @return -
     */
    public List<T> getNewElements() {
        return newElements;
    }

    /**
     * Previous elements getter
     * @return  -
     */
    public List<T> getPreviousElements() {
        return previousElements;
    }
}
