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

package binding.property.source.object;

import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;

import binding.property.source.PropertyBindingSource;
import binding.tools.IntrospectionTools;

/**
 * An abstract binding source able to recover add and remove listeners methods
 * for a standard {@link PropertyChangeListener} mechanism.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public abstract class AbstractObjectBindingSource implements
		PropertyBindingSource {

	/** Add listener method (not null) **/
	private final Method addListenerMethod;

	/** Remove listener method (not null) **/
	private final Method removeListenerMethod;

	/** Bean source **/
	private final Object beanSource;

	/**
	 * Constructor
	 * 
	 * @param beanSource
	 *            : bean source (when add and remove property change listener
	 *            methods should be found)
	 * @throws IllegalArgumentException
	 *             if the bean source is null
	 * @throws IllegalArgumentException
	 *             if the
	 *             addPropertyChangeListener(String,PropertyChangeListener) is
	 *             not defined for the bean source
	 * @throws IllegalArgumentException
	 *             if the
	 *             removePropertyChangeListener(String,PropertyChangeListener)
	 *             is not defined for the bean source
	 */
	public AbstractObjectBindingSource(Object beanSource) {

		// check parameters
		if (beanSource == null) {
			throw new IllegalArgumentException(getClass()
					+ ": The bean binding source can not be null");
		}

		this.beanSource = beanSource;

		// retrieve add listener method (or leave and exception propagate)
		addListenerMethod = IntrospectionTools.retrieveMethod(beanSource,
				"addPropertyChangeListener", String.class,
				PropertyChangeListener.class);

		// retrieve remove listener method (or leave and exception propagate)
		removeListenerMethod = IntrospectionTools.retrieveMethod(beanSource,
				"removePropertyChangeListener", String.class,
				PropertyChangeListener.class);
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// add the listener
		try {
			addListenerMethod.invoke(beanSource, getBindedName(), listener);
		} catch (Exception e) {
			// convert the error into a runtime error to not force the user
			// catching it
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// add the listener
		try {
			removeListenerMethod.invoke(beanSource, getBindedName(), listener);
		} catch (Exception e) {
			// convert the error into a runtime error to not force the user
			// catching it
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the binder property name (that should be fired by the bean
	 * listened)
	 * 
	 * @return -
	 */
	protected abstract String getBindedName();

	/**
	 * Getter -
	 * 
	 * @return the beanSource
	 */
	public Object getBeanSource() {
		return beanSource;
	}

}
