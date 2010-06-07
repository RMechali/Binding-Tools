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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import binding.tools.IntrospectionTools;

/**
 * Property binding source for a bean property.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class BeanBindingSource extends AbstractStandardBindingSource {

	/** Read method (not null) **/
	private final Method readMethod;

	/** Bean property name **/
	private final String propertyName;

	/**
	 * Constructor.<br>
	 * The constructor may propagate exception due to introspection mechanisms.
	 * Usually this should mean that your bean properties are bad defined.
	 * 
	 * @param beanSource
	 *            : bean source
	 * @param propertyName
	 *            : name of the property to be binded (that will be used to
	 *            retrieve the getter method and to listen to property changes)
	 * 
	 * @throws IllegalArgumentException
	 *             if the bean source is null
	 * @throws IllegalArgumentException
	 *             if the bean property name is null
	 * @throws IllegalArgumentException
	 *             if the get method is not defined for that property in the
	 *             bean source
	 * @throws IllegalArgumentException
	 *             if the
	 *             addPropertyChangeListener(String,PropertyChangeListener) is
	 *             not defined for the bean source
	 * @throws IllegalArgumentException
	 *             if the
	 *             removePropertyChangeListener(String,PropertyChangeListener)
	 *             is not defined for the bean source
	 * @see IntrospectionTools#getProperty(Object, String) for introspection
	 *      exceptions
	 */
	public BeanBindingSource(Object beanSource, String propertyName) {

		super(beanSource);

		// check parameters
		if (propertyName == null) {
			throw new IllegalArgumentException(getClass()
					+ ": The bean binding property name can not be null");
		}

		// store the property name
		this.propertyName = propertyName;

		// retrieve getter method
		PropertyDescriptor property = IntrospectionTools.getProperty(
				beanSource, propertyName);
		readMethod = property.getReadMethod();
		if (readMethod == null) {
			throw new IllegalArgumentException(
					"No read method defined for property " + propertyName
							+ " in bean " + beanSource);
		}

	}

	/**
	 * {@inherit}
	 */
	@Override
	public Object getInitialValue() {
		try {
			return readMethod.invoke(getBeanSource());
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
	protected String getBindedName() {
		return propertyName;
	}

}
