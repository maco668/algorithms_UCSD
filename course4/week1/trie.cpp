#include <string>
#include <iostream>
#include <vector>
#include <map>

using std::map;
using std::vector;
using std::string;

typedef map<char, int> edges;
typedef vector<edges> trie;


trie build_trie(vector<string> & patterns) {
  trie t;
  //edges::iterator it;
  // write your code here
  int currentNode;
  char currentSymbol;
  
  edges E;
  t.push_back(E);
  
  for ( size_t i = 0; i < patterns.size(); i++){
	  currentNode = 0;
	  
	  for ( size_t j = 0; j < patterns[i].size(); j++){
		currentSymbol = patterns[i][j];
		
		//edges::iterator it = t[currentNode].find(currentSymbol); 
		//std::cout<<t[currentNode]<<std::endl;
		
		edges E = t[currentNode];
		if( E.find(currentSymbol) != E.end() )
			currentNode = E.find(currentSymbol)->second;
		else{
			edges e;
			t.push_back(e);			
			int newNode = t.size() - 1;
			
			t[currentNode].insert( std::make_pair(currentSymbol, newNode) );
			currentNode = newNode;
		}
	  }
		
  
  }
  
  return t;	
}

int main() {
  size_t n;
  edges::iterator it;
  std::cin >> n;
  vector<string> patterns;
  for (size_t i = 0; i < n; i++) {
    string s;
    std::cin >> s;
    patterns.push_back(s);
  }
  
  trie t = build_trie(patterns);
  for (size_t i = 0; i < t.size(); ++i) {
    for (const auto & j : t[i]) {
      std::cout << i << "->" << j.second << ":" << j.first << "\n";
    }
  }
  
  return 0;  	
}