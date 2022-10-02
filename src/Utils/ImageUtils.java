/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Tee Zhuo Xuan
 */
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import org.apache.commons.codec.binary.Base64;

public class ImageUtils {

    // convert BufferedImage to byte[]
    public static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

    }

    // convert byte[] to BufferedImage
    public static BufferedImage toBufferedImage(byte[] bytes)
            throws IOException {

        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(is);
        return bi;

    }

    public static void main(String[] args) throws IOException {

        BufferedImage bi = ImageIO.read(new File("A:\\OneDrive\\OneDrive - Tunku Abdul Rahman University College\\Pictures\\Screenshots\\Screenshot (1).png"));

        // convert BufferedImage to byte[]
        byte[] bytes = toByteArray(bi, "png");

        //encode the byte array for display purpose only, optional
        String bytesBase64 = Base64.encodeBase64String(bytes);

        System.out.println(bytesBase64);

        // decode byte[] from the encoded string
        byte[] bytesFromDecode = Base64.decodeBase64(bytesBase64);

        // convert the byte[] back to BufferedImage
        BufferedImage newBi = toBufferedImage(bytesFromDecode);

        // save it somewhere
        ImageIO.write(newBi, "png", new File("A:\\OneDrive\\OneDrive - Tunku Abdul Rahman University College\\Desktop\\google-decode.png"));

    }

    public static BufferedImage iconToBufferedImage(Icon icon) {
        BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics g = bi.createGraphics();

        icon.paintIcon(null, g, 0, 0);
        g.dispose();

        return bi;
    }

    public static byte[] imgViewToByte(ImageView imgView) throws IOException {
        BufferedImage bImage = SwingFXUtils.fromFXImage(imgView.getImage(), null);
        if (bImage != null) {
            ByteArrayOutputStream s = new ByteArrayOutputStream();

            ImageIO.write(bImage, "png", s);
            byte[] res = s.toByteArray();

            s.close(); //especially if you are using a different output stream.
            return res;
        }
        return null;
    }

}
