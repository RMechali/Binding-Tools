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

import java.lang.reflect.Method;

import binding.tools.IntrospectionTools;

/**
 * A property biding source to use when the bean stores many values in a map and
 * fires changes according with the map key (see AbstractAction implementation
 * for instance). To be used, the bean must define the methods below:<br>
 * - get[PropName](mapKey) (forInstance getValue(Action.SHORT_DESCRIPTION))<br>
 * - addPropertyChangeListener(mapKey)<br>
 * - removePropertyChangeListener(mapKey)
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class MapBindingSource extends AbstractObjectBindingSource {

	/** Bean property name **/
	private final String propertyKey;

	/** Read method **/
	private final Method readMethod;

	/**
	 * 
	 * Constructor
	 * 
	 * @param beanSource
	 *            : bean source
	 * @param getMethodName
	 *            : get method name (for instance "getValue" stands for the
	 *            method "getValue(propertyKey)")
	 * @param propertyKey
	 *            : property key (the map contained key that will be fired - see
	 *            #AbstractAction.putValue(String,Object) for an example)
	 * @throws IllegalArgumentException
	 *             if the bean source is null
	 * @throws IllegalArgumentException
	 *             if the get method name is null
	 * @throws IllegalArgumentException
	 *             if the map property key is null
	 * @throws IllegalArgumentException
	 *             if the get method is not defined for the bean source
	 * @throws IllegalArgumentException
	 *             if the
	 *             addPropertyChangeListener(String,PropertyChangeListener) is
	 *             not defined for the bean source
	 * @throws IllegalArgumentException
	 *             if the
	 *             removePropertyChangeListener(String,PropertyChangeListener)
	 *             is not defined for the bean source
	 */
	public MapBindingSource(Object beanSource, String getMethodName,
			String propertyKey) {
		super(beanSource);

		// check parameters
		if (getMethodName == null) {
			throw new IllegalArgumentException(getClass()
					+ ": The get method name can not be null");
		}
		if (propertyKey == null) {
			throw new IllegalArgumentException(getClass()
					+ ": The map binding property key can not be null");
		}

		// store source property
		this.propertyKey = propertyKey;

		readMethod = IntrospectionTools.retrieveMethod(beanSource,
				getMethodName, String.class);
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Object getInitialValue() {
		try {
			return readMethod.invoke(getBeanSource(), propertyKey);
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
		// TODO Auto-generated method stub
		return propertyKey;
	}

}
