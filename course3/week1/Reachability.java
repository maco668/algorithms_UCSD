import java.util.ArrayList;
import java.util.Scanner;

public class Reachability {
    boolean[] visited;
	int[] CCnum;
	int cc;
	ArrayList<Integer>[] adj;
	
	int reach(ArrayList<Integer>[] adj, int x, int y) {
        //write your code here
				
		int n = adj.length;
		visited = new boolean[n];
		CCnum = new int[n];
		DFS(adj);
		if (CCnum[x] != CCnum[y])		
			return 0;
		else
			return 1;
    }
	
	void Explore(ArrayList<Integer>[] adj, int vertex){
		visited[vertex] = true;
		CCnum[vertex] = cc;
		for (int i = 0; i < adj[vertex].size(); ++i){
			int w = adj[vertex].get(i);
			if (!visited[w])
				Explore(adj, w);
		}
		
	}
		
	void DFS(ArrayList<Integer>[] adj){
		cc = 1;
		for (int i = 0; i < adj.length; ++i){
			if (!visited[i])
				Explore(adj, i);
			cc = cc + 1;
		}
	}

	

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        
		// add new node
		for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
		
		
		
		// for each edge, register neighboring nodes
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(new Reachability().reach(adj, x, y));
    }
}

