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

package binding.property.target;

import java.lang.reflect.Method;

import binding.tools.IntrospectionTools;

/**
 * Map binding target, that reflects source changes directly onto a
 * put[XXX](propertyKey,value) method (for instance, this is a behavior adapted
 * to SwingX AbstractAction.putValue(...) method)<br>
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)<br>
 * 
 * @note : the binding methods will work for a map (String, Object) that is the
 *       most used actually. this is due to Java introspection restriction
 *       (about generic type methods). Actually it would be possible to do it if
 *       the user provides the set method, but then the code is a lot more
 *       verbose due to introspection again.<br>
 */
public class MapBindingTarget implements PropertyBindingTarget {

	/** Target bean **/
	private final Object beanTarget;

	/** Setter method **/
	private final Method writeMethod;

	/** Property key to be used to set the property value **/
	private final String propertyKey;

	/**
	 * 
	 * Constructor
	 * 
	 * @param beanTarget
	 *            : bean target
	 * @param setMethodName
	 *            : set method name name (for instance "putValue" if your bean
	 *            source is abstract action)
	 * @param propertyKey
	 *            : key of the property to update on value changes
	 * 
	 * @throws IllegalArgumentException
	 *             if the bean target is null
	 * @throws IllegalArgumentException
	 *             if the bean property name is null
	 * @throws IllegalArgumentException
	 *             if the set method is not defined for that property in the
	 *             bean source
	 * @warning : the setter type cannot be checked so be carefull while using
	 *          this type of target
	 */
	public MapBindingTarget(Object beanTarget, String setMethodName,
			String propertyKey) {

		// check parameters
		if (beanTarget == null) {
			throw new IllegalArgumentException(getClass()
					+ ": The bean binding target can not be null");
		}
		if (setMethodName == null) {
			throw new IllegalArgumentException(getClass()
					+ ": The bean map set method name can not be null");
		}
		if (propertyKey == null) {
			throw new IllegalArgumentException(getClass()
					+ ": The bean map property key can not be null");
		}

		// store the target bean
		this.beanTarget = beanTarget;
		// store the map property key
		this.propertyKey = propertyKey;
		// retrieve the write method
		this.writeMethod = IntrospectionTools.retrieveMethod(beanTarget,
				setMethodName, String.class, Object.class);
		if (this.writeMethod == null) {
			// no write method defined
			throw new IllegalArgumentException(getClass()
					+ ": the write method " + setMethodName
					+ "(String,Object) does not exist in "
					+ beanTarget.getClass());
		}
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void updateTarget(Object newValue) {
		try {
			writeMethod.invoke(beanTarget, propertyKey, newValue);
		} catch (Exception e) {
			// convert the error into a runtime error to not force the user
			// catching it
			throw new RuntimeException(e);
		}
	}
}
