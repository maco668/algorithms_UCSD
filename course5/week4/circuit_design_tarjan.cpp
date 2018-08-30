#include <bits/stdc++.h>
#include <sys/resource.h>
//Note:
// the second #include and the method in main() to increase stack size do not compile for g++ on minGW.
// To compile this program with increased stack size using g++ on minGW, comment out these two places and use the 
// following flag for g++:
 
//  g++ -pipe -o circuit_design_tarjan -std=c++14 circuit_design_tarjan.cpp -lm -Wl,--stack,52428800

using namespace std;

struct Clause {
    int firstVar;
    int secondVar;
};



struct TwoSatisfiability {
    int numVars;
    vector<Clause> clauses;
	//vector<vector<int> > implgraph, graphR, CCnum;
	//vector<int> order, pre, post;
	vector<vector<int> > implgraph, CCnum;
	vector<int> sccnum;

    TwoSatisfiability(int n, int m) :
        numVars(n),
        clauses(m),
		implgraph(2*n),
		sccnum(2*n)
    {  }
	
	
	
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
	
	void SCC(){
	//const vector<vector<int> > &implgraph, 
	//const vector<vector<int> > &graphR
	//){
		//vector<bool> visited; // to tell whether visited or not
		//vector<int> order, pre, post;
		//vector<vector<int>> CCnum; // stores SCC 
		int cc=0; // number of found SCC
		int n = implgraph.size();
		int clock = 0; // for setting pre-visit and post-visit order(not needed here)
		vector<int> stack;
		vector<int> index(n), lowlink(n);
		vector<bool> visited(n), onstack(n); // to tell whether visited or not
		
		
		for(int i=0; i<n; ++i)
			if(!visited[i])
				DFS(visited, onstack, stack, index, lowlink, i, clock, cc);
		
		
		//cout<<"DFS start..."<<endl;	
		//cout<<"DFS finish..."<<endl;
		//cout<<"visited[] after DFS:";

		//cout<<endl;
		//cout<<"visited[] after refresh:";
		//for(auto ele:visited)
		//	cout<<ele<<" ";
		//cout<<endl;
		

		/* cout<<"SCC begin:"<<endl;
		for(auto ele: CCnum){
			for(auto subele:ele)
				cout<<subele<<" ";
			cout<<endl;
		}
		cout<<"SCC end"<<endl;  */
		
		//return CCnum;
	}
	
	void DFS(
	//const vector<vector<int> > &graphR,
	vector<bool> &visited, 
	vector<bool> &onstack, 
	vector<int> &stack, 
	vector<int> &index, 
	vector<int> &lowlink,
	int &vertex,
	//vector<int> &order, 
	//vector<int> &pre, 
	//vector<int> &post, 
	int &clock,
	int &cc
	){
		int v;
		visited[vertex] = true;
		index[vertex] = clock;
		lowlink[vertex]=clock;
		++clock;
		
		stack.push_back(vertex);
		onstack[vertex] = true;
		
		for(int i=0; i<implgraph[vertex].size(); ++i){
			int w = implgraph[vertex][i];
			if(!visited[w]){
				DFS(visited, onstack, stack, index, lowlink, w, clock, cc);
				lowlink[vertex]=min(lowlink[vertex], lowlink[w]);
			}else if(onstack[w]) //it is visited before and has an edge back to an earlier vertex
				lowlink[vertex]=min(lowlink[vertex], index[w]);			
		}

		if(lowlink[vertex]==index[vertex] ){
			CCnum.push_back({});
			++cc;
			do{
				vector<int>::iterator it=stack.end();
				v = *(--it);
				CCnum[CCnum.size()-1].push_back(v);
				sccnum[v]=cc; // record which scc this vertex belongs to
				stack.pop_back();	
				onstack[v] = false;
			} while (vertex!=v);
		}
	}
	
	int xPos(const int & x){
		return (2*x);
	}
	
	int xnotPos(const int & x){
		return (2*x+1);
	}
	
	void createImplicationGraph(){
	
	//vector<Clause> &clauses){
		
		int cclause = clauses.size();
		int edge1_from, edge1_to, edge2_from, edge2_to;
		
		
		//assign space for each variable x and xnot
		//for(int i=0; i<numVars; ++i){
		//	implgraph.push_back({});
		//	implgraph.push_back({});
			//graphR.push_back({});
			//graphR.push_back({});
		//}
		//cout<<"size of implgraph:"<<implgraph.size()<<endl;
		
		
		for(int i=0; i<cclause; ++i){//register edges	

			//cout<<"v1:"<<v1<<" v2:"<<v2<<endl;
			
			
			if(clauses[i].firstVar>0){
				int v1 = clauses[i].firstVar-1;
				edge1_from = xnotPos(v1);
				edge2_to = xPos(v1);
			}
			else{// negative means negation
				int v1 = -clauses[i].firstVar-1;
				edge1_from = xPos(v1); //double negation = positive
				edge2_to = xnotPos(v1);
			}			
			
			if(clauses[i].secondVar>0){
				int v2 = clauses[i].secondVar-1;
				edge1_to = xPos(v2);
				edge2_from = xnotPos(v2);
			}else{
				int v2= -clauses[i].secondVar-1;
				edge1_to = xnotPos(v2);
				edge2_from = xPos(v2);
			}
			
			//cout<<"edge1_from:"<<edge1_from<<" edge1_to:"<<edge1_to<<" edge2_from:"<<
			//edge2_from<<" edge2_to:"<<edge2_to<<endl;
			
			implgraph[edge1_from].push_back(edge1_to);
			implgraph[edge2_from].push_back(edge2_to);
			//cout<<"before graphR"<<endl;
			//graphR[edge1_to].push_back(edge1_from);
			//graphR[edge2_to].push_back(edge2_from);
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
		//cout<<"createImplicationGraph finished"<<endl;
		//createImplicationGraph(implgraph, graphR, clauses);
		//cout<<"graph creation finished"<<endl;
		//cout<<"implication graph:"<<endl;
		//printG(implgraph);	
		//cout<<"reverse implication graph:"<<endl;
		//printG(graphR);
		
		
		SCC();
		//cout<<"SCC finished"<<endl;
		//cout<<"CCnum.size:"<<CCnum.size()<<endl;
		for(int i=0; i<CCnum.size(); ++i){
			//cout<<"CCnum["<<i<<"].size:"<<CCnum[i].size()<<endl;	
			if (CCnum[i].size() >= 2){//scc with a single variable automatically satisfies
				for(int j=0; j<CCnum[i].size(); ++j){					
					int x = CCnum[i][j];		
					//cout<<"vertex "<<x<<":"<<checked[x]<<endl;					
					if(!checked[x]){
						int xnot = x^1;
						if(sccnum[x]==sccnum[xnot])
							return false;
						else{
							checked[x] = true;
							checked[xnot] = true;
						//vertices[x] = 1;
						//vertices[xnot] = 0;
							if(!(x%2)) // if x is an original variable
								result[x/2] = 1;
							else
								result[x/2] =0; //if x is negation of a variable
							//cout<<"x:"<<x<<" xnot:"<<xnot<<endl;
						}
					}
				}
			}else{
				for(int j=0; j<1; ++j){
					int x=CCnum[i][j];
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
	//int min=1;
    int n, m;
	cin>>n>> m;

	
	//std::ifstream infile("rand400000_1000000.txt");
    //ifstream infile("rand100_1000.txt");
	//infile >> n >> m;
	
	//m=rand()%max+1;
	//cout<<endl;
	TwoSatisfiability twoSat(n, m);
	
	
	//TwoSatisfiability twoSat(n, m);
    for (int i = 0; i < m; ++i) {
        cin >> twoSat.clauses[i].firstVar >> twoSat.clauses[i].secondVar;
		//infile >> twoSat.clauses[i].firstVar >> twoSat.clauses[i].secondVar;
		/*  int v1 = min + (rand()% static_cast<int>(n-min+1)); //+1 because modulus can mostly 1 smaller than denominator
		int v2 = min + (rand()% static_cast<int>(n-min+1));
		int s1 = rand()% static_cast<int>(2);
		int s2 = rand()% static_cast<int>(2);
		
		if(s1==0)		
			v1 = -1*v1;
		twoSat.clauses[i].firstVar = v1;
		
		if(s2==0)
			v2 = -1*v2;
		twoSat.clauses[i].secondVar = v2;  */
		
		//cout<<v1<<" "<<v2<<endl;	
		
    }

    vector<int> result2(n);
    if (twoSat.isSatisfiable(result2)) {
        cout << "SATISFIABLE" << endl;
        for (int i = 1; i <= n; ++i) {
            if (result2[i-1]) {
                cout << i;
            } else {
                cout << -i;
            }
            if (i < n) {
                cout << " ";
            } else {
                cout << endl;
            }
        }
    } else {
        cout << "UNSATISFIABLE" << endl;
    }

    return 0;
}
