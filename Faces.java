/*
    Authors: David Eriksson(id16den) & Dardan Dauti(id16ddi)
    Course: Fundamentals of Artificial Intelligence(5DV121)
    Program information: ANN Face Recognition
*/



import java.io.*;


public class Faces {

    /**
     * Method: main method - Runs the program, controls minimum and maximum iterations and handles back propagation error minimum.
     * @param args - Program arguments, contains various files.
     */
    public static void main(String[] args) throws IOException {

        Network network = new Network();
        double totalErrorValue = 1;
        int iteration = 0;
        int minIteration = 20;
        int maxIterations = 100;
        while ((iteration < maxIterations) && (totalErrorValue > 0.525) || (iteration < minIteration)){
            network.trainingSet(args[0], args[1]);
            iteration++;
            totalErrorValue = network.testSet();
        }
        network.testLabres(args[2]);
    }
}
