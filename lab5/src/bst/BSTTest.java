package bst;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bst.BinarySearchTree;

public class BSTTest {
	private BinarySearchTree<Integer> tree;

	@Before
	public void setUp() throws Exception {
		tree = new BinarySearchTree<Integer>();
	}

	@After
	public void tearDown() throws Exception {
		tree = null;
	}

	@Test
	public final void testSize() {
		tree.add(1);
		tree.add(6);
		tree.add(98);
		tree.add(23);
		tree.add(8);
		assertTrue(tree.size() == 5);
	}

	@Test
	public final void testSizeEmptyTree() {
		assertTrue(tree.size() == 0);
	}

	@Test
	public final void testAdd() {
		tree.add(1);
		tree.add(6);
		tree.add(98);
		tree.add(23);
		tree.add(8);
		assertFalse(tree.add(8));
		assertTrue(tree.size() > 0);
	}

	@Test
	public final void testAddDuplicate() {
		tree.add(8);
		tree.add(8);
		assertTrue(tree.size() == 1);
	}

	@Test
	public final void testHeightEmptyTree() {
		assertTrue(tree.height() == 0);
	}

	@Test
	public final void testHeight() {
		tree.add(1);
		tree.add(6);
		tree.add(98);
		tree.add(23);
		tree.add(8);
		assertTrue(tree.height() > 0);
	}

}
