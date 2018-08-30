import java.util.*;

public class Dijkstra {
	
	static boolean[] visited;
	static int[] CCnum;
	static int cc;
	static int infinite = 200000001;
	static int size;
	static int[] distToH;
	
		
    private static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
        int vertex;
		int n = adj.length - 1;
		size = n;
		int[] dist = new int[n + 1];
		distToH = new int[n + 1];
		int[] H; 
		
		Integer[] prev = new Integer[n + 1];
		//LinkedList<Integer> Q = new LinkedList<Integer>(); //queue for BFS 
		for (int i = 1; i <= n; ++i)
			dist[i] =  infinite;  // initializing distances
		 
		//System.out.println("s:" + s + ", t:" + t);
		dist[s] = 0; // the distance to itself(starting point) is zero
		for (int j = 1; j <= n; j++)						
			System.out.print(dist[j] + ", ");
		System.out.println();
		H = MakeQueue(dist);
		System.out.println();
		for (int j = 1; j <= n; j++)						
			System.out.print(dist[j] + ", ");
		System.out.println();
		
		while (size != 0) { // if binary heap is not empty
			System.out.println("size:" + size);
			//System.out.println("starting ExtractMin...");
			vertex = ExtractMin(H, dist); // retrieve and return the head element
			//System.out.println("finishing ExtractMin...");
			System.out.println("vertex after ExtractMin: " + vertex);
			//if (vertex == 5){
				System.out.print("H: ");
				for (int j = 1; j <= n; j++)						
					System.out.print(H[j] + ", ");
				System.out.println();
				System.out.print("distToH: ");
				for (int j = 1; j <= n; j++)						
					System.out.print(distToH[j] + ", ");
				System.out.println();
			//}
			
			for (int i = 0; i < adj[vertex].size(); ++i){ // BFS all the connected nodes to u
				int w = adj[vertex].get(i); 
				System.out.println("vertex:" + vertex + ", w:" + w);
				System.out.println("dist[w]:" + dist[w] + ", dist[vertex]:" + dist[vertex] + ", cost[vertex].get(i):" 
									+ cost[vertex].get(i));
				if (dist[w] > (dist[vertex] + cost[vertex].get(i))){
					
					dist[w] = dist[vertex] + cost[vertex].get(i); //update the distance (from s to current)
					
					prev[w] = vertex; // register the parent node
					
					for (int j = 1; j <= n; j++)						
						System.out.print(dist[j] + ", ");
					System.out.println();
					
					//System.out.println("starting ChangePriority...");
					ChangePriority(H, w, dist);
					//System.out.println();
					for (int j = 1; j <= n; j++)						
						System.out.print(dist[j] + ", ");
					System.out.println();
					for (int j = 1; j <= size; j++)						
						System.out.print(H[j] + ", ");
					System.out.println();
					System.out.println();
					//System.out.println("finishing ChangePriority...");
				}
				
			}
			if ((vertex==t) && (dist[vertex] != infinite))
				return dist[vertex];
			
		}		
		
		return -1;
		
    }
	
	private static void ChangePriority(int[] H, int w, int[] dist){
		//if (w == 6)
			System.out.print("distToH[" + w + "]:" + distToH[w] + ", H[distToH[" + w + "]]:" + H[distToH[w]] + "\n");
		siftUp(distToH[w], H, dist);
		return;
		
	}
	
	private static int ExtractMin(int H[], int[] dist){
		distToH[H[size]] = 1;
		
		int result = H[1];		
		H[1] = H[size];
		
		size--;
		//System.out.println("size:" + size);
		siftDown(H, 1, dist);
		return result;		
		
	}
	
	private static int[] MakeQueue(int[] dist){	
		int n = dist.length - 1;
		int[] H = new int[n + 1];		
		
		System.out.print("\ni=");
		for (int i = 1; i <= n; ++i){
			H[i] = i; // binary heap of vertex number 
		// denotes position in dist[]
			System.out.print(i + ", ");
			distToH[i] = i;
		}
		
		int half = n / 2 - 1;		
		//System.out.println("half: " + half);
		
		for (int i = half; i >= 1; i-- )
			generateSwaps(i, H, dist);
			//System.out.println("node " + i + " is sifted")
			
		return H;
		
	}
	
	private static void generateSwaps(int i, int[] H, int[] dist) {
		//make heap-ordered based on dist[]
		int left  = 2*i;
		int right = 2*i + 1;
		int n = dist.length;
		if ((left >= n) && (right >= n))
			return;
		else{
			int min = i;
			if ((left <= n) && (dist[H[min]] > dist[H[left]]))
				min = left;
			if ((right <= n) && (dist[H[min]] > dist[H[right]]))
				min = right;
			if (min != i){// exchange pointers to dist[]
				distToH[H[i]] = min;
				distToH[H[min]] = i;
			
				int tmp = H[i];
				H[i] = H[min];
				H[min] = tmp;	

				
				generateSwaps(min, H, dist);
				}
			//System.out.println("min: " + min);			
		}		
		return;
	}
	//H[distToH[5]] = 0
	
	//siftUp(w, H, dist);
	private static void siftUp(int i, int[] H, int[] dist){	
		
			
		int parent = i / 2;
		if (parent == 0)
			return;
		
		
			
			while ((i > 1) && (dist[H[i]] <= dist[H[parent]])){				
				
				System.out.println("before swap i:" + i + ", parent:" + parent);
				System.out.println("before swap dist[H[i]]:" + dist[H[i]] + ", dist[H[parent]]:" + dist[H[parent]]);
				
				distToH[H[i]] = parent;
				distToH[H[parent]] = i;
				int tmp = H[i];
				H[i] = H[parent];
				H[parent] = tmp;

				System.out.print("distToH[" + i + "]:" + distToH[i] + ", H[distToH[" + i + "]]:" + H[distToH[i]] + "\n");
				System.out.println("after swap i:" + i + ", parent:" + parent);
				System.out.println("after swap dist[H[i]]:" + dist[H[i]] + ", dist[H[parent]]:" + dist[H[parent]]);
				
				i = parent;		
				parent = i / 2;
				
				
			}
	
		return;
	}
	
	//siftDown(H, 0, dist);
	private static void siftDown(int[] H, int i, int[] dist){
		int left  = 2*i;
		//System.out.println("left of " + i + ": " + left);
		int right = 2*i + 1;
		//System.out.println("right of " + i + ": " + right);
		//System.out.println("size: " + size);
		int min = i;
		if ((left >= size) && (right >= size))
			return;
		//consider both thread number and next free time in moving
		if (left < size) {			
			if (dist[H[min]] > dist[H[left]]){
				min = left;
				//System.out.println("left < size, > left, min= " + min);
			}
			
		}		
		
		if (right < size) {
			if (dist[H[min]] > dist[H[right]]){
				min = right;
				//System.out.println("right < size, > right, min= " + min);
			}
			
		}
						
		if (min != i){
			//System.out.println("min(" + min + ") !" + "= i(" + i + ")");
			distToH[H[i]] = min;
			distToH[H[min]] = i;
			
			int tmp = H[i];
			H[i] = H[min];
			H[min] = tmp;

			
			siftDown(H, min, dist);
			}
			//System.out.println("min: " + min);	
		return;
	}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n + 1];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 1; i <= m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x].add(y);
            cost[x].add(w);
        }
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        System.out.println(distance(adj, cost, x, y));
    }
}

