package bst;

public class BinarySearchTree<E extends Comparable<? super E>> {
	BinaryNode<E> root;
	int size;

	/**
	 * Constructs an empty binary searchtree.
	 */
	public BinarySearchTree() {
		size = 0;
	}

	/**
	 * Inserts the specified element in the tree if no duplicate exists.
	 * 
	 * @param x
	 *            element to be inserted
	 * @return true if the the element was inserted
	 */
	public boolean add(E x) {
		if (root == null) {
			root = new BinaryNode<E>(x);
			size++;
			return true;
		}
		return compareObject(root, x);
	}

	private boolean compareObject(BinaryNode<E> node, E x) {
		if (node.element.compareTo(x) > 0) {
			if (node.left == null) {
				node.left = new BinaryNode<E>(x);
				size++;
				return true;
			} else {
				return compareObject(node.left, x);
			}
		} else if (node.element.compareTo(x) < 0) {
			if (node.right == null) {
				node.right = new BinaryNode<E>(x);
				size++;
				return true;
			} else {
				return compareObject(node.right, x);
			}
		}
		return false;
	}

	/**
	 * Computes the height of tree.
	 * 
	 * @return the height of the tree
	 */
	public int height() {
		return heightOfTree(root);
	}

	private int heightOfTree(BinaryNode<E> node) {
		if (node != null) {
			return 1 + Math.max(heightOfTree(node.left), heightOfTree(node.right));
		}
		return 0;
	}

	/**
	 * Returns the number of elements in this tree.
	 * 
	 * @return the number of elements in this tree
	 */
	public int size() {
		return size;
	}

	/**
	 * Print tree contents in inorder.
	 */
	public void printTree() {
		printTree(root);
	}

	private void printTree(BinaryNode<E> node) {
		if (node != null) {
			printTree(node.left);
			System.out.print(node.element);
			printTree(node.right);
		}

	}

	/**
	 * Builds a complete tree from the elements in the tree.
	 */
	public void rebuild() {
		E[] array = (E[]) new Comparable[size];
		root = buildTree(array, 0, toArray(root, array, 0) - 1);
	}

	/*
	 * Adds all elements from the tree rooted at n in inorder to the array a
	 * starting at a[index]. Returns the index of the last inserted element + 1 (the
	 * first empty position in a).
	 */
	private int toArray(BinaryNode<E> node, E[] array, int index) {
		if (node != null) {
			index = toArray(node.left, array, index);
			array[index] = node.element;
			index++;
			return toArray(node.right, array, index);
		}
		return index;
	}

	/*
	 * Builds a complete tree from the elements a[first]..a[last]. Elements in the
	 * array a are assumed to be in ascending order. Returns the root of tree.
	 */
	private BinaryNode<E> buildTree(E[] array, int first, int last) {
		if (last < first) {
			return null;
		}
		int mid = (first + last) / 2;
		BinaryNode<E> middleNode = new BinaryNode<E>(array[mid]);
		middleNode.left = buildTree(array, first, mid - 1);
		middleNode.right = buildTree(array, mid + 1, last);

		return middleNode;
	}

	static class BinaryNode<E> {
		E element;
		BinaryNode<E> left;
		BinaryNode<E> right;

		private BinaryNode(E element) {
			this.element = element;
		}
	}

	public static void main(String[] args) {
		BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
		BSTVisualizer bt = new BSTVisualizer("window", 600, 600);

		tree.add(9);
		tree.add(11);
		tree.add(3);
		tree.add(1);
		tree.add(13);
		tree.add(5);
		tree.add(7);

		tree.rebuild();
		bt.drawTree(tree);
	}

}
