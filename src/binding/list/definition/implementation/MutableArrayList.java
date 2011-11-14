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
package binding.list.definition.implementation;

import binding.list.definition.MutableList;
import binding.list.definition.TypedListDataEvent;
import binding.list.definition.TypedListDataListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Mutable array list implementation
 * 
 * Copyright 2011, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * @param <T> : list elements type
 */
public class MutableArrayList<T> extends ArrayList<T> implements MutableList<T>, Serializable {

    /** Typed listeners **/
    private Collection<TypedListDataListener<T>> _typedListeners;

    /** Untyped listeners **/
    private Collection<ListDataListener> _untypedListeners;

    /**
     * Constructor
     */
    public MutableArrayList() {
        super();
    }

    /**
     * Constructor
     * @param initialElements : initial elements 
     */
    public MutableArrayList(Collection<? extends T> initialElements) {
        super(initialElements);
    }

    /**
     * Constructor
     * @param initialCapacity : initial capacity
     */
    public MutableArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getElementAt(int index) {
        return get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T set(int index, T element) {
        T oldElement = super.set(index, element);
        fireContentsChanged(index, oldElement, element);
        return oldElement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T element) {
        // not using this class method because parent is optimized for this operation
        super.add(element);
        List<T> addedElements = new ArrayList<T>(1);
        addedElements.add(element);
        fireIntervalAdded(size() - 1, addedElements);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, T element) {
        super.add(index, element);
        List<T> addedElements = new ArrayList<T>(1);
        addedElements.add(element);
        fireIntervalAdded(index, addedElements);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        // not using this class method because parent is optimized for this operation
        int index = size();
        if (super.addAll(c)) {
            fireIntervalAdded(index, new ArrayList<T>(c));
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (super.addAll(index, c)) {
            fireIntervalAdded(index, new ArrayList<T>(c));
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T remove(int index) {
        final T object = super.remove(index);
        List<T> removedElements = new ArrayList<T>();
        removedElements.add(object);
        fireIntervalRemoved(index, removedElements);
        return object;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        final int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        final ArrayList<T> copy = new ArrayList<T>(this);
        super.clear();
        fireIntervalRemoved(0, copy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addListDataListener(TypedListDataListener<T> listener) {
        if (listener == null) {
            throw new RuntimeException(getClass() + " : you can not add a null listener");
        }
        getTypedListeners().add(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListDataListener(TypedListDataListener<T> listener) {
        if (listener == null) {
            throw new RuntimeException(getClass() + " : you can not remove a null listener");
        }
        getTypedListeners().add(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addListDataListener(ListDataListener listener) {
        if (listener == null) {
            throw new RuntimeException(getClass() + " : you can not add a null listener");
        }
        getUntypedListeners().add(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListDataListener(ListDataListener listener) {
        if (listener == null) {
            throw new RuntimeException(getClass() + " : you can not remove a null listener");
        }
        getUntypedListeners().add(listener);
    }

    /**
     * Returns typed listeners (never null)
     * @return -
     */
    public Collection<TypedListDataListener<T>> getTypedListeners() {
        if (_typedListeners == null) {
            _typedListeners = new ArrayList<TypedListDataListener<T>>();
        }
        return _typedListeners;
    }

    /**
     * Returns untyped listeners (never null)
     * @return  -
     */
    public Collection<ListDataListener> getUntypedListeners() {
        if (_untypedListeners == null) {
            _untypedListeners = new ArrayList<ListDataListener>();
        }
        return _untypedListeners;
    }

    /**
     * Fires an interval added event
     * @param index : insertion index
     * @param addedElements : added elements
     */
    protected void fireIntervalAdded(int index, List<T> addedElements) {
        TypedListDataEvent<T> event = null;

        for (TypedListDataListener<T> listener : getTypedListeners()) {
            if (event == null) {
                event = new TypedListDataEvent<T>(event, ListDataEvent.INTERVAL_ADDED, index,
                                                  index + addedElements.size() - 1, null,
                                                  addedElements);
            }
            listener.intervalAdded(event);
        }

        for (ListDataListener listener : getUntypedListeners()) {
            if (event == null) {
                event = new TypedListDataEvent<T>(event, ListDataEvent.INTERVAL_ADDED, index,
                                                  index + addedElements.size() - 1, null,
                                                  addedElements);
            }
            listener.intervalAdded(event);

        }
    }

    /**
     * Fires an interval removed event
     * @param index : insertion index
     * @param removedElements : removed elements
     */
    protected void fireIntervalRemoved(int index, List<T> removedElements) {
        TypedListDataEvent<T> event = null;

        for (TypedListDataListener<T> listener : getTypedListeners()) {
            if (event == null) {
                event = new TypedListDataEvent<T>(event, ListDataEvent.INTERVAL_REMOVED, index,
                                                  index + removedElements.size() - 1,
                                                  removedElements, null);
            }
            listener.intervalRemoved(event);
        }

        for (ListDataListener listener : getUntypedListeners()) {
            if (event == null) {
                event = new TypedListDataEvent<T>(event, ListDataEvent.INTERVAL_REMOVED, index,
                                                  index + removedElements.size() - 1,
                                                  removedElements, null);
            }
            listener.intervalRemoved(event);
        }
    }

    /**
     * Firest a content changed event
     * @param index : insertion index
     * @param oldElement : old value at that index
     * @param element  : new value at that index
     */
    protected void fireContentsChanged(int index, T oldElement, T element) {
        TypedListDataEvent<T> event = null;

        for (TypedListDataListener<T> listener : getTypedListeners()) {
            if (event == null) {
                List<T> previousElements = new ArrayList<T>(1);
                List<T> newElements = new ArrayList<T>(1);
                previousElements.add(oldElement);
                newElements.add(element);
                event = new TypedListDataEvent<T>(event, ListDataEvent.CONTENTS_CHANGED, index,
                                                  index, previousElements, newElements);
            }
            listener.contentsChanged(event);
        }

        for (ListDataListener listener : getUntypedListeners()) {
            if (event == null) {
                // typed event for untyped listeners, do not provide lists
                event = new TypedListDataEvent<T>(event, ListDataEvent.CONTENTS_CHANGED, index,
                                                  index, null, null);
            }
            listener.contentsChanged(event);
        }
    }
}
