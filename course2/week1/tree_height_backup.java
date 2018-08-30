import java.util.*;
import java.io.*;


public class tree_height {
 /*   class FastScanner {
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
	}*/
	
	class FastScanner {
		StringTokenizer tok = new StringTokenizer("");
		BufferedReader br;

	
		FastScanner(String fileName) {
			try {
				br = new BufferedReader(new FileReader(fileName));
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		

		String next() throws IOException {
			while (!tok.hasMoreElements())
				tok = new StringTokenizer(br.readLine());
			return tok.nextToken();
		}
		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	
	}
	

	public class TreeHeight {
		int n;
		int parent[];
		TreeHeight[] node;
		TreeHeight head;
		
		String inputName = "01";
		String answerName = "01.a";	
		
		void read() throws IOException {					
			
			FastScanner in = new FastScanner(inputName);
			
			//FastScanner in = new FastScanner();
			n = in.nextInt();
			parent = new int[n];
			TreeHeight[] node = new TreeHeight[n];
			TreeHeight head = new TreeHeight();		
			
			for (int i = 0; i < n; i++) {
				parent[i] = in.nextInt();	
				node[i] = new TreeHeight();
				node[i].Node(i);

			}		
		
			in.br.close();
			System.out.println("finishing input");
				
			for(int i = 0; i < n; i++){
				if (parent[i] != -1){
					node[parent[i]].addChild(node[i]);		
				}else{
					head = node[i];
				}		
			
			}
			System.out.println("finishing tree"	);
			head.printNode();
		//head.printChildren();
		//node[1].printChildren();
		//System.out.println("tree height:" + (1 + computeHeight()));
			
		}

		int readAnswer(){
			FastScanner in = new FastScanner(inputName);
			in = new FastScanner(answerName);
			int answer = in.nextInt();
			in.br.close();
			return answer;
		}
		
		int data;
		TreeHeight parent = null;
		//TreeHeight parent;
		List<TreeHeight> children = new LinkedList<TreeHeight>();
			
		public void TreeHeight(int data){
			this.data = data;
			this.children = new LinkedList<TreeHeight>();
			//System.out.println("this.data=" + data);
		}
		
		public TreeHeight addChild(TreeHeight childNode){
			//TreeHeight<Integer> childNode = new TreeHeight<Integer>(child);
			childNode.parent = this;
			this.children.add(childNode);
			//System.out.println("node: " + childNode.parent.data + " addChild: " + childNode.data);
			return childNode;
		}
		
		int computeHeight(){
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
				if (max < child.hcomputeHeight()){
					max = child.computeHeight();
				}
					//System.out.println("childrenHeight[" + i + "]=" + childrenHeight[i]);
			}
				
				
			//height = 1 + max;	
				//height = 1 + max(childrenHeight);
			return (1 + max);
			}
			
		}
		
	
		public void printNode(){
			System.out.println("TreeHeight: " + this.data + " ");
			
		}
		public void printChildren(){
			System.out.print("children of TreeHeight " + this.data + ": ");
			for(TreeHeight child:children)
				System.out.print(child.data + ", ");
			System.out.println();
		}
		
		
					
	}		
		
		


	static public void main(String[] args) throws IOException {
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
		System.out.println("answer:" + tree.readAnswer());
	}
}
