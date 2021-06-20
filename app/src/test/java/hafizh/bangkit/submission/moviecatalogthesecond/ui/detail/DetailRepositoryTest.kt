package hafizh.bangkit.submission.moviecatalogthesecond.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailRepositoryTest : TestCase() {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val scheduler = RxImmediateSchedulerRule()
    }

    private lateinit var movieEntity: MovieResponse
    private lateinit var tvShowEntity: TvShowResponse

    private lateinit var dataSource: DetailDataSource

    private lateinit var repository: DetailRepository

    @Before
    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testRepositoryDetailMovie() {
        movieEntity = MovieResponse(title = "Reservoir Dogs")
        setRepository(movieEntity)

        val movieResult = repository.getMovie()

        assertEquals(movieResult.title, movieEntity.title)

    }

    @Test
    fun testRepositoryDetailTvShow() {
        tvShowEntity = TvShowResponse(title = "Mad About You")
        setRepository(tvShowEntity)

        val tvShowResult = repository.getTvShow()

        assertEquals(tvShowResult.title, tvShowEntity.title)
    }

    @Test
    fun testRepositorySetMovie() {
        movieEntity = MovieResponse(title = "Reservoir Dogs")
        setRepository(movieEntity)
        val movieResult = repository.getMovie()

        assertEquals(movieResult.title, movieEntity.title)

        val newMovie = MovieResponse(title = "Hehehe")
        repository.setBase(newMovie)
        val newMovieResult = repository.getMovie()

        assertEquals(newMovie.title, newMovieResult.title)
    }


    private fun setRepository(baseResponse: BaseResponse) {
        dataSource = DetailDataSource(baseResponse)
        repository = DetailRepository(dataSource)
    }
}