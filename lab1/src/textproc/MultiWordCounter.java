package textproc;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MultiWordCounter implements TextProcessor {

	private String[] landskap = new String[25];
	private Map<String, Integer> counter = new HashMap<String, Integer>();

	public MultiWordCounter(String[] landskap) {
		for (int i = 0; i < 24; i++) {
			this.landskap[i] = landskap[i];
		}

		for (String a : landskap) {
			counter.put(a, 0);
		}
	}

	public void process(String w) {
		for (String a : landskap) {
			if (w.equals(a)) {
				int i = counter.get(a);
				i++;
				counter.put(a, i);
			}
		}
	}

	public void report() {
		for (String key : counter.keySet()) {
			System.out.println(key + ": " + counter.get(key));
		}
	}

	@Override
	public Set<Entry<String, Integer>> getWords() {
		// TODO Auto-generated method stub
		return null;
	}

}
