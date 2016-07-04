/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        
        int size = list.size();

        // base case
        if (size < 2)
        	return list;

        // split list in half
        List<T> firstHalf = new LinkedList<T>(list.subList(0, size/2));
        List<T> secondHalf = new LinkedList<T>(list.subList(size/2, size));

        // sort the halves recursively
        firstHalf = mergeSort(firstHalf, comparator);
        secondHalf = mergeSort(secondHalf, comparator);

        // merge the halves together
        return merge(firstHalf, secondHalf, comparator);
	}

	/**
	 * Helper function for mergesort
	 */
	public List<T> merge(List<T> firstHalf, List<T> secondHalf, Comparator<T> comparator) {
		List<T> mergedList = new LinkedList<T>();

		int firstIndex = 0;
		int secondIndex = 0;

		while (firstIndex < firstHalf.size() || secondIndex < secondHalf.size()) {

			// add rest of first list
			if (secondIndex == secondHalf.size()) {
				mergedList.add(firstHalf.get(firstIndex));
				firstIndex++;
			}

			// add rest of second list
			else if (firstIndex == firstHalf.size()) {
				mergedList.add(secondHalf.get(secondIndex));
				secondIndex++;
			}

			// otherwise, choose between the first and second list
			else {
				int result = comparator.compare(firstHalf.get(firstIndex), secondHalf.get(secondIndex));
				if (result < 0) {
					mergedList.add(firstHalf.get(firstIndex));
					firstIndex++;
				}
				else {
					mergedList.add(secondHalf.get(secondIndex));
					secondIndex++;
				}
			}

		}
		return mergedList;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
		// use priority queue to implement heap
        PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
        heap.addAll(list);
        list.clear();
        while (!heap.isEmpty())
        	list.add(heap.poll());
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        // use priority queue to implement heap
        PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);

        for (T item : list) {

        	// if topK isn't full yet, add automatically to list
        	if (heap.size() < k)
        		heap.offer(item);

        	else {
        		// compare with smallest element in topK, and replace if it's smaller
        		int compare = comparator.compare(item, heap.peek());
        		if (compare > 0) {
        			heap.poll();
        			heap.offer(item);
        		}
        	}
        }

        // return top K
        List<T> resultList = new ArrayList<T>();
        while (!heap.isEmpty())
        	resultList.add(heap.poll());
        return resultList;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
