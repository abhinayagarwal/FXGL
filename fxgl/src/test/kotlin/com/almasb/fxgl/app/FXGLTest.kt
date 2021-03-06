/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxgl.app

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

/**
 *
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
class FXGLTest {

    companion object {
        @BeforeAll
        @JvmStatic fun before() {
            FXGLMock.mock()
        }
    }

    @Test
    fun `GameApplication subclass is a singleton across FXGL`() {
        assertTrue(FXGL.getApp() === FXGL.getAppCast<MockGameApplication>())
        assertTrue(FXGL.getApp() === MockGameApplication.get())
    }

    @Test
    fun `fail if property not found`() {
        var count = 0

        try {
            FXGL.getBoolean("TestBoolean")
        } catch (e: IllegalArgumentException) {
            count++
        }

        assertThat(count, `is`(1))

        try {
            FXGL.getInt("TestInt")
        } catch (e: IllegalArgumentException) {
            count++
        }

        assertThat(count, `is`(2))

        try {
            FXGL.getDouble("TestDouble")
        } catch (e: IllegalArgumentException) {
            count++
        }

        assertThat(count, `is`(3))

        try {
            FXGL.getString("TestString")
        } catch (e: IllegalArgumentException) {
            count++
        }

        assertThat(count, `is`(4))
    }

    @Test
    fun `Property key is case sensitive`() {
        FXGL.setProperty("UPPERCASE", "value")

        assertThrows(IllegalArgumentException::class.java, {
            FXGL.getString("uppercase")
        })
    }

    @Test
    fun `Set and Get property`() {
        FXGL.setProperty("StringKey", "StringValue")
        assertThat(FXGL.getString("StringKey"), `is`("StringValue"))

        FXGL.setProperty("KeyForInt", 1234)
        assertThat(FXGL.getInt("KeyForInt"), `is`(1234))

        FXGL.setProperty("KeyForDouble", 4321.5)
        assertThat(FXGL.getDouble("KeyForDouble"), `is`(4321.5))

        FXGL.setProperty("KeyForBoolean", true)
        assertThat(FXGL.getBoolean("KeyForBoolean"), `is`(true))
    }
}