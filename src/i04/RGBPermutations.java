package i04;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author MuaazH
 */
public class RGBPermutations {

    private static int[] decodeColor(int c) {
        return new int[] {
            (c >> 0x10) & 0xFF,
            (c >> 0x08) & 0xFF,
            c & 0xFF, 
            (c >> 0x18) & 0xFF
        };
    }
    
    private static int encodeColor(int r, int g, int b, int a) {
        return ((a & 0xFF) << 0x18)
                | ((r & 0xFF) << 0x10)
                | ((g & 0xFF) << 0x08)
                | (b & 0xFF);
    }
    
    public static BufferedImage swapRGB(BufferedImage img, int r, int g, int b) {
        // lazy writing, this code is super slow
        // but it does not matter since it's only used for small images
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int[] colors = decodeColor(img.getRGB(i, j));
                out.setRGB(i, j, encodeColor(colors[r], colors[g], colors[b], colors[3]));
            }
        }
        return out;
    }
    
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: RGBPermutations <Image File>");
            System.exit(1);
        }
        BufferedImage img = ImageIO.read(new File(args[0]));
        for (int r = 0; r < 3; r++) {
            for (int g = 0; g < 3; g++) {
                for (int b = 0; b < 3; b++) {
                    BufferedImage out = swapRGB(img, r, g, b);
                    ImageIO.write(out, "png", new File(String.format("rgb_%d%d%d.png", r, g, b)));
                }
            }
        }
    }
    
}
