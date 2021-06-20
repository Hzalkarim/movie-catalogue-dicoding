package hafizh.bangkit.submission.moviecatalogthesecond.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import hafizh.bangkit.submission.moviecatalogthesecond.adapter.MoviePagingAdapter
import hafizh.bangkit.submission.moviecatalogthesecond.adapter.OnClickCallbackMovieTvItem
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.BaseResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
import hafizh.bangkit.submission.moviecatalogthesecond.databinding.MovieFragmentBinding
import hafizh.bangkit.submission.moviecatalogthesecond.di.Injection
import hafizh.bangkit.submission.moviecatalogthesecond.factory.MovieTvShowViewModelFactory
import hafizh.bangkit.submission.moviecatalogthesecond.ui.detail.DetailActivity
import hafizh.bangkit.submission.moviecatalogthesecond.utils.EspressoIdlingResource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieFragment : Fragment() {

    private lateinit var pagingViewModel: MoviePagingViewModel
    private var _binding: MovieFragmentBinding? = null
    private val binding get() = _binding

    private val movAdapter = MoviePagingAdapter()

    companion object {
        fun newInstance() = MovieFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagingViewModel = ViewModelProvider(this, MovieTvShowViewModelFactory.getInstance(Injection.provideMovieTvShowRepository()))
            .get(MoviePagingViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPagingAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            EspressoIdlingResource.increment()
            pagingViewModel.movies.collectLatest {
                if (!EspressoIdlingResource.idlingResource.isIdleNow)
                    EspressoIdlingResource.decrement()
                movAdapter.submitData(it)
            }
        }
    }

    private fun initPagingAdapter() {
        with(binding?.rvMovie) {
            this?.layoutManager = LinearLayoutManager(binding?.root?.context)
            this?.adapter = movAdapter
        }


        movAdapter.setOnClickCallback(object : OnClickCallbackMovieTvItem {
            override fun onClickCallback(response: BaseResponse) {
                val detailIntent = Intent(requireContext(), DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_DETAIL, response as MovieResponse)
                detailIntent.putExtra(DetailActivity.EXTRA_IS_MOVIE, true)
                startActivity(detailIntent)
            }
        })

        movAdapter.addLoadStateListener { loadStates ->
            binding?.loadingMovie?.visibility = when {
                loadStates.append is LoadState.Loading -> View.VISIBLE
                loadStates.refresh is LoadState.Loading -> View.VISIBLE
                else -> View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}