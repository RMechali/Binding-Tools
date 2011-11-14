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
package binding.list;

import java.util.ArrayList;
import java.util.List;


import binding.IBindingLink;
import binding.list.definition.TypedListDataEvent;
import binding.list.definition.TypedListDataListener;
import binding.list.source.ListBindingSource;
import binding.list.target.ListBindingTarget;

/**
 * A binding link for list event. It is based on MutableList (a typed extension of java.util.List 
 * and javax.swing.ListModel). As for PropertyBindingLink, you can call terminate binding 
 * to terminate binding and ensure GC can recover your (such call will "make the illusion" to 
 * binding target that the list is now empty so that it can destroy its corresponding objects).
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * 
 * @param <T>
 *            : source list elements type
 */
public class ListBindingLink<T> implements TypedListDataListener<T>,
                                           IBindingLink<ListBindingSource<T>, ListBindingTarget<T>> {

    /** List binding source **/
    private ListBindingSource<T> bindingSource;

    /** List binding target **/
    private ListBindingTarget<T> bindingTarget;

    /**
     * Constructor
     * 
     * @param bindingSource : binding source
     * @param bindingTarget : binding target
     */
    public ListBindingLink(ListBindingSource<T> bindingSource,
                           ListBindingTarget<T> bindingTarget) {
        setBindingSource(bindingSource);
        setBindingTarget(bindingTarget);
    }

    /**
     * {@inherit}
     */
    @Override
    public ListBindingSource<T> getBindingSource() {
        return bindingSource;
    }

    /**
     * {@inherit}
     */
    @Override
    public void setBindingSource(ListBindingSource<T> bindingSource) {
        // terminate previous listening (keep previous elements list before)
        List<T> previousElements = getCurrentElements();
        if (this.bindingSource != null) {
            this.bindingSource.removeListDataListener(this);
        }

        // Set up the new source listening
        this.bindingSource = bindingSource;
        if (this.bindingSource != null) {
            this.bindingSource.addListDataListener(this);
        }

        // Update binding target state : emulate a remove all / add all event
        List<T> currentElements = getCurrentElements();

        if (!previousElements.isEmpty()) {
            fireRemoveAll(0, previousElements.size(), previousElements);
        }

        int currentSize = currentElements.size();
        if (currentSize != 0) {
            fireAddAll(0, currentSize - 1, currentElements);
        }
    }

    /**
     * {@inherit}
     */
    @Override
    public ListBindingTarget<T> getBindingTarget() {
        return bindingTarget;
    }

    /**
     * {@inherit}
     */
    @Override
    public void setBindingTarget(ListBindingTarget<T> bindingTarget) {
        // remove previous binding target : leave it removing all current elements if it is not empty
        final List<T> currentElements = getCurrentElements();
        int size = currentElements.size();
        if (this.bindingTarget != null && !currentElements.isEmpty()) {
            fireRemoveAll(0, size - 1, currentElements);
        }

        // set new target
        this.bindingTarget = bindingTarget;

        // initialize new target binding value : fire an add All elements if the
        // source list is not empty
        if (!currentElements.isEmpty()) {
            fireAddAll(0, size - 1, currentElements);
        }
    }

    /**
     * {@inherit}
     */
    @Override
    public void terminateBinding() {
        // remove source first so that target is notified
        setBindingSource(null);
        // remove target to break every double link
        setBindingTarget(null);
    }

    /**
     * {@inherit}
     */
    @Override
    public void contentsChanged(TypedListDataEvent e) {
        fireChanges(e.getIndex0(), e.getIndex1(), e.getPreviousElements(), e.getNewElements());
    }

    /**
     * Emulates a changes event by sending an add / remove event
     * 
     * @param firstIndex : first index that changed
     * @param lastIndex  : last index that changed
     * @param previousElements : previous elements
     * @param newElements : new elements
     */
    private void fireChanges(int firstIndex, int lastIndex, List<T> previousElements,
                             List<T> newElements) {
        // convert that event into add / remove elements
        // a - Fire remove all previous elements
        fireRemoveAll(firstIndex, lastIndex, previousElements);
        // b - Fire add all new elements
        fireAddAll(firstIndex, lastIndex, newElements);
    }

    /**
     * {@inherit}
     */
    @Override
    public void intervalAdded(TypedListDataEvent e) {
        // fire the list add event
        fireAddAll(e.getIndex0(), e.getIndex1(), e.getNewElements());
    }

    /**
     * {@inherit}
     */
    @Override
    public void intervalRemoved(TypedListDataEvent e) {
        // fire the list remove event
        fireRemoveAll(e.getIndex0(), e.getIndex1(), e.getPreviousElements());
    }

    /**
     * Computes and returns the current source elements
     * 
     * @return an empty list if source is not set, the source elements
     *         otherwise
     */
    private List<T> getCurrentElements() {
        if (this.bindingSource != null) {
            return this.bindingSource.getElements();
        }
        return new ArrayList<T>();
    }

    /**
     * Fires a multiple elements removed event
     * 
     * @param firstIndex : first remove index in the source list
     * @param lastIndex : last remove index in the source list
     * @param removedElements : removed elements
     */
    private void fireRemoveAll(int firstIndex, int lastIndex, List<T> removedElements) {
        if (this.bindingTarget != null) {
            this.bindingTarget.intervalRemoved(getCurrentElements(), removedElements,
                                               firstIndex, lastIndex);
        }
    }

    /**
     * Fires an add all elements
     * 
     * @param firstIndex : first element added index
     * @param lastIndex : last element added index
     * @param addedElements : added elements
     */
    private void fireAddAll(int firstIndex, int lastIndex, List<T> addedElements) {
        if (this.bindingTarget != null) {
            // build the list of elements added
            List<T> currentElements = getCurrentElements();
            // fire elements added
            this.bindingTarget.intervalAdded(currentElements, addedElements,
                                             firstIndex, lastIndex);
        }
    }
}
