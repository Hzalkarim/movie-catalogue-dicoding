package hafizh.bangkit.submission.moviecatalogthesecond.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import hafizh.bangkit.submission.moviecatalogthesecond.R
import hafizh.bangkit.submission.moviecatalogthesecond.databinding.ActivityMainBinding
import hafizh.bangkit.submission.moviecatalogthesecond.ui.fav.FavActivity
import hafizh.bangkit.submission.moviecatalogthesecond.ui.movie.MovieFragment
import hafizh.bangkit.submission.moviecatalogthesecond.ui.tvshow.TvShowFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val movieFragment = MovieFragment.newInstance()
    private val tvShowFragment = TvShowFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Movie Catalogue"

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentView.id, movieFragment)
            .add(binding.fragmentView.id, tvShowFragment)
            .hide(tvShowFragment)
            .commit()

        binding.tablayoutMovietv.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val sup = supportFragmentManager.beginTransaction()
                when (tab?.position) {
                    0 -> {
                        sup.hide(tvShowFragment).show(movieFragment)
                    }
                    1 -> {
                        sup.hide(movieFragment).show(tvShowFragment)
                    }
                }
                sup.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_fav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_fav -> {
                val favIntent = Intent(this, FavActivity::class.java)
                val isMovie = binding.tablayoutMovietv.selectedTabPosition == 0
                favIntent.putExtra(FavActivity.EXTRA_IS_MOVIE, isMovie)
                startActivity(favIntent)
                true
            }
            else -> false
        }
}