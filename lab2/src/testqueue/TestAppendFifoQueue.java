package testqueue;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import queue_singlelinkedlist.FifoQueue;

public class TestAppendFifoQueue {
	private FifoQueue<Integer> q1;
	private FifoQueue<Integer> q2;

	@Before
	public void setUp() throws Exception {
		q1 = new FifoQueue<Integer>();
		q2 = new FifoQueue<Integer>();
	}

	@After
	public void tearDown() throws Exception {
		q1 = null;
		q2 = null;
	}

	@Test
	public void TestAppendEmptyEmpty() {
		q1.append(q2);
		assertTrue(q1.isEmpty());
	}

	@Test
	public void TestAppendTwoNonEmpty() {
		q1.offer(1);
		q1.offer(2);
		q1.offer(3);
		q1.offer(4);
		q2.offer(5);
		q2.offer(6);
		q2.offer(7);
		q1.append(q2);
		assertTrue(q1.size() == 7);
		// System.out.println(q1.toString());
		// assertTrue(q2.isEmpty());
		for (int i = 1; i <= 7; i++) {
			int k = q1.poll();
			// System.out.println(k);
			assertEquals("poll returns incorrect element", i, k);
		}
		assertTrue("Queue not empty", q1.isEmpty());

	}

	@Test
	public void TestAppendNonEmptyEmpty() {
		q1.offer(1);
		q1.offer(2);
		q1.offer(3);
		q1.offer(4);
		q1.append(q2);
		assertTrue(q1.size() == 4);
		assertTrue(q2.isEmpty());
	}

	@Test
	public void TestAppendEmptyNonEmpty() {
		q2.offer(5);
		q2.offer(6);
		q2.offer(7);
		q1.append(q2);
		assertTrue(q1.size() == 3);
		assertTrue(q2.isEmpty());
	}

	@Test
	public void TestAppendSelf() {
		try {
			q1.append(q1);
			fail("Should raise NoSuchElementException");
		} catch (IllegalArgumentException e) {
			// successful test
		}
	}

}
