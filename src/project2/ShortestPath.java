package project2;

import java.util.*;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

class Node {

    public int label;   // this node's label (parent node in path tree)
    public int weight;  // weight of edge to this node (distance to start)

    public Node(int v, int w) { // Constructor:
        label = v;
        weight = w;
    }
}

public class ShortestPath {

    public Scanner in;         // for standard input
    public int n, m;           // n = #vertices, m = #edges
    public LinkedList[] graph; // adjacency list representation
    public int start, end;      // start and end points for shortest path
    String allPath = "";
    String matrix = "";

    public void setAllPath(String allPath) {
        this.allPath = allPath;
    }

    public String getAllPath() {
        return allPath;
    }

    public void setMatrix(String matrix) {
        this.matrix = matrix;
    }

    public String getmatrix() {
        return matrix;
    }

    public ArrayList shortest(int start, int end) {
        this.start = start;
        this.end = end;
        ArrayList arr = new ArrayList();
        boolean[] done = new boolean[n]; // fill in as each node is completed

        // NEXT LOOP IS NOT NEEDED -- false is the default initial value for
        // boolean arrays
        // for (int i = 0; i < n; i++) done[i] = false;
        // Create table of shortest distance known so far (namely, all
        // nodes except "start" are infinitely far from start and have no
        // parent in the shortest path tree:
        Node[] table = new Node[n];
        for (int i = 0; i < n; i++) {
            table[i] = new Node(-1, Integer.MAX_VALUE); // no parent, infinite dist.
        }
        table[start].weight = 0; // Initially, we only know how to get to "start"

        // Build up shortest paths by adding nearest uncompleted node:
        for (int count = 0; count < n; count++) {
            // find smallest distance among all nodes that aren't yet done:
            int min = Integer.MAX_VALUE;
            int minNode = -1;
            for (int i = 0; i < n; i++) {
                if (!done[i] && table[i].weight < min) {
                    min = table[i].weight;
                    minNode = i;
                }
            }

            done[minNode] = true; // we are now finished with this node

      // Update each neighbor's distance if minNode creates a shorter path:
            // Prepare to loop through the elements of minNode's adjacency list:
            ListIterator iter = graph[minNode].listIterator();
            while (iter.hasNext()) {
                Node nd = (Node) iter.next();
                int v = nd.label;
                int w = nd.weight;

         // See if it is shorter to go from start to minNode weightto v than
                // v's current recorded distance from start:
                if (!done[v] && table[minNode].weight + w < table[v].weight) {
                    table[v].weight = table[minNode].weight + w;
                    table[v].label = minNode;
                }
            }
        }
        setAllPath("");
        for (int i = 0; i < n; i++) {
            if (table[i].weight < Integer.MAX_VALUE) {
                if ((table[i].weight) > 10000) {
                    table[i].weight = table[i].weight - 10000;
                }
                allPath += "Path from " + convert(i) + " to " + convert(this.start) + " with distance " + (table[i].weight) / 100.0 + "km:(or min for bus)" + convert(i) + "-->";
                System.out.print("Path from " + convert(i) + " to " + convert(this.start) + " with distance "
                        + (table[i].weight) / 100.0 + "km(or min for bus): " + convert(i) + "-->");
                if (end == i) {
                    arr.add(i);
                }
                int next = table[i].label;
                while (next >= 0) {
                    if (table[next].label >= 0) {
                        System.out.print(convert(next) + "-->");
                        allPath += convert(next) + "-->";
                        if (end == i) {
                            arr.add(next);
                        }
                    } else {
                        System.out.print(convert(next) + " ");
                        allPath += convert(next) + " ";
                    }
                    next = table[next].label;
                }
                System.out.println();
                allPath += "\n";
            } else {
                System.out.println("No path from " + i + " to " + start);
                allPath += "No path from " + i + " to " + start;
            }

        }
        arr.add(start);
        return arr;
    }

    public void allShortest() {
        boolean[] done = new boolean[n]; // fill in as each node is completed
        Node[] table = new Node[n];
        for (int i = 0; i < n; i++) {
            table[i] = new Node(-1, Integer.MAX_VALUE); // no parent, infinite dist.
        }
        table[start].weight = 0; // Initially, we only know how to get to "start"

        // Build up shortest paths by adding nearest uncompleted node:
        for (int count = 0; count < n; count++) {
            // find smallest distance among all nodes that aren't yet done:
            int min = Integer.MAX_VALUE;
            int minNode = -1;
            for (int i = 0; i < n; i++) {
                if (!done[i] && table[i].weight < min) {
                    min = table[i].weight;
                    minNode = i;
                }
            }

            done[minNode] = true; // we are now finished with this node
            
            ListIterator iter = graph[minNode].listIterator();
            while (iter.hasNext()) {
                Node nd = (Node) iter.next();
                int v = nd.label;
                int w = nd.weight;

                if (!done[v] && table[minNode].weight + w < table[v].weight) {
                    table[v].weight = table[minNode].weight + w;
                    table[v].label = minNode;
                }
            }
        }
        char a = 'A';
        System.out.print("       ");
        matrix += "       ";
        for (int z = 0; z < 26; z++) {
            System.out.print(a + "        ");
            matrix += a + "        ";
            a++;
        }
        System.out.println("");
        matrix.concat("\n");
        for (int i = 0; i < n; i++) {
            if (table[i].weight < Integer.MAX_VALUE) {
                System.out.print(convert(i));
                matrix += convert(i);
                for (int j = 0; j < n; j++) {
                    if (shortestFromTo(i, j) < 10) {
                        System.out.printf("     " + "%.2f", shortestFromTo(i, j));
                        matrix += "     " + shortestFromTo(i, j);
                    } else {
                        System.out.printf("    " + "%.2f", shortestFromTo(i, j));//(table[j].weight)/100.0);
                        matrix += "    " + shortestFromTo(i, j);
                    }

                    int next = table[j].label;
                    while (next >= 0) {
                        next = table[next].label;
                    }
                }
            }
            System.out.println();
            matrix.concat("\n");
        }
    }

    public double shortestFromTo(int start, int end) {
        this.start = start;
        this.end = end;
        boolean[] done = new boolean[n]; // fill in as each node is completed

    
        Node[] table = new Node[n];
        for (int i = 0; i < n; i++) {
            table[i] = new Node(-1, Integer.MAX_VALUE); // no parent, infinite dist.
        }
        table[start].weight = 0; // Initially, we only know how to get to "start"
        
        for (int count = 0; count < n; count++) {
            
            int min = Integer.MAX_VALUE;
            int minNode = -1;
            for (int i = 0; i < n; i++) {
                if (!done[i] && table[i].weight < min) {
                    min = table[i].weight;
                    minNode = i;
                }
            }

            done[minNode] = true; // we are now finished with this node

            ListIterator iter = graph[minNode].listIterator();
            while (iter.hasNext()) {
                Node nd = (Node) iter.next();
                int v = nd.label;
                int w = nd.weight;
                
                if (!done[v] && table[minNode].weight + w < table[v].weight) {
                    table[v].weight = table[minNode].weight + w;
                    table[v].label = minNode;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (table[i].weight < Integer.MAX_VALUE) {
                if (end == i) {
                    if ((table[i].weight) > 10000) {
                        table[i].weight = table[i].weight - 10000;
                    }
                    return ((table[i].weight) / 100.0);
                }
                int next = table[i].label;
                while (next >= 0) {
                    next = table[next].label;
                }
                //System.out.println();
            } else {
                System.out.println("No path from " + i + " to " + start);
            }
        }
        return 0.0;
    }

    public char convert(int num) {
        char a = 'A';
        for (int i = 0; i < 26; i++) {
            if (i == num) {
                return a;
            }
            a++;
        }
        return a;
    }
    
    //determine ifthe road is one way or two way
    public boolean oneWayTwo(char from, char to) {
        if ((from == 'U' && to == 'V') || (from == 'V' && to == 'W') || (from == 'W' && to == 'X') || (from == 'X' && to == 'U')) {
            return false;
        }
        return true;
    }
    
    //determine if the path is valid for car or not
    public boolean pathForCar(char from, char to) {
        if ((from == 'A' && to == 'B') || (from == 'B' && to == 'A') || (from == 'B' && to == 'C') || (from == 'C' && to == 'B')) {
            return true;
        } 
        else {
            return false;
        }
    }

    //determine if the path is for walking or not
    public boolean pathForWalk(char from, char to) {
        if ((from == 'P' && to == 'T') || (from == 'T' && to == 'Q')
                || (from == 'P' && to == 'Q') || (from == 'Q' && to == 'R') || (from == 'Q' && to == 'S')
                || (from == 'T' && to == 'P') || (from == 'Q' && to == 'T') || (from == 'Q' && to == 'P')
                || (from == 'R' && to == 'Q') || (from == 'S' && to == 'Q')) {
            return true;
        } 
        else {
            return false;
        }
    }
    
    //determine if the path has a bus stop
    public boolean pathForBus(char from, char to) {
        if ((from == 'F' && to == 'K') || (from == 'K' && to == 'T')
                || (from == 'B' && to == 'G') || (from == 'G' && to == 'L') || (from == 'L' && to == 'R')
                || (from == 'R' && to == 'Z') || (from == 'Z' && to == 'Y')) 
            return true;
       else if ((from == 'K' && to == 'F') || (from == 'T' && to == 'K')
                || (from == 'G' && to == 'B') || (from == 'L' && to == 'G') || (from == 'R' && to == 'L')
                || (from == 'Z' && to == 'R') || (from == 'Y' && to == 'Z')) 
            return true;    
         else {
            return false;
        }
    }
    
    //determine if the input path is valid or not
    public boolean validPath(String path){
    if(path.length()>1 || path.length()<1)
        return false;
    else if(path.length()==1){
        for(int i=65;i<=91;i++){
        if((char)i==path.charAt(0))
            return true;
        }
        return false;
    }
    else 
        return false;
    
    }

    // FOR DEBUGGING ONLY:
    public void displayGraph() {
        for (int i = 0; i < n; i++) {
            System.out.print(convert(i) + ": ");
            ListIterator nbrs = graph[i].listIterator(0);
            while (nbrs.hasNext()) {
                Node nd = (Node) nbrs.next();
                System.out.print(convert(nd.label) + "(" + (nd.weight) / 100.0 + ") ");
            }
            System.out.println();
        }
    }

    public void voice(String s) {
        try {
            System.setProperty("freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            Synthesizer synthesizer
                    = Central.createSynthesizer(new SynthesizerModeDesc(Locale.ENGLISH));

            synthesizer.allocate();
            synthesizer.resume();
            synthesizer.speakPlainText(s, null);
            //synthesizer.waitEngineState(1000000);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
            synthesizer.deallocate();
            synthesizer.removeEngineListener(null);
            //synthesizer.resume();
            //synthesizer.cancel();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
