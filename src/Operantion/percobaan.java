package Operantion;

import Operantion.UbahText.Thinning;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class percobaan {
//    static final int INPUT_NODES = 784;
//    static final int HIDDEN_NODES = 128;
//    static final int OUTPUT_NODES = 62;
//    static final double LEARNING_RATE = 0.1;
//    static final int EPOCHS = 10;
//
//    double[][] inputToHiddenWeights;
//    double[][] hiddenToOutputWeights;
//    double[] hiddenLayer;
//    double[] outputLayer;
//
//    public percobaan() {
//        inputToHiddenWeights = new double[HIDDEN_NODES][INPUT_NODES];
//        hiddenToOutputWeights = new double[OUTPUT_NODES][HIDDEN_NODES];
//        hiddenLayer = new double[HIDDEN_NODES];
//        outputLayer = new double[OUTPUT_NODES];
//        initializeWeights();
//    }
//
//    private void initializeWeights() {
//        for (int i = 0; i < HIDDEN_NODES; i++) {
//            for (int j = 0; j < INPUT_NODES; j++) {
//                inputToHiddenWeights[i][j] = Math.random() * 0.01;
//            }
//        }
//        for (int i = 0; i < OUTPUT_NODES; i++) {
//            for (int j = 0; j < HIDDEN_NODES; j++) {
//                hiddenToOutputWeights[i][j] = Math.random() * 0.01;
//            }
//        }
//    }
//
//    private double sigmoid(double x) {
//        return 1.0 / (1.0 + Math.exp(-x));
//    }
//
//    private double[] forwardPropagation(double[] input) {
//        for (int i = 0; i < HIDDEN_NODES; i++) {
//            double sum = 0.0;
//            for (int j = 0; j < INPUT_NODES; j++) {
//                sum += input[j] * inputToHiddenWeights[i][j];
//            }
//            hiddenLayer[i] = sigmoid(sum);
//        }
//
//        for (int i = 0; i < OUTPUT_NODES; i++) {
//            double sum = 0.0;
//            for (int j = 0; j < HIDDEN_NODES; j++) {
//                sum += hiddenLayer[j] * hiddenToOutputWeights[i][j];
//            }
//            outputLayer[i] = sigmoid(sum);
//        }
//
//        return outputLayer;
//    }
//
//    private void backPropagation(double[] input, double[] target) {
//        double[] outputErrors = new double[OUTPUT_NODES];
//        for (int i = 0; i < OUTPUT_NODES; i++) {
//            outputErrors[i] = target[i] - outputLayer[i];
//        }
//
//        double[] hiddenErrors = new double[HIDDEN_NODES];
//        for (int i = 0; i < HIDDEN_NODES; i++) {
//            double error = 0.0;
//            for (int j = 0; j < OUTPUT_NODES; j++) {
//                error += outputErrors[j] * hiddenToOutputWeights[j][i];
//            }
//            hiddenErrors[i] = error * hiddenLayer[i] * (1.0 - hiddenLayer[i]);
//        }
//
//        for (int i = 0; i < OUTPUT_NODES; i++) {
//            for (int j = 0; j < HIDDEN_NODES; j++) {
//                hiddenToOutputWeights[i][j] += LEARNING_RATE * outputErrors[i] * hiddenLayer[j];
//            }
//        }
//
//        for (int i = 0; i < HIDDEN_NODES; i++) {
//            for (int j = 0; j < INPUT_NODES; j++) {
//                inputToHiddenWeights[i][j] += LEARNING_RATE * hiddenErrors[i] * input[j];
//            }
//        }
//    }
//
//    public void trainModel(double[][] dataset, int epochs) {
//        for (int epoch = 0; epoch < epochs; epoch++) {
//            for (double[] data : dataset) {
//                double[] input = new double[INPUT_NODES];
//                double[] target = new double[OUTPUT_NODES];
//                System.arraycopy(data, 0, input, 0, INPUT_NODES);
//                System.arraycopy(data, INPUT_NODES, target, 0, OUTPUT_NODES);
//                forwardPropagation(input);
//                backPropagation(input, target);
//            }
//        }
//    }
//
//    public double testModel(double[][] dataset) {
//        int correctCount = 0;
//        for (double[] data : dataset) {
//            double[] input = new double[INPUT_NODES];
//            double[] target = new double[OUTPUT_NODES];
//            System.arraycopy(data, 0, input, 0, INPUT_NODES);
//            System.arraycopy(data, INPUT_NODES, target, 0, OUTPUT_NODES);
//            double[] output = forwardPropagation(input);
//            int predictedLabel = maxIndex(output);
//            int actualLabel = maxIndex(target);
//            if (predictedLabel == actualLabel) {
//                correctCount++;
//            }
//        }
//        return (double) correctCount / dataset.length;
//    }
//    public static String getCharacterFromIndex(int index) {
////         Mengonversi indeks label menjadi karakter huruf atau angka
//        if (index >= 0 && index <= 9) {
//            return String.valueOf(index);
//        } else if (index >= 10 && index <= 35) {
////             Huruf besar A-Z
//            return String.valueOf((char) ('A' + index - 10));
//        } else if (index >= 36 && index <= 61) {
////             Huruf kecil a-z
//            return String.valueOf((char) ('a' + index - 36));
//        } else {
//            return "Unknown";
//        }
//    }
//
//    public String predict(double[] input) {
//        double[] output = forwardPropagation(input);
//        int predictedIndex = maxIndex(output);
//        return getCharacterFromIndex(predictedIndex);
//    }
//
//    private int maxIndex(double[] array) {
//        int index = 0;
//        for (int i = 1; i < array.length; i++) {
//            if (array[i] > array[index]) {
//                index = i;
//            }
//        }
//        return index;
//    }
//
//    public static void loadDataset(File directory, List<double[]> dataset) throws IOException {
//        if (directory.isDirectory()) {
//            for (File file : directory.listFiles()) {
//                if (file.isDirectory()) {
//                    loadDataset(file, dataset);
//                } else if (file.isFile() && file.getName().endsWith(".png")) {
//                    BufferedImage img = ImageIO.read(file);
////                    double[] inputArray = imageToArray(img);
//                    double[] labelArray = new double[percobaan.OUTPUT_NODES];
//
////                     Asumsikan bahwa label dapat diambil dari nama direktori atau nama file
//                    String parentDirName = file.getParentFile().getName();
//                    int labelIndex = getLabelIndex(parentDirName);
//
////                     Pastikan labelIndex valid dan berada dalam rentang OUTPUT_NODES
//                    if (labelIndex >= 0 && labelIndex < percobaan.OUTPUT_NODES) {
//                        labelArray[labelIndex] = 1.0;
//                    }
//
//                    double[] rowData = new double[inputArray.length + labelArray.length];
//                    System.arraycopy(inputArray, 0, rowData, 0, inputArray.length);
//                    System.arraycopy(labelArray, 0, rowData, inputArray.length, labelArray.length);
//                    dataset.add(rowData);
//                }
//            }
//        }
//    }
//    public static int getLabelIndex(String parentDirName) {
//
//        if (parentDirName.startsWith("Sample")) {
//            try {
//                int sampleNumber = Integer.parseInt(parentDirName.substring(6)) - 1;
//
////                 Sample001 hingga Sample010 -> angka 0-9
//                if (sampleNumber < 10) {
//                    return sampleNumber;
//                }
//
////                 Sample011 hingga Sample036 -> huruf besar A-Z
//                if (sampleNumber < 36) {
//                    return sampleNumber - 10 + 'A' - '0';
//                }
//
////                 Sample037 hingga Sample062 -> huruf kecil a-z
//                if (sampleNumber < 62) {
//                    return sampleNumber - 36 + 'a' - '0';
//                }
//
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
//        return -1; // return -1 jika tidak valid
//    }

    public static /*double[]*/BufferedImage imageToArray(BufferedImage img) throws IOException {
        BufferedImage resizedImg = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = resizedImg.createGraphics();
        double scaleX = 28.0 / img.getWidth();
        double scaleY = 28.0 / img.getHeight();
        AffineTransform at = AffineTransform.getScaleInstance(scaleX, scaleY);
        g.drawRenderedImage(img, at);
        g.dispose();

        // Menyimpan BufferedImage yang telah diubah ukurannya ke file baru
        File thinningFile = new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\Thinning\\gambar_0 .png");
        ImageIO.write(resizedImg, "png", thinningFile);

        System.out.println("Gambar telah disimpan sebagai Z.png");

//        AffineTransformOp op = new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
//        op.filter(img, resizedImg);

//        BufferedImage imageThinning = thinning.thinning(segmentedLines2.get(i));


//        double[] array = new double[INPUT_NODES];
//        int index = 0;
//        for (int y = 0; y < 28; y++) {
//            for (int x = 0; x < 28; x++) {
//
//                int pixel = resizedImg.getRGB(x, y);
//
////                int red = (pixel >> 16) & 0xff;
////                array[index++] = red / 255.0;
//            }
//        }

//        return array;
        return resizedImg;
    }

    public static void main(String[] args) throws Exception {
        Thinning thinning = new Thinning();
//        NeuralNetworks ocr = new NeuralNetworks();

//         Persiapkan dataset untuk model
//        String datasetPath = "D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\EnglishFnt\\English\\Fnt";
//        List<double[]> dataset = new ArrayList<>();
//        loadDataset(new File(datasetPath), dataset);

//         Konversi List ke array dua dimensi
//        double[][] datasetArray = dataset.toArray(new double[0][]);

//         Bagi dataset menjadi training dan testing
//        int trainSize = (int) (0.8 * datasetArray.length);
//        double[][] trainData = new double[trainSize][];
//        double[][] testData = new double[datasetArray.length - trainSize][];
//
//        System.arraycopy(datasetArray, 0, trainData, 0, trainSize);
//        System.arraycopy(datasetArray, trainSize, testData, 0, datasetArray.length - trainSize);
//
////         Latih model
//        ocr.trainModel(trainData, NeuralNetworks.EPOCHS);

//         Load gambar huruf baru
        File imageFile = new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\segmentedVertical\\n.png"); // Gantilah dengan path ke gambar baru
        BufferedImage img = ImageIO.read(imageFile);
//        imageToArray(img);

        BufferedImage gambar = imageToArray(img);
        BufferedImage imageThinning = thinning.thinning(gambar);
        File thinningFile = new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\Thinning\\gambar_100.png");
        ImageIO.write(imageThinning, "png", thinningFile);

//        double[] inputArray = imageToArray(img);

//         Prediksi huruf pada gambar baru
//        String predictedChar = ocr.predict(inputArray);
//        System.out.println("Predicted character: " + predictedChar);
    }
}
