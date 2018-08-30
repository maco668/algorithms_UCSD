import java.util.*;
import java.io.*;

public class stress_test {
    static long getMaxPairwiseProductFast(long[] numbers) {
        long result = 0;
        int n = numbers.length;
        long dummy = 0;
        
        int max = 0;
        for (int i = 0; i < n; i++ ){
            if ( numbers[i]>numbers[max])
                max = i;
        }
        if (max != 0){
             dummy = numbers [0];
             numbers[0] = numbers[max];
             numbers[max] = dummy;
        }
        int secondMax = 1;
        for (int i = 1; i< n; i++){
            if (numbers[i] > numbers[secondMax])
                    secondMax = i; 
        }
  
        
        result =  numbers[0]*numbers[secondMax];
        
        return result;
    }

      static long getMaxPairwiseProduct(long[] numbers) {
        long result = 0;
        int n = numbers.length;
        
          for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (numbers[i] * numbers[j] > result) {
                    result = numbers[i] * numbers[j];
                }
            }
        }
          return result;
        }
    
    
    public static void main(String[] args) {
        int count = 0;
        while (true && count < 10000){
            Random rand =  new Random();
            int n = rand.nextInt(10)+2;            
            System.out.println(n);
            
            long[] numbers = new long[n];
            for (int i = 0; i < n; i++) {
                numbers[i] = (long)rand.nextInt(1000000);
                System.out.print(numbers[i]+" ");
            }
            System.out.println();
            
            long rest1=getMaxPairwiseProduct(numbers);
            long rest2=getMaxPairwiseProductFast(numbers);
            if (rest1 != rest2){
                System.out.println("Wrong answer:" + rest1 + " "+ rest2 + "\n");
                break;
            }
            count++;
        }
            
        
      FastScanner scanner = new FastScanner(System.in);// input stream 
      int n = (int)scanner.nextLong(); // receives user input and stores in long variable n
//        int n = 20000000;
        
        long[] numbers = new long[n];
//        Arrays.fill(numbers, 0); // assign an int value to each element of the array of ints
       for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextLong();
        }
        System.out.println(getMaxPairwiseProductFast(numbers));
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

        long nextLong() {
            return Long.parseLong(next());
        }
    }

}