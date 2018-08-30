#include <iostream>
#include <vector>
#include <algorithm>
#include <memory>
#include <deque>
#include <cmath>


using std::vector;
using std::cin;
using std::cout;
using std::deque;
using std::endl;

static int infinite = 1000001;

class FlowGraph {
public:
    struct Edge {
        int from, to, capacity, flow;
    };
	
	

private:
    /* List of all - forward and backward - edges */
    vector<Edge> edges;
	vector<Edge> Gf_edges;

    /* These adjacency lists store only indices of edges in the edges list */
    vector<vector<size_t> > graph;
	
	
public:
    explicit FlowGraph(size_t n): graph(n) {}

    void add_edge(int from, int to, int capacity) {
        /* Note that we first append a forward edge and then a backward edge,
         * so all forward edges are stored at even indices (starting from 0),
         * whereas backward edges are stored at odd indices in the list edges */
        Edge forward_edge = {from, to, capacity, 0};
        Edge backward_edge = {to, from, 0, 0};
		
		graph[from].push_back(edges.size());
        edges.push_back(forward_edge);		
		Gf_edges.push_back(forward_edge);
        
		graph[to].push_back(edges.size());
        edges.push_back(backward_edge);
		Gf_edges.push_back(backward_edge);
    }

    size_t size() const {
        return graph.size();
    }
	
	int edgeSize() const {
		return edges.size();
	}

    const vector<size_t>& get_ids(int from) const {
        return graph[from];
    }

    const Edge& get_Gf_edge(size_t id) const {
        return Gf_edges[id];
    }
	
	const Edge& get_edge(size_t id) const {
        return edges[id];
    }

	int sizeofFlow(int from){
		const vector<size_t> node = get_ids(from);
			int flow=0;
			for (auto const & ele:node){				
				if((get_edge(ele)).flow>0)
					flow+=(get_edge(ele)).flow;
			}
			return flow;
	}
	
    void add_flow(size_t id, int flow) {//added flow should always be non-negative even for originally backword edges
        /* To get a backward edge for a true forward edge (i.e id is even), we should get id + 1
         * due to the described above scheme. On the other hand, when we have to get a "backward"
         * edge for a backward edge (i.e. get a forward edge for backward - id is odd), id - 1
         * should be taken.
         *
         * It turns out that id ^ 1 works for both cases. Think this through! */
        edges[id].flow += flow;
        edges[id ^ 1].flow -= flow;		
		
		
    }
	
	void computeGf(vector<size_t> augmentingPath){
		vector<size_t>::const_iterator iter;
		for (iter=augmentingPath.begin(); iter!=augmentingPath.end(); ++iter){
			size_t i = (*iter/2)*2;	//To get the forward edge # in Gf, correct for both even (using the original forward edge) and odd (using the original backword edge) indices		
			Gf_edges[i].capacity = edges[i].capacity - edges[i].flow; // the edge[].capacity will be unchanged as reference 
			
			//cout<<"updated Gf_edge"<<i<<".capacity:"<< Gf_edges[i].capacity<<", "<<endl;
			Gf_edges[i+1].capacity=abs(edges[i+1].flow);
			//cout<<"updated Gf_edge"<<(i+1)<<".capacity:"<< Gf_edges[i+1].capacity<<", "<<endl;
				
		}
		
	}
	
	void printEdgeFlow(){
		for(size_t i=0; i<edgeSize(); ++i){
			cout<<"flow_on_edge"<<i<<":"<<(get_edge(i)).flow<<endl;
		}
	}
		
	void printGfEdgeCapacity(){
		for(size_t i=0; i<edgeSize(); ++i){
			cout<<"capacity_on_edge"<<i<<":"<<(get_Gf_edge(i)).capacity<<endl;
		}
	}
	
	
	vector<size_t> BFS(int s, int t){
		//write your code here
		//cout<<"s: "<<s<<", t: "<<t<<endl;
			
		int vertex, w;
		//vector<vector<size_t> > adj
		int n = size();
		

		//for(size_t i=0; i<graph.edgeSize(); ++i){
			//cout<<"edge"<<i<<":"<<(graph.get_edge(i)).from<<","<<(graph.get_edge(i)).to<<endl;
		//}
			
		
		vector<int> dist(n, infinite);
		vector<int>::iterator distIter;
		
		//cout<<"dist[]: ";
		//for(const auto & ele:dist)
			//cout<<ele<<", ";
		//cout<<endl;	
		
		vector<size_t> prevE(edgeSize(),0); // store the edge that connects the current node to the previous node on the path
		vector<size_t> pathEdges; // store edge numbers of the s-t path		
		deque<int> Q; //queue of vertex for BFS 
		 
		dist[s] = 0; // the distance to the starting point itself is zero
		
		Q.push_back(s); // the source is the first vertex to be explored
		while (!Q.empty()) { // if queue is not empty
			vertex = Q.front(); // retrieve and return the front node		
			Q.pop_front(); // remove the front node
			//cout<<"vertex:"<<vertex<<endl;
			const vector<size_t>& node = get_ids(vertex); // the address of the sub-vector that stores the numbers of out-going edges of the vertex		
			for (auto const & ele:node){ // BFS explores all the outgoing edges of the node
				Edge edge = get_Gf_edge(ele); // get Edge element belonging to residual network
				//cout<<"edge" << ele <<".capacity: " << edge.capacity<<endl;
				if(edge.capacity>0){	// if the capacity is zero, the edge won't be considered
					w = edge.to; // the downstream  node on that edge 
					//cout<<"w: "<<w<<endl;
					if (dist[w] == infinite){ // if the downstream node hasn't been discovered before
						Q.push_back(w);  // push it into the queue to be explored later
						//cout<<"Q.push_back done with node "<< w <<endl;
						
						dist[w] = dist[vertex]+1 ; //update the distance (from source to the current position)
						//cout<<"update dist done"<<endl;
						prevE[w] = ele; // register the connecting edge to this discovered node
						//cout<<"update prevE done"<<endl;
					}
					if (w==t){// if the sink has been reached for the first time, meaning path is found
						int u = w;
						while(u!=s){
							pathEdges.push_back(prevE[u]);			
							u=(get_Gf_edge(prevE[u])).from;
						}	
						return pathEdges;
						//break;
					}					
				}				
			}
		}
		return pathEdges;
	}


	int max_flow(int from, int to) {
		int flow = 0;
		/* your code goes here */
		
		int edge_count = edgeSize();
		
		int g = 1;
		vector<size_t> augmentingPath; //store edges on s-t pathto be updated
		vector<size_t>::const_iterator iter; 
		//int counter=0;
		while(1){
			computeGf(augmentingPath); //update the residual network
			//cout<<"computeGf done"<<endl;
			augmentingPath=BFS(from, to); // find the shortest s-t path
			//cout << "augmentingPath: ";
			//for (const auto &ele :augmentingPath)
			//	cout<<ele<<", ";
			//cout<<endl;
			vector<int> augmentingFlow; 
			if (!augmentingPath.empty()){			
				for(auto const & edgeNumber:augmentingPath) //form the capacity vector along the path
					augmentingFlow.push_back(abs((get_Gf_edge(edgeNumber)).capacity));
				auto g = min_element(augmentingFlow.begin(), augmentingFlow.end(),[](const int &a, const int &b){return a < b;}); //find the minimum
				//cout<<"g flow: "<<*g<<endl;
				for (iter=augmentingPath.begin(); iter!=augmentingPath.end(); ++iter){ //add flow with the value of the minimum capacity
					//cout<<"*iter:"<<*iter<<endl;
					add_flow(*iter,*g);
				}
				//cout<<"add_flow done"<<endl;
			}
			else{ //if no path is found	
				//cout<<"augmentingPath.empty() = "<< augmentingPath.empty() << endl;
				//graph.printGfEdgeCapacity();
				return sizeofFlow(from);
			}
			
			//graph.printEdgeFlow();
			
			//++counter;
			
				
			
		}		
		
		//printGfEdgeCapacity();
		
		return sizeofFlow(from);
	}	
	
};


class MaxMatching {
 public:
  void Solve() {
    vector<vector<bool>> adj_matrix = ReadData();
    vector<int> matching = FindMatching(adj_matrix);
    WriteResponse(matching);
	
  }

 private:
 
 FlowGraph* graph;
 
  vector<vector<bool>> ReadData() {
    int num_left, num_right;
    cin >> num_left >> num_right;
    vector<vector<bool>> adj_matrix(num_left, vector<bool>(num_right));
    for (int i = 0; i < num_left; ++i)
      for (int j = 0; j < num_right; ++j) {
        int bit;
        cin >> bit;
        adj_matrix[i][j] = (bit == 1);
      }
    return adj_matrix;
  }

  void WriteResponse(const vector<int>& matching) {
    for (int i = 0; i < matching.size(); ++i) {
      if (i > 0)
        cout << " ";
      if (matching[i] == -1)
        cout << "-1";
      else
        cout << (matching[i] + 1);
    }
    cout << "\n";
  } 
  
 /*  FlowGraph* MakeGPrime(const vector<vector<bool>>& adj_matrix){
    int vertex_count = adj_matrix.size();
	int edge_count=adj_matrix[0].size();
	int sink = vertex_count + edge_count + 1;
	int total_vertex_count = vertex_count+2;
    graph = new FlowGraph(total_vertex_count);   
	
	for(int i =1; i<=vertex_count; ++i)
		graph.add_edge(0, i, 1);
	
	for (int i = 0; i < vertex_count; ++i)
		for(int j=0; j< edge_count; ++j )
			if(adj_matrix[i][j]){
				graph.add_edge(i+1, j+1+vertex_count, 1);	
			} 
    for(int i =0; i<edge_count; ++i)
		graph.add_edge(i+1+vertex_count, sink, 1);	
	
	
    return graph;
  } */
  

  
  
  vector<int> FindMatching(const vector<vector<bool>>& adj_matrix) {
    // Replace this code with an algorithm that finds the maximum
    // matching correctly in all cases.
	int num_left = adj_matrix.size();
    int num_right = adj_matrix[0].size();
	int sink = num_left + num_right + 1;
	int total_vertex_count = sink + 1;
    graph = new FlowGraph(total_vertex_count);   
	
	for(int i =1; i<=num_left; ++i)
		graph->add_edge(0, i, 1);
	
	for (int i = 0; i < num_left; ++i)
		for(int j=0; j< num_right; ++j )
			if(adj_matrix[i][j]){
				graph->add_edge(i+1, j+1+num_left, 1);	
			} 
    for(int i =0; i<num_right; ++i)
		graph->add_edge(i+1+num_left, sink, 1);	
	
	//for(size_t i=0; i<graph->edgeSize(); ++i)
	//	cout<<"edge"<<i<<":"<<(graph->get_edge(i)).from<<","<<(graph->get_edge(i)).to<<endl;
		
	
	
	vector<int> matching(num_left, -1);
	
	int maxflow = graph->max_flow(0, sink);
	
	//cout<<"maxflow:"<<maxflow<<endl;
	
	//graph->printEdgeFlow();
	
	//for(int i=0; i<graph.graph[0].size(); ++i)
	const vector<size_t> node = graph->get_ids(0); //get the source node	
	int left_node, right_node;
	for (auto const & ele:node){		
		if((graph->get_edge(ele)).flow>0){
			left_node=(graph->get_edge(ele)).to; // find every left node that has a nonzero flow		
			//cout<<"left_node:"<<left_node<<" ";
			for(auto const & subele:graph->get_ids(left_node)) 
				if( (graph->get_edge(subele)).flow > 0 ){
					right_node = graph->get_edge(subele).to; // find the right node that has nonzero flow
					//cout<<"right_node:"<<right_node<<" "<<"edge"<<subele<<": "<<endl;
					matching[left_node-1] = right_node - num_left - 1;
					break;
				}
		}
	}	
		
    
    
    //vector<bool> busy_right(num_right, false);
    /* for (int i = 0; i < num_left; ++i)
      for (int j = 0; j < num_right; ++j)
        if (matching[i] == -1 && !busy_right[j] && adj_matrix[i][j]) {
          matching[i] = j;
          busy_right[j] = true;
        } */
    delete graph;
	return matching;
  }
};






int main() {
  std::ios_base::sync_with_stdio(false);
  MaxMatching max_matching;
  max_matching.Solve();
  return 0;
}
