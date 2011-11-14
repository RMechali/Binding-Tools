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
import javax.swing.ListModel;

/**
 * An observable list for list binding (mutable to avoid confusion with MutableList from 
 * JGoodies). Note that this list follows the same design tha JGoodies Observable list but allows
 * ListModel to be typed. Furthermore it optimizes transfer of deleted elements to 
 * TypedListDataListener.
 * 
 * Copyright 2011, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * 
 * @param T : objects in the list
 */
public interface MutableList<T> extends List<T>, ListModel {

    /**
     * Returns the value at the specified index.  
     * @param index the requested index
     * @return the value at <code>index</code>
     */
    @Override
    T getElementAt(int index);

    /**
     * Adds a typed list data listener
     * @param listener  : listener
     */
    public void addListDataListener(TypedListDataListener<T> listener);

    /**
     * Removes a typed list data listener
     * @param listener  : listener
     */
    public void removeListDataListener(TypedListDataListener<T> listener);
}
