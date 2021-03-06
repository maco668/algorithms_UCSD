#include <algorithm>
#include <iostream>
#include <string>
#include <vector>

using std::cin;
using std::cout;
using std::endl;
using std::string;
using std::vector;

string InverseBWT(const string& bwt) {
	//string column1 = bwt;
	//sort(column1.begin(), column1.end() );
  
/* 	cout << "column1:" << endl;
	cout<< column1 << endl;  */
	
	int count[5] = {0};
	int lastRow[bwt.length()] = {0};
	for (int i = 0; i < bwt.length(); i++){
		switch (bwt[i]) { 
		case '$': lastRow[i] = ++count[0]; break;
		case 'A': lastRow[i] = ++count[1]; break;
		case 'C': lastRow[i] = ++count[2]; break;
		case 'G': lastRow[i] = ++count[3]; break;
		case 'T': lastRow[i] = ++count[4]; break;
		}
	}
		
/* 	cout << "count:" << endl;
	for (const auto & ele : count){
		std::cout<< ele << ",	" ; 
	}
	cout << endl; */
	
	int n = 5;
	int pos1[n] = {0};
	
	for (int i = 1; i < n;i++){
		for (int j = 0; j < i; j++)
			pos1[i] += count[j];
	}
	
/* 	cout << "pos1:" << endl;
	for (const auto & ele : pos1){
		std::cout<< ele << ",	" ; 
	}
	cout << endl; */
  
	  
/* 	cout << "firstRow:" << endl;
	for (const auto & ele : firstRow){
		std::cout<< ele << ",	" ; 
	}	
	cout << endl; */
	
	string text = "";

	int currentpos=0, nextpos=0;
	for (int i = 1; i < bwt.length(); i++){
		text = bwt[currentpos] + text;
		
		switch (bwt[currentpos]) { 
			case '$': nextpos = pos1[0] + lastRow[currentpos] - 1; break;
			case 'A': nextpos = pos1[1] + lastRow[currentpos] - 1; break;
			case 'C': nextpos = pos1[2] + lastRow[currentpos] - 1; break;
			case 'G': nextpos = pos1[3] + lastRow[currentpos] - 1; break;
			case 'T': nextpos = pos1[4] + lastRow[currentpos] - 1; break;
		}
		currentpos = nextpos;
	}
	text += '$';
	
/* 	cout << "text:" << endl;
	cout << text << endl; */
  // write your code here

  return text;
}

int main() {
  string bwt;
  cin >> bwt;
  cout << InverseBWT(bwt) << endl;
  return 0;
}
