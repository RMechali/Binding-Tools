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

package binding.property.source;

import java.beans.PropertyChangeListener;

/**
 * A property binding source that provide initial value and add / remove change
 * listener method. If your event source is not a bean property, you may
 * implement a custom binding source using PropertyChangeSupport as delegate.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public interface PropertyBindingSource {

	/**
	 * Getter for the initial value
	 * 
	 * @return - the initial value
	 */
	Object getInitialValue();

	/**
	 * Adds a property change listener for this property binding source
	 * 
	 * @param listener
	 *            : listener to add
	 */
	void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Removes a property change listener for this property binding source
	 * 
	 * @param listener
	 *            : listener to remove
	 */
	void removePropertyChangeListener(PropertyChangeListener listener);

}
