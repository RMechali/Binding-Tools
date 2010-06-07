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

package binding.property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import binding.property.source.PropertyBindingSource;
import binding.property.target.PropertyBindingTarget;

/**
 * A binding link for property changes. It uses a binding source and a binding
 * target to perform binding. You can call terminateBinding() or
 * setBindingSource(null) to terminate binding and ensure it can be garbage
 * collected.
 * 
 * The property binding process updates the target as soon as you have set it.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class PropertyBindingLink implements PropertyChangeListener {

	/**
	 * Binding source, responsible for providing register / unregister change
	 * listener and initial value getter
	 **/
	private PropertyBindingSource bindingSource;

	/** Binding target, responsible for updating / notifying the value change **/
	private PropertyBindingTarget bindingTarget;

	/**
	 * Constructor
	 * 
	 * @param bindingSource
	 *            : binding source
	 * @param bindingTarget
	 *            : binding target
	 */
	public PropertyBindingLink(PropertyBindingSource bindingSource,
			PropertyBindingTarget bindingTarget) {
		super();
		// a - set the source first (to not notify two times the target)
		setBindingSource(bindingSource);
		// b - set the target
		setBindingTarget(bindingTarget);
	}

	/**
	 * Getter -
	 * 
	 * @return the bindingSource
	 */
	public PropertyBindingSource getBindingSource() {
		return bindingSource;
	}

	/**
	 * Setter -
	 * 
	 * @param bindingSource
	 *            the bindingSource to set
	 */
	public void setBindingSource(PropertyBindingSource bindingSource) {
		// Change binding source
		if (this.bindingSource != null) {
			// stop listening for the previous source events
			this.bindingSource.removePropertyChangeListener(this);
		}

		this.bindingSource = bindingSource;

		if (this.bindingSource != null) {
			// begin binding for the new source events
			this.bindingSource.addPropertyChangeListener(this);
		}
		// initialize target (with null value if there is no longer source)
		notifyInitialValue();
	}

	/**
	 * Getter -
	 * 
	 * @return the bindingTarget
	 */
	public PropertyBindingTarget getBindingTarget() {
		return bindingTarget;
	}

	/**
	 * Setter -
	 * 
	 * @param bindingTarget
	 *            the bindingTarget to set
	 */
	public void setBindingTarget(PropertyBindingTarget bindingTarget) {
		this.bindingTarget = bindingTarget;
		// initialize target
		notifyInitialValue();
	}

	/**
	 * Terminates the current binding (it avoids double link to ensure garbage
	 * collection)
	 */
	public void terminateBinding() {
		setBindingTarget(null);
	}

	/**
	 * Notifies the target of the initial value (to avoid unnecessary
	 * initialization code)
	 * 
	 * @note : binding source must not be null
	 */
	private void notifyInitialValue() {
		if (this.bindingSource != null) {
			updateTarget(this.bindingSource.getInitialValue());
		} else {
			updateTarget(null);
		}
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// the property changed, let the target update it
		updateTarget(evt.getNewValue());
	}

	/**
	 * Makes the binding target update
	 * 
	 * @param newValue
	 *            : new value
	 */
	private void updateTarget(Object newValue) {
		if (this.bindingTarget != null) {
			// let the target update the new value
			this.bindingTarget.updateTarget(newValue);
		}
	}

}
