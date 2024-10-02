//package Operantion.DetectionImage;
//
//import net.sourceforge.tess4j.ITesseract;
//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.TesseractException;
//
//import java.io.File;
//
//public class ImageDetection {
//    // Object (ITesseract)
//    ITesseract detaction = new Tesseract();
//    String hasilTxt;
//    public String imgdetaction(){
//        try {
//            detaction.setDatapath("D:\\java\\tess4j\\tessdata-main\\tessdata-main");
//            hasilTxt = detaction.doOCR(new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\gambar.png"));
//        } catch (TesseractException e) {
//            throw new RuntimeException(e);
//        }
//        return hasilTxt;
//    }
//}
