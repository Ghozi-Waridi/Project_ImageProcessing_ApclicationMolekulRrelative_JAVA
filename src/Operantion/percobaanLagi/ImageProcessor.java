package Operantion.percobaanLagi;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageProcessor {

    // Membaca gambar dari file dan mengkonversinya menjadi array 2D biner
    public static int[][] readImage(String filePath) {
        try {
            BufferedImage img = ImageIO.read(new File(filePath));
            int width = img.getWidth();
            int height = img.getHeight();
            int[][] binaryImage = new int[height][width];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = img.getRGB(x, y);
                    int gray = (rgb >> 16) & 0xff; // Assume grayscale image
                    binaryImage[y][x] = (gray < 128) ? 1 : 0;
                }
            }

            return binaryImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Menyimpan array 2D biner ke file gambar
    public static void saveImage(int[][] image, String filePath) {
        try {
            int width = image[0].length;
            int height = image.length;
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int value = image[y][x] * 255;
                    int rgb = (value << 16) | (value << 8) | value;
                    img.setRGB(x, y, rgb);
                }
            }

            ImageIO.write(img, "png", new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Algoritma thinning menggunakan metode Zhang-Suen
    public static int[][] thinImage(int[][] image) {
        boolean hasChange;
        int[][] binaryImage = image;
        int[][] tempImage = new int[binaryImage.length][binaryImage[0].length];
        
        do {
            hasChange = false;
            for (int i = 1; i < binaryImage.length - 1; i++) {
                for (int j = 1; j < binaryImage[i].length - 1; j++) {
                    if (binaryImage[i][j] == 1) {
                        int B = getBlackNeighborCount(binaryImage, i, j);
                        int A = getTransitionCount(binaryImage, i, j);
                        if (B >= 2 && B <= 6 && A == 1 
                                && binaryImage[i - 1][j] * binaryImage[i][j + 1] * binaryImage[i + 1][j] == 0 
                                && binaryImage[i][j + 1] * binaryImage[i + 1][j] * binaryImage[i][j - 1] == 0) {
                            tempImage[i][j] = 0;
                            hasChange = true;
                        } else {
                            tempImage[i][j] = binaryImage[i][j];
                        }
                    }
                }
            }
            for (int i = 0; i < binaryImage.length; i++) {
                System.arraycopy(tempImage[i], 0, binaryImage[i], 0, tempImage[i].length);
            }
        } while (hasChange);

        return binaryImage;
    }

    private static int getBlackNeighborCount(int[][] image, int x, int y) {
        int count = 0;
        if (image[x - 1][y] == 1) count++;
        if (image[x - 1][y + 1] == 1) count++;
        if (image[x][y + 1] == 1) count++;
        if (image[x + 1][y + 1] == 1) count++;
        if (image[x + 1][y] == 1) count++;
        if (image[x + 1][y - 1] == 1) count++;
        if (image[x][y - 1] == 1) count++;
        if (image[x - 1][y - 1] == 1) count++;
        return count;
    }

    private static int getTransitionCount(int[][] image, int x, int y) {
        int count = 0;
        if (image[x - 1][y] == 0 && image[x - 1][y + 1] == 1) count++;
        if (image[x - 1][y + 1] == 0 && image[x][y + 1] == 1) count++;
        if (image[x][y + 1] == 0 && image[x + 1][y + 1] == 1) count++;
        if (image[x + 1][y + 1] == 0 && image[x + 1][y] == 1) count++;
        if (image[x + 1][y] == 0 && image[x + 1][y - 1] == 1) count++;
        if (image[x + 1][y - 1] == 0 && image[x][y - 1] == 1) count++;
        if (image[x][y - 1] == 0 && image[x - 1][y - 1] == 1) count++;
        if (image[x - 1][y - 1] == 0 && image[x - 1][y] == 1) count++;
        return count;
    }

    // Fungsi dilate untuk menghilangkan noise dan menyambungkan bagian yang terputus
    public static int[][] dilate(int[][] image, int iterations) {
        int[][] dilatedImage = new int[image.length][image[0].length];
        for (int k = 0; k < iterations; k++) {
            for (int i = 1; i < image.length - 1; i++) {
                for (int j = 1; j < image[i].length - 1; j++) {
                    if (image[i][j] == 1 || 
                        image[i-1][j] == 1 || image[i+1][j] == 1 || 
                        image[i][j-1] == 1 || image[i][j+1] == 1) {
                        dilatedImage[i][j] = 1;
                    } else {
                        dilatedImage[i][j] = 0;
                    }
                }
            }
            for (int i = 0; i < image.length; i++) {
                System.arraycopy(dilatedImage[i], 0, image[i], 0, dilatedImage[i].length);
            }
        }
        return dilatedImage;
    }

    // Fungsi erode untuk mengurangi ketebalan garis dan menghilangkan noise yang tersisa
    public static int[][] erode(int[][] image, int iterations) {
        int[][] erodedImage = new int[image.length][image[0].length];
        for (int k = 0; k < iterations; k++) {
            for (int i = 1; i < image.length - 1; i++) {
                for (int j = 1; j < image[i].length - 1; j++) {
                    if (image[i][j] == 1 && 
                        image[i-1][j] == 1 && image[i+1][j] == 1 && 
                        image[i][j-1] == 1 && image[i][j+1] == 1) {
                        erodedImage[i][j] = 1;
                    } else {
                        erodedImage[i][j] = 0;
                    }
                }
            }
            for (int i = 0; i < image.length; i++) {
                System.arraycopy(erodedImage[i], 0, image[i], 0, erodedImage[i].length);
            }
        }
        return erodedImage;
    }

    public static void main(String[] args) {
        // Path to your image file
        String inputPath = "D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\Thinning\\gambar_1 .png";
        String outputPath = "D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\Thinning\\gambar_9 .png";

        // Read the image
        int[][] binaryImage = readImage(inputPath);

        // Apply thinning
        int[][] thinnedImage = thinImage(binaryImage);

        // Apply postprocessing: dilate and erode to remove noise and refine the image
        int[][] dilatedImage = dilate(thinnedImage, 1);
        int[][] finalImage = erode(dilatedImage, 1);

        // Save the processed image
        saveImage(finalImage, outputPath);
    }
}
