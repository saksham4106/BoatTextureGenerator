/*

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {
    public static void main(String[] args) {
        int width;
        int height;
        BufferedImage image = null;
        BufferedImage img = null;
        try{
            File input_file =  new File("/home/sakshamk/IdeaProjects/BoatTextureGen/src/boat.png");
            File f = new File("/home/sakshamk/IdeaProjects/BoatTextureGen/src/planks.png");
            img = ImageIO.read(f);
            image = ImageIO.read(input_file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int s = img.getRGB(5, 1);
        int b1 = s & 0xff;
        int a1 = (s >> 24) & 0xff;
        int r1 = (s >> 16) & 0xff;
        int g1 = (s >> 8) & 0xff;
        float[] hsv = Color.RGBtoHSB(r1, g1, b1, null);
        float average_hue = hsv[0];
        float average_saturation = hsv[1];
        float average_brightness = hsv[2];

        width = image.getWidth();
        height = image.getHeight();

        for (int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                int p = image.getRGB(i, j);
                int a = (p>>24) & 0xff;
                int r = (p>>16) & 0xff;
                int g = (p>>8) & 0xff;
                int b = p & 0xff;

                float[] hsb = Color.RGBtoHSB(r, g, b, null);
                float hue = hsb[0];
                float saturation = hsb[1];
                float brightness = hsb[2];
                //if(hue != 0)
                //System.out.println("hue: " + hue + ", saturation: " + saturation + ", brightness: " + brightness);

//                hue = 0.12195122f;
//                saturation = 0.37575758f;
                System.out.println(average_hue + "   " + average_saturation);
                hue = average_hue;
                saturation = average_saturation;
                brightness += (average_brightness / 3f);

                int rgb = Color.HSBtoRGB(hue, saturation, brightness);

                r = (rgb>>16)&0xFF;
                g = (rgb>>8)&0xFF;
                b = rgb&0xFF;


                p= a << 24| r <<16 | g << 8 | b;
                image.setRGB(i, j, p);
            }
        }


        try{
            File output_file = new File("output1.png.png");
            ImageIO.write(image, "png", output_file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

*/

