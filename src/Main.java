import OpearationImage.PredictChar;
import Operantion.MoletkulRelative;
import Operantion.UbahText.ImageProcecing;
import Operantion.UbahText.Thinning;
import org.w3c.dom.ls.LSOutput;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//neuroul network
//thinning

public class Main {
    public static List<String> predictChar = new ArrayList<>();
    public static ImageProcecing imgprog = new ImageProcecing();
    public static Thinning thinning = new Thinning();
    public static PredictChar preChar = new PredictChar();
    public static MoletkulRelative molekul = new MoletkulRelative();
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        File file = new File("/media/ghozi-waridi/Pemograman/BAHASAPEMOGRAMAN/java/ProjectPBO/ApclicationMolekulRrelative/Image/ModelGambar/img_20.png");
        try {
            BufferedImage img = ImageIO.read(file);

            // grayscale
            BufferedImage grayscale = imgprog.grayScale(img);
            File hasilFile1 = new File("/media/ghozi-waridi/Pemograman/BAHASAPEMOGRAMAN/java/ProjectPBO/ApclicationMolekulRrelative/Image/grayScale/gambar.png");
            ImageIO.write(grayscale, "png", hasilFile1);

            // binary
            BufferedImage segmentImage = imgprog.binarysasi(grayscale);
            File hasilFile2 = new File("/media/ghozi-waridi/Pemograman/BAHASAPEMOGRAMAN/java/ProjectPBO/ApclicationMolekulRrelative/Image/binary/gambar.png");
            ImageIO.write(segmentImage, "png", hasilFile2);

            //segmented Horizontal
            List<BufferedImage> segmentedLines = imgprog.segmentedHorizontal(segmentImage);
            List<BufferedImage> segmentedLines2 = null;
            for (int i = 0; i < segmentedLines.size(); i++) {
                segmentedLines2 = imgprog.segmentedVertical(segmentedLines.get(i));
                File lineFile = new File("/media/ghozi-waridi/Pemograman/BAHASAPEMOGRAMAN/java/ProjectPBO/ApclicationMolekulRrelative/Image/segmentHorizontal/line_" + i + ".png");
                ImageIO.write(segmentedLines.get(i), "png", lineFile);
            }
            //segmented Vertical
            for (int i = 0; i < segmentedLines2.size(); i++) {

                BufferedImage imgResize = imgprog.resize(segmentedLines2.get(i));
                File lineFile = new File("/media/ghozi-waridi/Pemograman/BAHASAPEMOGRAMAN/java/ProjectPBO/ApclicationMolekulRrelative/Image/segmentedVertical/gambar_" + i + ".png");
                ImageIO.write(imgResize, "png", lineFile);

                // membuat sebuah metode thinning
                BufferedImage imageThinning = thinning.thinning(imgResize);
                File thinningFile = new File("/media/ghozi-waridi/Pemograman/BAHASAPEMOGRAMAN/java/ProjectPBO/ApclicationMolekulRrelative/Image/Thinning/gambar_" + i + " .png");
//                BufferedImage thinningFiks = thinning.fixLines(imageThinning, 2);
                ImageIO.write(imageThinning, "png", thinningFile);

                /* Deteksi huruf (konvert Image to Text) */
                String datasetPath = "/media/ghozi-waridi/Pemograman/BAHASAPEMOGRAMAN/java/ProjectPBO/ApclicationMolekulRrelative/Image/ModelUji";
                List<double[]> dataset = new ArrayList<>();
                List<String> labels = new ArrayList<>();

                preChar.loadDataset(new File(datasetPath), dataset, labels);

//                File imageFile = new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\Thinning\\gambar_4 .png");
                BufferedImage img2 = ImageIO.read(thinningFile);
                double[] inputArray = preChar.imageToArray(img2);

                String predictedChar = preChar.predict(inputArray, dataset, labels);
                predictChar.add(predictedChar);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /* Hasil */
        System.out.print("Hasil : ");
        String hasil1 = "";
        for(var hasil : predictChar){
            hasil1 = hasil + hasil1;
            System.out.print(hasil + ", ");
        }


        HashMap<String, Double> hasilMolekul = molekul.VitungMR(hasil1);
        double hasilAkhir = molekul.hitungMassaMolekul(hasilMolekul);
        System.out.println("\nHasil Akhir : " + hasilAkhir);
    }

}
