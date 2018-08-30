import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class PhoneBook {

    private FastScanner in = new FastScanner();
    // Keep list of all existing (i.e. not deleted yet) contacts.
    //private List<Contact>[] contacts = new arrayList<Contact>();
	//private Contact[] contacts;
    String[] names;
	boolean[] hasNum;
	public static void main(String[] args) {
        new PhoneBook().processQueries();
    }

    private Query readQuery() {
        String type = in.next();
        int number = in.nextInt();
        if (type.equals("add")) {
            String name = in.next();
            return new Query(type, name, number);
        } else {
            return new Query(type, number);
        }
    }

    private void writeResponse(String response) {
        System.out.println(response);
    }


    private void processQuery(Query query) {
        if (query.type.equals("add")) {
            // if we already have contact with such number,
            // we should rewrite contact's name
            //boolean wasFound = false;
			
			//contacts[query.number].name = query.name;
			//contacts[query.number].hasRecord = true;
			
			names[query.number] = query.name;
			hasNum[query.number] = true;
			
/*            for (Contact contact : contacts)
                if (contact.number == query.number) {
                    contact.name = query.name;
                    wasFound = true;
                    break;
                }
   
            if (!wasFound)
                contacts.add(new Contact(query.name, query.number));
*/
        } else if (query.type.equals("del")) {
			//contacts[query.number].hasRecord = false;
			hasNum[query.number] = false;
			
/*            for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); )
                if (it.next().number == query.number) {
                    it.remove();
                    break;
                }
*/
        } else {
            String response = "not found";
            //if (contacts[query.number].hasRecord == true)
			//	response = contacts[query.number].name;
			
			if (hasNum[query.number] == true)
				response = names[query.number];
			
/*			for (Contact contact: contacts)
                if (contact.number == query.number) {
                    response = contact.name;
                    break;
                }
*/
            writeResponse(response);
        }
    }

    public void processQueries() {
        int queryCount = in.nextInt();
		//contacts = new Contact[10000000];
		names = new String[10000000];
		hasNum = new boolean[10000000];

        for (int i = 0; i < queryCount; ++i)
            processQuery(readQuery());
		
    }

    static class Contact {
        String name;
		boolean hasRecord;
    
    }

    static class Query {
        String type;
        String name;
        int number;


        public Query(String type, String name, int number) {
            this.type = type;
            this.name = name;
            this.number = number;
        }

        public Query(String type, int number) {
            this.type = type;
            this.number = number;
        }
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
