package hafizh.bangkit.submission.moviecatalogthesecond.ui.tvshow

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
import hafizh.bangkit.submission.moviecatalogthesecond.adapter.OnClickCallbackMovieTvItem
import hafizh.bangkit.submission.moviecatalogthesecond.adapter.TvShowPagingAdapter
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.BaseResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.TvShowResponse
import hafizh.bangkit.submission.moviecatalogthesecond.databinding.TvShowFragmentBinding
import hafizh.bangkit.submission.moviecatalogthesecond.di.Injection
import hafizh.bangkit.submission.moviecatalogthesecond.factory.MovieTvShowViewModelFactory
import hafizh.bangkit.submission.moviecatalogthesecond.ui.detail.DetailActivity
import hafizh.bangkit.submission.moviecatalogthesecond.utils.EspressoIdlingResource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TvShowFragment : Fragment() {

    private lateinit var pagingViewModel: TvShowPagingViewModel

    private var _binding: TvShowFragmentBinding? = null
    private val binding get() = _binding

    private val tvShowAdapter = TvShowPagingAdapter()

    companion object {
        fun newInstance() = TvShowFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TvShowFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MovieTvShowViewModelFactory.getInstance(Injection.provideMovieTvShowRepository())
        pagingViewModel = ViewModelProvider(this, factory)[TvShowPagingViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPagingAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            EspressoIdlingResource.increment()
            pagingViewModel.tvShows.collectLatest {
                if (!EspressoIdlingResource.idlingResource.isIdleNow)
                    EspressoIdlingResource.decrement()
                tvShowAdapter.submitData(it)
            }
        }
    }

    private fun initPagingAdapter() {
        tvShowAdapter.setOnClickCallback(
            object : OnClickCallbackMovieTvItem {
                override fun onClickCallback(response: BaseResponse) {
                    val detailIntent = Intent(requireContext(), DetailActivity::class.java)
                    detailIntent.putExtra(DetailActivity.EXTRA_DETAIL, response as TvShowResponse)
                    detailIntent.putExtra(DetailActivity.EXTRA_IS_MOVIE, false)
                    startActivity(detailIntent)
                }
            }
        )

        binding?.rvTvshow?.layoutManager = LinearLayoutManager(binding?.root?.context)
        binding?.rvTvshow?.adapter = tvShowAdapter

        tvShowAdapter.addLoadStateListener { loadStates ->
            binding?.loadingTvShow?.visibility = when {
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