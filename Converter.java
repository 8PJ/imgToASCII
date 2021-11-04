import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.Color;

import javax.imageio.ImageIO;

public class Converter {
    public static void main(String[] args) {

        String imgPath = "";
        BufferedImage img = null;

        try {
            imgPath = args[0]; // get image path
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("No file path provided");
            System.exit(0);
        }

        try {
            img = ImageIO.read(new File(imgPath)); // create a BufferedImage from the provided path
            if (img == null) {
                throw new IOException("Could not open image at " + imgPath);
            }
        } catch (IOException e) {
            System.err.println("File not found");
            System.exit(0);
        }

        char[][] ascii = getASCII(img);

        img = resize(img, 100);
    }

    private static char[][] getASCII(BufferedImage img) {

        int height = img.getHeight();
        int width = img.getWidth();

        char[][] ascii = new char[width][height]; // for storing ascii chars

        Color color;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                color = new Color(img.getRGB(i, j));

            }
        }

        return ascii;
    }

    private static BufferedImage resize(BufferedImage img, int maxDimention) {

        int height = img.getHeight();
        int width = img.getWidth();

        if (height > maxDimention || width > maxDimention) {

            double scale = (height > width) ? height : width;
            double ratio = maxDimention / scale;

            int newHeight = (int) (height * ratio);
            int newWidth = (int) (width * ratio);

            // Resize the image, so that height and length <= maxDimention
            Image temp = img.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
            img = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            img.getGraphics().drawImage(temp, 0, 0, null);
        }
        return img;
    }
}