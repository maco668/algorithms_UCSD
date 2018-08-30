import java.util.ArrayList;
import java.util.Scanner;

public class Acyclicity {	

	static boolean[] visited;
	static int[] CCnum; //connected component number
	static int[] pre; 
	static int[] post;
	static int cc;
	static boolean cyclic;
	static int clock;
	static ArrayList<Integer>[] adj;
	
	 private int acyclic(ArrayList<Integer>[] adj) {
        //write your code here
		
		int n = adj.length;
		visited = new boolean[n]; //default is false
		CCnum = new int[n];
		pre = new int[n];
		post =  new int[n];
		clock = 1;
		DFS(adj);
		//print pre[]
		//System.out.println();
		//System.out.print("pre-order:");
		//for(int i : pre)
		//	System.out.print(i + " ");
		
		//System.out.println();
		//System.out.print("post-order:");
		//for(int i : post)
		//	System.out.print(i + " ");
		
		if (cyclic)
			return 1;
		else
			return 0;
    }

	void Explore(ArrayList<Integer>[] adj, int vertex){
		//print currently visiting vertex
		//System.out.print((vertex + 1) + " ");
		visited[vertex] = true;		
		CCnum[vertex] = cc;
		previsit(vertex); // update pre-order number
		for (int i = 0; i < adj[vertex].size(); ++i){
			int w = adj[vertex].get(i);
			if(visited[w] && (pre[w] < pre[vertex]) && (post[w] == post[vertex])) {
				//if a cycle is formed along current path, both post numbers haven't updated
				//System.out.println();
				//System.out.println("w=" + (w+1) + " vertex=" + (vertex+1));
				//System.out.println("pre[w]=" + pre[w] + " pre[vertex]=" + pre[vertex]) ;
				//System.out.println("post[w]=" + post[w] + " post[vertex]=" + post[vertex]) ;
				cyclic = true;
				return;				
			}
			if (!visited[w]) // recursion
				Explore(adj, w);
		}
		postvisit(vertex); // update post-order number
	}
	
	//depth first search 	
	void DFS(ArrayList<Integer>[] adj){
		cc = 0;
		for (int i = 0; i < adj.length; ++i){
			if (cyclic) //if cyclic found, get out
				break;
			else if (!visited[i]){
				cc++;
				Explore(adj, i);				
			}			
		}
	}
	
	void previsit(int vertex){
		pre[vertex] = clock;
		++clock; 
	}
	
	void postvisit(int vertex){
		//System.out.println();
		//System.out.println("postvisit_vertex=" + (vertex+1));
		post[vertex] = clock;
		++clock; 
	}	

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        // this is how to define an array of ArrayLists
		ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n]; 
		
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1); //only save pair once
        }
		int cyclic = new Acyclicity().acyclic(adj);
		//System.out.println();
        System.out.println(cyclic);
    }
}

