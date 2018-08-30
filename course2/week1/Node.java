import java.util.*;
import java.io.*;

class Node{
		private int key;
		//Node parent;
		private List<Integer> children;
			
		public void Node(int number){
			key = number;
			
		}
		
		public int key(){
			return key;
		}
		
		public void addchildren(int childnum){
			children.push(childnum);
		}
}

public class tree_height {
  
	
	

	public class TreeHeight {
		int n;
		int parent[];
//		int leaf[]; //only reference
		Node node[];
		
		/*void read() throws IOException {
			
		}*/
			
/*			for(int i=0; i<n; i++){
				System.out.print("label=" + node[i].label + ", parent=" 
									+ node[i].parent + ", children=");
				for (int j=0; j<node[i].children.size(); j++)
					System.out.print(node[i].children.get(j) + ", ");
				System.out.println();			
				
			}	*/
			
			
//			leaf = new int[n]; // refernce pointing to the created array
			//System.out.println("leaf.length=" + leaf.length);
			//for (int i = 0; i < n; i++){
			//	System.out.print("leaf[" + i + "]=" + leaf[i] + ", ");
			//}
			
			//System.out.println();
			
			
				
			//System.out.println("leaf nodes=" + (n - sum));			
		}		


	static public void main(String[] args) throws IOException {
                        
		FastScanner in = new FastScanner();
		int n = in.nextInt();
		int[] parent = new int[n];
		Node node = new Node[n];
		//System.out.println("node size = " + node.length);
		//System.out.println(node[0].label);
			
		for (int i = 0; i < n; i++) {
			parent[i] = in.nextInt();				
		}
		
		TreeHeight tree = new TreeHeight();                      

		}
		
		public void donothing() {
		
			
//		tree.();
		//System.out.println(tree.computeHeight());
		}
		
		
	static  class FastScanner {
		StringTokenizer tok = new StringTokenizer("");
		BufferedReader in;

		FastScanner() {
			in = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (!tok.hasMoreElements())
				tok = new StringTokenizer(in.readLine());
			return tok.nextToken();
		}
		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}
		
}
	
	

	


	
	

