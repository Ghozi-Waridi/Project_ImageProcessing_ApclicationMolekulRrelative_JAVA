package Operantion.UbahText;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageProcecing {

    public BufferedImage grayScale(BufferedImage img) {
        int tinggi = img.getHeight();
        int lebar = img.getWidth();

        //mengkonversi gambar ke grayscale
        for (int y = 0; y < tinggi; y++) {
            for (int x = 0; x < lebar; x++) {
                // mengambil semua nilai rgb Pixel
                int rgb = img.getRGB(x, y);
                //mengambil warna merah
                int red = (rgb >> 16) & 0xFF;
                //mengambil warna mhijau
                int green = (rgb >> 8) & 0xFF;
                //mengambil warna biru
                int blue = rgb & 0xFF;

                int gray = (red + green + blue) / 3;
                Color color = new Color(gray, gray, gray);
                img.setRGB(x, y, color.getRGB());
            }
        }
        return img;
    }


    // binarysasy
    public  BufferedImage binarysasi(BufferedImage image) {
        int lebar = image.getWidth();
        int tinggi = image.getHeight();
        BufferedImage img = new BufferedImage(lebar, tinggi, BufferedImage.TYPE_BYTE_BINARY);

        int totalIntensitas = 0;

        for (int y = 0; y < tinggi; y++) {
            for (int x = 0; x < lebar; x++) {
                int pixel = new Color(image.getRGB(x, y)).getRed();
                totalIntensitas += pixel;
            }
        }


        //mengambil rata rata dari semua warna yang ada di gambar
        int ambangBatas1 = totalIntensitas / (lebar * tinggi);

        for (int y = 0; y < tinggi; y++) {
            for (int x = 0; x < lebar; x++) {
                int warna = new Color(image.getRGB(x, y)).getRed();
                int ambangBatas2 = (ambangBatas1 + warna) / 2;

                if (warna > ambangBatas2) {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    img.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        return img;
    }

    public  List<BufferedImage> segmentedHorizontal(BufferedImage img) {
        List<BufferedImage> barisGambar = new ArrayList<>();
        int tinggi = img.getHeight();
        int lebar = img.getWidth();

        int ambangBatas = 50;

        int batasKiri = 0;
        int batasKanan = 0;

        boolean dalamTeks = false;
        for (int y = 0; y < tinggi; y++) {
            int warnaText = 0;
            for (int x = 0; x < lebar; x++) {
                int warna = new Color(img.getRGB(x, y)).getRed();
                if (warna == 255) {
                    warnaText++;
                }
            }
            if (!dalamTeks && warnaText > ambangBatas && warnaText != lebar) {
                batasKiri = y -10;
                dalamTeks = true;
            } else if (dalamTeks && warnaText <= ambangBatas ) {
                batasKanan = y + 10;
                dalamTeks = false;

                BufferedImage baris = img.getSubimage(0, batasKiri, lebar, batasKanan - batasKiri);
                barisGambar.add(baris);
            }
        }
        return barisGambar;
    }

    public  List<BufferedImage> segmentedVertical(BufferedImage image) {

        List<BufferedImage> barisGambar = new ArrayList<>();

        int tinggi = image.getHeight();
        int lebar = image.getWidth();

        int ambangBatas = 10;

        int batasAtas = 0;
        int batasBawah = 0;

        boolean dalamText = false;

        for (int x = 0; x < lebar; x++) {
            int warnaText = 0;
            for (int y = 0; y < tinggi; y++) {
                int warnaPixel = new Color(image.getRGB(x, y)).getRed();
                if (warnaPixel == 255) {
                    warnaText++;
                }
            }
            if (!dalamText && warnaText > ambangBatas) {
                batasAtas = x - 10;
                dalamText = true;
            } else if (dalamText && warnaText <= ambangBatas) {
                batasBawah = x + 10;
                dalamText = false;

                BufferedImage baris = image.getSubimage(batasAtas, 0, batasBawah - batasAtas, tinggi);
                barisGambar.add(baris);
            }
        }
        return barisGambar;
    }

    public BufferedImage resize(BufferedImage img){
        BufferedImage resizedImg = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = resizedImg.createGraphics();
        double scaleX = 28.0 / img.getWidth();
        double scaleY = 28.0 / img.getHeight();
        AffineTransform at = AffineTransform.getScaleInstance(scaleX, scaleY);
        g.drawRenderedImage(img, at);
        g.dispose();
        return resizedImg;
    }
}
