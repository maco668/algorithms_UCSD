#include <bits/stdc++.h>
using namespace std;

struct Edge {
    int from;
    int to;
};

struct ConvertHampathToSat {
    int numVertices;
    vector<Edge> edges;
	



    ConvertHampathToSat(int n, int m) :
        numVertices(n),
        edges(m)
    { }

    
	int varnum(int i, int j){// i is the vertex number, j is the position number (0-based)
		return (i*numVertices + j + 1);
	}
	
	int negvarnum(int i, int j){
		return -(i*numVertices + j + 1);
	}
	
/* 	void ANDgate(vector<vector<int>> &clauses, int h1, int h2, int & varcount){
		++h1; // change to 1-based
		++h2;
		int g = ++varcount; // add one new variable
		clauses.push_back({h1, -g}); 
		clauses.push_back({h2, -g});
		clauses.push_back({-h1, -h2, g});
	} */
	
	
	pair<vector<vector<int>>, vector<vector<int>>> FindSubset(int n, int r){
		vector<vector<int>> found; // combinations with requested number of elements
		vector<vector<int>> less; // combinations with less number of requested elements 
		vector<vector<int>> lessBefore;
		//vector<vector<int>> smaller;
		int numFound=0;
		if(n==1){
			vector<int> empty;
			vector<int> first={1};

			if(r>1){
				less.push_back(empty);
				less.push_back(first);
			}else if(r==1){
				found.push_back(first);
				less.push_back(empty);				
			}else if(r==0){
				found.push_back(empty);
			}
			return {found, less};
		}
		else{
			pair<vector<vector<int>>, vector<vector<int>>> solution = FindSubset(n-1, r);
			found = solution.first;
			lessBefore = solution.second;
			vector<vector<int> >::iterator It; 
			
			less = lessBefore; // 
			for(It = less.begin(); It!=less.end(); ++It)
				(*It).push_back(n);
			
			for(It=less.begin(); It!=less.end(); ++It){//filter out the found and the less sets
				if((*It).size() == r)
					found.push_back(*It);
				if((*It).size() < r)
					lessBefore.push_back(*It);
			}
			
			//lessBefore.insert(lessBefore.end(), less.begin(), less.end());
			
			
			return {found, lessBefore};
		}	
	}
	
	
	vector<vector<int>> Combination2(int n, int m){
		pair<vector<vector<int>>, vector<vector<int>>> solution = FindSubset(n, m); // +1 is for the added very bignumber row

		return solution.first;
	}
	
    void printEquisatisfiableSatFormula() {
        // This solution prints a simple satisfiable formula
        // and passes about half of the tests.
        // Change this function to solve the problem.
        
		vector<vector<int>> clauses;
		vector<vector<int>> adjMap;
		
		for(int i=0; i<numVertices; ++i)
			adjMap.push_back({});
		
		
		// 1) each vertex has been positioned at least once
		for(int i=0; i<numVertices; ++i){
			vector<int> inPosition;
			for(int j=0; j<numVertices; ++j)
				inPosition.push_back(varnum(i,j));
			clauses.push_back(inPosition);
		}
		
		// 2) each vertex is only associated with exactly one position
		vector<vector<int>> subset = Combination2(numVertices, 2);
		//cout<<"combinations of "<<numVertices<<" choose 2:"<<subset.size()	<<endl;
		/*for(auto ele:subset){
			for(auto subele:ele)
				cout<<subele<<" ";
			cout<<endl;
		}
		cout<<"*******************"<<endl; */
		for(int i=0; i<numVertices; ++i){
			for(int j=0; j<subset.size(); ++j){
				int a = negvarnum(i,subset[j][0]-1);
				int b = negvarnum(i,subset[j][1]-1);
				clauses.push_back({a, b});
			}			
		}
		
		// 3) each position only corresponds to exactly one vertex, this doesn't cover situation when no vertex is related to this position
		for(int j=0; j<numVertices; ++j){// position
			for(int i=0; i<subset.size(); ++i){ // vertex numbers
				int a = negvarnum(subset[i][0]-1, j);
				int b = negvarnum(subset[i][1]-1, j);
				clauses.push_back({a, b});
			}			
		}
		
		//cout<<"finished constraint 3"<<endl;
		//two successive vertices must be connected by an edge	
		for(auto ele: edges){
			adjMap[(ele.from - 1)].push_back(ele.to - 1);
			adjMap[(ele.to - 1)].push_back(ele.from - 1);
		}
		//cout<<"finished adjacency matrix"<<endl;
		for(int j=0; j<(numVertices-1); ++j){
			for(int i=0; i<numVertices; ++i){
				vector<int> connect;
				connect.push_back(negvarnum(i,j));
				for(auto ele:adjMap[i])
					connect.push_back(varnum(ele, j+1));
				clauses.push_back(connect);
			}
		}
		
		 int varcount = numVertices * numVertices;
		/*for(int i=0; i<(numVertices-1); ++i){
			vector<int> newVar;
			for(int j=0; j<edges.size(); ++j){
				int g1 = ++varcount;
				int g2= ++varcount;
				newVar.push_back(g1);
				newVar.push_back(g2);
				clauses.push_back({-g1, varnum(edges[j].from-1, i)});
				clauses.push_back({-g1, varnum(edges[j].to-1, i+1)});
				clauses.push_back({-g2, varnum(edges[j].to-1, i)});
				clauses.push_back({-g2, varnum(edges[j].from-1, i+1)});
				
			}
			clauses.push_back(newVar);				
		} */
		
		
					
	
		
		cout<<clauses.size()<<" "<<varcount<<endl;
 		for(auto ele:clauses){
			for(auto subele:ele)
				cout<<subele<<" ";
			cout<<"0"<<endl;  
		}
    }
};

int main() {
    ios::sync_with_stdio(false);

    int n, m;
    cin >> n >> m;
    ConvertHampathToSat converter(n, m);
    for (int i = 0; i < m; ++i) {
        cin >> converter.edges[i].from >> converter.edges[i].to;
    }

    converter.printEquisatisfiableSatFormula();

    return 0;
}
