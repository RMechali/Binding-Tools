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

package binding.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import binding.IBindingLink;
import binding.list.source.ListBindingSource;
import binding.list.target.ListBindingTarget;

/**
 * A binding link for list event. It is based on JGoodies Observable lists
 * system. As for PropertyBindingLink, you can call terminate binding or
 * setBindingSource(null) to terminate binding and ensure GC can recover your
 * objects.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * 
 * @param <T>
 *            : source list elements type
 */
public class ListBindingLink<T> implements ListDataListener,
		IBindingLink<ListBindingSource<T>, ListBindingTarget<T>> {

	/** List binding source **/
	private ListBindingSource<T> bindingSource;

	/** List binding target **/
	private ListBindingTarget<T> bindingTarget;

	/** Previous list value (used to compute differences) **/
	private List<T> previousValue;

	/**
	 * Constructor
	 * 
	 * @param bindingSource
	 *            : binding source
	 * @param bindingTarget
	 *            : binding target
	 */
	public ListBindingLink(ListBindingSource<T> bindingSource,
			ListBindingTarget<T> bindingTarget) {
		previousValue = new ArrayList<T>();
		setBindingSource(bindingSource);
		setBindingTarget(bindingTarget);
	}

	/**
	 * {@inherit}
	 */
	@Override
	public ListBindingSource<T> getBindingSource() {
		return bindingSource;
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void setBindingSource(ListBindingSource<T> bindingSource) {
		// terminate previous listening
		if (this.bindingSource != null) {
			this.bindingSource.removeListDataListener(this);
		}

		this.bindingSource = bindingSource;

		// start the new listening
		if (this.bindingSource != null) {
			this.bindingSource.addListDataListener(this);
		}

		// initialize target
		int previousSize = previousValue.size();
		// store the new source elements value (make it non correlated with
		// source)
		List<T> currentElements = getCurrentElements();
		this.previousValue = new ArrayList<T>(currentElements);
		// initialize binding source : emulate a remove all / add all event
		if (previousSize != 0) {
			fireRemoveAll(0, previousSize - 1);
		}
		int currentSize = currentElements.size();
		if (currentSize != 0) {
			fireAddAll(0, currentSize - 1);
		}
	}

	/**
	 * {@inherit}
	 */
	@Override
	public ListBindingTarget<T> getBindingTarget() {
		return bindingTarget;
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void setBindingTarget(ListBindingTarget<T> bindingTarget) {
		this.bindingTarget = bindingTarget;

		// initialize target binding value : fire an add All elements if the
		// source list is not empty
		int size = getCurrentElements().size();
		if (size != 0) {
			fireAddAll(0, size - 1);
		}
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void terminateBinding() {
		setBindingSource(null);
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void contentsChanged(ListDataEvent e) {
		if (this.bindingTarget != null) {
			fireChanges(e.getIndex0(), e.getIndex1());
		}
	}

	/**
	 * Emulates a changes event by sending an add / remove event
	 * 
	 * @param firstIndex
	 *            : first index that changed
	 * @param lastIndex
	 *            : last index that changed
	 */
	private void fireChanges(int firstIndex, int lastIndex) {
		// convert that event into add / remove elements
		// a - store the new list state (make it non correlated with source)
		List<T> previousStep = this.previousValue;
		previousValue = new ArrayList<T>(getCurrentElements());
		// b - remove all (ensure the last item in in the previous list
		// size)
		fireRemoveAll(firstIndex, Math.min(lastIndex, previousStep.size()));
		// c - add all
		fireAddAll(firstIndex, lastIndex);
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void intervalAdded(ListDataEvent e) {
		// store the new list state (make it non correlated with source)
		this.previousValue = new ArrayList<T>(this.getCurrentElements());
		// fire the list change
		fireAddAll(e.getIndex0(), e.getIndex1());
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void intervalRemoved(ListDataEvent e) {
		// store the new list state (make it non correlated with source)
		this.previousValue = new ArrayList<T>(this.getCurrentElements());
		// fire changes
		fireRemoveAll(e.getIndex0(), e.getIndex1());
	}

	/**
	 * Computes and returns the current source elements
	 * 
	 * @return - an empty list if source is not set, the source elements
	 *         otherwise
	 */
	private List<T> getCurrentElements() {
		if (this.bindingSource != null) {
			return this.bindingSource.getElements();
		}
		return new ArrayList<T>();
	}

	/**
	 * Fires a multiple elements removed event
	 * 
	 * @param firstIndex
	 *            : first remove index in the source list
	 * @param lastIndex
	 *            : last remove index in the source list
	 */
	private void fireRemoveAll(int firstIndex, int lastIndex) {
		if (this.bindingTarget != null) {
			this.bindingTarget.intervalRemoved(getCurrentElements(),
					firstIndex, lastIndex);
		}
	}

	/**
	 * Fires an add all elements
	 * 
	 * @param firstIndex
	 *            : first element added index
	 * @param lastIndex
	 *            : last element added index
	 */
	private void fireAddAll(int firstIndex, int lastIndex) {
		if (this.bindingTarget != null) {
			// build the list of elements added
			List<T> currentElements = getCurrentElements();
			List<T> elementsAdded = buildSubList(currentElements, firstIndex,
					lastIndex);
			// fire elements added
			this.bindingTarget.intervalAdded(currentElements, elementsAdded,
					firstIndex);
		}
	}

	/**
	 * Builds a sub list from index0 to index1 (inclusive)
	 * 
	 * @param list
	 *            : initial list
	 * @param index0
	 *            : first index of the sub list
	 * @param index1
	 *            : last index of the sub list
	 * @return - the built sub list
	 */
	private static <T> List<T> buildSubList(List<T> list, int index0, int index1) {
		List<T> subList = new ArrayList<T>();
		for (int i = index0; i <= index1; i++) {
			subList.add(list.get(i));
		}
		return subList;
	}

}
