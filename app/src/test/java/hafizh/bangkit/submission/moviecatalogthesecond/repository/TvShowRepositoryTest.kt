package hafizh.bangkit.submission.moviecatalogthesecond.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowRepositoryTest : TestCase() {

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

    @Before
    public override fun setUp() {
        super.setUp()
        dataSource = RemoteDataSource(client)
        repository = MovieTvShowRepository(dataSource)
    }

    @Test
    fun testRepositoryGetTvShow() {
        val tvShow = TvShowResponse("Mad About You")

        Mockito.`when`(client.getTvShow(500)).thenReturn(Observable.just(tvShow))
        repository.getTvShowObservable(listOf(500))
        Mockito.verify(client).getTvShow(500)
    }
}