import java.util.Arrays;

/**
 * A utility class containing methods for sorting arrays, written for CSCI 261.
 * Implementations based on Koffman and Wolfgang.
 *
 * @author Joel Ross
 */
@SuppressWarnings({"unchecked","rawtypes"}) //So we can easily work with arrays of Comparables
public class Sorting {
	
	/**
	 * Uses bubble sort to sort the provided list (in place)
	 * @param list The list to sort
	 */
	public static void bubbleSort(Comparable[] list)
	{
		boolean sorted;
		do
		{
			sorted = true;
			for(int i=0; i<list.length-1; i++)
			{
				//System.out.println("Bubble Sort comparison");
				if(list[i].compareTo(list[i+1]) > 0)
				{
					Comparable tmp = list[i];
					list[i] = list[i+1];
					list[i+1] = tmp;
					sorted = false;
				}
			}
		}
		while(!sorted);
	}


	/**
	 * Uses selection sort to sort the provided list (in place)
	 * @param list The list to sort
	 */
	public static void selectionSort(Comparable[] list)
	{
		for(int i=0; i<list.length-1; i++)
		{
			int selected = i;
			for(int j=i+1; j<list.length; j++)
			{
				//System.out.println("Selection Sort comparison");
				if(list[j].compareTo(list[selected]) < 0)
				{
					selected = j;
				}
			}
			Comparable tmp = list[i];
			list[i] = list[selected];
			list[selected] = tmp;
		}
	}


	/**
	 * Uses insertion sort to sort the provided list (in place)
	 * @param list The list to sort
	 */
	public static void insertionSort(Comparable[] list)
	{
		for(int i=1; i<list.length; i++)
		{
			Comparable inserted = list[i];
			int hole = i;
			while(hole > 0 && inserted.compareTo(list[hole-1]) < 0)
			{
			//System.out.println("Insertion Sort comparison");
				list[hole] = list[hole-1];
				hole--;
			}
			list[hole] = inserted;
		}
	}

	/**
	 * Uses Shell sort to sort the provided list (in place)
	 * @param list The list to sort
	 */
	public static void shellSort(Comparable[] list)
	{
		//int[] gaps = {701, 301, 132, 57, 23, 10, 4, 1};
		int gap = list.length/2;

		while(gap > 0)
		{
			for(int i = gap; i < list.length; i++)
			{
				Comparable inserted = list[i];
				int hole = i;
				while(hole >= gap && inserted.compareTo(list[hole-gap]) < 0)
				{
					//System.out.println("Shell Sort comparison");
					list[hole] = list[hole-gap];
					hole = hole-gap;
				}
				list[hole] = inserted;
			}
			if(gap != 2)
				gap = (int)(gap/2.2);
			else
				gap = 1;
		}
	}


	/**
	 * Uses Merge sort to sort the provided list (NOT in place)
	 * @param list The list to sort
	 * @return The sorted list
	 */
	public static <T extends Comparable<T>> void mergeSort(T[] list)
	{
		if(list.length <= 1)
		{
			return;
		}
		else
		{
			int mid = list.length/2;
			T[] left = Arrays.copyOfRange(list,0,mid);
			T[] right = Arrays.copyOfRange(list,mid,list.length);
			mergeSort(left);
			mergeSort(right);
			merge(left,right,list);
		}
	}

	/**
	 * Merge the left and right sorted arrays into the master array. Replaces the master's contents
	 */
	private static <T extends Comparable<T>> void merge(T[] left, T[] right, T[] master)
	{
		int leftIndex = 0;
		int rightIndex = 0;
		int masterIndex = 0;

		while(leftIndex < left.length && rightIndex < right.length)
		{
			//System.out.println("Merge Sort comparison");
			if(left[leftIndex].compareTo(right[rightIndex]) < 0)
			{
				master[masterIndex] = left[leftIndex];
				leftIndex++;
			}
			else
			{
				master[masterIndex] = right[rightIndex];
				rightIndex++;
			}
			masterIndex++;
		}

		while(leftIndex < left.length)
		{
			master[masterIndex] = left[leftIndex];
			leftIndex++;
			masterIndex++;
		}

		while(rightIndex < right.length)
		{
			master[masterIndex] = right[rightIndex];
			rightIndex++;
			masterIndex++;
		}
	}

	/**
	 * Uses heap sort to sort the provided list (in place)
	 * @param list The list to sort
	 */
	public static void heapSort(Comparable[] list)
	{
		for(int i=1; i<list.length; i++)
		{
			//adapted from Heap.add()
			int child = i;
			int parent = (child-1)/2;
			while(parent >=0 && list[parent].compareTo(list[child]) < 0)
			{
				//System.out.println("Heap Sort comparison");
				Comparable tmp = list[parent];
				list[parent] = list[child];
				list[child] = tmp;

				child = parent;
				parent = (child-1)/2;
			}
		}

		for(int last=list.length-1; last > 0; last--)
		{
			Comparable tmp = list[0];
			list[0] = list[last];
			list[last] = tmp;

			//adapted from Heap.pop()
			int current = 0;
			boolean adjusting = true; //sentinel
			while(adjusting)
			{
				int leftChild = current*2+1;
				int rightChild = leftChild+1;

				if(leftChild < last) //if has left child
				{
					int maxChild = leftChild;
					//System.out.println("Heap Sort comparison");
					if(rightChild < last && list[rightChild].compareTo(list[leftChild]) > 0) //has right, and he's bigger?
						maxChild = rightChild;

					//System.out.println("Heap Sort comparison");
					if(list[current].compareTo(list[maxChild]) < 0)
					{
						//swap
						tmp = list[maxChild];
						list[maxChild] = list[current];
						list[current] = tmp;

						current = maxChild; //current min, who I just swapped
					}
					else //kids are smaller, so done
						adjusting = false;
				}
				else //no kids, done
					adjusting = false;
			}
		}
	}

	/**
	 * Uses quicksort to sort the provided list (in place)
	 * @param list The list to sort
	 */
	public static void quickSort(Comparable[] list)
	{
		quickSort(list,0,list.length-1);
	}

	/**
	 * Quicksorts a piece of a list
	 * @param list the list the sort
	 * @param first the index of the first item
	 * @param last the index of the last item
	 */
	private static void quickSort(Comparable[] list, int first, int last)
	{
		if(first < last)
		{
			int pivot = partition(list, first, last);
			quickSort(list, first, pivot-1);
			quickSort(list, pivot+1, last);
		}
	}

	/**
	 * Partitions the given list.
	 * @param list the list the sort
	 * @param first the index of the first item
	 * @param last the index of the last item
	 * @return the index of the pivot AFTER partitioning
	 */
	private static int partition(Comparable[] list, int first, int last)
	{
		Comparable pivot = list[first]; //pick the pivot

		int up = first;
		int down = last;
		do
		{
			while(up < last && list[up].compareTo(pivot) <= 0){ //if in correct side
				//System.out.println("Quicksort comparison");
				up++; //walk past
			}
			//up is pointing at someone on wrong side

			while(pivot.compareTo(list[down]) < 0){ //if on correct side
				//System.out.println("Quicksort comparison");
				down--; //walk past
			}
			//down is pointing at someoneon wrong side

			//at this point, up and down are either at the end of the list or found people to move

			if(up < down) //if there are items to move
			{
				Comparable tmp = list[up]; //swap the two out of place elements
				list[up] = list[down];
				list[down] = tmp;
			}

		} while(up < down);

		Comparable tmp = list[first]; //swap pivot into position
		list[first] = list[down];
		list[down] = tmp;

		return down; //return pivot's new position
	}
}
