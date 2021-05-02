import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CPM {
	private static Scanner sc;
	private static Object object;
	
	


	@SuppressWarnings("resource")
	public static void main(String[] args) {
		boolean exit = false;
		ArrayList<Node> nodes = new ArrayList<Node>();
		while(exit == false) {
			sc = new Scanner(System.in);
			System.out.println("Please enter file input");
			String response = sc.next();
			if(response.equals("exit")) System.exit(0);
			
			try {
				File file = new File(response);
				Scanner scfile = new Scanner(file);
	
				while(scfile.hasNextLine()) {
					Scanner line = new Scanner(scfile.nextLine());
					if(!line.next().equals("task")) {
						throw new BadInputException();
					}
					ArrayList<Integer> parameters = new ArrayList<Integer>();
					while(line.hasNext()) {
						parameters.add(line.nextInt());
					}
					int name = parameters.get(0);
					int time = parameters.get(parameters.size()-1);
					ArrayList<Integer> req = new ArrayList<Integer>();
					for(int i = 1; i < parameters.size()-1; i++) {
						req.add(parameters.get(i));
					}
					nodes.add(new Node(name, req, time));
					line.close();
				}
				scfile.close();
			} catch (FileNotFoundException e) {
				System.out.println("file not found!");
				continue;
			} catch (BadInputException e ) {
				System.out.println("bad input exception!");
				continue;
			}
			exit = true;
		}
		//end while
		
		//create acyclic graph and topologically sort
		Graph g = new Graph(nodes.size());
		for(int i = 0; i < nodes.size(); i++) {
			Node curnode = nodes.get(i);
			if (curnode.req.isEmpty()) continue;
			for(int j = 0; j < curnode.req.size(); j++) {
				g.addEdge(curnode.name, curnode.req.get(j));
			}
		}
		ArrayList<Integer> nodeorder = g.topologicalSort();
		Collections.reverse(nodeorder);
		printElements(nodeorder);
		
		for(int i = 0; i < nodes.size(); i++) {
			Node curNode = findNode(i, nodes);
			if(curNode.req.isEmpty()) {
				curNode.ES = 0;
			} else {
				curNode.ES = 1; //TODO Max of all prereq node EF values
			}
			
			curNode.EF = curNode.ES + curNode.time;
		}
	}

    public static void printElements(ArrayList<Integer> alist)
    {
        for (int i = 0; i < alist.size(); i++) {
            System.out.print(alist.get(i) + " ");
        }
    }
    
    public static Node findNode(int name, ArrayList<Node> arr) {
    	for(int i = 0; i < arr.size(); i++) {
    		if(arr.get(i).name == name) {
    			return arr.get(i);
    		}
    	}
    	return null;
    }
}
