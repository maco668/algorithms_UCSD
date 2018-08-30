#include <bits/stdc++.h>
#include <sys/resource.h>
using namespace std;

struct Clause {
    int firstVar;
    int secondVar;
};



struct TwoSatisfiability {
    int numVars;
    vector<Clause> clauses;
	vector<vector<int> > implgraph, graphR;


    TwoSatisfiability(int n, int m) :
        numVars(n),
        clauses(m)
    {  }
	
	int xPos(const int & x){
		return (2*x);
	}
	
	int xnotPos(const int & x){
		return (2*x+1);
	}
	
	int revPos(const int & x){
		if(x%2 ==0)
			return (x/2);
		else
			return (-1*x/2);
	}
	
	void printG(const vector<vector<int>> & G){
		int row_size = G.size();
		//std::cout.precision(PRECISION);
		//cout<<fixed;
		for (int row = 0; row < row_size; ++row){
			cout<<row<<" ";
			for(int column = 0; column < G[row].size(); ++column){
				//int realNum = revPos(G[row][column]);
				//cout <<G[row][column]<<" ";
				cout<<G[row][column]<<" ";
			}				
			cout<<endl;
		}
		cout<<"************"<<endl;
	}
	
	vector<vector<int>> SCC(){
	//const vector<vector<int> > &implgraph, 
	//const vector<vector<int> > &graphR
	//){
		vector<bool> visited; // to tell whether visited or not
		vector<int> order, pre, post;
		vector<vector<int>> CCnum; // stores SCC 
		int cc; // number of found SCC
		int n = implgraph.size();
		int clock = 1; // for setting pre-visit and post-visit order(not needed here)
		for(int i=0; i<n; ++i){
			visited.push_back(false);
			//CCnum.push_back(0);
			pre.push_back(0);
			post.push_back(0);
		}		
		//cout<<"DFS start..."<<endl;
		DFS(visited, order, pre, post, clock);	
		//cout<<"DFS finish..."<<endl;
		//cout<<"visited[] after DFS:";
		for(int i=0; i<n; ++i){
			//cout<<visited[i]<<" ";
			visited[i]=false;
		}
		//cout<<endl;
		//cout<<"visited[] after refresh:";
		//for(auto ele:visited)
		//	cout<<ele<<" ";
		//cout<<endl;
		
		for(int i = n-1; i>=0; --i){
			int vertex = order[i];
			if(!visited[vertex]){
				CCnum.push_back({});
				Explore(visited, CCnum, vertex);
			}
		}
		/* cout<<"SCC begin:"<<endl;
		for(auto ele: CCnum){
			for(auto subele:ele)
				cout<<subele<<" ";
			cout<<endl;
		}
		cout<<"SCC end"<<endl; */
		
		return CCnum;
	}
	
	void DFS(
	//const vector<vector<int> > &graphR,
	vector<bool> &visited,
	vector<int> &order, 
	vector<int> &pre, 
	vector<int> &post, 
	int &clock	
	){
		//cc=0;
		for(int i=0; i<graphR.size(); ++i){
			if(!visited[i]){
				//++cc;
				ExploreR(visited, order, pre, post, i, clock);
			}			
		}		
	}
	
	void ExploreR(
	//const vector<vector<int> > &adj,
	vector<bool> &visited,
	vector<int> &order, 
	vector<int> &pre, 
	vector<int> &post, 
	const int &vertex,
	int &clock	
	){		
		visited[vertex] = true;
		
		//CCnum[vertex] = cc;
		previsit(pre, vertex, clock);
		for(int i=0; i<graphR[vertex].size(); ++i){
			int w = graphR[vertex][i];
			if(!visited[w])
				ExploreR(visited, order, pre, post, w, clock);
		}
		postvisit(post, vertex, clock);
		order.push_back(vertex);
	}
	
	void Explore(
	//const vector<vector<int> > &adj, 
	vector<bool> &visited, 
	vector<vector<int>> &CCnum, 
	const int &vertex
	){
		visited[vertex] = true;
		CCnum[CCnum.size()-1].push_back(vertex);
		for(int i=0; i<implgraph[vertex].size(); ++i){
			int w = implgraph[vertex][i];
			if(!visited[w])
				Explore(visited, CCnum, w);
		}
	}
	
	void previsit(vector<int> &pre,const int &vertex, int &clock){
		pre[vertex]=clock;
		++clock;
		//cout<<"preClock:"<<clock<<endl;
	}
	
	void postvisit(vector<int> &post, const int &vertex, int &clock){
		post[vertex]=clock;
		++clock;
		//cout<<"postClock:"<<clock<<endl;
		
	}
	
	
	void createImplicationGraph(){
	
	//vector<Clause> &clauses){
		
		int cclause = clauses.size();
		int edge1_from, edge1_to, edge2_from, edge2_to;
		
		
		//assign space for each variable x and xnot
		for(int i=0; i<numVars; ++i){
			implgraph.push_back({});
			implgraph.push_back({});
			graphR.push_back({});
			graphR.push_back({});
		}
		//cout<<"size of implgraph:"<<implgraph.size()<<endl;
		
		
		for(int i=0; i<cclause; ++i){//register edges
			int v1=clauses[i].firstVar;			
			int v2 = clauses[i].secondVar;			
			//cout<<"v1:"<<v1<<" v2:"<<v2<<endl;
			
			
			if(v1>0){
				edge1_from = xnotPos(v1-1);
				edge2_to = xPos(v1-1);
			}
			else{// negative means negation
				edge1_from = xPos(abs(v1)-1); //double negation = positive
				edge2_to = xnotPos(abs(v1)-1);
			}			
			
			if(v2>0){
				edge1_to = xPos(v2-1);
				edge2_from = xnotPos(v2-1);
			}else{
				edge1_to = xnotPos(abs(v2)-1);
				edge2_from = xPos(abs(v2)-1);
			}
			
			//cout<<"edge1_from:"<<edge1_from<<" edge1_to:"<<edge1_to<<" edge2_from:"<<
			//edge2_from<<" edge2_to:"<<edge2_to<<endl;
			
			implgraph[edge1_from].push_back(edge1_to);
			implgraph[edge2_from].push_back(edge2_to);
			//cout<<"before graphR"<<endl;
			graphR[edge1_to].push_back(edge1_from);
			graphR[edge2_to].push_back(edge2_from);
			//cout<<"after graphR"<<endl;
		}
		
	}

	
    bool isSatisfiable(vector<int>& result) {
        // This solution tries all possible 2^n variable assignments.-
        // It is too slow to pass the problem.
        // Implement a more efficient algorithm here.
		
		
		int n=result.size();
		//vector<bool> assigned(2*n);
		//vector<int> vertices(2*n);
		vector<bool> checked(2*n);
		
		createImplicationGraph();
		//createImplicationGraph(implgraph, graphR, clauses);
		//cout<<"graph creation finished"<<endl;
		//cout<<"implication graph:"<<endl;
		//printG(implgraph);	
		//cout<<"reverse implication graph:"<<endl;
		//printG(graphR);
		
		vector<vector<int>> scc = SCC();
		//cout<<"SCC finished"<<endl;
		//cout<<"scc.size:"<<scc.size()<<endl;
		for(int i=0; i<scc.size(); ++i){
			//cout<<"scc["<<i<<"].size:"<<scc[i].size()<<endl;	
			if (scc[i].size() >= 2){//scc with a single variable automatically satisfies
				for(int j=0; j<scc[i].size(); ++j){					
					int x = scc[i][j];		
					//cout<<"vertex "<<x<<":"<<checked[x]<<endl;					
					if(!checked[x]){
						int xnot = x^1;
						checked[x] = true;
						checked[xnot] = true;
						//vertices[x] = 1;
						//vertices[xnot] = 0;
						if(!(x%2)) // if x is an original variable
							result[x/2] = 1;
						else
							result[x/2] =0; //if x is negation of a variable
						//cout<<"x:"<<x<<" xnot:"<<xnot<<endl;
						for(int k=j+1; k<scc[i].size(); ++k ){
							//cout<<"scc["<<i<<"]["<<k<<"]:"<<scc[i][k]<<endl;
							if(xnot == scc[i][k])
								return false;
						}
					}
				}
			}else{
				for(int j=0; j<1; ++j){
					int x=scc[i][j];
					if(!checked[x]){
						//vertices[x] = 1;
						//vertices[x^1] = 0;
						checked[x] = true;
						checked[x^1] = true;
						if(!(x%2))
							result[x/2] = 1;
						else
							result[x/2] =0;
					}					
				}		
			}
		}
		//cout<<"assignment finished"<<endl;
		
		// in reverse order
		/* for(int i=0; i<scc.size(); ++i){			
			for(int j=0; j<scc[i].size(); ++j){
				int x=scc[i][j];
				if(!assigned[x]){
					vertices[x] = 1;
					vertices[x^1] = 0;
					assigned[x] = true;
					assigned[x^1] = true;
					if(!(x%2))
						result[x/2] = 1;
					else
						result[x/2] =0;
				}
					
			}			
		} */
		
		//cout<<"vertices:"<<endl;
		//for(int i=0; i<vertices.size(); ++i)
		//	cout<<"v["<<i<<"]:"<<vertices[i]<<endl;
		
		//for(int i=0; i<result.size(); ++i)
		//	result[i] = vertices[2*i];
		
		return true;
		
		
			
		
		/* for (int mask = 0; mask < (1 << numVars); ++mask) {
            for (int i = 0; i < numVars; ++i) {
                result[i] = (mask >> i) & 1;
            }

            bool formulaIsSatisfied = true;

            for (const Clause& clause: clauses) {
                bool clauseIsSatisfied = false;
                if (result[abs(clause.firstVar) - 1] == (clause.firstVar < 0)) {
                    clauseIsSatisfied = true;
                }
                if (result[abs(clause.secondVar) - 1] == (clause.secondVar < 0)) {
                    clauseIsSatisfied = true;
                }
                if (!clauseIsSatisfied) {
                    formulaIsSatisfied = false;
                    break;
                }
            }

            if (formulaIsSatisfied) {
                return true;
            }
        }
        return false; */
    }
};

int main() {
    ios::sync_with_stdio(false);
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
	//int max=1000000;
	int min=1;
    int n, m;
    cin >> n >> m;
	
	//m=rand()%max+1;
	//cout<<"n:"<<n<<" m:"<<m<<endl;
	TwoSatisfiability twoSat(n, m);
	
	
	//TwoSatisfiability twoSat(n, m);
    for (int i = 0; i < m; ++i) {
        //cin >> twoSat.clauses[i].firstVar >> twoSat.clauses[i].secondVar;
		int v1 = min + (rand()% static_cast<int>(n-min+1)); //+1 because modulus can mostly 1 smaller than denominator
		int v2 = min + (rand()% static_cast<int>(n-min+1));
		int s1 = rand()% static_cast<int>(2);
		int s2 = rand()% static_cast<int>(2);
		
		if(s1==0)		
			v1 = -1*v1;
		twoSat.clauses[i].firstVar = v1;
		
		if(s2==0)
			v2 = -1*v2;
		twoSat.clauses[i].secondVar = v2;
		
		//cout<<v1<<" "<<v2<<endl;	
		
    }

    vector<int> result(n);
    if (twoSat.isSatisfiable(result)) {
        cout << "SATISFIABLE" << endl;
/*         for (int i = 1; i <= n; ++i) {
            if (result[i-1]) {
                cout << i;
            } else {
                cout << -i;
            }
            if (i < n) {
                cout << " ";
            } else {
                cout << endl;
            }
        } */
    } else {
        cout << "UNSATISFIABLE" << endl;
    }

    return 0;
}
