package app;

/**
 * Priority Queue, where the lowest priority (highest number) is at the top
 */
public class LowestPriorityQueue {
    private PriorityItem[] queue;
    private int sizeOfQueue;

    public LowestPriorityQueue(int memoryPages) {
        this.queue = new PriorityItem[memoryPages];
        this.sizeOfQueue = 0;
    }

    public void addItem(Object data, int priority) {
        addItem(new PriorityItem(data, priority));
    }

    /**
     * Gets added to the end of the queue, then moved up depending on priority
     */
    public void addItem(PriorityItem item) {
        if (sizeOfQueue < this.queue.length) {

            this.queue[this.sizeOfQueue] = item;
            this.sizeOfQueue++;
            updatePriorityUpward(this.sizeOfQueue-1);
        }   else {
            this.queue[0] = item;
            updatePriorityDownward(0);
        }
        
        
    }

    /**
     * Note that the highest integer value is the next to be replaced
     */
    public void changeAllPrioritiesBy(int priorityChange) {
        for (PriorityItem item : this.queue) {
            item.setPriority(item.getPriority()+priorityChange);
        }
    }

    public void updatePriorityOf(Object data, int newPriority) {
        try {
            int itemToUpdate = findItemIndexWithData(data);
            int changeInPriority = newPriority-this.queue[itemToUpdate].getPriority();
            this.queue[itemToUpdate].setPriority(newPriority);
            if (changeInPriority > 0) {
                updatePriorityUpward(itemToUpdate);
            }   else {
                updatePriorityDownward(itemToUpdate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        String itemString = "";
        for (PriorityItem item : this.queue) {
            itemString += item.getData().toString() + " - " + item.getPriority() + "\n";
        }
        return itemString;
    }

    private int findItemIndexWithData(Object data) throws Exception {
        for (int i = 0; i < sizeOfQueue; i++) {
            if (this.queue[i].getData().equals(data)) {
                return i;
            }
        }
        throw new Exception("Can't find specified item!");
    }

    /**
     * Only checks for decreased priority, moving the object's position up if needed
     */
    private void updatePriorityUpward(int index) {
        int parentIndex;
        if ((parentIndex = getParentIndex(index)) != -1 && this.queue[parentIndex].hasPriority(this.queue[index])) {
            swap(index, parentIndex);
            updatePriorityUpward(parentIndex);
        }
    }

    /**
     * Moves element as far down the queue as possible, giving precedence to the left side
     * @param index
     */
    private void updatePriorityDownward(int index) {
        int leftChild, rightChild = 9999;
        if ((leftChild = getLeftChildIndex(index)) != -1 && this.queue[index].hasPriority(this.queue[leftChild])) {
            swap(index, leftChild);
            updatePriorityDownward(leftChild);
        }   else if ((rightChild = getRightChildIndex(index)) != -1 && this.queue[index].hasPriority(this.queue[rightChild])) {
            swap(index, rightChild);
            updatePriorityDownward(rightChild);
        }
    }

    /**
     * @return index of parent, -1 if an error
     */
    private int getParentIndex(int childIndex) {
        if (childIndex % 2 == 1)
            childIndex++;
        return childIndex / 2 - 1;
    }

    /**
     * @return index of left child, -1 if an error
     */
    private int getLeftChildIndex(int parentIndex) {
        int childIndex = 2 * parentIndex + 1;
        return (childIndex < this.sizeOfQueue) ? childIndex : -1;
    }

    /**
     * @return index of right child, -1 if an error
     */
    private int getRightChildIndex(int parentIndex) {
        int childIndex = 2 * parentIndex + 2;
        return (childIndex < this.sizeOfQueue) ? childIndex : -1;
    }

    private void swap(int index1, int index2) {
        PriorityItem temp = this.queue[index1];
        this.queue[index1] = this.queue[index2];
        this.queue[index2] = temp;
    }

}