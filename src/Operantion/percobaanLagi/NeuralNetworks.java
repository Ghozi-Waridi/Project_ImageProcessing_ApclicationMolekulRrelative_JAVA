package Operantion.percobaanLagi;


import Operantion.UbahText.ImageProcecing;
import Operantion.UbahText.Thinning;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Definisi kelas NeuralNetworks
public class NeuralNetworks {
    static final int INPUT_NODES = 784; // Jumlah node input (28x28 piksel gambar)
    static final int HIDDEN_NODES = 128; // Jumlah node pada lapisan tersembunyi
    static final int OUTPUT_NODES = 26; // Jumlah node output (26 huruf alfabet)
    static final double LEARNING_RATE = 0.1; // Laju pembelajaran
    static final int EPOCHS = 10; // Jumlah epoch pelatihan

    double[][] inputToHiddenWeights; // Bobot dari input ke lapisan tersembunyi
    double[][] hiddenToOutputWeights; // Bobot dari lapisan tersembunyi ke output
    double[] hiddenLayer; // Lapisan tersembunyi
    double[] outputLayer; // Lapisan output

    // Konstruktor kelas NeuralNetworks
    public NeuralNetworks() {
        inputToHiddenWeights = new double[HIDDEN_NODES][INPUT_NODES];
        hiddenToOutputWeights = new double[OUTPUT_NODES][HIDDEN_NODES];
        hiddenLayer = new double[HIDDEN_NODES];
        outputLayer = new double[OUTPUT_NODES];
        initializeWeights();
    }

    // Inisialisasi bobot dengan nilai random kecil
    private void initializeWeights() {
        Random rand = new Random();
        for (int i = 0; i < HIDDEN_NODES; i++) {
            for (int j = 0; j < INPUT_NODES; j++) {
                inputToHiddenWeights[i][j] = rand.nextGaussian() * 0.01;
            }
        }
        for (int i = 0; i < OUTPUT_NODES; i++) {
            for (int j = 0; j < HIDDEN_NODES; j++) {
                hiddenToOutputWeights[i][j] = rand.nextGaussian() * 0.01;
            }
        }
    }

    // Fungsi sigmoid
    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    // Propagasi maju
    private double[] forwardPropagation(double[] input) {
        // Hitung aktivasi lapisan tersembunyi
        for (int i = 0; i < HIDDEN_NODES; i++) {
            double sum = 0.0;
            for (int j = 0; j < INPUT_NODES; j++) {
                sum += input[j] * inputToHiddenWeights[i][j];
            }
            hiddenLayer[i] = sigmoid(sum);
        }

        // Hitung aktivasi lapisan output
        for (int i = 0; i < OUTPUT_NODES; i++) {
            double sum = 0.0;
            for (int j = 0; j < HIDDEN_NODES; j++) {
                sum += hiddenLayer[j] * hiddenToOutputWeights[i][j];
            }
            outputLayer[i] = sigmoid(sum);
        }

        return outputLayer;
    }

    // Propagasi balik
    private void backPropagation(double[] input, double[] target) {
        double[] outputErrors = new double[OUTPUT_NODES];
        // Hitung error pada lapisan output
        for (int i = 0; i < OUTPUT_NODES; i++) {
            outputErrors[i] = target[i] - outputLayer[i];
        }
        double[] hiddenErrors = new double[HIDDEN_NODES];
        // Hitung error pada lapisan tersembunyi
        for (int i = 0; i < HIDDEN_NODES; i++) {
            double error = 0.0;
            for (int j = 0; j < OUTPUT_NODES; j++) {
                error += outputErrors[j] * hiddenToOutputWeights[j][i];
            }
            hiddenErrors[i] = error * hiddenLayer[i] * (1.0 - hiddenLayer[i]);
        }
        // Perbarui bobot dari lapisan tersembunyi ke output
        for (int i = 0; i < OUTPUT_NODES; i++) {
            for (int j = 0; j < HIDDEN_NODES; j++) {
                hiddenToOutputWeights[i][j] += LEARNING_RATE * outputErrors[i] * hiddenLayer[j];
            }
        }
        // Perbarui bobot dari input ke lapisan tersembunyi
        for (int i = 0; i < HIDDEN_NODES; i++) {
            for (int j = 0; j < INPUT_NODES; j++) {
                inputToHiddenWeights[i][j] += LEARNING_RATE * hiddenErrors[i] * input[j];
            }
        }
    }

    // Latih model menggunakan dataset dan sejumlah epoch
    public void trainModel(double[][] dataset, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (double[] data : dataset) {
                double[] input = new double[INPUT_NODES];
                double[] target = new double[OUTPUT_NODES];
                System.arraycopy(data, 0, input, 0, INPUT_NODES);
                System.arraycopy(data, INPUT_NODES, target, 0, OUTPUT_NODES);
                forwardPropagation(input);
                backPropagation(input, target);
            }
        }
    }

    // Konversi indeks menjadi karakter alfabet
    public static String getCharacterFromIndex(int index) {
        return String.valueOf((char) ('A' + index));
    }

    // Prediksi karakter berdasarkan input
    public String predict(double[] input) {
        double[] output = forwardPropagation(input);
        int predictedIndex = maxIndex(output);
        return getCharacterFromIndex(predictedIndex);
    }

    // Temukan indeks dengan nilai maksimum
    private int maxIndex(double[] array) {
        int index = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[index]) {
                index = i;
            }
        }
        return index;
    }

    // Muat dataset dari direktori yang berisi gambar-gambar
    public static void loadDataset(File directory, List<double[]> dataset) throws IOException {
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile() && file.getName().matches("[A-Z]\\.png")) {
                    BufferedImage img = ImageIO.read(file);
                    ImageProcecing imgPro = new ImageProcecing();
                    Thinning thinning = new Thinning();

//                    BufferedImage gambar1 = imgPro.resize(img);
//                    BufferedImage gambar2 = thinning.thinning(gambar1);
//                    File fileGambar = new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\binary\\" + file.getName() + });
//                    ImageIO.write(gambar2, "png", fileGambar);
//


                    double[] inputArray = imageToArray(img);
                    double[] labelArray = new double[NeuralNetworks.OUTPUT_NODES];
//                    System.out.println("huruffggg : " + file.getName());
                    int labelIndex = getLabelIndex(file.getName().charAt(0));

                    if (labelIndex >= 0 && labelIndex < NeuralNetworks.OUTPUT_NODES) {
                        labelArray[labelIndex] = 1.0;
                    }
//                    int i = 0;
//                    System.out.println( "arrayLabel : " + labelArray[i]);
//                    i++;
//                    System.out.println("panjang  : " + labelArray.length);
// mengabungkan smeua value array antara (inputaArray dam labelArray)

                    double[] rowData = new double[inputArray.length + labelArray.length];
                    System.arraycopy(inputArray, 0, rowData, 0, inputArray.length);
                    System.arraycopy(labelArray, 0, rowData, inputArray.length, labelArray.length);
//                    for (double fata : rowData){
//                        if(fata == rowData[inputArray.length]){
//
//                            System.out.println("angka : " + fata);
//                        }
//                            System.out.println("angka2 : " + fata);
//                    }
                    dataset.add(rowData);
                }
            }
        }
    }

    // Dapatkan indeks label dari karakter
    public static int getLabelIndex(char character) {
        return character - 'A';
    }

    // Konversi gambar menjadi array
    public static double[] imageToArray(BufferedImage img) {
        BufferedImage resizedImg = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = resizedImg.createGraphics();
        double scaleX = 28.0 / img.getWidth();
//        System.out.println("scale ; " + scaleX);
        double scaleY = 28.0 / img.getHeight();
        AffineTransform at = AffineTransform.getScaleInstance(scaleX, scaleY);
        g.drawRenderedImage(img, at);
        g.dispose();

        double[] array = new double[INPUT_NODES];
        int index = 0;
        for (int y = 0; y < 28; y++) {
            for (int x = 0; x < 28; x++) {
                int pixel = resizedImg.getRGB(x, y) & 0xFF;
                array[index++] = pixel/255.0;
            }
        }
        return array;
    }

    // Metode main untuk menjalankan program
    public static void main(String[] args) throws Exception {
        NeuralNetworks ocr = new NeuralNetworks();

        String datasetPath = "D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\ModelUji";

        List<double[]> dataset = new ArrayList<>();
        loadDataset(new File(datasetPath), dataset);

        double[][] datasetArray = dataset.toArray(new double[0][]);
//        for(int x = 0; x < datasetArray.length; x++){
//            for(int i = 0; i < datasetArray.length; i++){
//                System.out.print(datasetArray[x][i] + "-");
//            }
//            System.out.println();
//        }

        int trainSize = (int) (0.8 * datasetArray.length);
        double[][] trainData = new double[trainSize][];
        double[][] testData = new double[datasetArray.length - trainSize][];

        System.arraycopy(datasetArray, 0, trainData, 0, trainSize);
        System.arraycopy(datasetArray, trainSize, testData, 0, datasetArray.length - trainSize);

        ocr.trainModel(trainData, NeuralNetworks.EPOCHS);

        File imageFile = new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\Thinning\\gambar_3.png");
        BufferedImage img = ImageIO.read(imageFile);
        double[] inputArray = imageToArray(img);

        String predictedChar = ocr.predict(inputArray);
        System.out.println("Predicted character: " + predictedChar);
    }
}
