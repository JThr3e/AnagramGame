// Java program to print all combination of size r in an array of size n
import java.util.HashSet;
 
public class Combinations{

	
    /* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Staring and Ending indexes in arr[]
    index  ---> Current index in data[]
    r ---> Size of a combination to be printed */
    public static void combinationUtil(char arr[], char data[], int start,
                                int end, int index, int r, HashSet<String> combos)
    {
        // Current combination is ready to be printed, print it
        if (index == r)
        {
        	String combo ="";
            for (int j=0; j<r; j++)
            	combo += data[j];
            combos.add(combo);
            return;
        }
 
        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r, combos);
        }
    }
 
    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    public static void findCombinations(char arr[], int n, int r, HashSet<String> combos)
    {
        // A temporary array to store all combination one by one
        char data[]=new char[r];
 
        // Print all combination using temporary array 'data[]'
        combinationUtil(arr, data, 0, n-1, 0, r, combos);
    }
 
    /*Driver function to check for above function*/
    public static void main (String[] args) {
        String str = "apples";
        char arr[] = str.toCharArray();
        int r = 3;
        int n = arr.length;
        HashSet<String> combos = new HashSet<String>();
        findCombinations(arr, n, r, combos);
        for(String s : combos){
        	System.out.println(s);
        }
    }
}