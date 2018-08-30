#include <algorithm>
#include <cassert>
#include <cstdio>
#include <iostream>
#include <string>
#include <vector>

using namespace std;

using std::vector;
using std::string;

//typedef map<char, int> edges;
//typedef vector<Node> trie;

int const Letters =    4;
int const NA      =   -1;


class Node
{
	public:
	int next [Letters];
	string cs;

	Node ()
	{
		fill (next, next + Letters, NA);
	}

	bool isLeaf () const
	{
	    return (next[0] == NA && next[1] == NA && next[2] == NA && next[3] == NA);
	}
	
};

int letterToIndex (char letter)
{
	switch (letter)
	{
		case 'A': return 0; break;
		case 'C': return 1; break;
		case 'G': return 2; break;
		case 'T': return 3; break;
		default: assert (false); return -1;
	}
}

vector<Node> build_trie(const vector<string> & patterns) {
  vector<Node> trie;
  //edges::iterator it;
  // write your code here
  int currentNode;
  char currentSymbol;
  string cs = "";
  
  Node node;
  trie.push_back(node);
  
  std::cout<< "build_trie func: patterns[0] size = " << patterns[0].size()<<std::endl;
  for ( size_t i = 0; i < patterns.size(); i++){
	  currentNode = 0;
	  
	  for ( size_t j = 0; j < patterns[i].size(); j++){
		currentSymbol = patterns[i][j];
		
		//edges::iterator it = t[currentNode].find(currentSymbol); 
		std::cout<< "currentSymbol = " << currentSymbol <<std::endl;
		std::cout<< "currentNode = " << currentNode <<std::endl;
		Node *E = & trie[currentNode];
		int pos = letterToIndex(currentSymbol);
		std::cout<< "POS = " << pos <<std::endl;
		std::cout<< "E->next[pos] = " << E->next[pos] <<std::endl;
		if( E->next[pos] != NA )
			currentNode = E->next[pos];
		else{
			Node e;
			
			e.cs = E->cs + currentSymbol;
			std::cout<< "e.cs = " << e.cs <<std::endl;
			
			std::cout<< "before pushback " <<std::endl;
			trie.push_back(e);	
			std::cout<< "after pushback " << currentNode <<std::endl;
			
			int newNode = trie.size() - 1;
			
			E->next[pos] = newNode;
			currentNode = newNode;
			std::cout<< "currentNode = " << currentNode <<std::endl;
		}
	  }  
  }
  std::cout<< "trie.size() = " << trie.size() <<std::endl;
  for (size_t i = 0; i < trie.size(); ++i) {
    
	int j = 0;
	while ( (trie[i].next[j] != NA) && (j < Letters) ) {
      std::cout << i << "->" << trie[i].next[j] << ":" << j << "\n";
	  j++;
    }
  }
  
  return trie;	
}




bool prefixTrieMatching(const string& text, const vector<Node>& trie, const int& startpos){
	

	int iter2=startpos;
	char symbol = text[iter2];
	Node v = trie[0];
	
	while (true){
		if (v.isLeaf())
			return true;
		
		else if ( (iter2 < text.size()) && (v.next[letterToIndex(symbol)] !=NA) ){
			int w = v.next[letterToIndex(symbol)];
			iter2++;
			symbol = text[iter2];
			v = trie[w];				
		}
		else
			return false;
			
	}
	
}

vector <int> solve (const string& text, int n, const vector <string>& patterns)
{
	vector <int> result;

	// write your code here
	std::cout<<"solve func: patterns size = " << patterns.size() << std::endl;
	vector<Node> t = build_trie(patterns);
	
	int i = 0;
	while ( i < text.size()){
		if (prefixTrieMatching(text, t, i)){
			result.push_back(i);
			i++; 
		}	
		
	}
	
	return result;
}

int main (void)
{
	string t;
	cin >> t;

	int n;
	cin >> n;

	vector <string> patterns (n);
	for (int i = 0; i < n; i++)
	{
		cin >> patterns[i];
	}
	

	vector <int> ans;
	ans = solve (t, n, patterns);

	for (int i = 0; i < (int) ans.size (); i++)
	{
		cout << ans[i];
		if (i + 1 < (int) ans.size ())
		{
			cout << " ";
		}
		else
		{
			cout << endl;
		}
	}

	return 0;
}
