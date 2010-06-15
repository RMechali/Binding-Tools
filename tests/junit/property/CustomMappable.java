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

package junit.property;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for tests; it defines a map over which it fires changes. However, it
 * does not have any meaning as it has been created for tets
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class CustomMappable {

	/** Map of key / properties **/
	private final Map<String, Object> myProperties;

	/** Property change support **/
	private final PropertyChangeSupport changeSupport;

	/**
	 * Constructor
	 */
	public CustomMappable() {
		myProperties = new HashMap<String, Object>();
		changeSupport = new PropertyChangeSupport(this);
	}

	/**
	 * Puts a property in the properties map
	 * 
	 * @param key
	 *            : property key
	 * @param newValue
	 *            : property new value
	 */
	public void putValue(String key, Object newValue) {
		Object oldValue = myProperties.get(key);
		myProperties.put(key, newValue);
		changeSupport.firePropertyChange(key, oldValue, newValue);
	}

	/**
	 * Returns the current value for the key as parameter
	 * 
	 * @param key
	 *            : property key
	 * @return - the value for that key
	 */
	public Object getValue(String key) {
		return myProperties.get(key);
	}

	/**
	 * Delegate method.
	 * 
	 * @param propertyName
	 *            -
	 * @param listener
	 *            -
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String,
	 *      java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * Delegate method.
	 * 
	 * @param propertyName
	 *            -
	 * @param listener
	 *            -
	 * 
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String,
	 *      java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}

}
