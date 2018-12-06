/*
    Authors: David Eriksson(id16den) & Dardan Dauti(id16ddi)
    Course: Fundamentals of Artificial Intelligence (5DV121)
    Program information: ANN Face Recognition
*/

import java.io.IOException;
import java.util.ArrayList;


public class Network {

    static final double LC = 0.1;

    FileHandler handler = new FileHandler();

    double[] weightOne = new double[400];
    double[] weightTwo = new double[400];
    double[] weightThree = new double[400];
    double[] weightFour = new double[400];

    ArrayList<Image> trainingImages;
    ArrayList<Image> testImages;
    ArrayList<Key> holdsActualValuesList;
    ArrayList<Double> keyList = new ArrayList<>();
    ArrayList<Double> pixelValue = new ArrayList<>();

    Double neuronOne = 0.0;
    Double neuronTwo = 0.0;
    Double neuronThree = 0.0;
    Double neuronFour = 0.0;

    Double expectedValue = 0.0;
    Double expectedValueOne = 0.0;
    Double expectedValueTwo = 0.0;
    Double expectedValueThree = 0.0;
    Double expectedValueFour = 0.0;

    Double errorOne = 0.0;
    Double errorTwo = 0.0;
    Double errorThree = 0.0;
    Double errorFour = 0.0;

    double actualValueOne = 0;
    double actualValueOTwo = 0;
    double actualValueThree = 0;
    double actualValueFour = 0;

    double testValueOne = 0;
    double testValueOTwo = 0;
    double testValueThree= 0;
    double testValueFour = 0;

    Integer beginHere = 0;

    /**
     * Method: Network - Constructor for the Network class
     * @throws IOException
     */
    public Network() throws IOException {
        setRandomWeightValues(weightOne);
        setRandomWeightValues(weightTwo);
        setRandomWeightValues(weightThree);
        setRandomWeightValues(weightFour);
        //Collections.shuffle(trainingImages);
    }

    /**
     * Method: setRandomWeightValues - Sets a random value of a weight.
     * @param weightArray - Weight to be randomized.
     * @return weightArray - Randomized weight.
     */
    public double[] setRandomWeightValues(double[] weightArray){
        for (int i = 0; i < 400; i++){
            weightArray[i] = Math.random();
        }
        return weightArray;
    }

    /**
     * Method: trainingSet - Trains the ANN on the files from the program arguments.
     * @param imgArg - Program argument for the image.txt file.
     * @param keyArg - Program argument for the keys.txt file.
     * @throws IOException
     */
    public void trainingSet(String imgArg, String keyArg) throws IOException {
        beginHere = 0;
        trainingImages = handler.ReadImage(imgArg);
        holdsActualValuesList = handler.ReadKeys(keyArg);

        for (Key key : holdsActualValuesList){
            keyList.add(key.getKeys());
        }

        for (int x = 0; x < (trainingImages.size() * 0.66); x++){

            Image img = trainingImages.get(x);
            expectedValue = keyList.get(x);

            setExpectedValues();

            pixelValue = img.getPixels();

            neuronOne = 0.0;
            neuronTwo = 0.0;
            neuronThree = 0.0;
            neuronFour = 0.0;

            neuronOne = initiateNeurons(neuronOne, weightOne);
            neuronTwo = initiateNeurons(neuronTwo, weightTwo);
            neuronThree = initiateNeurons(neuronThree, weightThree);
            neuronFour = initiateNeurons(neuronFour, weightFour);

            actualValueOne = sigmoidCalc(neuronOne);
            actualValueOTwo = sigmoidCalc(neuronTwo);
            actualValueThree = sigmoidCalc(neuronThree);
            actualValueFour = sigmoidCalc(neuronFour);

            errorOne = expectedValueOne - actualValueOne;
            errorTwo = expectedValueTwo - actualValueOTwo;
            errorThree = expectedValueThree - actualValueThree;
            errorFour = expectedValueFour - actualValueFour;

            for (int i = 0; i < 400; i++) {
                weightOne[i] = weightOne[i] + (errorOne * LC * pixelValue.get(i));
                weightTwo[i] = weightTwo[i] + (errorTwo * LC * pixelValue.get(i));
                weightThree[i] = weightThree[i] + (errorThree * LC * pixelValue.get(i));
                weightFour[i]= weightFour[i] + (errorFour * LC * pixelValue.get(i));
            }
            beginHere++;
        }
    }

    /**
     * Method: setExpectedValues - Sets the expected value based on the current key.
     */
    public void setExpectedValues(){
        if (expectedValue == 1.0){
            expectedValueOne = 1.0;
            expectedValueTwo = 0.0;
            expectedValueThree = 0.0;
            expectedValueFour = 0.0;
        }
        else if (expectedValue == 2.0){
            expectedValueOne = 0.0;
            expectedValueTwo = 1.0;
            expectedValueThree = 0.0;
            expectedValueFour = 0.0;
        }
        else if (expectedValue == 3.0){
            expectedValueOne = 0.0;
            expectedValueTwo = 0.0;
            expectedValueThree = 1.0;
            expectedValueFour = 0.0;
        }
        else if (expectedValue == 4.0){
            expectedValueOne = 0.0;
            expectedValueTwo = 0.0;
            expectedValueThree = 0.0;
            expectedValueFour = 1.0;
        }
    }

    /**
     * Method: getBiggestValue - Compares the values of the neurons.
     * @param valueOne - Value of neuron one.
     * @param valueTwo - Value of neuron two.
     * @param valueThree - Value of neuron three.
     * @param valueFour - Value of neuron four.
     * @return - Returns the largest value.
     */
    public int getBiggestValue(double valueOne, double valueTwo, double valueThree, double valueFour){
        double biggestValue = Math.max(Math.max(valueOne, valueTwo),Math.max(valueThree, valueFour));
        if (biggestValue == valueOne)
            return 1;
        else if (biggestValue == valueTwo)
            return 2;
        else if (biggestValue == valueThree)
            return 3;
        else return 4;
    }

    /**
     * Method: sigmoidCalc - Activation method for the neurons.
     * @param value - Neuron to be calculated.
     * @return - Returns calculated neuron.
     */
    public double sigmoidCalc(double value){
        return  (1.0 / (1 + Math.exp(-value)));
    }

    /**
     * Method: testSet - Tests the ANN on the pictures.
     * @return - Returns total error.
     */
    public double testSet() {
        double testScore = 0.0;
        double totalError = 0.0;
        for (int x = beginHere; x < trainingImages.size() ; x++){
            Image test = trainingImages.get(x);
            expectedValue = keyList.get(x);
            pixelValue = test.getPixels();

            setExpectedValues();

            neuronOne = 0.0;
            neuronTwo = 0.0;
            neuronThree = 0.0;
            neuronFour = 0.0;

            neuronOne = initiateNeurons(neuronOne, weightOne);
            neuronTwo = initiateNeurons(neuronTwo, weightTwo);
            neuronThree = initiateNeurons(neuronThree, weightThree);
            neuronFour = initiateNeurons(neuronFour, weightFour);

            testValueOne = sigmoidCalc(neuronOne);
            testValueOTwo = sigmoidCalc(neuronTwo);
            testValueThree = sigmoidCalc(neuronThree);
            testValueFour = sigmoidCalc(neuronFour);

            if (getBiggestValue(testValueOne, testValueOTwo, testValueThree, testValueFour) == expectedValue) {
                testScore++;
            }

            errorOne = expectedValueOne - testValueOne;
            errorTwo = expectedValueTwo - testValueOTwo;
            errorThree = expectedValueThree - testValueThree;
            errorFour = expectedValueFour - testValueFour;

            totalError += (Math.pow(errorOne,2) + Math.pow(errorTwo,2) + Math.pow(errorThree,2) + Math.pow(errorFour,2));
        }
        return totalError / (trainingImages.size() - beginHere);
    }

    /**
     * Method: initiateNeuron - This method is set to summarize the weights in each neurons weight array.
     * @param neuron - Our neuron that is to be summarized.
     * @param array - The array that is filled with all the weights.
     * @return - The summarized neuron
     */
    public double initiateNeurons(double neuron, double[] array) {
        for (int i = 0; i < 400; i++) {
            neuron += (array[i] * pixelValue.get(i));
        }
        return neuron;
    }

    /**
     * Method: testLabres - Runs the testing method to Labres with the desired printouts for the assignment.
     * @param args - File that is to be tested with the program.
     * @throws IOException
     */
    public void testLabres(String args) throws IOException{
        testImages = handler.ReadImage(args);

        for(int i = 0; i < testImages.size(); i++){
            Image testingLab = testImages.get(i);
            pixelValue = testingLab.getPixels();

            neuronOne = 0.0;
            neuronTwo = 0.0;
            neuronThree = 0.0;
            neuronFour = 0.0;

            neuronOne = initiateNeurons(neuronOne, weightOne);
            neuronTwo = initiateNeurons(neuronTwo, weightTwo);
            neuronThree = initiateNeurons(neuronThree, weightThree);
            neuronFour = initiateNeurons(neuronFour, weightFour);


            testValueOne = sigmoidCalc(neuronOne);
            testValueOTwo = sigmoidCalc(neuronTwo);
            testValueThree = sigmoidCalc(neuronThree);
            testValueFour = sigmoidCalc(neuronFour);

            System.out.println(testingLab.getName() + " " + getBiggestValue(testValueOne, testValueOTwo, testValueThree, testValueFour));
        }
    }
}
