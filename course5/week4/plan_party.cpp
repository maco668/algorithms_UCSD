#include <iostream>
#include <vector>
#include <sys/resource.h>

using namespace std;

struct Vertex {
    int weight;
    vector <int> children;
	
};
typedef std::vector<Vertex> Graph;
typedef std::vector<size_t> Sum;
const size_t INFINITY = -1;

Graph ReadTree() {
    int vertices_count;
    std::cin >> vertices_count;
	
    Graph tree(vertices_count);

    for (int i = 0; i < vertices_count; ++i)
        std::cin >> tree[i].weight;
	
	
	if(vertices_count>1)
		for (int i = 1; i < vertices_count; ++i) {
			int from, to, weight;
			std::cin >> from >> to;
			tree[from - 1].children.push_back(to - 1);
			tree[to - 1].children.push_back(from - 1);
		}

    return tree;
}

size_t dfs(const Graph &tree, Sum &maxW, size_t vertex, size_t parent) {
    
	if (maxW[vertex] == INFINITY){
		//cout << "tree["<<vertex<<"].children.size:"<<tree[vertex].children.size()<<endl;
		if (tree[vertex].children.size()==0 )
			maxW[vertex] = tree[vertex].weight;
		else{
			size_t m1 = tree[vertex].weight;
			size_t m0 = 0;
			
			for (size_t i=0; i<tree[vertex].children.size(); ++i){				
				size_t u = tree[vertex].children[i];
				if((u!=parent) && (tree[u].children.size()!=0)){
					//cout<<"u:"<<u<<" ";
					//cout<<"w: ";
					for (size_t j=0; j<tree[u].children.size(); ++j ){
						size_t w = tree[u].children[j];
						if(w!=vertex){							
							m1 += dfs(tree, maxW, w, u );
							//cout<<w<<" ";
						}
					}
					//cout<<endl;
				}				
			}
			for (size_t u : tree[vertex].children){
				if(u!=parent){
					m0 += dfs(tree, maxW, u, vertex );
				}
			}
			//cout<<"m1:"<<m1<<" m0:"<<m0<<endl;
			maxW[vertex] = max(m1, m0 );
		}
	}
	return maxW[vertex];

    // This is a template function for processing a tree using depth-first search.
    // Write your code here.
    // You may need to add more parameters to this function for child processing.
	
	
	
}

int MaxWeightIndependentTreeSubset(const Graph &tree) {
    size_t size = tree.size();
	//cout<<"size:"<<size<<endl;
	
	Sum maxW(size, INFINITY);
	
	/* for(int i=0; i<size; ++i){
		cout<<"node "<<i+1<<":";
		for(int j=0; j<tree[i].children.size(); ++j )
			cout<<tree[i].children[j]+1<<" ";
		cout<<endl;
	} */
	
    if (size == 0)
        return 0;
	
	
	
	//size_t maxfun = 10;
	size_t maxfun = dfs(tree, maxW, 0, -1);
	//for(int i=1; i<size; ++i)		
	//	dfs(tree, maxW, i, i-1);
    // You must decide what to return.
    return maxfun;
}

int main() {
    // This code is here to increase the stack size to avoid stack overflow
    // in depth-first search.
    const rlim_t kStackSize = 64L * 1024L * 1024L;  // min stack size = 64 Mb
    struct rlimit rl;
    int result;
    result = getrlimit(RLIMIT_STACK, &rl);
    if (result == 0)
    {
        if (rl.rlim_cur < kStackSize)
        {
            rl.rlim_cur = kStackSize;
            result = setrlimit(RLIMIT_STACK, &rl);
            if (result != 0)
            {
                fprintf(stderr, "setrlimit returned result = %d\n", result);
            }
        }
    }

    // Here begins the solution
    Graph tree = ReadTree();
	//cout<<"ReadTree finished"<<endl;
    int weight = MaxWeightIndependentTreeSubset(tree);
    std::cout << weight << std::endl;
    return 0;
}
