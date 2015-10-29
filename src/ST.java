import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ST {
	BufferedReader in;

	public List<String> readStrings(String filename) throws IOException {
		BufferedReader f_in = new BufferedReader(new FileReader(new File(filename)));
		List<String> strings = new ArrayList<String>();
		String line;
		while ((line = f_in.readLine()) != null) {
			strings.add(line + '\n');
		}

		f_in.close();

		return strings;
	}

	public ST(String[] args) throws Exception {
		in = new BufferedReader(new InputStreamReader(System.in));
		List<String> strings;
		Map<Integer, Integer> results;
		
		if(args.length!=3) {
			System.out.println("Usage: java -jar process.jar [adapter] [dataFile] [numMismatches]");
		}
		
		strings = readStrings(args[1]);

		if(Integer.parseInt(args[2])<=0) {
			results = SuffixTreeBatcher.findPrefixSuffixMatches(args[0], strings);
		} else {
			results = SuffixTreeBatcher.findPrefixSuffixMatchesWithTolerance(args[0], strings, Integer.parseInt(args[2]));
		}

		for (int i = 0; i < strings.size(); i++) {
			if (results.containsKey(i)) {
				String s = strings.get(i);
				/*
				System.out.print(s.substring(0, s.length()-1)+" - ");
				System.out.print(s.substring(0, results.get(i)) + " - ");
				System.out.println(s.substring(results.get(i), s.length()-1));
				*/
				System.out.println(s.substring(0, results.get(i)));
			}
		}
	}

	// TGGAATTCTCGGGTGCCAAGGAACTCCAGTCACACAGTGATCTCGTATGCCGTCTTCTGCTTG
	public static void main(String... args) throws Exception {
		new ST(args);
	}
}