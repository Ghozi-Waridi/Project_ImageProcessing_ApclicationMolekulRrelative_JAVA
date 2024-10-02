package Operantion.percobaanLagi;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageSkeletonization {

    // Membaca gambar dari file
    public static BufferedImage readImage(String filePath) throws Exception {
        File file = new File(filePath);
        return ImageIO.read(file);
    }

    // Menulis gambar ke file
    public static void writeImage(BufferedImage image, String filePath) throws Exception {
        File file = new File(filePath);
        ImageIO.write(image, "png", file);
    }

    // Konversi gambar ke hitam-putih
    public static BufferedImage binarizeImage(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage binarized = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = original.getRGB(x, y);
                int gray = (rgb >> 16) & 0xff; // mengambil nilai merah untuk grayscale
                if (gray < 128) {
                    binarized.setRGB(x, y, 0xff000000); // hitam
                } else {
                    binarized.setRGB(x, y, 0xffffffff); // putih
                }
            }
        }
        return binarized;
    }

    // Skeletonisasi menggunakan algoritma Zhang-Suen
    public static BufferedImage skeletonize(BufferedImage binaryImage) {
        int width = binaryImage.getWidth();
        int height = binaryImage.getHeight();
        boolean[][] binary = new boolean[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                binary[x][y] = binaryImage.getRGB(x, y) == 0xff000000;
            }
        }

        boolean change;
        do {
            change = false;
            boolean[][] marker = new boolean[width][height];

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (binary[x][y] && checkFirstStep(x, y, binary)) {
                        marker[x][y] = true;
                        change = true;
                    }
                }
            }
            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (marker[x][y]) {
                        binary[x][y] = false;
                    }
                }
            }

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (binary[x][y] && checkSecondStep(x, y, binary)) {
                        marker[x][y] = true;
                        change = true;
                    }
                }
            }
            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (marker[x][y]) {
                        binary[x][y] = false;
                    }
                }
            }
        } while (change);

        BufferedImage skeletonized = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                skeletonized.setRGB(x, y, binary[x][y] ? 0xff000000 : 0xffffffff);
            }
        }
        return skeletonized;
    }

    // Mengecek kondisi untuk langkah pertama dari algoritma Zhang-Suen
    private static boolean checkFirstStep(int x, int y, boolean[][] binary) {
        int neighbors = countNeighbors(x, y, binary);
        int transitions = countTransitions(x, y, binary);
        return (neighbors >= 2 && neighbors <= 6) &&
                (transitions == 1) &&
                (!binary[x - 1][y] || !binary[x][y + 1] || !binary[x + 1][y]) &&
                (!binary[x][y + 1] || !binary[x + 1][y] || !binary[x][y - 1]);
    }

    // Mengecek kondisi untuk langkah kedua dari algoritma Zhang-Suen
    private static boolean checkSecondStep(int x, int y, boolean[][] binary) {
        int neighbors = countNeighbors(x, y, binary);
        int transitions = countTransitions(x, y, binary);
        return (neighbors >= 2 && neighbors <= 6) &&
                (transitions == 1) &&
                (!binary[x - 1][y] || !binary[x][y + 1] || !binary[x][y - 1]) &&
                (!binary[x][y + 1] || !binary[x + 1][y] || !binary[x][y - 1]);
    }

    // Menghitung jumlah tetangga berwarna hitam
    private static int countNeighbors(int x, int y, boolean[][] binary) {
        int count = 0;
        if (binary[x - 1][y]) count++;
        if (binary[x - 1][y + 1]) count++;
        if (binary[x][y + 1]) count++;
        if (binary[x + 1][y + 1]) count++;
        if (binary[x + 1][y]) count++;
        if (binary[x + 1][y - 1]) count++;
        if (binary[x][y - 1]) count++;
        if (binary[x - 1][y - 1]) count++;
        return count;
    }

    // Menghitung jumlah transisi hitam-putih di sekitar piksel
    private static int countTransitions(int x, int y, boolean[][] binary) {
        int count = 0;
        boolean[] neighbors = new boolean[8];
        neighbors[0] = binary[x - 1][y];
        neighbors[1] = binary[x - 1][y + 1];
        neighbors[2] = binary[x][y + 1];
        neighbors[3] = binary[x + 1][y + 1];
        neighbors[4] = binary[x + 1][y];
        neighbors[5] = binary[x + 1][y - 1];
        neighbors[6] = binary[x][y - 1];
        neighbors[7] = binary[x - 1][y - 1];
        for (int i = 0; i < 8; i++) {
            if (!neighbors[i] && neighbors[(i + 1) % 8]) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        try {
            BufferedImage originalImage = readImage("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\segmentedVertical\\gambar_1.png");
//            BufferedImage binaryImage = binarizeImage(originalImage);
            BufferedImage skeletonizedImage = skeletonize(originalImage);
            writeImage(skeletonizedImage, "D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\Thinning\\gambar_10 .png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
