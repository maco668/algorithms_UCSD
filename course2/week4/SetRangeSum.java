import java.io.*;
import java.util.*;

public class SetRangeSum {

    BufferedReader br;
    PrintWriter out;
	StringTokenizer st;
    boolean eof;

    // Splay tree implementation

    // Vertex of a splay tree
    class Vertex {
        int key;
        // Sum of all the keys in the subtree - remember to update
        // it after each operation that changes the tree.
        long sum;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(int key, long sum, Vertex left, Vertex right, Vertex parent) {
            this.key = key;
            this.sum = sum;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    void update(Vertex v) {//update sum, update pointers of double-linked list
        if (v == null) return;
		//splay only affects the node and its parent, doesn't change its children's sum
        v.sum = v.key + (v.left != null ? v.left.sum : 0) + (v.right != null ? v.right.sum : 0);
        if (v.left != null) {
            v.left.parent = v;
        }
        if (v.right != null) {
            v.right.parent = v;
        }
    }

    void smallRotation(Vertex v) {
        Vertex parent = v.parent;
        if (parent == null) {
            return;
        }
        Vertex grandparent = v.parent.parent;
        if (parent.left == v) {
            Vertex m = v.right;
            v.right = parent;
            parent.left = m;
        } else {
            Vertex m = v.left;
            v.left = parent;
            parent.right = m;
        }
        update(parent);
        update(v);
        v.parent = grandparent;
        if (grandparent != null) {
            if (grandparent.left == parent) {
                grandparent.left = v;
            } else {
                grandparent.right = v;
            }
        }
    }

    void bigRotation(Vertex v) {
        if (v.parent.left == v && v.parent.parent.left == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else if (v.parent.right == v && v.parent.parent.right == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else {
            // Zig-zag
            smallRotation(v);
            smallRotation(v);
        }
    }

    // Makes splay of the given vertex and returns the new root.
    Vertex splay(Vertex v) {
        if (v == null) return null;
		
        while (v.parent != null) {//keep splaying until v becomes root
            if (v.parent.parent == null) {//if v is a child of root
				//Zig
                smallRotation(v);
                break;
            }
			// otherwise zig-zig or zig-zag
            bigRotation(v);
        }
		// return the new root pointer, cannot update root here because splay()
		// can also apply to sub-trees
        return v;
    }
    
	class VertexPair {
        Vertex left; // for real right
        Vertex right; // for real left
		//Vertex realNext;
        VertexPair() {
        }
        VertexPair(Vertex left, Vertex right) {
			// left is the smallest bigger key, right is the root
            this.left = left;
            this.right = right;
        }
    }
    

    VertexPair split(Vertex root, int key) {
		// if key is found, next=root, the found key will be root of right tree. 
		// if key is not found, the next greater key will be root (without left child) of right tree
		// therefore after split, left tree < key <= root of right tree 
		// if key is smaller than the smallest key, left is null, right root is the smallest
		// if key is greater than the biggest key, next from find is null, root=last=the greatest key, so right is nulll.
		
        VertexPair result = new VertexPair();// result is not a vertex, it is an ojbect of a pair class		
        VertexPair findAndRoot = find(root, key);
		
		//in find: return new VertexPair(next, root);
        root = findAndRoot.right; //current root of the tree on the right of the return object
        result.right = findAndRoot.left; //smallest greater key on the right of the result ojbect
        if (result.right == null) {
			// if current root is in fact the biggest key(no next greater),
			// put current root on the left of the result object and return.
            result.left = root;
            return result;
        }
		// if current root is not the biggest
		// splay the smallest greater key to root and make the right pointer  
		// of the result pair point to the new root
        result.right = splay(result.right);
		
		// make the left pointer of the result pair point to the left child of the new root 
        result.left = result.right.left;
		
		// detach left tree from right tree
        result.right.left = null;
		
        if (result.left != null) {// if the tree has more than a single node(result.right)
			// set the right pointer as the root of the left tree
            result.left.parent = null;
        }
		
        update(result.left);
        update(result.right);
        return result;
    }
	
	

    Vertex merge(Vertex left, Vertex right) {
		// merge left and right trees
		// splay the smallest key to the root of the right tree
		// attach the left tree to the left child of the right tree root
	
        if (left == null) return right;
        if (right == null) return left;
        
		while (right.left != null) {//find the smallest key in the right tree
            right = right.left;
        }
		
		// splay the smallest key to the root of the right tree
		// this make the right tree root have no left child
        right = splay(right);
		
		// attach the left tree to the left child of the right tree root
        right.left = left;
		
		// update the sum and the double-linked list pointers of the right tree root
        update(right);
        return right;
    }

    // Code that uses splay tree to solve the problem

    Vertex root = null;

    void insert(int x) {
        Vertex left = null;
        Vertex right = null;
        Vertex new_vertex = null;
		
		// find the key of interest: key equal to the given value or the key right next to the unfound given value, 
		// and the key that is the smallest value greater than the key of interest
		// so if the given value is not found, it should fall in between the key of interest and the smallest greater key
		// splay this key then splay the smallest greater key 
		// let the key of interest to be the root of the left tree
		// let the smallest greater key to be the root of the right tree
        VertexPair leftRight = split(root, x);
		
		// referencing left and right trees
        left = leftRight.left;
        right = leftRight.right;
		
        if (right == null || right.key != x) {
            new_vertex = new Vertex(x, x, null, null, null);
        }
		
		// first point left child of the new node to the left tree
		// then attach the new node as the left child of the right tree root
        root = merge(merge(left, new_vertex), right);
    }
	
	// Searches for the given key in the tree with the given root
    // and calls splay for the deepest visited node after that.
    // Returns pair of the result and the new root.
    // If found, result is a pointer to the node with the given key.
    // Otherwise, result is a pointer to the node with the smallest
    // bigger key (next value in the order).
    // If the key is bigger than all keys in the tree,
    // then result is null.
    VertexPair find(Vertex root, int key) {
        Vertex v = root;
        Vertex last = root;
        Vertex next = null;

        while (v != null) {
			// if eventually v.key != key, next won't be always the next greater key
            if (v.key >= key && (next == null || v.key < next.key)) {
                next = v;

            }
            last = v;
            if (v.key == key) {
                break;
            }
            if (v.key < key) {
                v = v.right;
            } else {
                v = v.left;
            }
        }
        root = splay(last);// if the key is not found, the new root can be either smaller 
		// or larger than the key
		// root is given to right pointer, next is given to left pointer
        return new VertexPair(next, root);
    }
	
	boolean find(int x) {
        // Implement find yourself
		if (root == null) return false;
		Vertex v = root;
        Vertex last = root;
        Vertex next = null;

        while (v != null) {
			//
			//System.out.println("x = " + x);
			//System.out.println("v.key = " + v.key);
            if (v.key >= x && (next == null || v.key < next.key)) {
                next = v;
				//System.out.println("next = v");

            }
            last = v;
            if (v.key == x) {
				//System.out.println("equal");
                break;
            }
            if (v.key < x) {
				//System.out.println("smaller");
                v = v.right;
            } else {
				//System.out.println("greater");
                v = v.left;
            }
        }
		
		if (next != null) {
			root = splay(next);
			//System.out.println("next.key = " + next.key);
		}
		//System.out.println("root.key = " + root.key);
		if (root.key == x)
			return true;
		else		
			return false;
    }
	


    void erase(int x) {
        // Implement erase yourself
				
		Vertex right = null;
		Vertex left = null;
		Vertex v = root;
        Vertex last = root;
        Vertex next = null;

        while (v != null) {
            if (v.key >= x && (next == null || v.key < next.key)) {
                next = v;
            }
            last = v;
            if (v.key == x) {
				break;
/*				if (v.right != null){
					next = v.right;
					while (next.left != null)
						next = next.left;
				}
				break;*/
            }
            if (v.key < x) {
                v = v.right;
            } else {
                v = v.left;
            }
        }
		if (next != null){
			if (v.key != x){
				root = splay(next);
			}else {						
				root = splay(next);
				right = root.right;
				left = root.left;
				if (root.right != null){				
					right.parent = null;
					update(right);
				}
				if (root.left != null){
					left.parent = null;
					update(left);
				}					
				
				root = merge(left, right);
					
			}
		} else{ 
			root = splay(last); // has to be last, not v, v can be null.
			//System.out.println("root.key = " + root.key);
		}
    }

    
    long sum(int from, int to) {
		if (root == null) return 0;
		long ans = 0;
		//left < from <= middle 
        VertexPair leftMiddle = split(root, from);
        Vertex left = leftMiddle.left;
        Vertex middle = leftMiddle.right;
		Vertex right = null;
		//  from <= middleSplit.left <  to <= middleSplit.right		
		
		if (middle == null){
			if (left.key != from){
				ans = 0;
				root = merge(left, middle);
				return ans;
			} else {
				ans = left.key;
				root = merge(left, middle);
				return ans;
			}
		} else{
			VertexPair middleRight = split(middle, to + 1);
			middle = middleRight.left; // if to is the smallest, middle will only have one node which is to
			right = middleRight.right;
			
			if (middle == null){
				ans = 0;
				root = merge(left, merge(middle, right)); 
				return ans;
			}
			
			ans = middle.sum;
			root = merge(left, merge(middle, right)); 
			return ans;
		
		}
		
        
		
        
        // Complete the implementation of sum
	
			
		
		//root = merge(merge(middle, right), left);

        //return ans;
    }

	void InOrderTraversal(Vertex vertex){
			if (vertex == null)
				return;
			InOrderTraversal(vertex.left);
			//result.add(tree.key);
			System.out.print(vertex.key + ", ");
			InOrderTraversal(vertex.right);
			
		}

    public static final int MODULO = 1000000001;

    void solve() throws IOException {
        int n = nextInt();
        int last_sum_result = 0;
        for (int i = 0; i < n; i++) {
            char type = nextChar();
            switch (type) {
                case '+' : {
                    int x = nextInt();
                    insert((x + last_sum_result) % MODULO);
                } break;
                case '-' : {
                    int x = nextInt();
                    erase((x + last_sum_result) % MODULO);
                } break;
                case '?' : {
                    int x = nextInt();
                    out.println(find((x + last_sum_result) % MODULO) ? "Found" : "Not found");
                } break;
                case 's' : {
                    int l = nextInt();
                    int r = nextInt();
                    long res = sum((l + last_sum_result) % MODULO, (r + last_sum_result) % MODULO);
                    out.println(res);
                    last_sum_result = (int)(res % MODULO);
                }
            }
        //if (i == 2){
		//	InOrderTraversal(root); 
		//}
			
		
		}
    }

    SetRangeSum() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
		//out = new PrintWriter("result.txt");
        solve();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new SetRangeSum();
    }

    String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return null;
            }
        }
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }
    char nextChar() throws IOException {
        return nextToken().charAt(0);
    }
}
