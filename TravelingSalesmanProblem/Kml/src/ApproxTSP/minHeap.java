package ApproxTSP;

public class minHeap {
	private Vertex[] heapArray;
	private int size;
	private int maxSize;
	private static final int FRONT = 1;
	
	public minHeap(int maxSize) {
		Vertex a = new Vertex();
		a.distance = Integer.MIN_VALUE;
		this.maxSize = maxSize;
		this.size = 0;
		heapArray = new Vertex[this.maxSize + 1];
		this.heapArray[0] = a;
		
	}
    
	public int getPosition(int index) {
		int temp = 0;
		for(int i=1; i <= size; i++) {
			if(heapArray[i].index==index) {
				temp = i;
			}
		}
		return temp;
		
	}
	
	public Vertex getVertex(int position) {
		return heapArray[position];
	}
	
	public int getSize() {
		return size;
	}
	
	private int getParent(int position) {
		return position/2;
	}
	
	private int getLeftchild(int position) {
		return position*2;
	}
	
	private int getRightchild(int position) {
		return (position*2)+1;
	}
	
	private boolean isLeaf(int position) {
		if (position >=  (size / 2)  &&  position <= size) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Swapping data between parent and child
	 * @param childPosition
	 * @param parentPosition
	 */
	public void swap(int childPosition, int parentPosition) {
        Vertex tmp = heapArray[childPosition];
        heapArray[childPosition] = heapArray[parentPosition];
        heapArray[parentPosition] = tmp;
    }
	
	/**
	 * add a new data
	 * @param data
	 */
//	public void insertData(Vertex data) {
//        size += 1;
//        heapArray[size] = data;
//        
//
//        int currentPosition = size;
//        int parentPosition = getParent(currentPosition);
//
//        while (heapArray[currentPosition].distance < heapArray[parentPosition].distance) {
//            swap(currentPosition, parentPosition);
//            currentPosition = parentPosition;
//            parentPosition = getParent(currentPosition);
//
//        }
//    }
	public void insertData(Vertex element)
    {
        heapArray[++size] = element;
        int current = size;
 
        while (heapArray[current].distance < heapArray[getParent(current)].distance)
        {
            swap(current,getParent(current));
            current = getParent(current);
        }	
    }

	
	/**
	 * get min value index from the tree
	 * @return
	 */
	public Vertex getMinValue() {
        Vertex minVal = heapArray[1];
        heapArray[1] = heapArray[size];
        size -= 1;
        if (size == 0) {
            System.out.println(" End of the MinHeap");
        } else {
            heapify(1);
        }

        return minVal;
    }
//	public Vertex getMinValue()
//    {
//        Vertex popped = heapArray[FRONT];
//        heapArray[FRONT] = heapArray[size--]; 
//        heapify(FRONT);
//        return popped;
//    }
	
	/**
	 * exchange parent value with smallest child
	 * @param position
	 */
	public void heapify(int position) {

        if (!isLeaf(position)) {
            int leftChild = getLeftchild(position);
            int rightChild = getRightchild(position);

            if (heapArray[position].distance > heapArray[leftChild].distance || heapArray[position].distance > heapArray[rightChild].distance) {
                if (heapArray[leftChild].distance < heapArray[rightChild].distance) {
                    swap(leftChild, position);
                    heapify(leftChild);
                } else {
                    swap(rightChild, position);
                    heapify(rightChild);
                }

            }

        }
    }
	
	
	
	
}
