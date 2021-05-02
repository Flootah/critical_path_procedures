import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Graph {
	//No. of Nodes
	private int N;
	
	//Adjacency List as Arraylist of Arraylists
	private ArrayList<ArrayList<Integer>> adj;
	
	//Constructor
	public Graph(int n) {
		N = n;
		adj = new ArrayList<ArrayList<Integer> >(n);
		for(int i = 0; i < n; ++i) {
			adj.add(new ArrayList<Integer>());
		}
	}
	
	// Function to add an edge into the graph
	public void addEdge(int v, int w) { 
		adj.get(v).add(w); 
	}
	
    // A recursive function used by topologicalSort
    private void topologicalSortUtil(int v, boolean visited[], Stack<Integer> stack)
    {
        // Mark the current node as visited.
        visited[v] = true;
        Integer i;
 
        // Recur for all the vertices adjacent
        // to thisvertex
        Iterator<Integer> it = adj.get(v).iterator();
        while (it.hasNext()) {
            i = it.next();
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }
 
        // Push current vertex to stack
        // which stores result
        stack.push(new Integer(v));
    }
    
    // The function to do Topological Sort.
    // It uses recursive topologicalSortUtil()
    public ArrayList<Integer> topologicalSort()
    {
        Stack<Integer> stack = new Stack<Integer>();
 
        // Mark all the vertices as not visited
        boolean visited[] = new boolean[N];
        for (int i = 0; i < N; i++)
            visited[i] = false;
 
        // Call the recursive helper
        // function to store
        // Topological Sort starting
        // from all vertices one by one
        for (int i = 0; i < N; i++)
            if (visited[i] == false)
                topologicalSortUtil(i, visited, stack);
        
        ArrayList<Integer> result = new ArrayList<Integer>();
        // Print contents of stack
        while (stack.empty() == false)
        	result.add(stack.pop());
        return result;
    }
 
 
}
