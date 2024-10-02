package Operantion.DetectionImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


public class ImagePreprocessing {

    public static void main(String[] args) throws IOException {
        // Path ke gambar input dan output
        String inputImagePath = "D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\segmentedVertical\\gambar_1.png";
        String outputImagePath = "D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\Thinning\\gambar_8.png";

        // Membaca gambar
        BufferedImage inputImage = ImageIO.read(new File(inputImagePath));
        BufferedImage binaryImage = binarizeImage(inputImage);
        BufferedImage thinnedImage = thinning(binaryImage);

        // Resize gambar ke ukuran 1 pixel
        BufferedImage resizedImage = resizeImage(thinnedImage, 1, 1);

        // Menyimpan hasil gambar
        ImageIO.write(resizedImage, "png", new File(outputImagePath));

        System.out.println("Image has been processed and saved to " + outputImagePath);
    }

    // Fungsi untuk mengonversi gambar menjadi biner (thresholding)
    public static BufferedImage binarizeImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage binaryImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int gray = (rgb >> 16) & 0xFF; // Mengambil nilai grayscale dari channel merah
                int binaryValue = gray < 128 ? 0x00 : 0xFFFFFF; // Thresholding
                binaryImage.setRGB(x, y, binaryValue);
            }
        }

        return binaryImage;
    }

    // Fungsi untuk melakukan thinning menggunakan algoritma Zhang-Suen
//    public static BufferedImage thinning(BufferedImage image) {
//        // Implementasi algoritma thinning
//        return image; // Sementara, return gambar asli
//    }

    // Fungsi untuk melakukan thinning menggunakan algoritma Zhang-Suen
    public static BufferedImage thinning(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        boolean[][] binaryArray = new boolean[height][width];

        // Mengonversi gambar menjadi matriks boolean
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                binaryArray[y][x] = (image.getRGB(x, y) & 0xFFFFFF) == 0;
            }
        }

        boolean[][] thinnedArray = zhangSuenThinning(binaryArray);

        // Mengonversi matriks boolean kembali menjadi gambar
        BufferedImage thinnedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                thinnedImage.setRGB(x, y, thinnedArray[y][x] ? 0x000000 : 0xFFFFFF);
            }
        }

        return thinnedImage;
    }

    // Fungsi untuk algoritma Zhang-Suen Thinning
    public static boolean[][] zhangSuenThinning(boolean[][] binaryArray) {
        int width = binaryArray[0].length;
        int height = binaryArray.length;
        boolean[][] thinnedArray = new boolean[height][width];

        // Salin matriks binaryArray ke thinnedArray
        for (int y = 0; y < height; y++) {
            System.arraycopy(binaryArray[y], 0, thinnedArray[y], 0, width);
        }

        boolean changed;
        do {
            changed = false;

            // Step 1
            boolean[][] step1Array = new boolean[height][width];
            for (int y = 0; y < height; y++) {
                System.arraycopy(thinnedArray[y], 0, step1Array[y], 0, width);
            }
            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (thinnedArray[y][x]) {
                        int neighbors = countNeighbors(thinnedArray, x, y);
                        if (neighbors >= 2 && neighbors <= 6) {
                            int transitions = countTransitions(thinnedArray, x, y);
                            if (transitions == 1) {
                                if (!thinnedArray[y - 1][x] || !thinnedArray[y][x + 1] || !thinnedArray[y + 1][x] && !thinnedArray[y][x - 1]) {
                                    step1Array[y][x] = false;
                                    changed = true;
                                }
                            }
                        }
                    }
                }
            }

            // Step 2
            boolean[][] step2Array = new boolean[height][width];
            for (int y = 0; y < height; y++) {
                System.arraycopy(step1Array[y], 0, step2Array[y], 0, width);
            }
            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (step1Array[y][x]) {
                        int neighbors = countNeighbors(step1Array, x, y);
                        if (neighbors >= 2 && neighbors <= 6) {
                            int transitions = countTransitions(step1Array, x, y);
                            if (transitions == 1) {
                                if (!step1Array[y - 1][x] || !step1Array[y][x + 1] && !step1Array[y][x - 1] || !step1Array[y + 1][x]) {
                                    step2Array[y][x] = false;
                                    changed = true;
                                }
                            }
                        }
                    }
                }
            }

            thinnedArray = step2Array;
        } while (changed);

        return thinnedArray;
    }

    // Fungsi untuk menghitung jumlah tetangga yang berwarna hitam
    private static int countNeighbors(boolean[][] array, int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (array[y + i][x + j]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // Fungsi untuk menghitung jumlah transisi dari putih ke hitam di sekitar piksel (P2 ke P9)
    private static int countTransitions(boolean[][] array, int x, int y) {
        int[] px = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] py = {-1, -1, 0, 1, 1, 1, 0, -1};

        int transitions = 0;
        for (int i = 0; i < 8; i++) {
            if (!array[y + py[i]][x + px[i]] && array[y + py[(i + 1) % 8]][x + px[(i + 1) % 8]]) {
                transitions++;
            }
        }
        return transitions;
    }


    // Fungsi untuk meresize gambar ke ukuran tertentu
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();

        return resizedImage;
    }
}
