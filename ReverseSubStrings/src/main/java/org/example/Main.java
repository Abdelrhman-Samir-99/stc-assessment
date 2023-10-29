package org.example;

public class Main {

	public static void main(String[] args) {
	}

	public static String solution(String word) {
		int openParenthesesIndex = -1;
		StringBuilder result = new StringBuilder(word);

		for(int i = 0; i < word.length(); ++i) {
			char currentChar = word.charAt(i);
			if(currentChar == '(') {
				openParenthesesIndex = i + 1;
			}
			else if(currentChar == ')') {
				reverseSubstring(result, openParenthesesIndex, i);
			}
		}

		return result.toString();
	}

	public static void reverseSubstring(StringBuilder sb, int startIndex, int endIndex) {
		int left = startIndex;
		int right = endIndex - 1;
		while (left < right) {
			char temp = sb.charAt(left);
			sb.setCharAt(left, sb.charAt(right));
			sb.setCharAt(right, temp);
			left++;
			right--;
		}
	}
}