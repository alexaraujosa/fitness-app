package utils;

import josefinFA.JosefinFitnessApp;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Logger {
    public static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JosefinFitnessApp.class.getName());

    public static java.util.logging.Logger setup(String logName) {
        try {
            // Remove the default console handler
            logger.setUseParentHandlers(false);

            Path cwd = Paths.get(System.getProperty("user.dir"));
            Path trueFile = Paths.get(cwd.toString(), Logger.generateLogFileName(logName));

            FileHandler fileHandler = new FileHandler(trueFile.toString(), true);
            fileHandler.setFormatter(new LoggerFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logger;
    }

    private static String generateLogFileName(String prefix) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String currentDateTime = dateFormat.format(new Date());
        return prefix + "-" + currentDateTime + ".txt";
    }

    private static class LoggerFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return String.format(
                    "[%s] [%tT] [%s:%s] %s%n",
                    record.getLevel(),
                    record.getMillis(),
                    record.getSourceClassName(),
                    record.getSourceMethodName(),
                    record.getMessage()
            );
        }
    }
}
