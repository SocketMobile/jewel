package com.socketmobile.tools.jewel

import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.logging.Handler
import java.util.logging.LogManager
import java.util.logging.LogRecord

@RunWith(AndroidJUnit4::class)
class JewelTest {

    val rootLogger = LogManager.getLogManager().getLogger("")

    private var handlerStash: Array<Handler> = emptyArray()

    @Before fun setUp() {
        handlerStash = rootLogger.handlers
    }

    @After fun tearDown() {
        rootLogger.handlers.forEach {
            rootLogger.removeHandler(it)
        }
        handlerStash.forEach {
            rootLogger.addHandler(it)
        }
    }

    @Test fun unmodified_logging_setup_only_has_default_handler() {
        assertEquals(1, rootLogger.handlers.size)
        assertEquals(
            1,
            rootLogger.handlers.count { it.javaClass.name == Jewel.DEFAULT_HANDLER_CLASSNAME })
    }

    @Test fun default_handler_is_removed_when_Jewel_installed() {
        Jewel.install()

        assertEquals(
            0,
            rootLogger.handlers.count { it.javaClass.name == Jewel.DEFAULT_HANDLER_CLASSNAME })
    }

    @Test fun jewel_Android_handler_is_added_when_Jewel_installed() {
        Jewel.install()

        assertEquals(1, rootLogger.handlers.count { it is AndroidHandler })
    }

    @Test fun other_handlers_are_not_removed_when_Jewel_installed() {
        val otherHandler = object : Handler() {
            override fun flush() {}
            override fun close() {}
            override fun publish(record: LogRecord?) {}
        }
        rootLogger.addHandler(otherHandler)
        Jewel.install()

        assertEquals(1, rootLogger.handlers.count { it == otherHandler })
    }

    @Test fun jewel_can_be_uninstalled() {
        Jewel.install()
        Jewel.uninstall()

        assertEquals(0, rootLogger.handlers.count { it is AndroidHandler })
    }
}
