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

package main.list;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import binding.list.ListBindingLink;
import binding.list.source.BasicListBindingSource;
import binding.list.target.BasicListBindingTarget;

import com.jgoodies.common.collect.ArrayListModel;

/**
 * Test for list binding
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class TestListBinding {

	/**
	 * Test default source and target for binding
	 */
	@Test
	public void testDefaultBindings() {
		ArrayListModel<Integer> source = new ArrayListModel<Integer>();
		source.add(2);
		source.add(4);
		source.add(6);
		source.add(8);

		List<Integer> target = new ArrayList<Integer>();

		// branch source on target
		new ListBindingLink<Integer>(
				new BasicListBindingSource<Integer>(source),
				new BasicListBindingTarget<Integer>(target));

		// is initialization Ok?
		Assert.assertEquals(source, target);

		// test add
		source.add(16);
		Assert.assertEquals(source, target);

		// test add all
		List<Integer> temp = new ArrayList<Integer>();
		temp.add(32);
		temp.add(256);
		source.addAll(temp);
		Assert.assertEquals(source, target);

		// test insert all
		temp = new ArrayList<Integer>();
		temp.add(64);
		temp.add(128);
		source.addAll(source.size() - 1, temp);
		Assert.assertEquals(source, target);

		// test clear
		source.clear();
		Assert.assertEquals(source, target);

		// replenish the list
		source.add(2);
		source.add(32);
		Assert.assertEquals(source, target);

		// test insert
		source.add(1, 4);
		source.add(2, 16);
		source.add(2, 8);
		Assert.assertEquals(source, target);

		// test replace
		source.set(3, 555);
		Assert.assertEquals(source, target);

		// test remove
		source.remove(3);
		Assert.assertEquals(source, target);

		// test remove all
		temp = new ArrayList<Integer>();
		temp.add(4);
		temp.add(2);
		source.removeAll(temp);
		Assert.assertEquals(source, target);
	}

	/**
	 * Test changing source of a binding
	 */
	@Test
	public void testChangeSource() {
		ArrayListModel<Integer> source1 = new ArrayListModel<Integer>();
		source1.add(2);
		source1.add(4);
		source1.add(6);

		ArrayListModel<Integer> source2 = new ArrayListModel<Integer>();
		source2.add(1);
		source2.add(3);
		source2.add(5);

		List<Integer> target = new ArrayList<Integer>();

		// bind source 1 on target
		ListBindingLink<Integer> bindingLink = new ListBindingLink<Integer>(
				new BasicListBindingSource<Integer>(source1),
				new BasicListBindingTarget<Integer>(target));

		// verify initialization
		Assert.assertEquals(source1, target);
		// verify binding
		source1.add(8);
		Assert.assertEquals(source1, target);

		// bind source 2 on target
		bindingLink.setBindingSource(new BasicListBindingSource<Integer>(
				source2));

		// verify initialization
		Assert.assertNotSame(source1, target);
		Assert.assertEquals(source2, target);

		// verify binding
		source2.add(7);
		Assert.assertNotSame(source1, target);
		Assert.assertEquals(source2, target);

		// verify previous binding suppression
		source1.add(16);
		Assert.assertNotSame(source1, target);
		Assert.assertEquals(source2, target);
	}

	/**
	 * Test changing target of a binding
	 */
	@Test
	public void testChangeTarget() {
		ArrayListModel<Integer> source = new ArrayListModel<Integer>();
		source.add(2);
		source.add(4);
		source.add(6);
		source.add(8);

		List<Integer> target1 = new ArrayList<Integer>();
		List<Integer> target2 = new ArrayList<Integer>();

		// branch source on target 1
		ListBindingLink<Integer> bindingLink = new ListBindingLink<Integer>(
				new BasicListBindingSource<Integer>(source),
				new BasicListBindingTarget<Integer>(target1));

		// verify initialization
		Assert.assertEquals(source, target1);

		// verify link
		source.add(16);
		Assert.assertEquals(source, target1);

		// change target
		bindingLink.setBindingTarget(new BasicListBindingTarget<Integer>(
				target2));

		// verify initialization
		Assert.assertEquals(source, target2);

		// verify new link consistency
		source.add(32);
		Assert.assertNotSame(source, target1);
		Assert.assertEquals(source, target2);
	}

	/**
	 * Test to terminate binding
	 */
	@Test
	public void testTerminateBinding() {
		ArrayListModel<Integer> source = new ArrayListModel<Integer>();
		source.add(2);
		source.add(4);
		source.add(6);
		source.add(8);

		List<Integer> target = new ArrayList<Integer>();

		// branch source on target
		ListBindingLink<Integer> bindingLink = new ListBindingLink<Integer>(
				new BasicListBindingSource<Integer>(source),
				new BasicListBindingTarget<Integer>(target));

		// is initialization Ok?
		Assert.assertEquals(source, target);

		// test add
		source.add(16);
		Assert.assertEquals(source, target);

		// terminate binding
		bindingLink.terminateBinding();
		source.add(32);
		Assert.assertNotSame(source, target);
	}

}
