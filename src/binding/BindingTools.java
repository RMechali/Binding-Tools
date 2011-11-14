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
package binding;

import java.util.List;

import binding.list.ListBindingLink;
import binding.list.definition.MutableList;
import binding.list.source.BasicListBindingSource;
import binding.list.target.BasicListBindingTarget;
import binding.list.target.ListBindingTarget;
import binding.property.PropertyBindingLink;
import binding.property.source.PropertyBindingSource;
import binding.property.source.object.BeanBindingSource;
import binding.property.source.object.MapBindingSource;
import binding.property.target.BeanBindingTarget;
import binding.property.target.PropertyBindingTarget;

/**
 * Static API to create bindings faster.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public final class BindingTools {

    /**
     * Constructor
     */
    private BindingTools() {
        // forbids external instance
    }

    /**
     * Creates a simple bean binding (from a bean property to a bean property)
     * 
     * see {@link BeanBindingSource}, {@link BeanBindingTarget} and
     * {@link PropertyBindingLink} for thrown exceptions
     * 
     * @param source : source bean
     * @param sourceProperty : source property
     * @param target : target bean
     * @param targetProperty : target property
     * @return - the binding link
     */
    public static PropertyBindingLink createBinding(Object source, String sourceProperty, Object target,
                                                    String targetProperty) {
        return new PropertyBindingLink(new BeanBindingSource(source, sourceProperty),
                                       new BeanBindingTarget(target, targetProperty));
    }

    /**
     * Creates a map property binding into a bean setter
     * 
     * see {@link MapBindingSource}, {@link BeanBindingTarget} and
     * {@link PropertyBindingLink} for thrown exceptions
     * 
     * @param source : source bean
     * @param getMethodName : method to get the map value (see {@link MapBindingSource}
     *            constructor)
     * @param mapPrpertyKey : property key to listen to / to use in map getter (see
     *            {@link MapBindingSource} constructor)
     * @param target : target bean
     * @param targetProperty : target property
     * @return - the binding link
     */
    public static PropertyBindingLink createBinding(Object source, String getMethodName, String mapPrpertyKey,
                                                    Object target, String targetProperty) {
        return new PropertyBindingLink(new MapBindingSource(source, getMethodName, mapPrpertyKey),
                                       new BeanBindingTarget(target, targetProperty));
    }

    /**
     * Creates a binding link using a custom source and a bean setter as
     * target
     * 
     * see {@link BeanBindingTarget} and {@link PropertyBindingLink} for thrown
     * exceptions
     * 
     * @param source : Binding link source
     * @param target : target bean
     * @param targetProperty : target property name
     * @return - The link created
     */
    public static PropertyBindingLink createBinding(PropertyBindingSource source, Object target, String targetProperty) {
        return new PropertyBindingLink(source, new BeanBindingTarget(target, targetProperty));
    }

    /**
     * Creates a binding link using a bean source and a custom bean target
     * 
     * see {@link BeanBindingTarget} and {@link PropertyBindingLink} for thrown
     * exceptions
     * 
     * @param source : source object
     * @param property : source property
     * @param target : target
     * @return - The link created
     */
    public static PropertyBindingLink createBinding(Object source, String property, PropertyBindingTarget target) {
        return new PropertyBindingLink(new BeanBindingSource(source, property), target);
    }

    /**
     * Creates a simple direct binding from one list to another
     * 
     * see {@link BasicListBindingSource}, {@link BasicListBindingTarget} and
     * {@link ListBindingLink} for thrown exceptions
     * 
     * @param <T> : lists element type
     * @param source : binding source list
     * @param target : binding target list
     * @return - the binding link
     */
    public static <T> ListBindingLink<T> createBinding(MutableList<T> source, List<T> target) {
        return new ListBindingLink<T>(new BasicListBindingSource<T>(source), new BasicListBindingTarget<T>(target));
    }

    /**
     * Creates a binding link from a list of type T to a binding target (for
     * quick implementation, take a look at AbstractListBindingTarget class)
     * 
     * see {@link BasicListBindingSource} and {@link ListBindingLink} for thrown
     * exceptions
     * 
     * @param <T> : source list element type
     * @param source : binding source list
     * @param bindingTarget : binding target (null available)
     * @return - the binding link
     */
    public static <T> ListBindingLink<T> createBinding(MutableList<T> source, ListBindingTarget<T> bindingTarget) {
        return new ListBindingLink<T>(new BasicListBindingSource<T>(source), bindingTarget);
    }
}
