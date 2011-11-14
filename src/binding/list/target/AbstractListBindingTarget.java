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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class that propagates changes of a source list of type MutableList<T> to a
 * target list of type List<U>
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * 
 * @param <T> : source list elements type
 * @param <U> : target list elements type
 */
public abstract class AbstractListBindingTarget<T, U> implements
        ListBindingTarget<T> {

    /** Target list **/
    private final List<U> target;

    /**
     * Constructor
     * 
     * @param target : target list
     * @throws IllegalArgumentException : if the target list is null
     */
    public AbstractListBindingTarget(List<U> target) {
        // check parameters
        if (target == null) {
            throw new IllegalArgumentException(getClass()
                    + ": the target list can not be null");
        }

        this.target = target;
    }

    @Override
    public final void intervalAdded(List<T> sourceElements, List<T> elementsAdded,
                                    int insertionIndex, int lastInsertionIndex) {
        this.target.addAll(insertionIndex, convertElements(insertionIndex, elementsAdded));
    }

    /**
     * Converts a a list of elements of type T into a list of element of type U
     * (target list type)
     * 
     * @param toConvert : elements to convert
     * @param index  : index to convert
     * @return - the new elements list
     */
    protected final Collection<U> convertElements(int index, List<T> toConvert) {
        List<U> converted = new ArrayList<U>();
        for (int i = 0; i < toConvert.size(); i++) {
            converted.add(convert(index, toConvert.get(i)));
        }
        return converted;
    }

    /**
     * Converts an element of type T into an element of type U
     * 
     * @param  elementIndex : index of the element to convert in list 
     * @param element : element to convert
     * @return - the converted element
     */
    protected abstract U convert(int elementIndex, T element);

    /**
     * {@inherit}
     */
    @Override
    public final void intervalRemoved(List<T> sourceElements, List<T> elementsRemoved,
                                      int firstIndex,
                                      int lastIndex) {

        List<U> toRemove = this.target.subList(firstIndex, lastIndex + 1);
        removeElements(toRemove);
    }

    /**
     * Removes the following elements from the local copy list.
     * Note : pay attention to call this method if you override it to dispose of elements removed
     * 
     * @param toRemove : elements to remove
     */
    protected void removeElements(List<U> toRemove) {
        toRemove.clear();
    }
}
