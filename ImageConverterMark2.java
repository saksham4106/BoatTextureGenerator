import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageConverterMark2 {

    public static void main(String[] args){
        String fileName = "planks";
        BufferedImage plankTexture = readImage(fileName + ".png");
        BufferedImage grayBoat;

        File file = new File("grayscaled.png");
        if(!file.exists()){
            grayBoat = convertToGrayScale("boat.png");
        }else{
            grayBoat = readImage("grayscaled.png");
        }

        Map<Float, Float> hueMap = new HashMap<>();
        Map<Float, Float> satMap = new HashMap<>();
        float lowest_brightness = 1f;

        float[] hsv1 = RGBToHSV(plankTexture.getRGB(1, 0));
        for(int x = 0; x < plankTexture.getWidth(); x++){
            for(int y = 0; y < plankTexture.getHeight(); y++){
                float[] hsv = RGBToHSV(plankTexture.getRGB(x, y));
                hueMap.put(hsv[2], hsv[0]);
                satMap.put(hsv[2], hsv[1]);
                if(hsv[2] < lowest_brightness){
                    lowest_brightness = hsv[2];
                }
            }
        }

        for(int x = 0; x < grayBoat.getWidth(); x++){
            for(int y = 0; y < grayBoat.getHeight(); y++){
                float[] hsv = RGBToHSV(grayBoat.getRGB(x, y));
                if(hueMap.containsKey(hsv[2]) && satMap.containsKey(hsv[2])){
                    hsv[0] = hueMap.get(hsv[2]);
                    hsv[1] = satMap.get(hsv[2]);
                    grayBoat.setRGB(x, y, HSVToRGB(hsv));
                }else{
                    if((hsv[2] != 0 && hsv[2] != 1)){
                        hsv[0] = hsv1[0];
                        hsv[1] = hsv1[1];
                        grayBoat.setRGB(x, y, HSVToRGB(hsv));
                    }
                }
            }
        }
        writeImage(grayBoat, "output.png");
    }


    public static BufferedImage readImage(String path) {
        try {
            File input_file = new File(path);
            return ImageIO.read(input_file);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void writeImage(BufferedImage image, String path){
        try{
            File output_file = new File(path);
            ImageIO.write(image, "png", output_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] getRGB(int p){
        int a = (p>>24) & 0xff;
        int r = (p>>16) & 0xff;
        int g = (p>>8) & 0xff;
        int b = p & 0xff;
        return new int[] {
           a,r,g,b
        };
    }

    public static int HSVToRGB(float[] hsv){
        return Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
    }
    public static float[] RGBToHSV(int p){
        int [] argb = getRGB(p);
        return Color.RGBtoHSB(argb[1], argb[2], argb[3], null);
    }


    public static BufferedImage convertToGrayScale(String inputPath){
        BufferedImage inputImage = readImage(inputPath);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                int[] argb = getRGB(inputImage.getRGB(x, y));
                int avg = (argb[1] + argb[2] + argb[3]) / 3;
                int p = (argb[0] <<24) | (avg<<16) | (avg<<8) | avg;
                inputImage.setRGB(x, y, p);
            }
        }
        writeImage(inputImage, "grayscaled.png");
        return readImage("grayscaled.png");
    }
}
