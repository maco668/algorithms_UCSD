/* Author: Ding Ma 
Date: 06/03/2016
Task. The first step of the HeapSort algorithm is to create a heap from the array you want to sort. By the
way, did you know that algorithms based on Heaps are widely used for external sort, when you need
to sort huge files that dont fit into memory of a computer?
Your task is to implement this first step and convert a given array of integers into a heap. You will
do that by applying a certain number of swaps to the array. Swap is an operation which exchanges
elements ai and aj of the array a for some i and j. You will need to convert the array into a heap
using only O(n) swaps, as was described in the lectures. Note that you will need to use a min-heap
instead of a max-heap in this problem.

Input Format. The first line of the input contains single integer n. The next line contains n space-separated
integers ai.

Constraints. 1 <= n <= 100 000; 0 <= i; j <= n - 1; 0 <= a0; a1 <= 1e9. All ai are distinct.

Output Format. The first line of the output should contain single integer m: the total number of swaps.
m must satisfy conditions 0 <= m <= 4n. The next m lines should contain the swap operations used
to convert the array a into a heap. Each swap is described by a pair of integers i; j | the 0-based
indices of the elements to be swapped. After applying all the swaps in the specified order the array
must become a heap, that is, for each i where 0 <= i <= n - 1 the following conditions must be true:
1. If 2i + 1 <= n - 1, then ai < a2i+1.
2. If 2i + 2 <= n - 1, then ai < a2i+2.

Note that all the elements of the input array are distinct. Note that any sequence of swaps that has
length at most 4n and after which your initial array becomes a correct heap will be graded as correct.
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class BuildHeap {
    private int[] data;
    private List<Swap> swaps;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
		
        new BuildHeap().solve();
    }

    private void readData() throws IOException {
        int n = in.nextInt();
        data = new int[n];
        for (int i = 0; i < n; ++i) {
          data[i] = in.nextInt();
        }
		swaps = new ArrayList<Swap>();
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
          out.println(swap.index1 + " " + swap.index2);
        }
    }

    private void generateSwaps(int i) {
      // The following naive implementation just sorts 
      // the given sequence using selection sort algorithm
      // and saves the resulting sequence of swaps.
      // This turns the given array into a heap, 
      // but in the worst case gives a quadratic number of swaps.
      //
      // TODO: replace by a more efficient implementation
	  
			int left  = 2*i + 1;
			int right = 2*i + 2;
			if ((left >= data.length) && (right >= data.length))
				return;
			else{
				int min = i;
				if ((left < data.length) && (data[min] > data[left]))
					min = left;
				if ((right < data.length) && (data[min] > data[right]))
					min = right;
				if (min != i){
					int tmp = data[i];
					data[i] = data[min];
					data[min] = tmp;
					swaps.add(new Swap(i, min));
					generateSwaps(min);
					}
				//System.out.println("min: " + min);
				
			}
		
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
		
		int half = data.length / 2 -1;
		
		//System.out.println("half: " + half);
		
		for (int i = half; i >= 0; --i ){ 
			generateSwaps(i);
			//System.out.println("node " + i + " is sifted")
		}
		
        
        writeResponse();
        out.close();
    }

    static class Swap {
        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
