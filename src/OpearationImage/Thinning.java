package OpearationImage;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Thinning {

    public BufferedImage thinning(BufferedImage img) {
        int lebar = img.getWidth();
        int tinggi = img.getHeight();

        // Salin gambar
        BufferedImage hasilGambar = new BufferedImage(lebar, tinggi, BufferedImage.TYPE_BYTE_BINARY);
        for (int y = 0; y < tinggi; y++) {
            for (int x = 0; x < lebar; x++) {
                hasilGambar.setRGB(x, y, img.getRGB(x, y));
            }
        }

        boolean start = true;
        while (start) {
            start = false;
            boolean[][] diHapus = new boolean[tinggi][lebar];

            // Proses iterasi pertama
            for (int y = 1; y < tinggi - 1; y++) {
                for (int x = 1; x < lebar - 1; x++) {
                    int pixel = new Color(hasilGambar.getRGB(x, y)).getRed();
                    if (pixel == 255 && cekTetangga(x, y, hasilGambar, true)) {
                        diHapus[y][x] = true;
                        start = true;
                    }
                }
            }

            // Menghapus piksel yang ditandai pada iterasi pertama
            for (int y = 1; y < tinggi -1; y++) {
                for (int x = 1; x < lebar -1; x++) {
                    if (diHapus[y][x]) {
                        hasilGambar.setRGB(x, y, 0);
                    }
                }
            }

            diHapus = new boolean[tinggi][lebar];

            // Proses iterasi kedua
            for (int y = 1; y < tinggi -1 ; y++) {
                for (int x = 1; x < lebar -1 ; x++) {
                    int pixel = new Color(hasilGambar.getRGB(x, y)).getRed();
                    if (pixel == 255 && cekTetangga(x, y, hasilGambar, false)) {
                        diHapus[y][x] = true;
                        start = true;
                    }
                }
            }

            // Menghapus piksel yang ditandai pada iterasi kedua
            for (int y = 1; y < tinggi -1; y++) {
                for (int x =1; x < lebar -1; x++) {
                    if (diHapus[y][x]) {
                        hasilGambar.setRGB(x, y, 0);
                    }
                }
            }
        }

        return hasilGambar;
    }

    private boolean cekTetangga(int x, int y, BufferedImage gambar, boolean kondisi) {
        int[][] operation = {
                {y - 1, x - 1}, {y - 1, x}, {y - 1, x + 1}, {y, x + 1},
                {y + 1, x + 1}, {y + 1, x}, {y + 1, x - 1}, {y, x - 1}
        };



        int count = 0;
        for (int[] pos : operation) {
            int posX = pos[1];
            int posY = pos[0];

            if (posX >= 0 && posX < gambar.getWidth() && posY >= 0 && posY < gambar.getHeight()) {
                int p = new Color(gambar.getRGB(posX, posY)).getRed();
                if (p == 255) {
                    count++;
                }
            }
        }

        if (count <= 2 || count >= 6) return false;

        int transisi = 0;
        for (int i = 0; i < operation.length; i++) {
            int indexNext = (i + 1) % operation.length;
            int posX = operation[i][1];
            int posY = operation[i][0];

            int nextX = operation[indexNext][1];
            int nextY = operation[indexNext][0];

            if (posX >= 0 && posX < gambar.getWidth() && posY >= 0 && posY < gambar.getHeight()
                    && nextX >= 0 && nextY >= 0 && nextX < gambar.getWidth() && nextY < gambar.getHeight()) {
                int p = new Color(gambar.getRGB(posX, posY)).getRed();
                int p1 = new Color(gambar.getRGB(nextX, nextY)).getRed();

                if (p == 0 && p1 == 255) {
                    transisi++;
                }
            }
        }

        if (transisi != 1) {
            return false;
        }

        if (kondisi) {
            if (isPNull(gambar, operation[0][0], operation[0][1]) * isPNull(gambar, operation[2][0], operation[2][1]) * isPNull(gambar, operation[4][0], operation[4][1]) != 0) {
                return false;
            }
            if (isPNull(gambar, operation[2][0], operation[2][1]) * isPNull(gambar, operation[4][0], operation[4][1]) * isPNull(gambar, operation[6][0], operation[6][1]) != 0) {
                return false;
            }
        } else {
            if (isPNull(gambar, operation[0][0], operation[0][1]) * isPNull(gambar, operation[2][0], operation[2][1]) * isPNull(gambar, operation[6][0], operation[6][1]) != 0) {
                return false;
            }
            if (isPNull(gambar, operation[0][0], operation[0][1]) * isPNull(gambar, operation[4][0], operation[4][1]) * isPNull(gambar, operation[6][0], operation[6][1]) != 0) {
                return false;
            }
        }

        return true;
    }
    

    private int isPNull(BufferedImage img, int x, int y) {
        if (x >= 0 && x < img.getWidth() && y >= 0 && y < img.getHeight()) {
            return new Color(img.getRGB(x, y)).getRed();
        }
        return 0;
    }
}



