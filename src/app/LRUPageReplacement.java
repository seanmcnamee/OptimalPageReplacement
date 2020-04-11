package app;

import java.util.LinkedList;

public class LRUPageReplacement extends PageReplacement {
    protected LinkedList<Integer> previousReferences = new LinkedList<Integer>();

    public LRUPageReplacement(String referenceString, int numOfFrames) throws Exception {
        super(referenceString, numOfFrames);
    }

    @Override
    protected boolean logicalAlgorithm(int next) {
        this.previousReferences.addFirst(next);
        return super.logicalAlgorithm(next);
    }

    @Override
    protected void oneStepAlgorithm(int next) {
        //Set priorities
        int[] priorities = new int[this.frames.length];
        for (int i = 0; i < this.frames.length; i++) {
            int priority = this.previousReferences.indexOf(this.frames[i]);
            priorities[i] = (priority == -1)? Integer.MAX_VALUE: priority;
        }

        //Choose worst
        int worstIndex = 0;
        for (int i = 1; i < this.frames.length; i++) {
            if (priorities[i] > priorities[worstIndex]) {
                worstIndex = i;
            }
        }

        //Replace it
        this.frames[worstIndex] = next;
    }

}