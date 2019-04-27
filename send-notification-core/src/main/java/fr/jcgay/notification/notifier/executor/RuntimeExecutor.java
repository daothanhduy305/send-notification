package fr.jcgay.notification.notifier.executor;

import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import fr.jcgay.notification.SendNotificationException;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.slf4j.LoggerFactory.getLogger;

public class RuntimeExecutor implements Executor {

    private static final Logger LOGGER = getLogger(RuntimeExecutor.class);
    private static final int DEFAULT_WAIT_PROCESS_TIMEOUT = 500;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final long timeout;

    public RuntimeExecutor(long timeout) {
        this.timeout = timeout == -1 ? DEFAULT_WAIT_PROCESS_TIMEOUT : timeout;
    }

    @Override
    public void exec(final String[] command) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Will execute command line: {} (waiting at most: {} milliseconds)", logCommand(command), timeout);
        }

        Future<Integer> task = executor.submit(() -> {
            try {
                Process execution = new ProcessBuilder(command)
                    .redirectErrorStream(true)
                    .start();

                int returnCode = execution.waitFor();
                if (returnCode != 0) {
                    String message = "Command <[" + logCommand(command) + "]> returns code: " + returnCode + ".\n" + asString(execution.getInputStream());
                    LOGGER.debug(message);
                    throw new SendNotificationException(message);
                }

                LOGGER.debug("Command <[{}]> ends successfully.", logCommand(command));

                return returnCode;

            } catch (IOException e) {
                throw Throwables.propagate(e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw Throwables.propagate(e);
            }
        });

        try {
            task.get(timeout, MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw Throwables.propagate(e);
        } catch (ExecutionException e) {
            LOGGER.debug("An error occurs while running command: <[{}]>", logCommand(command), e);
            throw Throwables.propagate(e.getCause());
        } catch (TimeoutException e) {
            LOGGER.debug("Command <[{}]> takes too long to execute. Do not wait for the result...", logCommand(command), e);
        }
    }

    @Override
    public boolean tryExec(String[] command) {
        try {
            exec(command);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static String logCommand(String[] command) {
        return Joiner.on(" ").join(command);
    }

    private static String asString(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            return output.toString();
        }
    }

    @Override
    public void close() {
        executor.shutdownNow();
    }
}
