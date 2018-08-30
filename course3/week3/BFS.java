import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BFS {
	
	static boolean[] visited;
	static int[] CCnum;
	static int cc;
	static int infinite = 1000001;
	//ArrayList<Integer>[] adj;
	
    private static int distance(ArrayList<Integer>[] adj, int s, int t) {
        //write your code here
		int vertex;
		int n = adj.length;
		int[] dist = new int[n];
		int[] prev = new int[n];
		LinkedList<Integer> Q = new LinkedList<Integer>(); //queue for BFS 
		for (int i = 0; i < n; ++i)
			dist[i] =  infinite;  // initializing distances
		 
		dist[s] = 0; // the distance to itself(starting point) is zero
		
		Q.add(s);
		while (!Q.isEmpty()) { // if queue is not empty
			vertex = Q.poll(); // retrieve and return the head element
			for (int i = 0; i < adj[vertex].size(); ++i){ // BFS all the connected nodes to u
				int w = adj[vertex].get(i); 
				if (dist[w] == infinite){
					Q.add(w);
					dist[w] = dist[vertex] + 1; //update the distance (from s to current)
					prev[w] = vertex; // register the parent node
				}
				if (w==t)
					return dist[w];
			}
		}
		// if not found
        return -1;
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, x, y));
    }
}

