package app;

public class MyMemory {
    public static void main(String[] args) {
        var memory = new LowestPriorityQueue(3);
        memory.addItem("Sean", 3);
        memory.addItem("Rob", 2);
        memory.addItem("Tom", 1);
        System.out.println(memory.toString());
        memory.updatePriorityOf("Tom", 5);
        System.out.println(memory.toString());
        memory.updatePriorityOf("Tom", 1);
        System.out.println(memory.toString());
        memory.addItem("New Person", 2);
        System.out.println(memory.toString());
    }
}
