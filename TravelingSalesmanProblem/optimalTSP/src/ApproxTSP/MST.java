package ApproxTSP;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MST {
	
	private LinkedList<Integer>[] nodeList;

	
	
	public MST(Graph graph) {
		this.nodeList = findMST(buildVertex(graph), graph.weights);
	}
	
	
	public static LinkedList<Integer>[] findMST(Vertex[] vertex, double[][] weight) {
		int v = vertex.length;
		int visited[] = new int[v];
		int total, current;
		@SuppressWarnings("unchecked")		
		LinkedList<Integer>[] mst = new LinkedList[v];
      	minHeap heap = new minHeap(v-1);
		for (int i=1;i<v; i++) {
			heap.insertData(vertex[i]);
		}	
		total = 1;
		current = 0;
		visited[0]=1;
	
		//visitOrder.add(0, 0);
		for (int i=0; i<v; i++) {
			mst[i] = new LinkedList<Integer>();
		}
		//update all the data
		while(total!=v) {
			current  = heap.getVertex(1).index;
			mst[heap.getVertex(1).parent].add(heap.getVertex(1).index);	
			heap.getMinValue();
			
			for(int i = 1; i < v; i++) {			
				if ( weight[current][i] != 0) { //vertex are connected with other
					if( visited[i] == 0) { //vertex not in the mst					
						if (heap.getVertex(heap.getPosition(i)).distance > weight[current][i]) {
							heap.getVertex(heap.getPosition(i)).distance = weight[current][i];
							heap.getVertex(heap.getPosition(i)).parent = current;
							heap.heapify(heap.getPosition(i));							
						}else {
							continue;
						}
					}else {
						continue;
					}
				}else {
					continue;
				}
			}			
			total= total +1;
			
		}
				
		return mst;
	}
	
	/**
	 * select date user entered
	 * @param data
	 * @param start
	 * @param end
	 * @return
	 */
	public static ArrayList<String> selectDate(ArrayList<String> data, String start, String end){
		String[] startDay = start.split("/");
		int startY = Integer.parseInt(startDay[2]);
		int startD = Integer.parseInt(startDay[1]);
		int startM = Integer.parseInt(startDay[0]);
		String[] endDay = end.split("/");
		int endY = Integer.parseInt(endDay[2]);
		int endD = Integer.parseInt(endDay[1]);
		int endM = Integer.parseInt(endDay[0]);
		ArrayList<String> selectedDate = new ArrayList<String>();
		int n = data.size();
		for(int i = 0; i < n; i++) {
			String[] input = data.get(i).split(",");
			String[] dates = input[5].split("/");
			int year = Integer.parseInt(dates[2]);
			int day = Integer.parseInt(dates[1]);
			int month = Integer.parseInt(dates[0]);
			if(year >= startY && year <= endY) {
				if(month >= startM && month <= endM) {
					if(day >= startD && day <= endD) {
						selectedDate.add(data.get(i));
					}else {
						continue;
					}
				}else {
					continue;
				}
			}else {
				continue;
			}
		}
		return selectedDate;
	}
	
	/**
	 * input all the data
	 * @param fileName
	 * @return
	 */
	public static ArrayList<String> readFile(String fileName){
		ArrayList<String> dataArray = new ArrayList<String>();
		try {
			FileInputStream file = new FileInputStream(fileName);
			DataInputStream data = new DataInputStream(file);
			InputStreamReader reader = new InputStreamReader(data);
			BufferedReader buffer = new BufferedReader(reader);
			buffer.readLine();
			String lineInFile;
			while((lineInFile = buffer.readLine()) != null) {
				dataArray.add(lineInFile);
			}
			data.close();
			
		}catch(Exception e) {
			System.err.println("Error:" + e.getMessage());
		}
		
		return dataArray;
	}
	
	
	/**
	 * build graph from all the records
	 * @param records
	 * @return
	 */
	public static Graph buildGraph(ArrayList<String> records) {
		int size = records.size();
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<Double> y = new ArrayList<Double>();
		Graph graph = new Graph(size);
		for(int i = 0; i < size; i++ ) {
			String newRecord = records.get(i);
			graph.addRecord(i, newRecord);
			String[] splitedRecords = newRecord.split(",");
			x.add(i,Double.parseDouble(splitedRecords[0]));
			y.add(i,Double.parseDouble(splitedRecords[1]));		
		}
		
		for (int i = 0; i < size; i++) {
			double x1 = x.get(i);
			double y1 = y.get(i);
			for (int j = 0; j < size; j++) {
				double x2 = x.get(j);
				double y2 = y.get(j);
				double distance = Math.sqrt(Math.pow((x1-x2)*0.00018939, 2)+Math.pow((y1-y2)*0.00018939, 2));  
			    graph.addEdge(i, j, distance);
			}
		}
		return graph;		
	}
	
	/**
	 * build vertex from the graph
	 * @param graph
	 * @return
	 */
	public static Vertex[] buildVertex(Graph graph) {
		int size = graph.getSize();
		Vertex j = new Vertex();
		Vertex[] vertex = new Vertex[size];
		
		for (int i = 0; i < size; i++) {
			Vertex a = new Vertex();
			a.index = i;
			a.distance = graph.getWeight(0, i);
			a.parent = 0;
			vertex[i] = a;
		}
		return vertex;
	}
	
	/**
	 * preorder read of array of linkedlist
	 * @return
	 */
	public ArrayList<Integer> preorder(){
		ArrayList<Integer> preorderTSP = new ArrayList<Integer>();
		preorder(0,preorderTSP);
		preorderTSP.add(0);
		return preorderTSP;
	}
	
	public void preorder(int root, ArrayList<Integer> preorderTSP) {
		preorderTSP.add(root);
		if(nodeList[root] != null) {
			for(int i = 0; i < nodeList[root].size(); i++) {
				preorder(nodeList[root].get(i), preorderTSP);
			}
			
	   }
	   
	}
	
	
	public ArrayList<ArrayList<Integer>> permute(int[] num) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		result.add(new ArrayList<Integer>());
	 
		for (int i = 0; i < num.length; i++) {
			//list of list in current iteration of the array num
			ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();
	 
			for (ArrayList<Integer> l : result) {
				// # of locations to insert is largest index + 1
				for (int j = 0; j < l.size()+1; j++) {
					// + add num[i] to different locations
					l.add(j, num[i]);
	 
					ArrayList<Integer> temp = new ArrayList<Integer>(l);
					current.add(temp);
					l.remove(j);
				}
			}
	 
			result = new ArrayList<ArrayList<Integer>>(current);
		}
	 
		return result;
	}
	
	public ArrayList<ArrayList<Integer>> addIndex(ArrayList<ArrayList<Integer>> result){
		for(int i = 0; i < result.size();i++) {
			result.get(i).add(0);
			result.get(i).add(0, 0);
		}
		return result;
	}
	
	public ArrayList<Integer> optimalPath(Graph graph, ArrayList<ArrayList<Integer>> path){
		ArrayList<Integer> optimal  = path.get(0);
		double minLength = graph.length(optimal);
		for(int i = 0; i < path.size();i ++) {
			double temp = graph.length(path.get(i));
			if(temp <= minLength) {
				minLength = temp;
				optimal = path.get(i);
			}
		}
		return optimal;
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> allData = readFile("CrimeLatLonXY1990.csv");
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter start date");
		String start = input.nextLine();
		System.out.println("Enter end date");
		String end = input.nextLine();
		ArrayList<String> selectedDate = selectDate(allData,start,end);
		System.out.println("Crime records between" + start + "and" + end);
		for (int i = 0; i < selectedDate.size(); i++) {
			System.out.println(selectedDate.get(i) + " ");
		}
		Graph graph = buildGraph(selectedDate);
	    Vertex[] vertex = buildVertex(graph);
	    //System.out.print(vertex.length);
		MST mstPreorder = new MST(graph);
		ArrayList<Integer> tsp = mstPreorder.preorder();
		System.out.println("Hamiltonian Cycle(not necessarily optimum)");
		System.out.println(tsp);
		double length = graph.length(tsp);		
		System.out.println("Length of cycle:"+ length + "miles");
//		ArrayList<ArrayList<Integer>> tspoptimal = mstPreorder.combine(vertex.length - 1);
//		ArrayList<Integer> optimalcycle = mstPreorder.optimalPath(graph,tspoptimal );
//		System.out.println(optimalcycle);
        int[] cycle = new int[graph.getSize()-1];
        for(int i=0; i< graph.getSize()-1; i++) {
        	    cycle[i] = i+1;
        }
        ArrayList<ArrayList<Integer>> optimal = mstPreorder.permute(cycle);
        ArrayList<ArrayList<Integer>> optimalAdd = mstPreorder.addIndex(optimal);
        ArrayList<Integer> optimalPath = mstPreorder.optimalPath(graph, optimalAdd);
        System.out.println("Looking at every permutation to find the optimal solution");
        System.out.println("The best permulation");
        System.out.println(optimalPath);
        
        System.out.println("Optimal cycle length:" + graph.length(optimalPath));
		

		
	}

}
