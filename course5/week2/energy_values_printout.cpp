#include <cmath>
#include <iostream>
#include <iomanip>
#include <vector>

const double EPS = 1e-6;
const int PRECISION = 6;

typedef std::vector<double> Column;
typedef std::vector<double> Row;
typedef std::vector<Row> Matrix;
using std::vector;
using std::cin;
using std::cout;
using std::endl;
using std::fixed;

struct Equation {
    Equation(const Matrix &a, const Column &b):
        a(a),
        b(b)
    {}

    Matrix a;
    Column b;
};

struct Position {
    Position(int column, int row):
        column(column),
        row(row)
    {}

    int column;
    int row;
};

Equation ReadEquation() {
    int size;
    std::cin >> size;
    Matrix a(size, std::vector <double> (size, 0.0));
    Column b(size, 0.0);
    for (int row = 0; row < size; ++row) {
        for (int column = 0; column < size; ++column)
            std::cin >> a[row][column];
        std::cin >> b[row];
    }
    return Equation(a, b);
}

Position SelectPivotElement(
  const Matrix &a, 
  std::vector <bool> &used_rows, 
  std::vector <bool> &used_columns) {
    // This algorithm selects the first free element.
    // You'll need to improve it to pass the problem.
    Position pivot_element(0, 0);
    while (used_rows[pivot_element.row])
        ++pivot_element.row;
    while (used_columns[pivot_element.column])
        ++pivot_element.column;
	
	while (a[pivot_element.row][pivot_element.column]==0) // skip the row with potential pivot element equal to zero
		++pivot_element.row;
    
	return pivot_element;
}

void SwapLines(Matrix &a, Column &b, std::vector <bool> &used_rows, Position &pivot_element) {
    std::swap(a[pivot_element.column], a[pivot_element.row]); // pivot_element.column denotes the pivot row in the line to be processed
    std::swap(b[pivot_element.column], b[pivot_element.row]);
    std::swap(used_rows[pivot_element.column], used_rows[pivot_element.row]);
    pivot_element.row = pivot_element.column;
}

void ProcessPivotElement(Matrix &a, Column &b, const Position &pivot_element) {
    // Write your code here
	int size = a.size();
	//for(int i = pivot_element.column; i < size; ++i)
	//	a[pivot_element.row][i]/=a[pivot_element.row][pivot_element.column];
	
	//b[pivot_element.row]/=a[pivot_element.row][pivot_element.column];
	
	for(int i = pivot_element.row+1; i < size; ++i){
		double multiplier = 0;
		if(a[i][pivot_element.column]!=0 ){ //avoid dividing zero later on (no need for elimination if the coefficient is already zero)
			multiplier = -a[pivot_element.row][pivot_element.column] / a[i][pivot_element.column];
			for(int j = pivot_element.column; j < size; ++j)			
				a[i][j] = a[pivot_element.row][j] + multiplier * a[i][j];
			b[i] = b[pivot_element.row] + multiplier * b[i];
		}		
	}			
}

void CalculateSolution(Matrix &a, Column &b){ //backtrace the solution from bottom
	int size = a.size();
	if ((size > 0) && (a[size-1][size-1]!=0)){ 
		b[size-1]/=a[size-1][size-1];
		if(size>1)
			for(int i = size - 2; i >= 0; --i){
				for(int j = size - 1; j > i; --j)
					b[i] -= a[i][j]*b[j];
				b[i] /=a[i][i];		
			}
	}
}

void MarkPivotElementUsed(const Position &pivot_element, std::vector <bool> &used_rows, std::vector <bool> &used_columns) {
    used_rows[pivot_element.row] = true;
    used_columns[pivot_element.column] = true;
}

void PrintMatrix(const Matrix &a) {
    int size = a.size();
	cout<<"************"<<endl;
    std::cout.precision(PRECISION);
	cout<<fixed;
    for (int row = 0; row < size; ++row){
		for(int column = 0; column < size; ++column)
			cout <<a[row][column]<<"\t";
		cout<<endl;
	}
	cout<<"************"<<endl;
}

void PrintColumn(const Column &column) {
    int size = column.size();
    std::cout.precision(PRECISION);
	cout<<fixed;
    for (int row = 0; row < size; ++row)
        std::cout <<column[row] << std::endl;
}

Column SolveEquation(Equation equation) {
    Matrix &a = equation.a;
    Column &b = equation.b;
    int size = a.size();

    std::vector <bool> used_columns(size, false);
    std::vector <bool> used_rows(size, false);
    for (int step = 0; step < size; ++step) {
		cout<<"step:"<<step<<endl;
        Position pivot_element = SelectPivotElement(a, used_rows, used_columns);
		cout<<"pivot:"<<"a["<<pivot_element.row<<"]["<<pivot_element.column<<"]"<<endl;
        SwapLines(a, b, used_rows, pivot_element);
		//cout<<"matrix after SwapLines:"<<endl;
		//PrintMatrix(a);
        ProcessPivotElement(a, b, pivot_element);
		//cout<<"matrix after ProcessPivotElement:"<<endl;
		//PrintMatrix(a);
        MarkPivotElementUsed(pivot_element, used_rows, used_columns);
    }
	
	PrintMatrix(a);
	
	CalculateSolution(a, b);	
	
	cout<<"size of b:" <<b.size()<<endl;
	cout<<"b[0]:"<<b[0]<<endl;
	cout<<"b[1]:"<<b[1]<<endl;
	
    return b;
}



int main() {
    Equation equation = ReadEquation();
    Column solution = SolveEquation(equation);
    PrintColumn(solution);
    return 0;
}
