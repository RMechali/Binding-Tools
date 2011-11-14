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

/**
 * A binding link interface (allowing default operations on a binding link)
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * 
 * @param <S> : binding source type
 * @param <T> : biding target type
 */
public interface IBindingLink<S, T> {

    /**
     * Get the link binding source
     * 
     * @return the link binding source
     */
    S getBindingSource();

    /**
     * Set the link binding source
     * 
     * @param newSource : new source (or null to terminate binding). If source is null, default value will 
     * be automatically provided to target. Otherwise current source wil be provided to target
     */
    void setBindingSource(S newSource);

    /**
     * Get the link binding target
     * 
     * @return the link binding target
     */
    T getBindingTarget();

    /**
     * Set the link binding target
     * 
     * @param newTarget
     *            : new target (or null). Previous target, if not null, will receive defaut dourec 
     * value. New target will atuomatically receive current source value
     */
    void setBindingTarget(T newTarget);

    /**
     * Terminates the binding (equivalent to call setBindingSource(null)). Pays attention to first 
     * send default value to current target then disconnect both target and source.
     */
    void terminateBinding();
}
