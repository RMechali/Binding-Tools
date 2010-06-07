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

package binding.list.source;

import java.util.List;

import javax.swing.event.ListDataListener;

/**
 * A list binding source, that allows adding / removing a list listener and
 * getting the initial value
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * 
 * @param <T>
 *            : source list elements type
 */
public interface ListBindingSource<T> {

	/**
	 * Adds a list data listener to the source list changes
	 * 
	 * @param listener
	 *            : listener
	 */
	void addListDataListener(ListDataListener listener);

	/**
	 * Remove a list data listener
	 * 
	 * @param listener
	 *            : listener
	 */
	void removeListDataListener(ListDataListener listener);

	/**
	 * Returns the list elements (called at binding start or o major list
	 * update)
	 * 
	 * @return -
	 */
	List<T> getElements();

}
