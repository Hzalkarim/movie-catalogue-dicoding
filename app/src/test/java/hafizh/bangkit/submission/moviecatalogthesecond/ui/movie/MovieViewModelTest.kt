package hafizh.bangkit.submission.moviecatalogthesecond.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import hafizh.bangkit.submission.moviecatalogthesecond.data.MovieTvShowRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.RemoteDataSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.MovieTvShowApiService
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
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
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest : TestCase() {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val scheduler = RxImmediateSchedulerRule()
    }

    @Mock
    private lateinit var apiService: MovieTvShowApiService

    private lateinit var repository: MovieTvShowRepository

    @Mock
    private lateinit var observer : Observer<ArrayList<MovieResponse>>

    private lateinit var viewModel: MovieViewModel

    @Before
    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        val dataSource = RemoteDataSource(apiService)
        repository = MovieTvShowRepository(dataSource)
        viewModel = MovieViewModel(repository)
        viewModel.setUseIdlingResource = false
    }

    @Test
    fun testViewModelGetMovie() {
        val movie = MovieResponse("Reservoir Dogs")
        `when`(apiService.getMovie(500)).thenReturn(Observable.just(movie))

        viewModel.movie.observeForever(observer)
        viewModel.getMovie(listOf(500))

        assert(viewModel.movie.value!![0].title == movie.title)
    }
}