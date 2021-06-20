package hafizh.bangkit.submission.moviecatalogthesecond.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
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
    fun testGoToFavActivity_fromMovieFragment_checkDisplayTitle() {
        atStartToFavMovie()
        onView(withId(R.id.tv_display_fav)).check(matches(withText(R.string.display_movie_fav)))
    }

    @Test
    fun testGoToFavActivity_fromTvShowFragment_checkDisplayTitle() {
        atStartToFavTvShow()
        onView(withId(R.id.tv_display_fav)).check(matches(withText(R.string.display_tvshow_fav)))
    }

    private fun atStartToFavTvShow() {
        onView(withId(R.id.tablayout_movietv)).perform(clickTabAt(1))
        onView(withId(R.id.menu_fav)).perform(click())
    }

    private fun atStartToFavMovie() {
        onView(withId(R.id.menu_fav)).perform(click())
    }

    @Test
    fun testClickFirstMovieItem_gotoDetailActivity_checkTitleIsDisplayed() {
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                click()
            )
        )
        onView(withId(R.id.tv_title_detail)).check(matches(isDisplayed()))
    }

    @Test
    fun testClickFirstTvShowItem_gotoDetailActivity_checkTitleIsDisplayed() {
        onView(withId(R.id.tablayout_movietv)).perform(clickTabAt(1))
        onView(withId(R.id.rv_tvshow)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                click()
            )
        )
        onView(withId(R.id.tv_title_detail)).check(matches(isDisplayed()))
    }

    @Test
    fun testAddFirstMovieToFav_atZeroFav_checkAddFavButtonChange() {
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                click()
            )
        )
        onView(withId(R.id.btn_fav_control_detail)).check(matches(withText(R.string.add_fav)))
        onView(withId(R.id.btn_fav_control_detail)).perform(click())
        onView(withId(R.id.btn_fav_control_detail)).check(matches(withText(R.string.del_fav)))
    }

    @Test
    fun testRemoveMovieFav_from_favActivity_checkRemoveFavButtonChange() {
        atStartToFavMovie()
        onView(withId(R.id.rv_movietv_fav)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                click()
            )
        )
        onView(withId(R.id.btn_fav_control_detail)).check(matches(withText(R.string.del_fav)))
        onView(withId(R.id.btn_fav_control_detail)).perform(click())
        onView(withId(R.id.btn_fav_control_detail)).check(matches(withText(R.string.add_fav)))
    }

    @Test
    fun testMovieFav_isEmpty_byFailingToClick() {
        atStartToFavMovie()
        onView(withId(R.id.rv_movietv_fav)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                click()
            )
        ).withFailureHandler { e, _ -> assert(e is NullPointerException) }
    }

    @Test
    fun testAddFirstTvShowToFav_atZeroFav_checkAddFavButtonChange() {
        onView(withId(R.id.tablayout_movietv)).perform(clickTabAt(1))
        onView(withId(R.id.rv_tvshow)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                click()
            )
        )
        onView(withId(R.id.btn_fav_control_detail)).check(matches(withText(R.string.add_fav)))
        onView(withId(R.id.btn_fav_control_detail)).perform(click())
        onView(withId(R.id.btn_fav_control_detail)).check(matches(withText(R.string.del_fav)))
    }

    @Test
    fun testRemoveTvShowFav_from_favActivity_checkRemoveFavButtonChange() {
        atStartToFavTvShow()
        onView(withId(R.id.rv_movietv_fav)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                click()
            )
        )
        onView(withId(R.id.btn_fav_control_detail)).check(matches(withText(R.string.del_fav)))
        onView(withId(R.id.btn_fav_control_detail)).perform(click())
        onView(withId(R.id.btn_fav_control_detail)).check(matches(withText(R.string.add_fav)))
    }

    @Test
    fun testTvShowFav_isEmpty_byFailingToClick() {
        atStartToFavTvShow()
        onView(withId(R.id.rv_movietv_fav)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                click()
            )
        ).withFailureHandler { e, _ -> assert(e is NullPointerException) }
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