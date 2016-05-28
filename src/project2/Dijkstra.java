package project2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 *
 * @author PritomKumar
 */
class Vertex implements Comparable<Vertex>{
    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
        public Vertex(String argName) { 
            name = argName; 
        }
        public String toString() { 
            return name;
        }
        public int compareTo(Vertex other){
        return Double.compare(minDistance, other.minDistance);
        }
        public boolean contains(String contains,Vertex[] vv){
            for(Vertex v:vv){
            if(v.name.equals(contains))
                return true;
            }
            return false;
        }
       

}


class Edge{
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight){ 
        target = argTarget; weight = argWeight; 
    
    }
}

public class Dijkstra{
    public static void computePaths(Vertex source){
        source.minDistance = 0;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies){
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < v.minDistance) {
		    vertexQueue.remove(v);

		    v.minDistance = distanceThroughU ;
		    v.previous = u;
		    vertexQueue.add(v);
		}
            }
        }
    }
    public static void computePathFromTo(Vertex source,Vertex end){
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies){
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < v.minDistance) {
		    vertexQueue.remove(v);

		    v.minDistance = distanceThroughU ;
		    v.previous = u;
		    vertexQueue.add(v);
		}
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target){
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);

        Collections.reverse(path);
        return path;
    }
    public static List<Vertex> getShortPathFromTo(Vertex source,Vertex target){
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);

        Collections.reverse(path);
        return path;
    }
    public static void insertPath(String file,String file1,Vertex[] v) throws Exception {
            FileReader fr = null;
            FileReader fr1 = null;
            //Vertex[] v=new Vertex[26];
            
            int count=0;
            char a;
                try {
                    fr = new FileReader(file);
                    fr1 = new FileReader(file1);
                    BufferedReader br = new BufferedReader(fr);
                    BufferedReader br1 = new BufferedReader(fr1);
                    String line,place,place1,line1;
                    while((line=br1.readLine())!=null){
                        
                            
                            place=line.charAt(0)+"";
                            v[count]=new Vertex(place); 
                            System.out.println(count+" "+place);
                                count++;
                            /*
                            if(count==0) {
                                v[count]=new Vertex(place); 
                                System.out.println(count+" "+place);
                            count++;
                            }
                            else if (count>0 && !v[count-1].name.equals(place)){
                            v[count]=new Vertex(place); 
                            System.out.println(count+" "+place+" "+v[count-1].name);
                            count++;
                            }
                                    */
                    }
                    fr1.close();
                    br.readLine();
                    br.readLine();
                    br.readLine();
                    while ((line1 = br.readLine()) != null) {
                                
                                    char find='A',find1='A';
                                    int number=0,number1=0;
                                    for(int i=0;i<26;i++){
                                        if(find==line1.charAt(2)){
                                          number=i;
                                            break;
                                        }
                                        find++;
                                    }
                                    for(int i=0;i<26;i++){
                                        if(find1==line1.charAt(0)){
                                          number1=i;
                                            break;
                                        }
                                        find1++;
                                    }
                                    v[number1].adjacencies=new Edge[]{ new Edge( v[number], Double.parseDouble(line1.substring(4)))};
                                    System.out.println( number1+" "+line1.charAt(0)+" "+number+" "+line1.charAt(2)+" "+Double.parseDouble(line1.substring(4)));
                                    //tree.delete(line);
                            }
                    //computePaths(v[1]);
                } 
                finally {
                    fr.close();
                    
                }
    }

    
}
