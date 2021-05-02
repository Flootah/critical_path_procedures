import java.util.ArrayList;

public class Node {
	public int name;
	public ArrayList<Integer> req;
	public ArrayList<Integer> successors;
	public int time;
	public int ES;
	public int EF;
	public int LS;
	public int LF;
	public int slack;
	public boolean critical;
	
	public Node(int n, ArrayList<Integer> r, int t) {
		name = n;
		req = r;
		time = t;
		successors = new ArrayList<Integer>();
		ES = -1;
		EF = -1;
		LS = -1;
		LF = Integer.MAX_VALUE;
		slack = -1;
	}
	
	public void addSuccessor(int i) {
		successors.add(i);
	}

	public void calcSlack() {
		slack = LS-ES;
		if(slack == 0) {
			critical = true;
		}
	}
}
