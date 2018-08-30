import java.util.ArrayList;
import java.util.Scanner;

public class NegativeCycle {
	
	static int infinite = 200000001;
	static int nil = -200000001;
	//static int size;
	
	
    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        // write your code here
		int vertex;
		int n = adj.length;		
		int[] dist = new int[n];
		int[] prev = new int[n];
		int[] H; 
		int flag = 0;
		
		for (int i = 0; i < n; ++i){
			dist[i] =  infinite;  // initializing distances
			prev[i] = nil;
		}
		
		//LinkedList<Integer> Q = new LinkedList<Integer>(); //queue for BFS 
		
		int s = 0;
		//System.out.println("s:" + s + ", t:" + t);
		dist[s] = 0; // the distance to itself(starting point) is zero
		//Q.add(s);
		
		for(int i = 0; i < n; ++i){
		
			for (vertex = 0; vertex < n; ++vertex){
				
				//vertex = Q.poll();
				for (int j = 0; j < adj[vertex].size(); ++j){ // BFS all the connected nodes to u
					int w = adj[vertex].get(j); 
					//System.out.println("vertex:" + vertex + ", w:" + w);
					//System.out.println("dist[w]:" + dist[w] + ", dist[vertex]:" + dist[vertex] + ", cost[vertex].get(i):" 
					//					+ cost[vertex].get(i));
					if (dist[w] > (dist[vertex] + cost[vertex].get(j))){
						
						dist[w] = dist[vertex] + cost[vertex].get(j); //update the distance (from s to current)
						
						prev[w] = vertex; // register the parent node
						
						if (i == (n - 1)){
							flag = 1;
							break;
						}
						//for (int j = 1; j <= n; j++)						
						//	System.out.print(dist[j] + ", ");
						//System.out.println();

					}
					
				}
			}			
			
		}
		if (flag == 1)
			return 1;
		else
			return 0;		
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        System.out.println(negativeCycle(adj, cost));
    }
}

