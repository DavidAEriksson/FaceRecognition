/*
    Authors: David Eriksson(id16den) & Dardan Dauti(id16ddi)
    Course: Fundamentals of Artificial Intelligence(5DV121)
    Program information: ANN Face Recognition
*/

public class Key {
    Double keys;
    String name;

    /**
     * Method: Constructor for the Key data structure.
     * @param name - The name of the image.
     * @param keys - The key of the image.
     */
    public Key(String name, Double keys) {
        this.name = name;
        this.keys = keys;
    }

    /**
     * Method: getKeys - Getter for the key of a Key object.
     * @return keys - The key of a image.
     */
    public Double getKeys() {
        return keys;
    }

}
