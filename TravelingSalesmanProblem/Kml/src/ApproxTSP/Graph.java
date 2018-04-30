package ApproxTSP;

import java.util.ArrayList;

public class Graph {
     public double [][] weights;
     public int vertexCount;
     public String record[];
     
     public Graph(int vertexCount) {
    	      this.vertexCount = vertexCount;
    	      this.weights = new double[vertexCount][vertexCount]; 
    	      this.record = new String[vertexCount];
     }
     
     public void addEdge(int i, int j, double d) {
    	     if (i >= 0 && i < vertexCount && j > 0 && j < vertexCount) {
             weights[i][j] = d;
             weights[j][i] = d;
         }
     }
     
     
     public String getRecord(int i) {
	      return record[i];
     }
     public double length(ArrayList<Integer> preorder) {
    	      double length = 0;
    	      for(int i = 0; i < preorder.size()-1; i++) {
    	    	     length = length + weights[preorder.get(i)][preorder.get(i+1)];
    	    	     
    	      }
    	      return length;
     }
     
     public void addRecord(int index, String data) {
    	       record[index] = data;
     }
     
     public int getSize() {
    	      return vertexCount;
     }
     
     public double getWeight(int i, int j) {
    	      return weights[i][j];
     }
     
     
     
     public void deleteEdge(int i, int j) {
    	    if (i >= 0 && i < vertexCount && j > 0 && j < vertexCount) {
             weights[i][j] = 0;
             weights[j][i] = 0;
       }
     } 
     
     
   
    		 
}
