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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import binding.tools.IntrospectionTools;

/**
 * Bean setter binding target, that reflects changes directly on a setter
 * method. Note that setter must be public. Also take in account that primary type binding 
 * termination will terminate with a null pointer exception (Java can not convert a null value into
 * primary type default value) so try avoinding binding to a setter that has a primary type.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class BeanBindingTarget implements PropertyBindingTarget {

    /** Target bean **/
    private final Object beanTarget;

    /** Setter method **/
    private final Method writeMethod;

    /**
     * 
     * Constructor
     * 
     * @param beanTarget : bean target
     * @param propertyName : property name (to find the linked setter)
     * 
     * @throws IllegalArgumentException if the bean target is null
     * @throws IllegalArgumentException if the bean property name is null
     * @throws IllegalArgumentException if the set method is not defined for that property in the
     *             bean source
     * @warning : the setter type cannot be checked so be carefull while using
     *          this type of target
     */
    public BeanBindingTarget(Object beanTarget, String propertyName) {
        // check parameters
        if (beanTarget == null) {
            throw new IllegalArgumentException(getClass()
                    + ": The bean binding target can not be null");
        }
        if (propertyName == null) {
            throw new IllegalArgumentException(getClass()
                    + ": The bean binding target property name can not be null");
        }

        // store the bean target
        this.beanTarget = beanTarget;

        // retrieve the write method
        PropertyDescriptor property = IntrospectionTools.getProperty(
                beanTarget, propertyName);
        writeMethod = property.getWriteMethod();
        if (writeMethod == null) {
            throw new IllegalArgumentException("Write method not provided");
        }
    }

    /**
     * {@inherit}
     * 
     * @note : a class cast exception could be propagated if you branched
     *       incompatible properties together. Like any other type of exception
     *       that may be propagated by the setter, it is automatically
     *       encapsulated into a runtime exception
     */
    @Override
    public void updateTarget(Object newValue) {
        try {
            writeMethod.invoke(beanTarget, newValue);
        }
        catch (Exception e) {
            // convert the error into a runtime error to not force the user
            // catching it
            throw new RuntimeException(e);
        }
    }
}
