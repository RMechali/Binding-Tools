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

import binding.list.definition.MutableList;
import binding.list.definition.TypedListDataListener;
import java.util.List;

/**
 * A straight forward implementation based on JGoodies observable typed list.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * 
 * @param <T>
 *            : Type of elements in the list
 */
public class BasicListBindingSource<T> implements ListBindingSource<T> {

    /**
     * Source observable list
     */
    private final MutableList<T> list;

    /**
     * Constructor
     * 
     * @param list : source list
     * @throws IllegalArgumentException if the source list is null
     */
    public BasicListBindingSource(MutableList<T> list) {
        // check parameters
        if (list == null) {
            throw new IllegalArgumentException(getClass()
                    + ": the source list can not be null");
        }

        this.list = list;
    }

    /**
     * {@inherit}
     */
    @Override
    public void addListDataListener(TypedListDataListener listener) {
        this.list.addListDataListener(listener);

    }

    /**
     * {@inherit}
     */
    @Override
    public void removeListDataListener(TypedListDataListener listener) {
        this.list.removeListDataListener(listener);
    }

    /**
     * {@inherit}
     */
    @Override
    public List<T> getElements() {
        return this.list;
    }
}
