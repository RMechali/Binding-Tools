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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class that propagates changes of a source list of type ObservableList<T> to a
 * target list of type ObservableList<U>
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * 
 * @param <T>
 *            : source list elements type
 * @param <U>
 *            : target list elements type
 */
public abstract class AbstractListBindingTarget<T, U> implements
		ListBindingTarget<T> {

	/** Target list **/
	private final List<U> target;

	/**
	 * Constructor
	 * 
	 * @param target
	 *            : target list
	 * @throws IllegalArgumentException
	 *             : if the target list is null
	 */
	public AbstractListBindingTarget(List<U> target) {
		// check parameters
		if (target == null) {
			throw new IllegalArgumentException(getClass()
					+ ": the target list can not be null");
		}

		this.target = target;
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void intervalAdded(List<T> sourceElements, List<T> elementsAdded,
			int insertionIndex) {
		this.target.addAll(insertionIndex, convertElements(elementsAdded));
	}

	/**
	 * Converts a a list of elements of type T into a list of element of type U
	 * (target list type)
	 * 
	 * @param toConvert
	 *            : elements to convert
	 * @return - the new elements list
	 */
	protected Collection<U> convertElements(List<T> toConvert) {
		List<U> converted = new ArrayList<U>();
		for (T element : toConvert) {
			converted.add(convert(element));
		}
		return converted;
	}

	/**
	 * Converts an element of type T into an element of type U
	 * 
	 * @param element
	 *            : element to convert
	 * @return - the converted element
	 */
	protected abstract U convert(T element);

	/**
	 * {@inherit}
	 */
	@Override
	public void intervalRemoved(List<T> sourceElements, int firstIndex,
			int lastIndex) {
		// number of elements to remove
		int toRemove = (lastIndex - firstIndex) + 1;
		for (int i = 0; i < toRemove; i++) {
			// always remove the first index, that should shift the list tail
			this.target.remove(firstIndex);
		}
	}
}
