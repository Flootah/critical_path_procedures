import java.util.ArrayList;

public class Node {
	public int name;
	public ArrayList<Integer> req;
	public int time;
	public int ES;
	public int EF;
	public int LS;
	public int LF;
	
	public Node(int n, ArrayList<Integer> r, int t) {
		name = n;
		req = r;
		time = t;
	}
}
