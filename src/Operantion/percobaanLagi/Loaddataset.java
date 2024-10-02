package Operantion.percobaanLagi;

import Operantion.UbahText.ImageProcecing;
import Operantion.UbahText.Thinning;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Loaddataset {
    public static ImageProcecing imgPro = new ImageProcecing();
    public static Thinning thinning = new Thinning();

    public static void loadDataset(File directory) throws IOException {
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                System.out.println("File Nama : " + directory.listFiles().toString());
                if (file.isFile() && file.getName().matches("[A-Z]\\.png")) {
                    BufferedImage img = ImageIO.read(file);
                    System.out.println( "Nama File : " +file.getName());
                    File fileLoad = new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\loadGambar\\" + file.getName() + ".png");
                    ImageIO.write(img, "png", fileLoad);

                    BufferedImage img2 = ImageIO.read(fileLoad);
                    BufferedImage gambar1 = imgPro.resize(img2);
                    ImageIO.write(gambar1, "png", fileLoad);

                    BufferedImage imgThinning = thinning.thinning(gambar1);
                    ImageIO.write(imgThinning, "png", fileLoad);

                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File gambar = new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\ModelUji");
        loadDataset(gambar);
    }
}
