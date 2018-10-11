
//import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import textproc.TextProcessor;

public class GeneralWordCounter implements TextProcessor {

	Map<String, Integer> counter = new TreeMap<String, Integer>();
	Set<String> undantagsord = new HashSet<String>();
	boolean stop = false;

	public GeneralWordCounter(Scanner s) {
		while (s.hasNext()) {
			undantagsord.add(s.next());
		}
	}

	public void process(String w) {
		stop = false;
		for (String a : undantagsord) {

			if (a.equals(w)) {
				stop = true;
			}

		}
		if (stop != true) {
			if (counter.containsKey(w)) {
				int i = counter.get(w);
				i++;
				counter.put(w, i);
			} else {
				counter.put(w, 1);
			}
		}

	}

	public void report() {
		for (String key : counter.keySet()) {
			if (counter.get(key) > 200) {
				System.out.println(key + ": " + counter.get(key));
			}
		}

	}

	public Set<Map.Entry<String, Integer>> getWords() {
		return counter.entrySet();
	}

}