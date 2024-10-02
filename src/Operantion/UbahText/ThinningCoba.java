package Operantion.UbahText;


import java.awt.*;
import java.awt.image.BufferedImage;

public class ThinningCoba {



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

            for (int y = 2; y < tinggi - 2; y++) {
                for (int x = 2; x < lebar - 2; x++) {
                    int pixel = new Color(hasilGambar.getRGB(x, y)).getRed();
                    if (pixel == 255 && cekTetangga(x, y, hasilGambar, true)) {
                        diHapus[y][x] = true;
                        start = true;
                    }
                }
            }

            for (int y = 2; y < tinggi - 2; y++) {
                for (int x = 2; x < lebar - 2; x++) {
                    if (diHapus[y][x]) {
                        hasilGambar.setRGB(x, y, 0);
                    }
                }
            }

            diHapus = new boolean[tinggi][lebar];

            for (int y = 2; y < tinggi - 2; y++) {
                for (int x = 2; x < lebar - 2; x++) {
                    int pixel = new Color(hasilGambar.getRGB(x, y)).getRed();
                    if (pixel == 255 && cekTetangga(x, y, hasilGambar, false)) {
                        diHapus[y][x] = true;
                        start = true;
                    }
                }
            }

            for (int y = 2; y < tinggi - 2; y++) {
                for (int x = 2; x < lebar - 2; x++) {
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
                {y - 2, x - 2}, {y - 2, x - 1}, {y - 2, x}, {y - 2, x + 1}, {y - 2, x + 2},
                {y - 1, x + 2}, {y, x + 2}, {y + 1, x + 2}, {y + 2, x + 2},
                {y + 2, x + 1}, {y + 2, x}, {y + 2, x - 1}, {y + 2, x - 2},
                {y + 1, x - 2}, {y, x - 2}, {y - 1, x - 2}
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

    public BufferedImage fixBrokenLines(BufferedImage image, int distanceThreshold) {
        int lebar = image.getWidth();
        int tinggi = image.getHeight();
        BufferedImage gambar = new BufferedImage(lebar, tinggi, BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < tinggi; y++) {
            for (int x = 0; x < lebar; x++) {
                gambar.setRGB(x, y, image.getRGB(x, y));
            }
        }

        for (int y = 0; y < tinggi; y++) {
            for (int x = 0; x < lebar; x++) {
                int p = new Color(image.getRGB(x, y)).getRed();
                if (p == 255) {
                    for (int di = -distanceThreshold; di <= distanceThreshold; di++) {
                        for (int dj = -distanceThreshold; dj <= distanceThreshold; dj++) {
                            int ni = x + di;
                            int nj = y + dj;
                            int pixel = new Color(image.getRGB(ni, nj)).getRed();
                            if (ni >= 0 && ni < lebar && nj >= 0 && nj < tinggi && pixel == 255) {
                                if (Math.abs(di) + Math.abs(dj) <= distanceThreshold && (di != 0 || dj != 0)) {
                                    drawLine(gambar, x, y, ni, nj);
                                }
                            }
                        }
                    }
                }
            }
        }

        return gambar;
    }

    public void drawLine(BufferedImage image, int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            image.setRGB(x0, y0, Color.WHITE.getRGB());
            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }
}
