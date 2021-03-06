package problem;

import java.util.*;

import graph.BFS;
import graph.Edge;
import graph.Graph;
import graph.Vertex;

public class ConnectedHorse {
	static long MOD = 1000000007;
	
	 // Method to find factorial of given number 
    static long factorial(long n) { 
        long res = 1; 
        for (long i=2; i<=n; i++) 
        	res = (res % MOD * (i % MOD)) % MOD; 
        return res; 
    } 
    
	public static void main(String[] args) {
		// Write your code here
        Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
        for(int t = 0; t < T; t++){
            int m, n, horses;
            m = sc.nextInt();
            n = sc.nextInt();
            horses = sc.nextInt();
            
            // read horses
            Graph<Vertex> G = new Graph<Vertex>(horses);
            int hx, hy;
            for(int i = 0; i < horses; i++) {
            	hx = sc.nextInt();
            	hy = sc.nextInt();
            	Vertex v = new Vertex(hx, hy);
            	G.addVertex(v);
            }
            
            // add edge to graph
            
            for(Vertex vt : G.getVertices()) {
            	// horse can jump at
            	for(int i = -2; i <= 2; i += 1) {
            		if(i == 0) {
            			continue;
            		}
            		
            		int dj = 0;
            		if(i == -2 || i == 2) {
            			dj = 1;
            		}
            		
            		if(i == 1 || i == -1) {
            			dj = 2;
            		}
            		
            		Vertex pe = new Vertex(vt.x + i, vt.y + dj);
        			if(G.contains(pe)) {
        				// horse at vt can jump to pe
        				Edge<Vertex> e = new Edge<Vertex>(vt, pe, 1);
        				G.addEdge(e);
        			}
        			
        			pe = new Vertex(vt.x + i, vt.y - dj);
        			if(G.contains(pe)) {
        				// horse at vt can jump to pe
        				Edge<Vertex> e = new Edge<Vertex>(vt, pe, 1);
        				G.addEdge(e);
        			}
            	}
            }
            
            // runBFS on graph to find connected components
            BFS<Vertex> bfs = new BFS<Vertex>(G);
            
            // get count of each connected component
            Map<Integer, HashSet<Vertex>> cc = bfs.getConnectedComponent();
            
            // find x! * y! * ... * z! where x, y, z are size of each connected component
            long ans = 1;
            for(Map.Entry<Integer, HashSet<Vertex>> entry: cc.entrySet()) {
            	HashSet<Vertex> nodes = entry.getValue();
            	ans *= factorial(nodes.size());
            	ans %= MOD;
            }
            System.out.println(ans);
        }
	}
}