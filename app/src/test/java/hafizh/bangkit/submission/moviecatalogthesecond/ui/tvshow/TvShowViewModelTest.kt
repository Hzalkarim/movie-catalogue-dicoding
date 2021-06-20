package hafizh.bangkit.submission.moviecatalogthesecond.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import hafizh.bangkit.submission.moviecatalogthesecond.data.MovieTvShowRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.RemoteDataSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.MovieTvShowApiService
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.TvShowResponse
import hafizh.bangkit.submission.moviecatalogthesecond.utils.RxImmediateSchedulerRule
import io.reactivex.rxjava3.core.Observable
import junit.framework.TestCase
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest : TestCase() {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val scheduler = RxImmediateSchedulerRule()
    }

    @Mock
    private lateinit var client: MovieTvShowApiService

    private lateinit var dataSource: RemoteDataSource

    private lateinit var repository: MovieTvShowRepository

    @Mock
    private lateinit var observer : Observer<ArrayList<TvShowResponse>>

    private lateinit var viewModel: TvShowViewModel

    @Before
    public override fun setUp() {
        super.setUp()
        dataSource = RemoteDataSource(client)
        repository = MovieTvShowRepository(dataSource)
        viewModel = TvShowViewModel(repository)
        viewModel.setUseIdlingResource = false
    }

    @Test
    fun testViewModelGetTvShow() {
        val tvShow = TvShowResponse("Mad About You")

        `when`(client.getTvShow(500)).thenReturn(Observable.just(tvShow))

        viewModel.tvShow.observeForever(observer)
        viewModel.getTvShows(listOf(500))

        assert(viewModel.tvShow.value!![0].title == tvShow.title)
    }
}