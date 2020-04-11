package app;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class PageReplacement {
    protected LinkedList<Integer> referenceOrder = new LinkedList<Integer>();
    protected int[] frames;
    protected int pageFaults;
    private final String MEM_IS = ": Memory is: ";
    private final String PAGE_FAULT = ": Page Fault: ";
    private final String HIT = ": Hit: ";
    private final String PAGE_FAULT_COUNT = "(Number of Page Faults: ";
    private final String CLOSE_FAULT_COUNT = ")";
    private final String TOTAL_PAGE_FAULTS = "Total Number of Page Faults: ";

    public PageReplacement(String referenceString, int numOfFrames) throws Exception {
        createReferenceOrder(referenceString);
        createFrames(numOfFrames);
        this.pageFaults = 0;
    }

    public void simulate() {
        System.out.println("Start" + this.MEM_IS + this.getFrameString());
        
        while (referenceOrder.size() > 0) {
            // Simulate logically
            int next = this.referenceOrder.removeFirst();
            boolean ispageFault = logicalAlgorithm(next);

            // Print out information
            System.out.println(next + MEM_IS + this.getFrameString() + ((ispageFault) ? PAGE_FAULT : HIT)
                    + this.PAGE_FAULT_COUNT + this.pageFaults + this.CLOSE_FAULT_COUNT);
        }

        System.out.println(TOTAL_PAGE_FAULTS + this.pageFaults);
    }

    /**
     * @param next : the next page to be processed
     * @return true when there is a page fault. False for a hit
     */
    protected boolean logicalAlgorithm(int next) {
        boolean ispageFault = true;
        // Case 1: If a hit, no page fault
        if (hitInFrames(next)) {
            ispageFault = false;
        } else {
            // Case 2: If the frame isn't full yet, page fault and add it to the frame
            int inUse = framesInUse();
            if (inUse < this.frames.length) {
                this.frames[inUse] = next;
            } else {
                // Case 3: Optimize based on algorithm
                oneStepAlgorithm(next);
            }
            this.pageFaults++;
        }
        return ispageFault;
    }

    /**
     * Removes specific page based on the replacement method
     */
    protected abstract void oneStepAlgorithm(int next);

    /**
     * returns true if given integer is currently in the frames
     */
    private boolean hitInFrames(int check) {
        for (int page : this.frames) {
            if (check == page) {
                return true; // HIT!
            }
        }
        return false;
    }

    /**
     * Gets a count of the amount of frames in use Also gets the next unused index
     */
    private int framesInUse() {
        for (int i = 0; i < this.frames.length; i++) {
            if (this.frames[i] == -1) {
                return i;
            }
        }
        return this.frames.length;
    }

    private void createFrames(int numOfFrames) {
        this.frames = new int[numOfFrames];
        for (int i = 0; i < numOfFrames; i++) {
            this.frames[i] = -1;
        }
    }

    private void createReferenceOrder(String referenceString) throws Exception {
        while (referenceString.length() > 0) {
            // Put each number in a queue
            int indexStoppingAt = referenceString.indexOf(" ");
            if (indexStoppingAt < 0) {
                indexStoppingAt = referenceString.length();
            }

            // Check for proper conversion
            String stringToConvert = referenceString.substring(0, indexStoppingAt);
            try {
                int value = Integer.parseInt(stringToConvert);
                this.referenceOrder.add(value);
                if (indexStoppingAt == referenceString.length()) {
                    break;
                } else {
                    referenceString = referenceString.substring(indexStoppingAt + 1);
                }
            } catch (Exception e) {
                throw new Exception(
                        "Reference String must only consist of numbers! " + stringToConvert + " was invalid.");
            }
        }
    }

    private String getFrameString() {
        String frameString = "";
        for (int i : this.frames) {
            String symbolToAdd = i + "";
            if (i == -1) {
                symbolToAdd = "*";
            }
            frameString += symbolToAdd + " ";
        }
        return frameString;
    }

    ///////////// For debugging ///////////////////

    protected String getReferenceString() {
        String referenceString = "";
        Iterator<Integer> iterator = this.referenceOrder.iterator();
        while (iterator.hasNext()) {
            referenceString += iterator.next() + " ";
        }
        return referenceString;
    }

    protected void printFrame() {
        System.out.println(getFrameString());
    }

}