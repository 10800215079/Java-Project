package com.gov.Authmis.test;

import java.util.Arrays;

//System.arraycopy(Object src, int srcPos, Object dest, int destPos, int length);
/*
 src: The source array from which elements are to be copied.
 srcPos: The starting position in the source array.
 dest: The destination array to which elements are to be copied.
 destPos: The starting position in the destination array.
 length: The number of elements to copy.
 */
//Rotate an array to the right by k steps

public class RotateArray {
	
	public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6};
        int k = 2; // Number of positions to rotate
        
        System.out.println("Original Array: " + Arrays.toString(nums));
        
        int[] rotated = rotateArray(nums, k);
        System.out.println("Rotated Array: " + Arrays.toString(rotated));
    }

    public static int[] rotateArray(int[] nums, int k) {
        int n = nums.length;
        k = k % n; // Handle cases where k > n
        int[] result = new int[n];
        
        // Copy the last k elements to the beginning
        System.arraycopy(nums, n - k, result, 0, k);
        
        // Copy the first n-k elements to the remaining positions
        System.arraycopy(nums, 0, result, k, n - k);
        
        return result;
    }

}
