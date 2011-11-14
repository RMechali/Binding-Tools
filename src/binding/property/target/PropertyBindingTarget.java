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

/**
 * Property binding target. It is notified at link creation, at source change
 * time and at source and target setter invocations. It must update the target
 * element.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public interface PropertyBindingTarget {

    /**
     * Update method for this binding target (invoked at link creation, at
     * source change time and at source and target setter invocations).
     * 
     * @note : You should never assert that the value cannot be null. Actually,
     *       at least when terminating the binding, a null value will be
     *       propagated
     * 
     * @param newValue new value of the source property
     */
    void updateTarget(Object newValue);
}
