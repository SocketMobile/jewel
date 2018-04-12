package com.socketmobile.tools.jewel;

import android.util.Log;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
class JewelUtils {
    private JewelUtils() {
        throw new UnsupportedOperationException();
    }

    static @Nonnull String getLevelName(int level) {
        switch (level) {
            case 1:
                return "VERY VERBOSE";
            case Log.VERBOSE:
                return "VERBOSE";
            case Log.DEBUG:
                return "DEBUG";
            case Log.INFO:
                return "INFO";
            case Log.WARN:
                return "WARN";
            case Log.ERROR:
                return "ERROR";
            case Log.ASSERT:
                return "ASSERT";
            default:
                return "UNKNOWN";
        }
    }

    static int levelFromLevel(Level level) {
        int levelInt = level.intValue();
        if (levelInt >= 1000) {
            return Log.ERROR;
        } else if (levelInt >= 900) {
            return Log.WARN;
        } else if (levelInt >= 700) {
            return Log.INFO;
        } else if (levelInt >= 500) {
            return Log.DEBUG;
        } else {
            return Log.VERBOSE;
        }
    }

    static @Nonnull Level levelFromLevel(int level) {
        switch (level) {
            case 1:
                return Level.FINEST;
            case Log.VERBOSE:
                return Level.FINER;
            case Log.DEBUG:
                return Level.FINE;
            case Log.INFO:
                return Level.INFO;
            case Log.WARN:
                return Level.WARNING;
            case Log.ERROR:
                return Level.SEVERE;
            case Log.ASSERT:
                return Level.SEVERE;
            default:
                return Level.FINE;
        }
    }
}
