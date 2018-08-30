#include <ios>
#include <iostream>
#include <vector>

using namespace std;

struct Edge {
    int from;
    int to;
};

struct ConvertGSMNetworkProblemToSat {
    int numVertices;
    vector<Edge> edges;
	int colornum=3;

    ConvertGSMNetworkProblemToSat(int n, int m) :
        numVertices(n),
        edges(m)
    {  }

	int varnum(int i, int j){// i is the vertex number, j is the color number (0-based)
		return (i*colornum + j + 1);
	}
	
	int negvarnum(int i, int j){
		return -(i*colornum + j + 1);
	}
	
	vector<vector<int>> FindSubset(int n){
		vector<vector<int>> subset1;
		vector<vector<int>> subset2;
		if(n==1){
			vector<int> empty;
			vector<int> first={1};

			subset1.push_back(empty);
			subset1.push_back(first);
			return subset1;
		}
		else{
			subset1 = FindSubset(n-1);
			subset2 = subset1;
			for(int i = 0; i <subset2.size(); ++i)
				subset2[i].push_back(n);
			subset1.insert(subset1.end(), subset2.begin(), subset2.end());
			return subset1;
		}	
	}
	
	vector<vector<int>> Combination(int n, int m){
		vector<vector<int>> allSubset = FindSubset(n); // +1 is for the added very bignumber row
		vector<vector<int>> subset;
		for(auto set : allSubset)
			if(set.size() == m)	//locate subsets of m
				subset.push_back(set);
		return subset;
	}
	
    void printEquisatisfiableSatFormula() {
        // This solution prints a simple satisfiable formula
        // and passes about half of the tests.
        // Change this function to solve the problem.
        
		vector<vector<int>> clauses;
		
		//each node has at least one color
		for(int i=0; i<numVertices; ++i){
			vector<int> hasColor;
			for(int j=0; j<colornum; ++j)
				hasColor.push_back(varnum(i,j));
			clauses.push_back(hasColor);
		}
		
		//each node only has one color
		vector<vector<int>> subset = Combination(colornum, 2);
		for(int i=0; i<numVertices; ++i){
			for(int j=0; j<subset.size(); ++j){
				int a = negvarnum(i,subset[j][0]-1);
				int b = negvarnum(i,subset[j][1]-1);
				vector<int> pair={a,b};
				clauses.push_back(pair);
			}			
		}
		
		//each edge should have heterochromatic vertices
		for(int i=0; i<edges.size(); ++i){
			for(int j=0; j<colornum; ++j){
				int a=negvarnum(edges[i].from-1, j); //make vertex number 0-based
				int b=negvarnum(edges[i].to-1, j);
				vector<int> pair={a, b};
				clauses.push_back(pair);
			}			
		}
		
		for(int i; i<clauses.size(); ++i){
			clauses[i].push_back(0);
		}
		//for(auto ele:clauses)
		//	ele.push_back(0);
		
		
		int V=varnum(numVertices-1,colornum-1);
		cout<<clauses.size()<<" "<<V<<endl;
		for(auto ele:clauses){
			for(auto subele:ele)
				cout<<subele<<" ";
			cout<<endl;
		}
    }
};

int main() {
    ios::sync_with_stdio(false);

    int n, m;
    cin >> n >> m;
    ConvertGSMNetworkProblemToSat converter(n, m);
    for (int i = 0; i < m; ++i) {
        cin >> converter.edges[i].from >> converter.edges[i].to;
    }

    converter.printEquisatisfiableSatFormula();

    return 0;
}
