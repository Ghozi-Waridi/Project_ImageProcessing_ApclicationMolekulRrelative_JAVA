package Operantion.percobaanLagi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int[][] toArray = new int[28][28];
        BufferedImage gambar  = ImageIO.read(new File("D:\\java\\ProjectPBO\\ApclicationMolekulRrelative\\Image\\binary\\A.png.png"));
        for(int i = 0; i < 28; i++){
            for(int x = 0; x < 28; x ++){
                int p = gambar.getRGB(i, x);
                if(p == Color.white.getRed()){
                    toArray[i][x] = 1;
                } else {
                    toArray[i][x] = 0;
                }


                System.out.print(toArray[i][x] + " ");
            }
            System.out.println("");
        }
    }
}
