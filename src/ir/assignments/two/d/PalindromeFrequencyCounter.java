package ir.assignments.two.d;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PalindromeFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private PalindromeFrequencyCounter() {}

	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique palindrome found in the original list. The frequency of each palindrome
	 * is equal to the number of times that palindrome occurs in the original list.
	 * 
	 * Palindromes can span sequential words in the input list.
	 * 
	 * The returned list is ordered by decreasing size, with tied palindromes sorted
	 * by frequency and further tied palindromes sorted alphabetically. 
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["do", "geese", "see", "god", "abba", "bat", "tab"]
	 * 
	 * The output list of palindromes should be 
	 * ["do geese see god:1", "bat tab:1", "abba:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of palindrome frequencies, ordered by decreasing frequency.
	 */
	private static List<Frequency> computePalindromeFrequencies(ArrayList<String> words) {
		List<Frequency> frequencies = new ArrayList<Frequency>();
		ArrayList<String> multiwords = new ArrayList<String>();
		ArrayList<Integer> leftIndex = new ArrayList<Integer>();
		ArrayList<Integer> rightIndex = new ArrayList<Integer>();
		for(int i = 0; i < words.size() ; i++){
			for (int j = words.size(); j > i; j--){
				List<String> sub = words.subList(i, j);
				String str = makeSublistToString(sub);
				if(isPalindrome(str)){
					if (leftIndex.size() == 0 && rightIndex.size() == 0){
						leftIndex.add(i);
						rightIndex.add(j);
						multiwords.add(str);
					}else{
						for (int k = 0; k < leftIndex.size(); k++ ){
							if (leftIndex.get(k) > i  || rightIndex.get(k) < j){
								leftIndex.add(i);
								rightIndex.add(j);
								multiwords.add(str);
							}
						}
						continue;
					}
				}
			}
		}
		
		Set<String> set = new HashSet<String>();
		for (String word : multiwords){
			set.add(word);
		}
		
		for (String word:set){
			frequencies.add(new Frequency(word, Collections.frequency(multiwords, word)));
		}



		Collections.sort(frequencies, new Comparator<Frequency>(){

			@Override
			public int compare(Frequency f1, Frequency f2) {
				if (f1.getText().length() == f2.getText().length()){
					if (f1.getFrequency() == f2.getFrequency()){
						return f1.getText().compareTo(f2.getText());
					}else{
						return f2.getFrequency() - f1.getFrequency();
					}
				}
				return f2.getText().length() - f1.getText().length();
			}
		});
		return frequencies;
	}

	private static String makeSublistToString(List<String> stringList){
		String result = "";
		for (String str: stringList){
			result = result + str + " ";
		}
		return result;
	}

	private static boolean isPalindrome(String str){
		String filteredstr = str.replaceAll(" ", "");
		if(filteredstr.length() <=2)
			return false;
		for(int i = 0 ; i <= filteredstr.length()/2 ; i++){
			if(filteredstr.charAt(i) == filteredstr.charAt(filteredstr.length() - i - 1))
				continue;
			else 
				return false;
		}
		return true;
	}
	/**
	 * Runs the 2-gram counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
		public static void main(String[] args) {
			File file = new File(args[0]);
			ArrayList<String> words = Utilities.tokenizeFile(file);
			List<Frequency> frequencies = computePalindromeFrequencies(words);
			Utilities.printFrequencies(frequencies);
		}
//	public static void main(String[] args) {
//		File file = new File("analysis.txt");
//		ArrayList<String> words = Utilities.tokenizeFile(file);
//		List<Frequency> frequencies = computePalindromeFrequencies(words);
//		Utilities.printFrequencies(frequencies);
//	}
}
