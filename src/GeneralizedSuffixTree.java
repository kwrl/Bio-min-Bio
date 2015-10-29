import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import javafx.util.Pair;

class GeneralizedSuffixTree extends SuffixTree {
	/**
	 * 
	 */
	private List<String> strings;
	private Map<Integer, List<Integer>> leafs = new HashMap<Integer, List<Integer>>();

	public GeneralizedSuffixTree(List<String> strings) {
		StringUtils.preprocessStrings(strings);
		this.strings = strings;
		String completeString = String.join("", this.strings);
		init(completeString.length());

		for (int i = 0; i < completeString.length(); i++) {
			try {
				this.addChar(completeString.charAt(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> getStrings() {
		return this.strings;
	}

	public Map<Integer, Integer> findLongestPrefixSuffixMatches(String query) {
		List<Integer> suffixIndexes;
		Map<Integer, Integer> longestMatches = new TreeMap<Integer, Integer>();
		int lineLength = this.strings.get(0).length();

		for (String prefix : StringUtils.prefixes(query)) {
			suffixIndexes = this.findSuffixes(prefix);
			if (suffixIndexes == null) {
				continue;
			}
			for (Integer globalIndex : suffixIndexes) {
				longestMatches.put(globalIndex / lineLength, globalIndex % lineLength);
			}
		}

		return longestMatches;
	}

	public Map<Integer, Integer> findLongestPrefixSuffixMatchesWithTolerance(String query, int tolerance) {
		List<Integer> suffixIndexes;
		Map<Integer, Integer> longestMatches = new TreeMap<Integer, Integer>();
		int lineLength = this.strings.get(0).length();

		for (String prefix : StringUtils.prefixes(query)) {
			suffixIndexes = this.findSuffixesWithTolerance(prefix, tolerance);
			if (suffixIndexes == null) {
				continue;
			}
			for (Integer globalIndex : suffixIndexes) {
				longestMatches.put(globalIndex / lineLength, globalIndex % lineLength);
			}
		}
		return longestMatches;
	}

	List<Integer> findSuffixes(String query) {
		List<Integer> suffixes = new LinkedList<Integer>();
		String edgeString, query_substring;
		int current_char, current_node, next_node;

		current_node = root;
		current_char = 0;

		while (true) {
			if (current_char >= query.length()) {
				for (Character c : nodes[current_node].next.keySet()) {
					if (StringUtils.isTerminator(c)) {
						suffixes.add(nodes[nodes[current_node].next.get(c)].start - query.length());
					}
				}
				return suffixes;
			}
			if (!nodes[current_node].next.containsKey(query.charAt(current_char))) {
				return null;
			}

			next_node = nodes[current_node].next.get(query.charAt(current_char));
			edgeString = edgeString(next_node);
			query_substring = query.substring(current_char);

			if (query_substring.startsWith(edgeString)) {
				current_char += edgeString.length();
				current_node = next_node;
			} else if (edgeString.startsWith(query_substring)) {
				if (StringUtils.isTerminator(edgeString.charAt(query_substring.length()))) {
					suffixes.add(nodes[next_node].start + query_substring.length() - query.length());
				}
				return suffixes;
			} else {
				return null;
			}
		}
	}

	List<Integer> findSuffixesWithTolerance(String query, int tolerance) {
		List<Integer> suffixIndexes = new LinkedList<Integer>();
		if (!leafs.containsKey(query.length())) {
			return new ArrayList<Integer>(0);
		}

		for (Integer leaf : leafs.get(query.length())) {
			if (isValidSuffixWithTolerance(leaf, query, tolerance)) {
				suffixIndexes.add(nodes[leaf].start - query.length() + StringUtils.terminatorIndex(edgeString(leaf)));
			}
		}

		return suffixIndexes;
	}

	private boolean isValidSuffixWithTolerance(int leaf, String query, int tolerance) {
		String edgeString, suffix;
		int currentNode = leaf;

		suffix = "";

		while (currentNode != root) {
			edgeString = edgeString(currentNode);
			if (currentNode == leaf) {
				if (StringUtils.terminatorIndex(edgeString) == -1) {
					System.out.println();
				}
				edgeString = edgeString.substring(0, StringUtils.terminatorIndex(edgeString));
				if (edgeString.equals("")) {
					currentNode = nodes[currentNode].parent;
					continue;
				}
			}
			suffix = edgeString + suffix;
			currentNode = nodes[currentNode].parent;

			if (StringUtils.mismatchCount(query.substring(query.length() - suffix.length()), suffix) > tolerance) {
				return false;
			}
		}

		return StringUtils.mismatchCount(suffix, query) <= tolerance;
	}

	public void tagNodes() {
		this.tagNodes(root, 0, -1);
	}

	public void tagNodes(int currentNode, int depth, int parent) {
		String edgeString;
		int depthOffset;
		nodes[currentNode].depth = depth;
		nodes[currentNode].parent = parent;

		for (Integer i : nodes[currentNode].next.values()) {
			edgeString = edgeString(i);
			depthOffset = edgeString.length();

			for (int j = 0; j < edgeString.length(); j++) {
				if (StringUtils.isTerminator(edgeString.charAt(j))) {
					depthOffset = j;
					break;
				}
			}

			tagNodes(i, depth + depthOffset, currentNode);
		}

		if (nodes[currentNode].next.isEmpty()) {
			if (!leafs.containsKey(depth)) {
				leafs.put(depth, new ArrayList<Integer>());
			}
			leafs.get(depth).add(currentNode);
		}
	}

	public void findLongSuffixes(int minLength) {

	}

	private void findLongSuffixes(int minLength, int currentNode, String parentString,
			Map<String, Integer> occurrences) {
		
		String edgeString = edgeString(currentNode);
		String completeString;
		int terminatorIndex;
		
		if(nodes[currentNode].next.isEmpty()) {
			terminatorIndex = StringUtils.terminatorIndex(edgeString);
			//completeString = parentString + edgeString(0, )
			occurrences.containsKey(key)
		}
	}

	public void closeStream() {
		out.close();
	}

}