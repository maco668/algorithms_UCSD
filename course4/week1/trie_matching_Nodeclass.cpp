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

	Node (string cs_1)
	{
		this->cs = cs_1;
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
  //string cs = "";
  
  Node node("");
  trie.push_back(node);
  
  //std::cout<< "build_trie func: patterns[0] size = " << patterns[0].size()<<std::endl;
  for ( size_t i = 0; i < patterns.size(); i++){
	  currentNode = 0;
	  
	  for ( size_t j = 0; j < patterns[i].size(); j++){
		currentSymbol = patterns[i][j];
		
		//edges::iterator it = t[currentNode].find(currentSymbol); 
		//std::cout<< "currentSymbol = " << currentSymbol <<std::endl;
		//std::cout<< "currentNode = " << currentNode <<std::endl;
		
		int pos = letterToIndex(currentSymbol);
		//std::cout<< "POS = " << pos <<std::endl;
		//std::cout<< "trie[currentNode].next[pos] = " << trie[currentNode].next[pos] <<std::endl;
		if( trie[currentNode].next[pos] != NA )
			currentNode = trie[currentNode].next[pos];
		else{
			Node node(trie[currentNode].cs + currentSymbol);
			
			//std::cout<< "node.cs = " << node.cs <<std::endl;
			
			//std::cout<< "before pushback " <<std::endl;
			trie.push_back(node);	
			//std::cout<< "after pushback " <<std::endl;
			
			int newNode = trie.size() - 1;
			
			trie[currentNode].next[pos] = newNode;
			currentNode = newNode;
			//std::cout<< "currentNode = " << currentNode <<std::endl;
		}
	  }  
  }
  /* std::cout<< "trie.size() = " << trie.size() <<std::endl;
  for (size_t i = 0; i < trie.size(); ++i) {
    std::cout << "trie[" << i << "] is leaf:" << trie[i].isLeaf() << std::endl;
	int j = 0;
	while ( j < Letters ) {
		std::cout<< "trie[" << i << "].next[" << j << "] = " << trie[i].next[j] <<std::endl;
		// if (trie[i].next[j] != NA)
		//	std::cout << i << "->" << trie[i].next[j] << ":" << j << std::endl; 
		j++;
    }
  } */
  
  return trie;	
}




bool prefixTrieMatching(const string& text, const vector<Node>& trie, const int& startpos){
	

	int iter2=startpos;	
	char symbol = text[iter2];
	Node v = trie[0];
	
	while (true){
		
		
		if (v.isLeaf()){
				//std::cout << "v is leaf" << std::endl;
				return true;
			}		
		else if ( (iter2 < (int) text.size()) && ( v.next[letterToIndex(text[iter2])] !=NA ) ){
			//std::cout << "iter2 = " << iter2 << std::endl;
			//symbol = text[iter2];	
			//std::cout << "v.next[letterToIndex(symbol)] = " << v.next[letterToIndex(symbol)] << std::endl;			
			//if ( v.next[letterToIndex(symbol)] !=NA ){
			int w = v.next[letterToIndex(text[iter2])];
			v = trie[w];
			++iter2;		
			//}
		}
		else
			return false;
			
	}
	
}

vector <int> solve (const string& text, int n, const vector <string>& patterns)
{
	vector <int> result;

	// write your code here
	//std::cout<<"solve func: patterns size = " << patterns.size() << std::endl;
	vector<Node> t = build_trie(patterns);
	
	int i = 0;
	//std::cout << "i = " << i << std::endl;
	//std::cout << "is it a match? :" << prefixTrieMatching(text, t, i) << std::endl;
	 while ( i < (int) text.size()){
		bool flag = prefixTrieMatching(text, t, i);
		//std::cout << "i = " << i << " ";
		if (flag){
			//std::cout << "is a match" << std::endl;
			result.push_back(i);
		}
		//else 
			//std::cout << "is not a match" << std::endl;
		i++; 
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
