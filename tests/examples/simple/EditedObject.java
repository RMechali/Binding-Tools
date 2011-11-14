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
package examples.simple;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Edited bean for the demo
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class EditedObject {

    /**
     * Edited property logical name. This is a general good practice to declare
     * it, in order to facilitate refactor
     */
    public static String EDITED_PROPERTY = "editedProperty";

    /**
     * Change support
     */
    private final PropertyChangeSupport changeSupport;

    /** Edited property, bindable **/
    private int editedProperty;

    /**
     * Constructor
     */
    public EditedObject() {
        changeSupport = new PropertyChangeSupport(this);
        editedProperty = 50;
    }

    /**
     * Getter -
     * 
     * @return the editedProperty
     */
    public int getEditedProperty() {
        return editedProperty;
    }

    /**
     * Setter -
     * 
     * @param editedProperty
     *            the editedProperty to set
     */
    public void setEditedProperty(int editedProperty) {
        int oldValue = this.editedProperty;
        this.editedProperty = editedProperty;
        firePropertyChange(EDITED_PROPERTY, oldValue, this.editedProperty);
    }

    /**
     * Delegate method - adds a property change listener
     * 
     * @param propertyName : property name
     * @param listener : listener to add
     * 
     * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String,
     *      java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(String propertyName,
                                          PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Delegate method - fires a property change
     * 
     * @param propertyName : property name
     * @param oldValue : old value
     * @param newValue : new value
     * 
     * @see java.beans.PropertyChangeSupport#firePropertyChange(java.lang.String,
     *      int, int)
     */
    public void firePropertyChange(String propertyName, int oldValue,
                                   int newValue) {
        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Delegate method - removes a property change listener
     * 
     * @param propertyName : property name
     * @param listener : listener to remove
     * 
     * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String,
     *      java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(String propertyName,
                                             PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(propertyName, listener);
    }
}
