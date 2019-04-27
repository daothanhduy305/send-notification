package fr.jcgay.notification.notifier.snarl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.io.Closeables;
import fr.jcgay.notification.Application;
import fr.jcgay.notification.DiscoverableNotifier;
import fr.jcgay.notification.Notification;
import fr.jcgay.notification.Notifier;
import fr.jcgay.snp4j.Icon;
import fr.jcgay.snp4j.Server;
import fr.jcgay.snp4j.SnpException;
import fr.jcgay.snp4j.impl.SnpNotifier;
import fr.jcgay.snp4j.request.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SnarlNotifier implements DiscoverableNotifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnarlNotifier.class);

    private final Application application;
    private final SnarlConfiguration configuration;
    private final fr.jcgay.snp4j.Application snarlApplication;

    private fr.jcgay.snp4j.Notifier snarl;

    public SnarlNotifier(Application application, SnarlConfiguration configuration) {
        LOGGER.debug("Configuring Snarl for application {}: {}.", application, configuration);
        this.application = application;
        this.configuration = configuration;
        this.snarlApplication = fr.jcgay.snp4j.Application.withPassword(application.id(), application.name(), configuration.applicationPassword());
    }

    @Override
    public Notifier init() {
        Server server = Server.builder()
            .withHost(configuration.host())
            .withPort(configuration.port())
            .build();

        try {
            snarl = SnpNotifier.of(snarlApplication, server);
        } catch (SnpException e) {
            throw new SnarlNotificationException("Cannot register application with Snarl.", e);
        }
        return this;
    }

    @Override
    public void send(Notification notification) {
        if (snarl == null) {
            LOGGER.warn("Snarl notifier is not initialized, cannot send notification.");
            return;
        }

        fr.jcgay.snp4j.request.Notification snarlNotification = new fr.jcgay.snp4j.request.Notification();
        snarlNotification.setIcon(Icon.base64(notification.icon().toByteArray()));
        snarlNotification.setText(notification.message());
        snarlNotification.setTitle(notification.title());
        snarlNotification.setPriority(toPriority(notification.level()));

        init();
        try {
            snarl.send(snarlNotification);
        } catch (SnpException e) {
            throw new SnarlNotificationException("Cannot send notification to Snarl.", e);
        }
    }

    @Override
    public void close() {
        if (snarl != null) {
            try {
                Closeables.close(snarl, true);
            } catch (IOException ignored) {}
        }
    }

    @Override
    public boolean isPersistent() {
        return false;
    }

    @Override
    public boolean tryInit() {
        try {
            init();
            return true;
        } catch (RuntimeException e) {
            close();
            return false;
        }
    }

    private static Priority toPriority(Notification.Level level) {
        switch (level) {
            case WARNING:
            case ERROR:
                return Priority.HIGH;
            default:
                return Priority.NORMAL;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(application, configuration);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SnarlNotifier other = (SnarlNotifier) obj;
        return Objects.equal(this.application, other.application)
            && Objects.equal(this.configuration, other.configuration);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("application", application)
            .add("configuration", configuration)
            .toString();
    }
}
