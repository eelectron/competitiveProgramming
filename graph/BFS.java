package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class BFS<T extends Comparable>{
    Map<T, Boolean> vis;		// mark visited vertex
    HashMap<T, T> parent;		// maintain parent of each vertex
    Map<T, Integer> id;			// component id
    Graph<T> G;
    public BFS(Graph<T> G){
        vis = new HashMap<T, Boolean>();
        parent = new HashMap<T, T>();
        id = new HashMap<T, Integer>();
        this.G = G;
    }
    
    // visit all vertices reachable from s and 
    // save node's parent as well
    // save their component id
    public void visit(T s, int cid){
        Queue<T> q = new LinkedList<T>();
        q.add(s);
        vis.put(s, true);
        
        while(q.isEmpty() == false){
            T nd = q.remove();
            id.put(nd, cid);
            
            // add child
            for(Edge<T> e : G.adj(nd)){
                T v = e.other(nd);
                if(vis.containsKey(v) == false){
                    q.add(v);
                    vis.put(v, true);
                    parent.put(v, nd);
                }
            }
        }
    }
    
    boolean hasPath(T u, T v){
        return vis.get(u) && vis.get(v);
    }
    
    // print path from u to v in reverse order
    public Stack<T> getPath(T u, T v){
        // create bfs tree
        visit(u, 0);
        Stack<T> path = new Stack<T>();
        // build path from u to v in reverse order
        path.push(v);
        while(v != parent.get(v)){
            v = parent.get(v);
            if(v == null) {
            	break;
            }
            path.push(v);
        }
        return path;
    }
    
    /*Return true if graph is connected*/
    boolean isConnected(){
        for(int i = 0; i < G.V(); i++){
            if(vis.get(i) == false){
                return false;
            }
        }
        return true;
    }
    
    /*
    Return all connected component .
    */
    public Map<Integer, HashSet<T>> getConnectedComponent(){
        int cid = 0;
        for(T v : G.getVertices()){
            if(vis.containsKey(v) == false){
                visit(v, cid);
                cid += 1;
            }
        }
        
        Map<Integer, HashSet<T>> cc = new HashMap<Integer, HashSet<T>>();
        for(Map.Entry<T, Integer> entry : id.entrySet()){
            Integer comp = entry.getValue();
            if(cc.containsKey(comp) == false){
                cc.put(comp, new HashSet<T>());
            }
            
            // add vertex to its component
            cc.get(comp).add(entry.getKey());
        }
        return cc;
    }
};