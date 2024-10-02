//package Operantion.percobaanLagi;
//
//import java.awt.image.BufferedImage;
//
//public class Erotion {
//    public boolean erosi(BufferedImage gambar, int x, int y){
//        int pixel = gambar.getRGB(x, y);
//        int[][] operation = {
//                {y - 1, x - 1}, {y - 1, x}, {y - 1, x + 1}, {y, x + 1},
//                {y + 1, x + 1}, {y + 1, x}, {y + 1, x - 1}, {y, x - 1}
//        };
//
//        int pos2X = operation[1][1];
//        int pos4X = operation[3][1];
//        int pos6X = operation[5][1];
//        int pos8X = operation[7][1];
//
//        int pos2Y = operation[1][0];
//        int pos4Y = operation[3][0];
//        int pos6Y = operation[5][0];
//        int pos8Y = operation[7][0];
//
//        if(pixel == 255){
//            return false;
//        }
//        if(){
//
//        }
//
//        return true;
//    }
//}
