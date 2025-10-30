/******************************************************************
 *
 *   Julia Harper / Assignment 5 -272 Data Structures
 *
 *   This java file contains the problem solutions of isSubSet, findKthLargest,
 *   and sort2Arrays methods. You should utilize the Java Collection Framework for
 *   these methods.
 *
 ********************************************************************/

import java.util.*; 



public class ProblemSolutions { 

    /**
     * Method: isSubset()
     *
     * Given two arrays of integers, A and B, return whether
     * array B is a subset of array A. Example:
     *      Input: [1,50,55,80,90], [55,90]
     *      Output: true
     *      Input: [1,50,55,80,90], [55,90, 99]
     *      Output: false
     *
     * The solution time complexity must NOT be worse than O(n).
     * For the solution, use a Hash Table.
     *
     * @param list1 - Input array A
     * @param list2 - input array B
     * @return      - returns boolean value B is a subset of A.
     */
    //checks if array B is a subset of array A
    public boolean isSubset(int[] list1, int[] list2) { //method start
        HashSet<Integer> setA = new HashSet<>(); //store all elements of A
        for (int a : list1) setA.add(a); //populate hashset
        for (int b : list2) { //loop through B
            if (!setA.contains(b)) return false; //if B element not in A, return false
        }
        return true; //if all B elements found, return true
    } //end isSubset


    /**
     * Method: findKthLargest
     *
     * Given an Array A and integer K, return the k-th maximum element in the array.
     * Example:
     *      Input: [1,7,3,10,34,5,8], 4
     *      Output: 7
     *
     * @param array - Array of integers
     * @param k     - the kth maximum element
     * @return      - the value in the array which is the kth maximum value
     */
    //finds the kth largest number in array using a min-heap
    public int findKthLargest(int[] array, int k) { //method start
        PriorityQueue<Integer> pq = new PriorityQueue<>(); //create min-heap
        for (int n : array) { //loop over array
            pq.add(n); //add element to heap
            if (pq.size() > k) pq.poll(); //keep only k largest
        }
        return pq.peek(); //top of heap is kth largest
    } //end findKthLargest


    /**
     * Method: sort2Arrays
     *
     * Given two arrays A and B with n and m integers respectively, return
     * a single array of all the elements in A and B in sorted order. Example:
     *      Input: [4,1,5], [3,2]
     *      Output: 1 2 3 4 5
     *
     * @param array1    - Input array 1
     * @param array2    - Input array 2
     * @return          - Sorted array with all elements in A and B.
     */
    //merges and sorts two arrays using a priority queue
    public int[] sort2Arrays(int[] array1, int[] array2) { //method start
        PriorityQueue<Integer> pq = new PriorityQueue<>(); //create min-heap
        for (int n : array1) pq.add(n); //add array1 elements
        for (int n : array2) pq.add(n); //add array2 elements

        int[] result = new int[pq.size()]; //prepare result array
        int i = 0; //index counter
        while (!pq.isEmpty()) { //extract all elements in sorted order
            result[i++] = pq.poll(); //poll min each time
        }
        return result; //return sorted array
    } //end sort2Arrays
} 
