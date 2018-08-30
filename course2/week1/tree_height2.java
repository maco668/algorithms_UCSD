import java.util.*;
import java.io.*;

	class Node {
		int data;
		Node parent = null;
		//Node parent;
		List<Node> children = new ArrayList<Node>();
			
		public void Node(int data){
			this.data = data;
			this.children = new LinkedList<Node>();
			//System.out.println("this.data=" + data);
		}
		
		public Node addChild(Node childNode){
			//Node<Integer> childNode = new Node<Integer>(child);
			childNode.parent = this;
			this.children.add(childNode);
			//System.out.println("node: " + childNode.parent.data + " addChild: " + childNode.data);
			return childNode;
		}
		
		public void printNode(){
			System.out.println("rootNode: " + this.data + " ");
			
		}
		public void printChildren(){
			System.out.print("children: ");
			for(Node child:children)
				System.out.print(child.data + ",");
			System.out.println();
		}
}

public class tree_height2 {
  
	
	

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
		Node[] node = new Node[n];
		Node head = new Node();
		
		//System.out.println("node size = " + node.length);
		//System.out.println(node[0].label);
			
		for (int i = 0; i < n; i++) {
			parent[i] = in.nextInt();	
			node[i] = new Node();
			node[i].Node(i);

		}		
		
		System.out.println("finishing input");
			
		for(int i = 0; i < n; i++){
			if (parent[i] != -1){
				node[parent[i]].addChild(node[i]);		
			}else{
				head = node[i];
			}		
			
		}
		System.out.println("finishing tree");
		head.printNode();
		head.printChildren();
		
		
		//tree_height2 tree = new tree_height2();                      

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
	
	

	


	
	

