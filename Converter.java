import java.awt.image.BufferedImage;
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

        img = resize(img, 80);

        char[][] ascii = getASCII(img);
        printASCII(ascii);
    }

    private static void printASCII(char[][] ascii) {

        for (int i = 0; i < ascii.length; i++) {
            for (int j = 0; j < ascii[0].length; j++) {
                System.out.print(ascii[i][j]);
            }
            System.out.println();
        }
    }

    private static char[][] getASCII(BufferedImage img) {

        // different chars for different strengths of luminance
        char[] strength = new char[11];
        strength[0] = ' ';
        strength[1] = '.';
        strength[2] = ':';
        strength[3] = '-';
        strength[4] = '=';
        strength[5] = '+';
        strength[6] = ':';
        strength[7] = '*';
        strength[8] = '#';
        strength[9] = '%';
        strength[10] = '@';

        int height = img.getHeight();
        int width = img.getWidth();

        char[][] ascii = new char[height][width];

        Color color;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                color = new Color(img.getRGB(j, i));
                double luminance = 0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue();
                ascii[i][j] = strength[(int) (luminance / 23.877)];
            }
        }
        return ascii;
    }

    private static BufferedImage resize(BufferedImage img, int maxDimention) {

        int height = img.getHeight();
        int width = img.getWidth();
        double scale;

        if (height > maxDimention || width > maxDimention) {
            scale = (height > width) ? height : width;
        } else {
            scale = 1;
        }

        double ratio = maxDimention / scale;

        int newHeight = (int) (height * ratio * 0.5);
        int newWidth = (int) (width * ratio);

        // Resize the image, so that height and length <= maxDimention
        Image temp = img.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        img = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        img.getGraphics().drawImage(temp, 0, 0, null);

        return img;
    }
}