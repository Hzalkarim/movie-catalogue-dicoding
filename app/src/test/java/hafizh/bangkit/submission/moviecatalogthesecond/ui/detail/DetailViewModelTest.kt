package hafizh.bangkit.submission.moviecatalogthesecond.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import hafizh.bangkit.submission.moviecatalogthesecond.data.DetailRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.DetailDataSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.BaseResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.TvShowResponse
import hafizh.bangkit.submission.moviecatalogthesecond.utils.RxImmediateSchedulerRule
import junit.framework.TestCase
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest : TestCase() {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val scheduler = RxImmediateSchedulerRule()
    }

    private lateinit var baseResponse: BaseResponse

    private lateinit var dataSource: DetailDataSource

    private lateinit var repository: DetailRepository

    @Mock
    private lateinit var observer : Observer<BaseResponse>

    private lateinit var viewModel: DetailViewModel

    @Before
    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetDetailMovie() {
        val movie = MovieResponse(title = "Reservoir Dogs")
        setViewModel(movie)

        viewModel.data.observeForever(observer)
        viewModel.setLiveData()

        assert(viewModel.data.value!!.title == movie.title)
    }

    @Test
    fun testGetDetailTvShow() {
        val tvShow = TvShowResponse(title = "Mad About You")
        setViewModel(tvShow)

        viewModel.data.observeForever(observer)
        viewModel.setLiveData()

        assert(viewModel.data.value!!.title == tvShow.title)
    }

    @Test
    fun testSetDetailMovie() {
        val movie = MovieResponse(title = "Non-Existent Movie ")
        setViewModel(movie)

        viewModel.data.observeForever(observer)
        viewModel.setLiveData()

        assert(viewModel.data.value!!.title == movie.title)

        val movieNew = MovieResponse(title = "Another Dummy Non Factual Movie ")
        viewModel.setLiveData(movieNew)

        assert(viewModel.data.value!!.title == movieNew.title)
    }

    private fun setViewModel(baseResponse: BaseResponse) {
        this.baseResponse = baseResponse
        dataSource = DetailDataSource(baseResponse)
        repository = DetailRepository(dataSource)
        viewModel = DetailViewModel(repository)
    }
}