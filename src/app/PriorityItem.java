package app;

public class PriorityItem {
    private Object data;
    private int priority;

    public PriorityItem(Object data, int priority) {
        this.data = data;
        this.priority = priority;
    }

    public void setPriority(int newPriority) {
        this.priority = newPriority;
    }

    public boolean hasPriority(PriorityItem other) {
        return this.priority < other.priority;
    }

    public int getPriority() {
        return this.priority;
    }

    public Object getData() {
        return data;
    }

    public Integer getIntData() {
        return (Integer)data;
    }

}