import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Lists;

public class SuffixTreeBatcher {
	public static final int BATCH_SIZE = 1000;
	public static final int TOLERANCE_BATCH_SIZE = 1000;
	
	public static List<Integer> range(int n) {
		List<Integer> values = new ArrayList<Integer>();
		for(int i = 0; i < n; i++) {
			values.add(i);
		}
		return values;
	}
	
	public static Map<Integer, Integer> findPrefixSuffixMatches(String query, List<String> collection) throws Exception {
		Map<Integer, Integer> results = new TreeMap<Integer, Integer> ();
		Map<Integer, Integer> sub_results;
		GeneralizedSuffixTree tree;
		
		int offset = 0;
		
		for(List<String> sublist : Lists.partition(collection, BATCH_SIZE)) {
			tree = new GeneralizedSuffixTree(sublist);
			
			//tree.printTree();
			//tree.closeStream();
			
			sub_results = tree.findLongestPrefixSuffixMatches(query);
			
			for(Integer key : sub_results.keySet()) {
				results.put(key+offset, sub_results.get(key));
			}
		
			offset+=BATCH_SIZE;
		}
		
		return results;
	}
	
	public static Map<Integer, Integer> findPrefixSuffixMatchesWithTolerance(String query, List<String> collection, int tolerance) throws Exception {
		Map<Integer, Integer> results = new ConcurrentHashMap<Integer, Integer> ();
		
		List<List<String>> sublists = Lists.partition(collection, TOLERANCE_BATCH_SIZE);
		
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");	
		
		range(sublists.size()).parallelStream().forEach( i -> {
			Map<Integer, Integer> sub_results;
			GeneralizedSuffixTree tree;
			tree = new GeneralizedSuffixTree(sublists.get(i));
			tree.tagNodes();
			tree.printTree();
			tree.closeStream();
			
			sub_results = tree.findLongestPrefixSuffixMatchesWithTolerance(query, tolerance);
			
			for(Integer key : sub_results.keySet()) {
				results.put(key+i*TOLERANCE_BATCH_SIZE, sub_results.get(key));
			}
		});
		
		return results;
	}
	
	public static int identifyAdapter(List<String> collection) {
		Map<Integer, Integer> sub_results;
		GeneralizedSuffixTree tree;
		
		for(List<String> sublist : Lists.partition(collection, BATCH_SIZE)) {
			tree = new GeneralizedSuffixTree(sublist);
			
		}
		return 0;
	}
}
