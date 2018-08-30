import java.util.ArrayList;
import java.util.Scanner;

public class ConnectedComponents {	
	
	static boolean[] visited;
	static int[] CCnum;
	static int cc;
	ArrayList<Integer>[] adj;
	
	private int numberOfComponents(ArrayList<Integer>[] adj) {
        //write your code here
				
		int n = adj.length;
		visited = new boolean[n]; //default is false
		CCnum = new int[n];
		DFS(adj);
		
		return cc;
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
		cc = 0;
		for (int i = 0; i < adj.length; ++i){
			if (!visited[i]){
				cc++;
				Explore(adj, i);				
			}
			
		}
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
        System.out.println(new ConnectedComponents().numberOfComponents(adj));
    }
}

