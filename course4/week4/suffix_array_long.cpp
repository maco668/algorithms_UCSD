#include <algorithm>
#include <iostream>
#include <map>
#include <string>
#include <vector>
#include <utility>

using std::cin;
using std::cout;
using std::endl;
using std::make_pair;
using std::pair;
using std::string;
using std::vector;
using std::map;

// Build suffix array of the string text and
// return a vector result of the same length as the text
// such that the value result[i] is the index (0-based)
// in text where the i-th lexicographically smallest
// suffix of text starts.

vector<int> SortCharacters(const string& ST){
	int len = ST.length();
	int alsize = 5; 	
	vector<int> order(len);
	map<char, int> count = {{'$', 0},{'A',0},{'C',0},{'G',0},{'T',0}};
	
 	for (int i = 0; i < len; i++){
		//cout << "count[" << ST[i] << "]=" << count[ ST[i] ] << endl;
		count[ST[i]] += 1; 
	} 
	
	/* cout << "count before addition" << endl;
	for ( auto & ele: count){
		cout << "count[" << ele.first << "]=" << ele.second << endl;
	} */
	
/* 	cout << "pos" << endl;
	for ( auto it = count.begin(); it != count.end(); ++it){
			
		cout << it->first << ":" << it->second << ",";		
	}
	cout << endl; */
	
	for (auto it = std::next(count.begin()); it != count.end(); ++it){
		it->second += std::prev(it)->second;
	}
	
/* 	cout << "position for counting sort" << endl;
	for (auto & ele: count){
		cout << "count[" << ele.first << "]=" << ele.second << endl;
	} */

	for (int i = len-1; i >= 0; --i){
		char c = ST[i];
		--count[c];
		order[count[c]] = i;	
	}
/* 	cout << "order" << endl;
	for (auto & ele: order){
		cout << ele << ",";
	} 	
	cout<<endl; */
	return order;
}

vector<int> ComputeCharClasses(const string & ST, const vector<int> & order){
	vector<int> shiftclass(ST.length());
	
	for (int i = 1; i < ST.length(); i++){
		if ( ST[order[i]] != ST[order[i-1]])
			shiftclass[order[i]] = shiftclass[order[i-1]] + 1;
		else
			shiftclass[order[i]] = shiftclass[order[i-1]];
	}
/* 	cout << "shiftclass" << endl;
	for (auto & ele: shiftclass){
		cout << ele << ","	<< endl;
	}	 */
	
	return shiftclass;
}

vector<int> SortDoubled(const string & ST, const int & L, const vector<int> & order, const vector<int> & shiftclass){
	int len = ST.length();
	vector<int> neworder(len);
	vector<int> count(len);
 	
	for (int i = 0; i < len; i++){
		//cout << "count[" << ST[i] << "]=" << count[ ST[i] ] << endl;
		count[shiftclass[i]] += 1; 
	} 

/* 	cout << "count before addition" << endl;
	for ( auto & ele: count){
		cout << ele << "," << endl;
	} */
	
	for (auto it = std::next(count.begin()); it != count.end(); ++it){
		*it += *std::prev(it);
	}
	
	//cout << "position for counting sort" << endl;
	
/* 	for (auto & ele: count){
		cout <<  ele << "," << endl;
	} */

	for (int i = len-1; i >= 0; --i){
		int order_L = (order[i] - L + len) % len;	
		int cl = shiftclass[order_L];
		--count[cl];
		neworder[count[cl]] = order_L;	
	}
	
/* 	cout << "neworder" << endl;
	for (auto & ele: neworder){
		cout << ele << ",";
	}	
	cout<<endl; */
	return neworder;
}	

vector<int> UpdateClasses( const int & L, const vector<int> & neworder, const vector<int> & shiftclass){
	int len = neworder.size();
	vector<int> newclass(len);
	
	for (int i = 1; i < len; i++){
		int cur = neworder[i]; // starting position of cyclic shift of length 2*L in the updated suffix array
		int prev = neworder[i-1];
		int order_L = (cur + L) % len; // left shift in the string 
		int order_L_1= (prev + L) % len;
		if ( shiftclass[cur] != shiftclass[prev] || shiftclass[order_L] != shiftclass[order_L_1])
			newclass[cur] = newclass[prev] + 1;
		else
			newclass[cur] = newclass[prev];
	}
/* 	cout << "newclass" << endl;
	for (auto & ele: newclass){
		cout << ele << ","	<< endl;
	}	 */
	
	return newclass;
}

vector<int> BuildSuffixArray(const string& text) {
  // Implement this function yourself
/*   cout << "text" << endl;
  for(const auto & ele: text){
		cout<< ele << "," ; 
	}
  cout << endl;  */
  int n = text.length();
  vector<int> order = SortCharacters(text);
  vector<int> shiftclass = ComputeCharClasses(text, order);
/*   cout << "shiftclass" << endl;
  
  for (auto & ele: shiftclass){
	cout << ele << ",";
  }
  cout << endl; */
  
  int L = 1;
  while (L < n){ // when L >= n/2, the pair of cyclic shifts already covers the whole length of string  
	  //cout << "L=" << L << endl;
	  order = SortDoubled(text, L, order, shiftclass);
	  shiftclass = UpdateClasses( L, order, shiftclass);
	  L = 2 * L;
	//  if (L >= n)
	//	  L = n;
  }
  
  return order;
}


int main() {
  string text;
  cin >> text;
  vector<int> suffix_array = BuildSuffixArray(text);
  for (int i = 0; i < suffix_array.size(); ++i) {
    cout << suffix_array[i] << ' ';
  }
  cout << endl;
  return 0;
}
