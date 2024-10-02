package Operantion.UbahText;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class MainA {

    public static void main(String[] args) throws IOException {
//        ThinningCoba thinning = new ThinningCoba();
//        Thinning thinning2 = new Thinning();

//        File file = new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\dataset\\Thinning\\D.png");
//        BufferedImage gambar2 = ImageIO.read(file);
//        BufferedImage gambar3 = thinning.thinning(gambar2);
//        BufferedImage gambar4 = thinning2.thinning(gambar3);
//        BufferedImage gambarHasil = thinning.fixBrokenLines(gambar4, 2);
//
//        File fileHasil = new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\Thinning\\gambar_8 .png");
//        ImageIO.write(gambarHasil, "png", fileHasil);

        ImageProcecing imgProg =new ImageProcecing();
        BufferedImage gambar  = ImageIO.read(new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\ModelGambar\\img_11.png"));
        List<BufferedImage> gambarHorizontal = imgProg.segmentedHorizontal(gambar);
        List<BufferedImage> gambarVertical = null;
        for(int i = 0; i < gambarHorizontal.size(); i++){
            gambarVertical = imgProg.segmentedVertical(gambarHorizontal.get(i));
            File lineFile = new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\segmentHorizontal\\line_" + i + ".png");
            ImageIO.write(gambarHorizontal.get(i), "png", lineFile);
        }

        for(int i = 0; i < gambarVertical.size(); i++){
            BufferedImage imgResize = imgProg.resize(gambarVertical.get(i));
            File lineFile = new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\ModelUji\\" + (i+1) + ".png");
            ImageIO.write(imgResize, "png", lineFile);
        }
    }
}