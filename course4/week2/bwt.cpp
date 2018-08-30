#include <algorithm>
#include <iostream>
#include <string>
#include <vector>

using std::cin;
using std::cout;
using std::endl;
using std::string;
using std::vector;

string BWT(const string& text) {
	int len = text.length();
	vector<string> smtx(len);
	int i = 0;
	
	for (int j = 0; j < len; j++){
		if (j == 0)
			smtx[j] = text.substr(len - 1 - j, j) + text.substr(0, len - j);
		else
			smtx[j] = text.substr(len - j, j) + text.substr(0, len - j);
		
	}
	
/* 	cout << "before sorting" << endl;
	for (int j = 0; j < len; j++){
		std::cout<< smtx[j] <<std::endl;
	} */
	
	
	sort(smtx.begin(), smtx.end());
/* 	cout << "after sorting" << endl;
	for ( const auto & j : smtx){
		std::cout<< j <<std::endl;
	} */
	
	string result = "";
	for ( const auto & j : smtx){
		result = result + j[len-1];
	}
	

  // write your code here

  return result;
}



int main() {
  string text;
  cin >> text;
  cout << BWT(text) << endl;
  return 0;
}