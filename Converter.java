import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Converter {
    public static void main(String[] args) {

        String imgPath = "";

        try {
            imgPath = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("No file path provided");
            System.exit(0);
        }

        try {
            BufferedImage img = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            System.err.println("File not found");
        }

    }
}