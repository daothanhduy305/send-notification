package fr.jcgay.notification;

import com.google.auto.value.AutoValue;
import com.google.common.io.ByteStreams;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * An icon that can be displayed in notification, or in software that need application registration.
 */
@AutoValue
public abstract class Icon {

    /**
     * A unique identifier. <br>
     * You should ensure that this id will be unique because it can be used to temporary write file on disk.
     *
     * @return unique identifier.
     */
    public abstract String id();

    /**
     * URL for this resource.
     *
     * @return URL to load icon.
     */
    public abstract URL content();

    public BufferedImage toImage() {
        try {
            return ImageIO.read(content());
        } catch (IOException e) {
            throw new SendNotificationException("Error while reading status icon.", e);
        }
    }

    public byte[] toByteArray() {
        try (InputStream is = content().openStream()) {
            return ByteStreams.toByteArray(is);
        } catch (IOException e) {
            throw new SendNotificationException("Error while reading status icon.", e);
        }
    }

    public String asPath() {
        String folder = System.getProperty("java.io.tmpdir") + "/send-notifications-icons/";
        String extension = extension();
        File icon = new File(folder + id() + "." + extension);
        if (!icon.exists()) {
            new File(folder).mkdirs();
            try {
                write(icon);
            } catch (IOException e) {
                throw new SendNotificationException("Can't write notification icon : " + icon.getPath(), e);
            }
        }
        return icon.getPath();
    }

    public String extension() {
        return content().getPath().substring(content().getPath().lastIndexOf(".") + 1);
    }

    public static Icon create(URL content, String id) {
        return new AutoValue_Icon(id, content);
    }

    private void write(File destination) throws IOException {
        try (InputStream input = content().openStream(); FileOutputStream output = new FileOutputStream(destination)) {
            byte[] buffer = new byte[1024 * 4];
            int n;
            while ((n = input.read(buffer)) != -1) {
                output.write(buffer, 0, n);
            }
        }
    }
}
