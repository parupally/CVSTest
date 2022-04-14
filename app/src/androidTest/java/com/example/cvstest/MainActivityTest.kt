package com.example.cvstest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.cvstest.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var instantExecutor = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Test
    fun validateTextHeader() {
        val tvHeader = onView(withId(R.id.tvHeader))
        tvHeader.check(matches(isDisplayed()))
        tvHeader.check(matches(isEnabled()))
        tvHeader.check(matches(withText("Images")))
    }

    @Test
    fun validateSearchView() {
        val tvHeader = onView(withId(R.id.search_view))
        tvHeader.check(matches(isDisplayed()))
        tvHeader.check(matches(isEnabled()))
        tvHeader.check(matches(withHint(R.string.search_images)))
    }
}