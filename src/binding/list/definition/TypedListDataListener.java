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

/**
 * List data listener with optimized methods and typed elements.
 * 
 * Copyright 2011, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public interface TypedListDataListener<T> {

    /**
     * An interval was added into listened list
     * @param event : event (describes added interval)
     */
    void intervalAdded(TypedListDataEvent event);

    /**
     * An interval was removed from listened list
     * @param event : event (describes removed interval)
     */
    void intervalRemoved(TypedListDataEvent event);

    /**
     * An interval was added into listened list
     * @param event : event (describes changed content)
     */
    void contentsChanged(TypedListDataEvent event);
}
