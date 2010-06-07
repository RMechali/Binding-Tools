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

package binding.tools;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Tools for the binding components using introspection.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class IntrospectionTools {

	/**
	 * Retrieves a bean property
	 * 
	 * @param bean
	 *            : bean to search in
	 * @param property
	 *            : property name
	 * @return - the bean property found
	 * @throws IllegalArgumentException
	 *             if the property does not exist in the bean
	 * @throws RuntimeException
	 *             if the introspection process failed
	 */
	public static PropertyDescriptor getProperty(Object bean, String property) {
		BeanInfo sourceInfo = null;
		try {
			sourceInfo = Introspector.getBeanInfo(bean.getClass());
		} catch (IntrospectionException e) {
			throw new RuntimeException(
					"Binding tools : introspection failed.\nReason: "
							+ e.getMessage());
		}
		for (PropertyDescriptor desc : sourceInfo.getPropertyDescriptors()) {
			if (desc.getName().equals(property)) {
				return desc;
			}
		}
		throw new IllegalArgumentException("No property " + property
				+ " in the bean class " + bean.getClass());
	}

	/**
	 * Retrieves a method within a bean
	 * 
	 * @param bean
	 *            : bean searched (not null)
	 * @param methodName
	 *            - method name
	 * @param parameters
	 *            - method parameters
	 * @return - the method found
	 * @throws RuntimeException
	 *             if a security problem happened (see
	 *             {@link Class#getMethod(String, Class...)})
	 * @throws IllegalArgumentException
	 *             : if the method is undefined
	 */
	public static Method retrieveMethod(Object bean, String methodName,
			Class<?>... parameters) {
		try {
			return bean.getClass().getMethod(methodName, parameters);
		} catch (SecurityException e) {
			throw new RuntimeException(e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("The method " + methodName
					+ " is not defined in bean " + bean);
		}
	}

}
