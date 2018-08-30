import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Toposort {
	
	static int[] CCnum; //connected component number
	static int[] pre; 
	static int[] post;
	static int cc;
	static int clock;
	
    private static ArrayList<Integer> toposort(ArrayList<Integer>[] adj) {
        boolean visited[] = new boolean[adj.length];
        ArrayList<Integer> order = new ArrayList<Integer>();
		//Stack<Integer> order = new Stack<Integer>(); //stack definition
		CCnum = new int[adj.length];
		pre = new int[adj.length];
		post =  new int[adj.length];
		clock = 1;
        //write your code here
		DFS(adj, visited, order);
		
        return order;
    }

	private static void DFS(ArrayList<Integer>[] adj, boolean[] visited, ArrayList<Integer> order){
		cc = 0;
		for (int i = 0; i < adj.length; ++i){
			if (!visited[i]){
				cc++;
				Explore(adj, i, visited, order);				
			}			
		}
	}
	
	private static void Explore(ArrayList<Integer>[] adj, int vertex, boolean[] visited, ArrayList<Integer> order){
		//print currently visiting vertex
		//System.out.print((vertex + 1) + " ");
		visited[vertex] = true;		
		CCnum[vertex] = cc;
		previsit(vertex); // update pre-order number
		for (int i = 0; i < adj[vertex].size(); ++i){
			int w = adj[vertex].get(i);		
			if (!visited[w]) // recursion
				Explore(adj, w, visited, order);
		}
		postvisit(vertex); // update post-order number
		order.add(vertex);
		//for(int i : order)
		//	System.out.print((i+1) + " ");
		//System.out.println();
		
	}
	
	private static void previsit(int vertex){
		pre[vertex] = clock;
		++clock; 
	}
	
	private static void postvisit(int vertex){
		//System.out.println();
		//System.out.println("postvisit_vertex=" + (vertex+1));
		post[vertex] = clock;
		++clock; 
	}	

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
        }
        ArrayList<Integer> order = toposort(adj);
        for (int i = (order.size() -1); i >= 0; --i) {
            System.out.print((order.get(i) + 1) + " ");
        }
    }
}

