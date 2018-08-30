#include <iostream>
#include <map>
#include <string>
#include <vector>

using std::cin;
using std::cout;
using std::endl;
using std::map;
using std::string;
using std::vector;
typedef map<char, int> edges;
typedef vector<edges> trie;
int const Letters =    4;
int const NA      =   -1;


// Build a suffix tree of the string text and return a vector
// with all of the labels of its edges (the corresponding 
// substrings of the text) in any order.
class Node
{
	public:
	map<char, int> next;
	int starting;
	int length;
	int pos;

	Node (const int& a, const int& b)
	{
		this->starting = a;
		this->length = b;
		/* this->next.insert( std::makepair('A', NA) );
		this->next.insert( std::makepair('C', NA) );
		this->next.insert( std::makepair('G', NA) );
		this->next.insert( std::makepair('T', NA) );
		this->next.insert( std::makepair('$', NA) ); */
		this->pos = NA;
	}
	Node (const int& a, const int& b, const int& c)
	{
		this->starting = a;
		this->length = b;
		/* this->next.insert( std::makepair('A', NA) );
		this->next.insert( std::makepair('C', NA) );
		this->next.insert( std::makepair('G', NA) );
		this->next.insert( std::makepair('T', NA) );
		this->next.insert( std::makepair('$', NA) ); */
		this->pos = c;
	}
	Node ()
	{
		/* this->next.insert( std::makepair('A', NA) );
		this->next.insert( std::makepair('C', NA) );
		this->next.insert( std::makepair('G', NA) );
		this->next.insert( std::makepair('T', NA) );
		this->next.insert( std::makepair('$', NA) ); */
		this->pos = NA;
	}


	/* bool isLeaf () const
	{
	    return (next['A'] == NA && next['C'] == NA && next['G'] == NA && next['T'] == NA && next['$'] == NA);
	} */
	
};

vector<Node> ComputeSuffixTreeEdges(const string& text) {
	//	vector<string> result;
	vector<Node> tree;
	// Implement this function yourself

	//char currentSymbol;

	Node root(NA, NA);
	tree.push_back(root);
  
  
  		
		
	//text += '$';
	int n = text.size();
	//int i = 0;
	for ( size_t i = 0; i < n; i++){
		//if (i >= 3)
		//	break;
		
		int currentNode = 0;
		int iter = i;
		
		while (true) {
			if ( tree[currentNode].next.find(text[iter]) == tree[currentNode].next.end()  ){ //if no availabe branch 
				//cout << "not found in map" << endl;
				Node node( iter, n - iter, i); //new node with updated starting pos and length
				//cout << "starting: " << node.starting << ", length: " << node.length << endl;
				tree.push_back(node);
				int newNode = tree.size() - 1;
				tree[currentNode].next.insert( std::make_pair(text[iter], newNode) );
				break;			
			}
			else if( tree[currentNode].next.find(text[iter]) != tree[currentNode].next.end() ){ 
			//if there is a match at text[iter]
				//cout << "found in map" << endl;
				int nextNode = tree[currentNode].next.find(text[iter])->second;
				int pos1 = tree[nextNode].starting;
				int len1 = tree[nextNode].length;				
				int pos2 = iter;
				int len2 = n - iter;				
				int count = 0;
				
				while ( (count < len1) && (count < len2) ){
					if ( text[pos1] == text[pos2] ){
						++pos1; ++pos2; ++count;
					}
					else 
						break;
				}
				//cout << "pos1: " << pos1 << ", len1: " << len1 << endl;
				//cout << "pos2: " << pos2 << ", len2: " << len2 << endl;
				//cout << "count: " 	<< count << endl;
				
				int diff = len1 - count;
				if ( diff > 0 ) { 
					//int newlen = count;
					Node node1( tree[nextNode].starting, count ); //insert node1 between current and its next
					node1.next[text[pos1]] = nextNode; // point old node to the new node
					tree[nextNode].starting = pos1; // update old node's substring info
					tree[nextNode].length = diff; //update old node's substring info
					tree.push_back(node1); 
					int nodepos1 = tree.size() - 1;
					tree[currentNode].next[text[iter]] = nodepos1; //point new node to current node 				
					
					
					Node node2( pos2, n - pos2, i );				
					tree.push_back(node2);
					int nodepos2 = tree.size() - 1;
					tree[nodepos1].next.insert( std::make_pair( text[pos2], nodepos2 ) );
					break;
				}
				else if ( diff == 0 ) {
					currentNode = tree[currentNode].next.find(text[iter])->second;
					iter += count;
				}			
				//if( j == patterns[i].size() - 1 )
				//	trie[currentNode].ispattern = true;
			}
		}
		
			//std::cout<< "currentNode = " << currentNode <<std::endl;
			
	} //for loop
  
  return tree;
}

int main() {
  string text;
  cin >> text;
  //vector<Node> tree = ComputeSuffixTreeEdges(text+='$');
  vector<Node> tree = ComputeSuffixTreeEdges(text);
  for (int i = 1; i < tree.size(); ++i) {
	//std::cout << tree[i].starting << "," << tree[i].length << std::endl;
	for (int j = tree[i].starting; j < (tree[i].starting + tree[i].length); ++j )
		std::cout << text[j]; 
	std::cout << std::endl;
	//std::cout << tree[i].pos << std::endl;
  }
  return 0;
}
