package com.gov.Authmis.test;

import java.util.ArrayList;
import java.util.List;

public class CombinationSum {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // List to store the result
        List<List<Integer>> result = new ArrayList<>();
        // Helper list to store the current combination
        List<Integer> currentCombination = new ArrayList<>();

        // Sort the candidates to optimize the backtracking process
        java.util.Arrays.sort(candidates);

        // Start the backtracking process
        backtrack(candidates, target, 0, currentCombination, result);

        return result;
    }

    private void backtrack(int[] candidates, int target, int start, 
                           List<Integer> currentCombination, 
                           List<List<Integer>> result) {
        // Base case: if the target is zero, add the current combination to the result
        if (target == 0) {
            result.add(new ArrayList<>(currentCombination));
            return;
        }

        // Iterate through the candidates
        for (int i = start; i < candidates.length; i++) {
            // If the current candidate exceeds the target, break out of the loop (pruning)
            if (candidates[i] > target) {
                break;
            }

            // Add the candidate to the current combination
            currentCombination.add(candidates[i]);

            // Recur with the reduced target and the current index
            backtrack(candidates, target - candidates[i], i, currentCombination, result);

            // Backtrack: remove the last added element
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    public static void main(String[] args) {
        CombinationSum solution = new CombinationSum();

        // Input candidates and target
        int[] candidates = {2, 3, 6, 7};
        int target = 7;

        // Get the result
        List<List<Integer>> combinations = solution.combinationSum(candidates, target);

        // Output the result
        System.out.println(combinations); // Output: [[2, 2, 3], [7]]
    }

}
