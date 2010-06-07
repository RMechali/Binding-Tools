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

package binding.list.target;

import java.util.List;

/**
 * A very straight forward binding implementation that propagate update events
 * to another list (by listening this second one, you could trigger repaint for
 * example - to do so use one of the JGoodies common lists or implement the
 * Swing untyped ListModel).
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * 
 * @param <T>
 *            : Type of elements in the target list and in the source one
 */
public class BasicListBindingTarget<T> extends AbstractListBindingTarget<T, T> {

	/**
	 * Constructor
	 * 
	 * @param target
	 *            : target list
	 * @throws IllegalArgumentException
	 *             : if the target list is null
	 */
	public BasicListBindingTarget(List<T> target) {
		super(target);
	}

	/**
	 * {@inherit}
	 */
	@Override
	protected T convert(T element) {
		return element;
	}

}
