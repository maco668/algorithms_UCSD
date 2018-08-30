import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.Stack;

class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
		  String text = reader.readLine();

			
        
			int flag = 0;
			Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();//Stack<Bracket> opening_brackets_stack()
			for (int position = 0; position < text.length(); ++position) {
				char next = text.charAt(position);
				Bracket br = new Bracket(next, position);

				if (next == '(' || next == '[' || next == '{') {
					// Process opening bracket, write your code here
									
					opening_brackets_stack.push(br);
				}

				if (next == ')' || next == ']' || next == '}') {
					// Process closing bracket, write your code here
					if (opening_brackets_stack.empty()){
						System.out.println(position+1); // 1-based index						
						flag = 1;
						break;
//						return;
					} else{					
						Bracket top = opening_brackets_stack.pop();
						if (!top.Match(next)){
							System.out.println(position+1); // 1-based index							
							flag = 1;
							break;
	//						return;
							}
					}
						
				}
				
			}
			if (flag ==0){
				if (!opening_brackets_stack.empty()){
					Bracket top = opening_brackets_stack.pop();
					System.out.println(top.position+1);
					
				} else			
					System.out.println("Success");
						
					
					
				}					
		

        // Printing answer, write your code here
    }
}
