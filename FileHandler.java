/*
    Authors: David Eriksson(id16den) & Dardan Dauti(id16ddi)
    Course: Fundamentals of Artificial Intelligence(5DV121)
    Program information: ANN Face Recognition
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {

    /**
     * Method: FileHandler - Constructor for the FileHandler class.
     */
    public FileHandler() {

    }

    /**
     * Method: ReadImage - Handles reading from image.txt file.
     * @param args - Program argument, should contain image.txt file.
     * @return listOfImage - List of all the image names and pixels read from the image.txt file.
     * @throws IOException
     */
    public ArrayList<Image> ReadImage(String args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(args));
        ArrayList<Image> listOfImages = new ArrayList<>();
        String namn = null;
        String line;

        while((line = br.readLine()) != null){
            if(line.startsWith("Image")){
                namn = line;
            }else if(!line.isEmpty() && !line.startsWith("#")){
                ArrayList<Double> pixels = new ArrayList<>();
                for (int j = 0; j < 20; j++){
                    String[] split = line.split(" ");
                    for (String aSplit : split) {
                        pixels.add((Double.parseDouble(aSplit) / 31));
                    }
                    line = br.readLine();
                }
                Image makeImage = new Image(namn, pixels);
                listOfImages.add(makeImage);
            }
        }
        return listOfImages;
    }

    /**
     * Method: ReadKeys - Handles reading from keys.txt file.
     * @param args - Program argument, should contain keys.txt file.
     * @return listOfAnswers - List of all the image names and keys read from the keys.txt file.
     * @throws IOException
     */
    public ArrayList<Key> ReadKeys(String args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(args));
        String names, line;
        Double key;
        ArrayList<Key> listOfAnswers = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            if (line.startsWith("Image")) {
                String[] split = line.split(" ");
                names = split[0];
                key = Double.parseDouble(split[1]);
                Key makeKey = new Key(names, key);
                listOfAnswers.add(makeKey);
            }
        }
        return listOfAnswers;
    }
}