package fr.jcgay.notification.notifier.systemtray;

import fr.jcgay.notification.Application;
import fr.jcgay.notification.Notification;
import fr.jcgay.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SystemTrayNotifier implements Notifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemTrayNotifier.class);

    private final Application application;

    private boolean skipNotifications;
    private TrayIcon icon;

    public SystemTrayNotifier(Application application) {
        LOGGER.debug("Configuring System Tray for application {}.", application);
        this.application = application;
    }

    @Override
    public void init() {
        if (!SystemTray.isSupported()) {
            skipNotifications = true;
            LOGGER.warn("SystemTray is not supported, skipping notifications...");
            return;
        }

        icon = new TrayIcon(createImage(application.icon().toByteArray()), application.name());
        icon.setImageAutoSize(true);

        try {
            SystemTray.getSystemTray().add(icon);
        } catch (AWTException e) {
            throw new SystemTrayNotificationException("Error initializing SystemTray Icon.", e);
        }
    }

    @Override
    public void send(Notification notification) {
        if (!skipNotifications) {
            icon.setImage(createImage(notification.icon().toByteArray()));
            icon.displayMessage(notification.title(), notification.message(), toMessageType(notification.level()));
        }
    }

    @Override
    public void close() {
        if (!skipNotifications) {
            try {
                Thread.sleep(application.timeout() == -1 ? SECONDS.toMillis(2) : application.timeout());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            SystemTray.getSystemTray().remove(icon);
        }
    }

    private static MessageType toMessageType(Notification.Level level) {
        switch (level) {
            case INFO:
                return MessageType.INFO;
            case WARNING:
                return MessageType.WARNING;
            case ERROR:
                return MessageType.ERROR;
            default:
                return MessageType.NONE;
        }
    }

    private Image createImage(byte[] imageData) {
        return Toolkit.getDefaultToolkit().createImage(imageData);
    }
}