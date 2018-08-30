/*Task. You are given a description of a rooted tree. Your task is to compute and output its height. Recall
that the height of a (rooted) tree is the maximum depth of a node, or the maximum distance from a
leaf to the root. You are given an arbitrary tree, not necessarily a binary tree.

Input Format. The first line contains the number of vertices n. The second line contains n integer numbers
from -1 to n - 1: parents of vertices. If the i-th one of them (0 <= i <= n - 1) is -1, vertex i is the
root, otherwise it's 0-based index of the parent of i-th vertex. It is guaranteed that there is exactly
one root. It is guaranteed that the input represents a tree.

Constraints. 1 <= n <= 1e5.

Output Format. Output the height of the tree.

What to do: To solve this problem, change the height function described in the lectures with an implementation which
will work for an arbitrary tree. Note that the tree can be very deep in this problem, so you should be careful
to avoid stack overflow problems if you're using recursion, and definitely test your solution on a tree with
the maximum possible height.
*/

import java.util.*;
import java.io.*;


public class tree_height {
    class FastScanner {
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
	
	public class TreeHeight {
	// it suffices to have a node class with recursive height method
		int n;
		int parent[];
		TreeHeight[] node; // an array of references to the tree nodes
		TreeHeight head;	//root node for starting the height method
		
		//String inputName = "21";
		//String answerName = "21.a";	
		
		void read() throws IOException {					
			
			//FastScanner in = new FastScanner(inputName);
			
			FastScanner in = new FastScanner();
			n = in.nextInt();
			parent = new int[n];
			TreeHeight[] node = new TreeHeight[n];
				
			
			for (int i = 0; i < n; i++) {
				parent[i] = in.nextInt();	
				node[i] = new TreeHeight(); 
				// create a new object, otherwise null pointer when
				// using method
				node[i].TreeHeight(i);

			}		
		
			//in.br.close();
			//System.out.println("finishing input");
				
			for(int i = 0; i < n; i++){
				//Interpret the input tree information
				//If not root node, find the parent node's reference
				// and register its children nodes to a linked list
				// List is a super class, will need ArrayList, or Linkedlist
				if (parent[i] != -1){
					node[parent[i]].addChild(node[i]);		
				}else{
					//Identify the root node and reference it using head variable
					head = node[i];
				}		
			
			}
			//System.out.println("finishing tree"	);
			//head.printNode();
		//head.printChildren();
		//node[1].printChildren();
		//System.out.println("tree height:" + (1 + computeHeight()));
			
		}

/*		int readAnswer() throws IOException{
			FastScanner in = new FastScanner(inputName);
			in = new FastScanner(answerName);
			int answer = in.nextInt();
			in.br.close();
			return answer;
		}*/
		
		int data;
		//Register parent node of current node, cannot have the same
		// name "parent" because it was defined as int[]
		TreeHeight parentNode = null;  
		//Storing children
		List<TreeHeight> children = new LinkedList<TreeHeight>();
			
		public void TreeHeight(int data){
			//User-defined constructor taking a parameter
			this.data = data;
			//create a new object and reference it using previously defined variable
			this.children = new LinkedList<TreeHeight>();
			//System.out.println("this.data=" + data);
		}
		
		public TreeHeight addChild(TreeHeight childNode){
			//TreeHeight<Integer> childNode = new TreeHeight<Integer>(child);
			//Cannot use generic Integer for the node type
			//Any class method has an internally passed parameter this.
			childNode.parentNode = this;
			this.children.add(childNode);
			//System.out.println("node: " + childNode.parentNode.data + " addChild: " + childNode.data);
			return childNode;
		}
		
		int computeHeightNode(){
			//int height;
			//System.out.println("height() of node: " + this.data);
			
			int max = -1;
			if (children.isEmpty())
				return 0;
			else{
				//int n = this.children.size();
				//System.out.println("n:" + n);
				//int[] childrenHeight = new int[n];
				
			for(TreeHeight child:children){
				int nodeheight = child.computeHeightNode();
				if (max < nodeheight){
					max = nodeheight;
				}
					//System.out.println("childrenHeight[" + i + "]=" + childrenHeight[i]);
			}
				
				
			//height = 1 + max;	
				//height = 1 + max(childrenHeight);
			return (1 + max);
			}
			
		}
		
		int computeHeight(){
			//Wrapping the recursive function to suite the 
			//setup in the starter file
			return (1 + head.computeHeightNode());
		}
		
	
		public void printNode(){
			System.out.println("Node: " + this.data + " ");
			
		}
		public void printChildren(){
			System.out.print("children of TreeHeight " + this.data + ": ");
			for(TreeHeight child:children)
				System.out.print(child.data + ", ");
			System.out.println();
		}
		
		
					
	}		
		
		


	static public void main(String[] args) throws IOException {
		// this one seems using multi-thread to avoid stack overflow during 
		// very deep recursive calls. Need to understand this more.
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_height().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
	}
	public void run() throws IOException {
		TreeHeight tree = new TreeHeight();
		tree.read();		
		System.out.println(tree.computeHeight());
		//System.out.println("tree height:" + tree.computeHeight());
		//System.out.println("answer:" + tree.readAnswer());
	}
}
