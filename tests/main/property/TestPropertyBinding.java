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

package main.property;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComponent;

import junit.framework.Assert;

import org.junit.Test;

import binding.property.PropertyBindingLink;
import binding.property.source.object.BeanBindingSource;
import binding.property.source.object.MapBindingSource;
import binding.property.target.BeanBindingTarget;
import binding.property.target.MapBindingTarget;
import binding.property.target.PropertyBindingTarget;

/**
 * Test class for property binding. Note that the introspections errors are not
 * tested : there are two many possible causes.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class TestPropertyBinding {

	/**
	 * Test binding link using bean source
	 */
	@Test
	public void testBeanSource() {
		// keep a final table to modify its content (dirty but quick)
		final int[] notifiedCount = { 0 };
		// test a property binding change using a JComponent
		JComponent component = new JButton();
		new PropertyBindingLink(new BeanBindingSource(component, "background"),
				new PropertyBindingTarget() {

					@Override
					public void updateTarget(Object newValue) {
						// update the target count
						notifiedCount[0]++;
					}
				});
		// was the initialization event propagated?
		Assert.assertEquals(1, notifiedCount[0]);

		// set a new value that is different from initial one
		component.setBackground(Color.orange);
		// as the update event be propagated?
		Assert.assertEquals(2, notifiedCount[0]);
	}

	/**
	 * Test binding link using map source
	 */
	@Test
	public void testMapSource() {
		// keep a final table to modify its content (dirty but quick)
		final int[] notifiedCount = { 0 };
		// test a map property binding change using a custom class (not abstract
		// action because it does not define standard addPropertyChangeListener
		// method...)

		CustomMappable customMappable = new CustomMappable();

		new PropertyBindingLink(new MapBindingSource(customMappable,
				"getValue", "propX"), new PropertyBindingTarget() {

			@Override
			public void updateTarget(Object newValue) {
				// update the target count
				notifiedCount[0]++;
			}
		});
		// was the initialization event propagated?
		Assert.assertEquals(1, notifiedCount[0]);
		// set a new value that is different from initial one
		customMappable.putValue("propX", 5);
		// as the update event be propagated?
		Assert.assertEquals(2, notifiedCount[0]);
	}

	/**
	 * Test binding link using bean target
	 */
	@Test
	public void testBeanTarget() {
		// create 2 JComponent, and make sure updating one updates the second
		// too (it requires the bean source test to work)
		JComponent source = new JButton();
		source.setBackground(Color.orange);
		JComponent target = new JButton();

		new PropertyBindingLink(new BeanBindingSource(source, "background"),
				new BeanBindingTarget(target, "background"));

		// has initialization bean performed?
		Assert.assertEquals(Color.orange, target.getBackground());

		// update once and verify background colors are still correlated
		source.setBackground(Color.red);
		Assert.assertEquals(Color.red, target.getBackground());
	}

	/**
	 * Test binding link using map target
	 */
	@Test
	public void testMapTarget() {
		// create two map bindable components and make sure updating one updates
		// the second too (it requires the map source test to work)
		CustomMappable source = new CustomMappable();
		source.putValue("propY", 10);
		CustomMappable target = new CustomMappable();

		new PropertyBindingLink(new MapBindingSource(source, "getValue",
				"propY"), new MapBindingTarget(target, "putValue", "propY"));

		// has initialization bean performed?
		Assert.assertEquals(10, target.getValue("propY"));

		// update once and verify "propY" are still correlated
		source.putValue("propY", 15);
		Assert.assertEquals(15, target.getValue("propY"));

	}

	/**
	 * Test terminate binding
	 */
	@Test
	public void testTerminateBinding() {
		// create 2 JComponent, and make sure updating one updates the second
		// too (it requires the bean target test to work). Then terminate biding
		// should decorrelate those properties
		JComponent source = new JButton();
		source.setBackground(Color.orange);
		JComponent target = new JButton();

		PropertyBindingLink bindingLink = new PropertyBindingLink(
				new BeanBindingSource(source, "background"),
				new BeanBindingTarget(target, "background"));

		// has initialization bean performed?
		Assert.assertEquals(Color.orange, target.getBackground());

		// update once and verify background colors are correlated
		source.setBackground(Color.red);
		Assert.assertEquals(Color.red, target.getBackground());

		// terminate binding and verify they are no longer correlated
		bindingLink.terminateBinding();
		source.setBackground(Color.blue);
		Assert.assertEquals(Color.red, target.getBackground());
	}

	/**
	 * Test change binding source
	 */
	@Test
	public void testChangeSource() {
		// create 3 JComponent, and make sure changing source makes the target
		// update and breaks the initial link (requires the bean target test to
		// work)
		JComponent source1 = new JButton();
		source1.setBackground(Color.orange);

		JComponent source2 = new JButton();
		source2.setBackground(Color.blue);

		JComponent target = new JButton();

		PropertyBindingLink bindingLink = new PropertyBindingLink(
				new BeanBindingSource(source1, "background"),
				new BeanBindingTarget(target, "background"));

		// initialized to the first source value?
		Assert.assertEquals(Color.orange, target.getBackground());

		// now change the property source
		bindingLink.setBindingSource(new BeanBindingSource(source2,
				"background"));
		// initialized to the second source value?
		Assert.assertEquals(Color.blue, target.getBackground());

		// has the previous link bean broken?
		source1.setBackground(Color.yellow);
		Assert.assertNotSame(Color.yellow, target.getBackground());

		// Is the new link working?
		source2.setBackground(Color.gray);
		Assert.assertEquals(Color.gray, target.getBackground());
	}

	/**
	 * Test change binding target
	 */
	@Test
	public void testChangeTarget() {
		// create 3 JComponent, and make sure changing the target will no longer
		// update the old target and update the new one instead
		// (it requires the bean target test to work).
		JComponent source = new JButton();
		source.setBackground(Color.orange);
		JComponent target1 = new JButton();
		target1.setBackground(Color.yellow);
		JComponent target2 = new JButton();
		target2.setBackground(Color.magenta);

		// begin binding the first target
		PropertyBindingLink propertyBindingLink = new PropertyBindingLink(
				new BeanBindingSource(source, "background"),
				new BeanBindingTarget(target1, "background"));

		// has the first target been updated, is the second target stil
		// unrelated?
		Assert.assertEquals(Color.orange, target1.getBackground());
		Assert.assertNotSame(Color.orange, target2.getBackground());

		// update once and verify the same assertion
		source.setBackground(Color.lightGray);
		Assert.assertEquals(Color.lightGray, target1.getBackground());
		Assert.assertNotSame(Color.lightGray, target2.getBackground());

		// now change the target
		propertyBindingLink.setBindingTarget(new BeanBindingTarget(target2,
				"background"));

		// verify that it has been updated
		Assert.assertEquals(Color.lightGray, target2.getBackground());

		// now update once again and verify the first target is not longer
		// correlated
		source.setBackground(Color.black);
		Assert.assertNotSame(Color.black, target1.getBackground());
		Assert.assertEquals(Color.black, target2.getBackground());

	}

}
