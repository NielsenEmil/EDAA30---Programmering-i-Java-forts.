package map;

public class SimpleHashMap<K, V> implements Map<K, V> {

	Entry<K, V>[] table;
	int capacity, size;

	/**
	 * Constructs an empty hashmap with the default initial capacity (16) and the
	 * default load factor (0.75).
	 */
	public SimpleHashMap() {
		capacity = 16;
		table = (Entry<K, V>[]) new Entry[capacity];
	}

	/**
	 * Constructs an empty hashmap with the specified initial capacity and the
	 * default load factor (0.75).
	 */
	public SimpleHashMap(int capacity) {
		this.capacity = capacity;
		table = (Entry<K, V>[]) new Entry[capacity];
		size = 0;
	}

	public String show() {
		String str = "";
		for (int i = 0; i < table.length; i++) {
			str += i + ". ";
			Entry<K, V> temp = table[i];
			while (temp != null) {
				str += temp.toString() + " ";
				temp = temp.next;
			}
			str += "\n";
		}
		return str;
	}

	private int index(K key) {
		return Math.abs(key.hashCode() % capacity);
	}

	private Entry<K, V> find(int index, K key) {
		Entry<K, V> e = table[index];
		while (e != null) {
			if (e.getKey().equals(key)) {
				return e;
			}
			e = e.next;
		}
		return null;
	}

	public V get(Object object) {
		Entry<K, V> e = find(index((K) object), (K) object);
		if (e != null) {
			return e.getValue();
		}
		return null;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public V put(K key, V value) {
		Entry<K, V> e = find(index(key), key);

		if (e != null) {
			V oldValue = e.getValue();
			e.setValue(value);

			return oldValue;
		}

		e = new Entry<K, V>(key, value);

		if (table[index(key)] != null) {
			e.next = table[index(key)];
		}

		table[index(key)] = e;
		size++;
		if ((double) size / capacity > 0.75) {
			rehash();
		}

		return null;
	}

	private void rehash() {
		capacity *= 2;
		Entry<K, V>[] prevTable = table;
		table = (Entry<K, V>[]) new Entry[capacity];
		size = 0;

		for (Entry<K, V> e : prevTable) {
			while (e != null) {
				put(e.getKey(), e.getValue());
				e = e.next;
			}
		}
	}

	public V remove(Object key) {
		Entry<K, V> e = table[index((K) key)];
		if (e != null) {
			if (e.getKey().equals(key)) {
				table[index((K) key)] = e.next;
				size--;
				return e.getValue();
			} else {
				while (e.next != null) {
					if (e.next.getKey().equals(key)) {
						V v = e.next.getValue();
						e.next = e.next.next;
						size--;
						return v;
					}

					e = e.next;
				}
			}
		}
		return null;
	}

	public int size() {
		return size;
	}

	public static class Entry<K, V> implements Map.Entry<K, V> {
		private K key;
		private V value;
		private Entry<K, V> next;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public V setValue(V value) {
			return this.value = value;
		}

		public String toString() {
			return this.key + "=" + this.value;
		}

	}

	public static void main(String[] args) {
		SimpleHashMap<String, Integer> shm = new SimpleHashMap<String, Integer>();
		shm.put("Adam", 123);
		shm.put("Bertil", 123);
		shm.put("Christoffer", 123);
		shm.put("David", 123);
		shm.put("Erik", 321);
		shm.put("Filip", 1);
		shm.put("Gustav", -10);

		System.out.print(shm.show());
	}

}
