package OpearationImage;

import Operantion.UbahText.ImageProcecing;
import Operantion.UbahText.Thinning;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.List;

public class PredictChar {
     final int INPUT_NODES = 784;
     final int OUTPUT_NODES = 26;
    public  double[] imageToArray(BufferedImage img) {
        double[] array = new double[INPUT_NODES];
        int index = 0;
        for (int y = 0; y < 28; y++) {
            for (int x = 0; x < 28; x++) {
                int pixel = img.getRGB(x, y) & 0xFF;
                array[index++] = pixel / 255.0;
            }
        }
        return array;
    }

    public  double euclideanDistance(double[] a, double[] b) {
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }

    public  void loadDataset(File directory, List<double[]> dataset, List<String> labels) throws IOException {
        ImageProcecing IMGPROG = new ImageProcecing();
        Thinning thinning = new Thinning();
        String[] folders = {"HurufBesar", "Hurufkecil", "Angka", "Charachter"};

        for (var folder : folders){
            File pathFile = new File(directory + File.separator + folder);
            if(pathFile.isDirectory()){
                for(File file : pathFile.listFiles()){
                    if(file.isFile() && file.getName().endsWith(".png")){
                        BufferedImage gambar = ImageIO.read(file);
                        BufferedImage imgResize = IMGPROG.resize(gambar);
                        BufferedImage imgThinning = thinning.thinning(imgResize);

                        double[] inputARRY = imageToArray(imgThinning);
                        dataset.add(inputARRY);
                        labels.add(file.getName().substring(0, 1));
                    }

                }
            }
        }
    }

    public  String predict(double[] input, List<double[]> dataset, List<String> labels) {
        double minDistance = Double.MAX_VALUE;
        String predictedLabel = "";
        for (int i = 0; i < dataset.size(); i++) {
            double distance = euclideanDistance(input, dataset.get(i));
            if (distance < minDistance) {
                minDistance = distance;
                predictedLabel = labels.get(i);
            }
        }
        return predictedLabel;
    }
}

