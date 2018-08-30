import java.io.*;
import java.util.*;

public class BinarySearch {

    static int binarySearch(int[] a, int x) {
        int left = 0, right = a.length;
        //write your code here
		
		int mid = 0;
		
		while (left < right) {
			mid = (left + right) / 2;
			
			if (a[mid] == x)
				return mid;
			else if (a[mid] < x){
				left = mid + 1;			
			}
			else 
				right = mid - 1;
			
		}
        return -1;
    }

    static int linearSearch(int[] a, int x) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == x) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
 /*       FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int m = scanner.nextInt();
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
          b[i] = scanner.nextInt();
        }
*/

		Random rand = new Random();
		int n = rand.nextInt(10) + 1; // 1<=n<=1e5
		int m = rand.nextInt(10) + 1; // 1<=k<=1e5
		
		int[] a = new int[n];
		int[] b = new int[m];
		
		int upper = (int)20;
		
		System.out.println("n = " + n);
		for (int i = 0; i < n; i++) {
            a[i] = rand.nextInt(upper) + 1; // 1<=ai<= 1e9
			System.out.print(a[i] + " ");
        }
		
		System.out.println("\n" + "m = " + m);		
		for (int i = 0; i < m; i++) {
            b[i] = rand.nextInt(upper) + 1; // 1<=ai<= 1e9
			System.out.print(b[i] + " ");
        }
		
		System.out.println();
        for (int i = 0; i < m; i++) {
            //replace with the call to binarySearch when implemented
            //System.out.print(linearSearch(a, b[i]) + " ");
			System.out.print(binarySearch(a, b[i]) + " ");
        }
    }
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
