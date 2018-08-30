import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Bipartite {	

	static int infinite = 1000001;
	
    private static int bipartite(ArrayList<Integer>[] adj) {
        //write your code here
		
		int vertex;
		int n = adj.length;
		int[] dist = new int[n];
		int[] prev = new int[n];
		boolean[] bipartite =new boolean[n];
		LinkedList<Integer> Q = new LinkedList<Integer>(); //queue for BFS 
		for (int i = 0; i < n; ++i)
			dist[i] =  infinite;  // initializing distances
		 
		dist[0] = 0; // set the first node as origin
		bipartite[0] = true;
		Q.add(0);
		while (!Q.isEmpty()) { // if queue is not empty
			vertex = Q.poll(); // retrieve and return the head element
			for (int i = 0; i < adj[vertex].size(); ++i){ // BFS all the connected nodes to u
				int w = adj[vertex].get(i); 
				if (dist[w] == infinite){
					Q.add(w);
					dist[w] = dist[vertex] + 1; //update the distance (from s to current)
					prev[w] = vertex; // register the parent node
					bipartite[w] = !bipartite[vertex];
				}
				if ((dist[w] != infinite) && (bipartite[w] == bipartite[vertex])){
					// if the node has been visited and has the same color as the pre-node
					return 0;
				}
			}
		}
		
        return 1;
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
            adj[y - 1].add(x - 1);
        }
        System.out.println(bipartite(adj));
    }
}

