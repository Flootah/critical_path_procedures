import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CPM {
	private static Scanner sc;

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

		printTasks(nodeorder);
		
		// sets ES and EF of all nodes based on times and node order
		for(int i = 0; i < nodes.size(); i++) {
			Node curNode = findNode(nodeorder.get(i), nodes);
			if(curNode.req.isEmpty()) {
				curNode.ES = 0;
				curNode.EF = curNode.time;
			} else {
				int tempES = 0;
				for(int j = 0; j < curNode.req.size(); j++) {
					findNode(curNode.req.get(j), nodes).addSuccessor(curNode.name);
					Node tempReq = findNode(curNode.req.get(j), nodes);
					if(tempReq.EF > tempES) {
						tempES = tempReq.EF;
					}
				}
				curNode.ES = tempES;
				curNode.EF = curNode.ES + curNode.time;
			}
		}
		
		// backwards pass to find LS and LF
		for(int i = nodes.size() - 1; i >= 0; i--) {
			Node curNode = findNode(nodeorder.get(i), nodes);
			// if final task, then LS and LF are same as ES and EF
			if(curNode.successors.isEmpty()) {
				curNode.LS = curNode.ES;
				curNode.LF = curNode.EF;
			} else {
				for(int j = 0; j < curNode.successors.size(); j++) {
					Node tempSuc = findNode(curNode.successors.get(j), nodes);
					if(tempSuc.LS < curNode.LF) {
						curNode.LF = tempSuc.LS;
					}
				}
				curNode.LS = curNode.LF - curNode.time;
			}

			//adjust LS values if needed.
		}
		
		ArrayList<Integer> critpath = new ArrayList<Integer>();
		for(int i = 0; i < nodeorder.size(); i++) {
			Node curNode = findNode(nodeorder.get(i), nodes);
			curNode.calcSlack();
			if(curNode.critical) critpath.add(curNode.name);
		}

		printNodes(nodes, nodeorder);
		System.out.println();
		System.out.println("Critical Path is as follows:");
		printElements(critpath);

	}
	
    private static void printTasks(ArrayList<Integer> nodeorder) {
    	System.out.println("Tasks inputted and have been determined to be processed in following order:");
		for(int i = 0; i < nodeorder.size(); i++) {
			System.out.print(nodeorder.get(i) + " ");
		}
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------");
    }

    private static void printNodes(ArrayList<Node> nodes, ArrayList<Integer> order) {
    	System.out.printf("%-7s %-7s %-7s %-7s %-7s %-7s %-7s", "Node", "ES", "EF", "LS", "LF", "Slack", "Critical?" );
    	System.out.println();
		for(int i = 0; i < nodes.size(); i++) {
			Node node = findNode(order.get(i), nodes);
			System.out.printf("%-8s", node.name);
			System.out.printf("%-8s", node.ES);
			System.out.printf("%-8s", node.EF);
			System.out.printf("%-8s", node.LS);
			System.out.printf("%-8s", node.LF);
			System.out.printf("%-8s", node.slack);
			if(node.critical) System.out.printf("%-7s", "!");
			System.out.println();
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
