import java.util.*;
import java.io.*;

public class tree_orders {
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

	public class TreeOrders {
		int n;
		int[] key, left, right;
		
		//the root node
		Tree treeHead;
		Tree[] trees;
		
		class Tree{			
			Tree left;
			Tree right;
			int key;
			int index;
			
		}
		
		void InOrderTraversal(Tree tree,ArrayList<Integer> result){
			if (tree == null)
				return;
			InOrderTraversal(tree.left, result);
			result.add(tree.key);
			//System.out.println("key" + tree.key);
			InOrderTraversal(tree.right, result);
			
		}
		
		void PreOrderTraversal(Tree tree,ArrayList<Integer> result){
			if (tree == null)
				return;
			result.add(tree.key);
			PreOrderTraversal(tree.left, result);			
			PreOrderTraversal(tree.right, result);
			
		}
		
		void PostOrderTraversal(Tree tree,ArrayList<Integer> result){
			if (tree == null)
				return;			
			PostOrderTraversal(tree.left, result);			
			PostOrderTraversal(tree.right, result);
			result.add(tree.key);
			
		}
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			key = new int[n];
			left = new int[n];
			right = new int[n];
			Tree[] trees = new Tree[n];
			for (int i = 0; i < n; i++) { 
				key[i] = in.nextInt();
				left[i] = in.nextInt();
				right[i] = in.nextInt();
			}
			//use array to initialize the tree
			for (int i = 0; i < n; i++){
				trees[i] = new Tree();
				trees[i].key = key[i];
				trees[i].index = i;
			}
			//after every node is created, assign them to their parents
			for (int i = 0; i < n; i++){
				if (left[i] != -1)
					trees[i].left = trees[left[i]];
				else 
					trees[i].left = null;
				
				if (right[i] != -1)
					trees[i].right = trees[right[i]];
				else 
					trees[i].right = null;
				
				//System.out.println("key: " + trees[i].key + " left key: " + trees[i].left.key
				//										  + " right key: " + trees[i].right.key);
			}
			
			treeHead = trees[0];
			
			
		}		
		

		List<Integer> inOrder() {
			ArrayList<Integer> result = new ArrayList<Integer>();
			//System.out.println("inOrder");
			InOrderTraversal(treeHead, result);                        
			return result;
		}
		
		

		List<Integer> preOrder() {
			ArrayList<Integer> result = new ArrayList<Integer>();
            PreOrderTraversal(treeHead, result);       
            return result;
		}

		List<Integer> postOrder() {
			ArrayList<Integer> result = new ArrayList<Integer>();
            PostOrderTraversal(treeHead, result);          
			return result;
		}
	}

	static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_orders().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
	}

	public void print(List<Integer> x) {
		for (Integer a : x) {
			System.out.print(a + " ");
		}
		System.out.println();
	}

	public void run() throws IOException {
		TreeOrders treeO = new TreeOrders();
		treeO.read();
		print(treeO.inOrder());
		print(treeO.preOrder());
		print(treeO.postOrder());
	}
}
