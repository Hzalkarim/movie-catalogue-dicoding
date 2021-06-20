package hafizh.bangkit.submission.moviecatalogthesecond.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.tabs.TabLayout
import hafizh.bangkit.submission.moviecatalogthesecond.R
import hafizh.bangkit.submission.moviecatalogthesecond.ui.main.MainActivity
import hafizh.bangkit.submission.moviecatalogthesecond.utils.EspressoIdlingResource
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest : TestCase() {

    @Before
    public override fun setUp() {
        super.setUp()
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    public override fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testRvMovieAndRvTvShow_afterClickTab_isDisplayed() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tablayout_movietv)).perform(clickTabAt(1))
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.tablayout_movietv)).perform(clickTabAt(0))
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun testClickFirstMovieItem_gotoDetailActivity_isInDetailActivityByCheckingPoster() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                ViewActions.click()
            )
        )

        onView(withId(R.id.img_poster_detail)).check(matches(isDisplayed()))
    }

    @Test
    fun testClickFirstMovieItem_gotoDetailActivity_checkTitle() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                ViewActions.click()
            )
        )

        onView(withId(R.id.tv_title_detail)).check(matches(withText("The Conjuring: The Devil Made Me Do It")))
    }

    @Test
    fun testClickFirstTvShowItem_gotoDetailActivity_checkTitle() {
        onView(withId(R.id.tablayout_movietv)).perform(clickTabAt(1))
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                ViewActions.click()
            )
        )

        onView(withId(R.id.tv_title_detail)).check(matches(withText("Loki")))
    }

    private fun clickTabAt(index : Int) : ViewAction = object : ViewAction {
        override fun getConstraints(): Matcher<View> =
            Matchers.allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

        override fun getDescription(): String = ""

        override fun perform(uiController: UiController?, view: View?) {
            val tabLayout = view as TabLayout
            val tabItem = tabLayout.getTabAt(index)
            tabItem?.select()
        }
    }
}