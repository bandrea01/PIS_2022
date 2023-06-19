package Business;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

public class ImmagineBusiness {
    private static ImmagineBusiness instance;

    public static synchronized ImmagineBusiness getInstance() {
        if (instance == null) {
            instance = new ImmagineBusiness();
        }
        return instance;
    }

    public Image getImageByFilename(String filename){
        try {
            return ImageIO.read(getClass().getResource("/Images/" + filename));
        } catch (IOException e) {
            System.out.println("Image file Error: " + e.getMessage());
        }
        return null;
    }

    public Image getImageByBlob(Blob blob) throws IOException,SQLException {
        byte[] imagedata = blob.getBytes(1L, (int) blob.length());
        final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagedata));
        ImageIO.write(bufferedImage, "png",new File("/Images/test1"));
        return bufferedImage;
    }
    public Blob getBlobByImage(Image image, String format){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write((BufferedImage) image, format, stream);
            return new SerialBlob(stream.toByteArray());
        } catch (IOException | SQLException e){
            System.out.println("Blob-Image conversion Error: " + e.getMessage());
        }
        return null;
    }
}
