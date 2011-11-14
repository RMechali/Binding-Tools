/** 
 * This file is part of Binding Tools project.
 *
 * Binding Tools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * Binding Tools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and GNU Lesser General Public License along with Binding Tools project.
 * If not, see <http://www.gnu.org/licenses/>.
 **/
package binding.list.target;

import java.util.List;

/**
 * A list binding target that receive add / remove / change / replace
 * operations. It provides typed source elements at each event fire time (to
 * ensure user can always update target without context). It also provides the
 * list segment that changed in the source list
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * 
 * @param <T>
 *            : source list elements type
 */
public interface ListBindingTarget<T> {

    /**
     * Indicates that some elements where added in the source list
     * 
     * @param sourceElements : source list content
     * @param elementsAdded : elements added
     * @param insertionIndex : first element added insertion index
     * @param lastInsertionIndex : last insertion index (inclusive)
     */
    void intervalAdded(List<T> sourceElements, List<T> elementsAdded,
                       int insertionIndex, int lastInsertionIndex);

    /**
     * Indicates that some elements where removed in the source list
     * 
     * @param sourceElements
     *            : source list content
     * @param elementsRemoved : elements removed
     * @param firstIndex : first remove index in the previous list value
     * @param lastIndex : last remove index in the previous list value (inclusive)
     */
    void intervalRemoved(List<T> sourceElements, List<T> elementsRemoved, int firstIndex,
                         int lastIndex);
}
