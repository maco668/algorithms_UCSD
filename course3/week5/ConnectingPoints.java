import java.util.*;

public class ConnectingPoints {
	
	static int infinite = 200000001;
	static int nil = -200000001;
	static int[] parent;
	static int[] rank;
	Edge[] edges;
	
	public class Edge {// edge class storing length, u, and v
		
		public double length;
		public int firstV;
		public int secondV;
		
		Edge(int i, int j) {
			this.firstV = i;
			this.secondV = j;
		}		
		
	}
	
    double minimumDistance(int[] x, int[] y) {
        double result = 0.;
        //write your code here
		int m = x.length;
		
		int Cm2 = m * (m - 1) / 2; //number of independent selections of 2 vertices 
		
		edges = new Edge[Cm2]; //array of Edge objects for undirected graph
		ArrayList<Edge> X = new ArrayList<Edge>();	

		parent = new int[m]; // disjoint sets' parent array
		rank = new int[m]; // rank arrary for path compression
		
		for (int i = 0; i < m; ++i)
			MakeSet(i);
		
		int edgeNum = 0; 
		//initialize edge objects
		for (int i = 0; i < m; ++i) {
            for (int j = i + 1; j < m; ++j) {								
				edgeNum++;
				edges[edgeNum - 1] = new Edge(i, j); 
				// create a new object, otherwise null pointer when
				// using method			
				
				edges[edgeNum - 1].length = Math.sqrt( Math.pow(x[i] - x[j], 2) + Math.pow(y[i] - y[j], 2));
				//System.out.println("adjM[" + i + "][" + j + "]" + ":" + adjM[i][j]);
				
			}
        }		
		
		/*System.out.println("before quicksort:");
		for (int i = 0; i < edges.length; ++i)
			System.out.print(edges[i].length + ", ");*/
		
		//2-way partitioning quicksort for non-decreasing order of edge lengths
		randomizedQuickSort(edges, 0, edges.length - 1);
		
		/*System.out.println("\nafter quicksort:");
		for (int i = 0; i < edges.length; ++i)
			System.out.print(edges[i].length + ", ");
		
		System.out.println();*/
		
		for (int i = 0; i < edges.length; ++i) {
			int u = edges[i].firstV; // find vertex 1
			int v = edges[i].secondV; // find vertex 2
			int pi_u = Find(u); // check head vertex of the tree
			int pi_v = Find(v);
			
			if (pi_u != pi_v) {// no cycle is formed by the edge
				X.add(edges[i]); // add the proper "next lightest edge"
				Union(u, v); //vertices now belong to the same tree, Kruskal algorithm				
			}
			
			
		}
		
		for (int i = 0; i < X.size(); ++i) // total distance
			result = result + X.get(i).length;
		
        return result;
    }
	
	void MakeSet(int i) {//initialize disjoint sets
		parent[i] = i;
		rank[i] = 0;
	}
	
	int Find(int i) { //find tree head
		if ( i != parent[i] ) // path compression
			parent[i] = Find(parent[i]);
		return parent[i];
	}
	
	void Union(int i, int j) { // attach one head node to the other
		int i_id = Find(i);
		int j_id = Find(j);		
		
		if ( i_id == j_id)
			return;		
		// merge by rank. rank means the height of the tree.
		if ( rank[i_id] > rank[j_id] )
			parent[j_id] = i_id;		 
		else{
			parent[i_id] = j_id;
			if ( rank[i_id] == rank[j_id] )//no difference b/w i and j, j is by convention
				rank[j_id] = rank[j_id] + 1;
		}
			
	}
	
	
	int partition2(Edge[] a, int l, int r) {//2-way partitioning
        Edge x = a[l]; //pivot node is now at left boundary
        int j = l; // left pointer starts from left boundary
        for (int i = l + 1; i <= r; i++) {//l, r are determined at the beginning
		// and after one partition returns the finalized pivot value position
            if (a[i].length <= x.length) {
                j++; // if a node on the left of pivot has smaller value, that means 
				// this node a[i] should be on the left of targetted pivot position.
				// therefore first move up left pointer 1, then exchange a[i] with a[j] 
				// either a[j] is a[i] or already checked to be > x.length. 
                Edge t = a[i]; 
                a[i] = a[j];
                a[j] = t;
            }
        }
        Edge t = a[l];
        a[l] = a[j];
        a[j] = t;
        return j;
    }

    void randomizedQuickSort(Edge[] a, int l, int r) {
        if (l >= r) {
            return;
        }
		
		Random rand =  new Random();
        int k = rand.nextInt(r - l + 1) + l;//pick a pivot
        Edge t = a[l];// exchange pivot with the node at left boundary
        a[l] = a[k];
        a[k] = t;
        //use partition2
        int m = partition2(a, l, r);
        randomizedQuickSort(a, l, m - 1);
        randomizedQuickSort(a, m + 1, r);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        System.out.println(new ConnectingPoints().minimumDistance(x, y));
    }
}

