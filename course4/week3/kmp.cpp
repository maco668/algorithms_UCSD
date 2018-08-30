#include <cstdio>
#include <iostream>
#include <string>
#include <vector>

using std::cin;
using std::string;
using std::vector;
using std::cout;
using std::endl;

// Find all occurrences of the pattern in the text and return a
// vector with all positions in the text (starting from 0) where 
// the pattern starts in the text.
vector<int> ComputePrefix(const string& pattern){
	int patternLen = pattern.length();
	vector<int> borders(patternLen);
	int border = 0;
	for (int i = 1; i < patternLen; ++i){
		//cout << "patter[" << i << "]" << "=" << pattern[i] << ", " << "pattern[border]=" << pattern[border] << endl;
		while (border > 0 && pattern[i] != pattern[border]){
			//cout << "patter[" << i << "]" << "=" << pattern[i] << ", " << "pattern[border]=" << pattern[border] << endl;
			border = borders[border - 1];
		}
		
		if (pattern[i] == pattern[border]){
			//cout << "patter[" << i << "]" << "=" << pattern[i] << ", " << "pattern[border]=" << pattern[border] << endl;
			++border;
			
		}
		else
			border = 0;
		//cout << "borders[" << i << "]=" << "border=" << border << endl;
		//cout << endl;
		borders[i] = border;			
	}
	//cout << "borders:" << endl;
	/* for(int i = 0; i < patternLen;i++){
		cout<< borders[i] << "," ; 
	}
	cout << endl; */
	//int* pt = borders;
	/* int test[3] = {0, 0, 1};
	int *pt = test;
	cout << "pt: sizeof " << sizeof(pt) << endl;
	
	for(int i = 0; i < 3;++i){
		cout<< pt[i] << "," ; 
	}
    cout << endl; */
	return borders;
}

vector<int> find_pattern(const string& pattern, const string& text) {
  vector<int> result;
  // Implement this function yourself
  string pnt = "";
  pnt += pattern;
  pnt += '$'; 
  pnt += text;
  int patternLen = pattern.length();
  int two_patternLen = 2 * patternLen;
  int pntLen = pnt.length();
  vector<int> borders;
  
 /*  cout << "pnt" << endl;
   for(const auto & ele: pnt){
		cout<< ele << "," ; 
	}
  cout << endl; */
  
  borders = ComputePrefix(pnt);
  
 
  /* cout << "borders:" << endl;
  for(int i = 0; i < borders.size();++i){
		
		cout<< borders[i] << "," ; 
	}
  cout << endl */;
  
/*   cout << "patternLen=" << patternLen << endl;
  cout << "two_patternLen=" << two_patternLen << endl; */
  
  for (int i = patternLen + 1; i < pntLen; ++i){
	  if ( borders[i] == patternLen )
		  result.push_back(i - two_patternLen);
  }
  return result;
}



int main() {
  string pattern, text;
  cin >> pattern;
  cin >> text;
  vector<int> result = find_pattern(pattern, text);
  for (int i = 0; i < result.size(); ++i) {
    printf("%d ", result[i]);
  }
  printf("\n");
  return 0;
}
