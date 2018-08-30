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
//        InputStreamReader input_stream = new InputStreamReader(System.in);
//        BufferedReader reader = new BufferedReader(input_stream);
//		  String text = reader.readLine();
		for(int i = 1; i <= 54; ++i){
//		for(int i = 14; i <= 14; ++i){
			String inputName = null;
			String answerName = null;
			
			if (i < 10){
				
				inputName = "0" + Integer.toString(i);
				answerName = inputName + ".a";
				
			} else {
				
				inputName = Integer.toString(i);
				answerName = inputName + ".a";
			}			
			
			String text = null;
			String answer = null;
			
			try{
				FileReader fileReader = new FileReader(inputName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				text = bufferedReader.readLine();
				bufferedReader.close();				
			} catch(IOException e){
				e.printStackTrace();
			}
			
			try{
				FileReader fileReader = new FileReader(answerName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				answer = bufferedReader.readLine();
				bufferedReader.close();				
			} catch(IOException e){
				e.printStackTrace();
			}
				
			//System.out.println(text);
			//System.out.println(answer);
			
        
			int flag = 0;
			Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
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
//						System.out.println(position+1); // 1-based index
						if (answer.equals(Integer.toString(position+1)))
							System.out.println("passed test " + inputName);
						else{
							System.out.println("Failed test " + inputName);
							System.out.println("p1");
						}
						flag = 1;
						break;
//						return;
					} else{					
						Bracket top = opening_brackets_stack.pop();
						if (!top.Match(next)){
//						System.out.println(position+1); // 1-based index
							if (answer.equals(Integer.toString(position+1)))
								System.out.println("passed test " + inputName);
							else{
								System.out.println("Failed test " + inputName);
								System.out.println("p2");
							}
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
	//				System.out.println(top.position+1);
					if (answer.equals(Integer.toString(top.position+1)))
						System.out.println("passed test " + inputName);
					else{
						System.out.println("Failed test " + inputName);	
						System.out.println("p3");
					}
				} else{
					
					if (answer.equals("Success")){
						
						System.out.println("passed test " + inputName);
						
					}
					else{
						System.out.println("Failed test " + inputName);
						//System.out.println("answer=" + answer);						
						System.out.println("p4");
					}
					
				}
					
			}
		}

        // Printing answer, write your code here
    }
}
