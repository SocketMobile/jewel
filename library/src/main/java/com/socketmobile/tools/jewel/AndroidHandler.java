package com.socketmobile.tools.jewel;

import android.util.Log;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class AndroidHandler extends Handler {

    private static final int TAG_MAX_LENGTH = 23;

    @Override public void publish(LogRecord record) {
        if (record == null || !super.isLoggable(record)) {
            return;
        }

        String tag = tagFromLoggerName(record.getLoggerName());

        int level = JewelUtils.levelFromLevel(record.getLevel());

        Log.println(level, tag, record.getMessage());
        if (record.getThrown() != null) {
            Log.println(level, tag, Log.getStackTraceString(record.getThrown()));
        }
    }

    @Override public void flush() {}

    @Override public void close() throws SecurityException {}

    private @Nonnull String tagFromLoggerName(@Nullable String name) {
        if (name == null) {
            return "";
        }

        if (name.length() > TAG_MAX_LENGTH) {
            return name.substring(name.length() - TAG_MAX_LENGTH, name.length());
        } else {
            return name;
        }
    }
}
