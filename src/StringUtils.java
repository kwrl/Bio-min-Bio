import java.util.ArrayList;
import java.util.List;

public class StringUtils {
	public static void preprocessStrings(List<String> strings) {
		char terminator = 'T' + 1;
		for (int i = 0; i < strings.size(); i++) {
			strings.set(i, strings.get(i).replace('\n', terminator++));
		}
	}

	public static List<String> prefixes(String string) {
		List<String> prefixes = new ArrayList<String>();
		for (int i = 1; i <= string.length(); i++) {
			prefixes.add(string.substring(0, i));
		}
		return prefixes;
	}

	private static int min(int int1, int int2, int int3) {
		if (int1 < int2) {
			if (int1 < int3) {
				return int1;
			} else {
				return int3;
			}
		} else {
			if (int2 < int3) {
				return int2;
			} else {
				return int3;
			}
		}
	}

	private static void printMatrix(int[][] mat) {
		for (int[] row : mat) {
			for (int cell : row) {
				System.out.print(cell + "\t");
			}
			System.out.println();
		}
	}

	public static int editDistance(String str1, String str2) {
		int mat[][] = new int[str1.length() + 1][str2.length() + 1];

		for (int i = 0; i < mat.length; i++) {
			mat[i][0] = i;
		}
		for (int i = 0; i < mat[0].length; i++) {
			mat[0][i] = i;
		}

		for (int x = 1; x < mat.length; x++) {
			for (int y = 1; y < mat[0].length; y++) {
				if (str1.charAt(x - 1) == str2.charAt(y - 1)) {
					mat[x][y] = mat[x - 1][y - 1];
				} else {
					mat[x][y] = min(mat[x - 1][y] + 1, mat[x][y - 1] + 1, mat[x - 1][y - 1] + 1);
				}
			}
		}

		return mat[mat.length - 1][mat[0].length - 1];
	}

	public static int terminatorIndex(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (isTerminator(s.charAt(i))) {
				return i;
			}
		}
		return -1;
	}

	public static boolean isTerminator(char s) {
		return s > 'T';
	}

	public static int mismatchCount(String a, String b) {
		int mismatches = 0;

		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != b.charAt(i)) {
				mismatches++;
			}
		}
		return mismatches;
	}
}
