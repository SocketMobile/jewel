package com.socketmobile.jewel;

import android.util.Log;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces the default Android {@link java.util.logging.Handler} with one that works
 *
 * Call {@link #install(Map)} early in your {@link android.app.Application#onCreate()}
 */
@ParametersAreNonnullByDefault public final class Jewel {

    private static final String TAG = Jewel.class.getName();

    static final String DEFAULT_HANDLER_CLASSNAME = "com.android.internal.logging.AndroidHandler";

    private static Handler instance;

    private Jewel() {
        throw new UnsupportedOperationException();
    }

    /**
     * Installs the logging handler without setting any log levels.
     *
     * @see #install(Map)
     */
    public static void install() {
        install(Collections.<String, Integer>emptyMap());
    }

    /**
     * Installs a modified logging handler and applies the given log levels.
     * <p>
     * Calling install a second time updates the log levels using the given map.
     * <p>
     * Usage:
     *
     * <pre>{@code
     * Map<String, Integer> levels = new HashMap<>();
     * levels.put("com.example.package1", Log.ERROR);
     * levels.put("com.example.package2", Log.VERBOSE);
     * Jewel.install(levels);
     * }</pre>
     *
     * @param levels logger names mapped to the {@link android.util.Log} level that should be shown in logcat
     */
    public static void install(Map<String, Integer> levels) {
        Log.i(TAG, "Installing AndroidHandler for java.util.logging");
        Logger rootLogger = getRootLogger();

        if (rootLogger != null) {
            boolean alreadyInstalled = false;

            for (Handler h : rootLogger.getHandlers()) {
                if (h.getClass().getName().equals(DEFAULT_HANDLER_CLASSNAME)) {
                    rootLogger.removeHandler(h);
                } else if (h instanceof AndroidHandler) {
                    alreadyInstalled = true;
                }
            }

            if (!alreadyInstalled) {
                instance = new AndroidHandler();
                rootLogger.addHandler(instance);
            }

            for (Map.Entry<String, Integer> level : levels.entrySet()) {
                Log.d(TAG, String.format("Setting level for \"%s\" logger to %s", level.getKey(),
                        JewelUtils.getLevelName(level.getValue())));
                Logger.getLogger(level.getKey())
                        .setLevel(JewelUtils.levelFromLevel(level.getValue()));
            }
        }
    }

    /**
     * Removes the {@link java.util.logging.Handler} that was installed by {@link #install(Map)}
     */
    public static void uninstall() {
        if (instance == null) {
            return;
        }

        Logger rootLogger = getRootLogger();
        if (rootLogger != null) {
            rootLogger.removeHandler(instance);
        }
    }

    private static @Nullable Logger getRootLogger() {
        return LogManager.getLogManager().getLogger("");
    }
}
