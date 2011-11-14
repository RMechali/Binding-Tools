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
package binding.property.source.adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import binding.property.source.PropertyBindingSource;

/**
 * A binding source adapter that converts Swing events into property change
 * events. Note that this adapter will be disposed if it is no longer listened
 * too and every reference to it as been removed.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * 
 * @param <T>
 *            : type of the event source
 */
public abstract class AbstractBindingSourceAdapter<T> implements
        PropertyBindingSource {

    /** Adapted property name **/
    public static final String ADAPTED_PROPERTY = "adaptedProperty";

    /** Adapted property **/
    private Object adaptedProperty;

    /** event source **/
    private final T eventSource;

    /** Property change support **/
    private PropertyChangeSupport changeSupport;

    /**
     * Constructor
     * 
     * @param eventSource : event source
     */
    public AbstractBindingSourceAdapter(T eventSource) {
        this.eventSource = eventSource;
        setAdaptedProperty(getInitialValue());
    }

    /**
     * Install listening system
     */
    protected void startListening() {
        // create adaptation support
        changeSupport = new PropertyChangeSupport(this);
        // let extending classes start the listening to event source
        startListening(this.eventSource);
    }

    /**
     * Uninstall listening system (and thus let this be collectible by the GC)
     */
    protected void stopListening() {
        // delete adaptation support
        this.changeSupport = null;
        // let extending classes stop the listening to event source
        stopListening(this.eventSource);
    }

    /**
     * Starts listening at events
     * 
     * @param eventSource
     *            : event source
     */
    protected abstract void startListening(T eventSource);

    /**
     * Stops listening at events (and thus let this be collectible by the GC)
     * 
     * @param eventSource
     *            : event source
     */
    protected abstract void stopListening(T eventSource);

    /**
     * Getter -
     * 
     * @return the adaptedProperty
     */
    protected Object getAdaptedProperty() {
        return adaptedProperty;
    }

    /**
     * Setter - (extending classes should call this method to notify the event
     * change)
     * 
     * @param adaptedProperty
     *            the adaptedProperty to set
     */
    protected void setAdaptedProperty(Object adaptedProperty) {
        Object oldValue = this.adaptedProperty;
        this.adaptedProperty = adaptedProperty;
        // fire value change
        if (changeSupport != null) {
            changeSupport.firePropertyChange(ADAPTED_PROPERTY, oldValue,
                                             this.adaptedProperty);
        }
    }

    /**
     * {@inherit}
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (listener != null && this.changeSupport == null) {
            // install listening system
            startListening();
        }
        // add the listener
        this.changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * {@inherit}
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        // remove the listener
        this.changeSupport.removePropertyChangeListener(listener);
        if (this.changeSupport != null
                && this.changeSupport.getPropertyChangeListeners().length == 0) {
            stopListening();
        }
    }

    /**
     * Getter -
     * 
     * @return the eventSource
     */
    public T getEventSource() {
        return eventSource;
    }
}
