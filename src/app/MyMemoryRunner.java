package app;
import java.util.Scanner;

public class MyMemoryRunner {
    public static void main(String[] args) {
        String referencePrompt = "Enter a reference string:\n\t";
        String framesPrompt = "Enter the # of frames:\n\t";
        String simulatingOptimal = "\nRunning Optimal Replacement Simulation:\n";
        String simulatingLRU = "\nRunning LRU Replacement Simulation:\n";
        String inputError = "There was an error with you input. " +
                            "\nThe reference string should be single space seperated. Ex: '8 6 7 5 3 0 9' " + 
                            "\nThe number of frames should be an integer.\nTry again.\n\n";
        PageReplacement optimal;
        PageReplacement leastRecentlyUsed;
        
        Scanner input = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(referencePrompt);
                String reference = input.nextLine();
        
                System.out.print(framesPrompt);
                String strFrames = input.nextLine();

                int frames = Integer.parseInt(strFrames);

                optimal = new OptimalPageReplacement(reference, frames);
                leastRecentlyUsed = new LRUPageReplacement(reference, frames);
                break;
            } catch (Exception e) {
                System.out.print(inputError);
            }
        }
        input.close();
        
        System.out.println(simulatingOptimal);
        optimal.simulate();

        System.out.println(simulatingLRU);
        leastRecentlyUsed.simulate();

    }
}
