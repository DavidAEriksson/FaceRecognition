/*
    Authors: David Eriksson(id16den) & Dardan Dauti(id16ddi)
    Course: Fundamentals of Artificial Intelligence(5DV121)
    Program information: ANN Face Recognition
*/

import java.util.ArrayList;

public class Image {
    ArrayList<Double> pixels = new ArrayList<>();
    String name;

    /**
     * Method: Image - Constructor for the Image data structure.
     * @param name - The name of the image.
     * @param pixels - The pixels of the image.
     */
    public Image(String name, ArrayList<Double> pixels) {
        this.pixels = pixels;
        this.name = name;
    }

    /**
     * Method: getPixels - Getter for the pixels of a Image object.
     * @return pixels - The pixels of the image.
     */
    public ArrayList<Double> getPixels() {
        return pixels;
    }

    /**
     * Method: getName - Getter for the name of a Image object.
     * @return name - The name of the image.
     */
    public String getName() {
        return name;
    }
}
