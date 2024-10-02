package Operantion.UbahText;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class percobaan {


        public static void main(String[] args) {
            try {
                // Load the image
                BufferedImage inputImage = ImageIO.read(new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\segmentedVertical\\gambar_4.png"));

                // Apply erosion
                BufferedImage erodedImage = applyErosion(inputImage);

                // Apply dilation
//                BufferedImage resultImage = applyDilation(erodedImage);

                // Save the result
                ImageIO.write(erodedImage, "png", new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\segmentedVertical\\n.png"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static BufferedImage applyErosion(BufferedImage inputImage) {
            int width = inputImage.getWidth();
            int height = inputImage.getHeight();
            BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    int pixel = inputImage.getRGB(x, y);
                    if (isWhite(pixel)) {
                        boolean erode = false;
                        for (int j = -1; j <= 1; j++) {
                            for (int i = -1; i <= 1; i++) {
                                if (!isWhite(inputImage.getRGB(x + i, y + j))) {
                                    erode = true;
                                    break;
                                }
                            }
                            if (erode) break;
                        }
                        outputImage.setRGB(x, y, erode ? 0xFF000000 : 0xFFFFFFFF);
                    } else {
                        outputImage.setRGB(x, y, pixel);
                    }
                }
            }
            return outputImage;
        }

        public static BufferedImage applyDilation(BufferedImage inputImage) {
            int width = inputImage.getWidth();
            int height = inputImage.getHeight();
            BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    int pixel = inputImage.getRGB(x, y);
                    if (!isWhite(pixel)) {
                        boolean dilate = false;
                        for (int j = -1; j <= 1; j++) {
                            for (int i = -1; i <= 1; i++) {
                                if (isWhite(inputImage.getRGB(x + i, y + j))) {
                                    dilate = true;
                                    break;
                                }
                            }
                            if (dilate) break;
                        }
                        outputImage.setRGB(x, y, dilate ? 0xFFFFFFFF : 0xFF000000);
                    } else {
                        outputImage.setRGB(x, y, pixel);
                    }
                }
            }
            return outputImage;
        }

        public static boolean isWhite(int rgb) {
            int whiteThreshold = 0xFFEEEEEE;
            return rgb >= whiteThreshold;
        }
}
