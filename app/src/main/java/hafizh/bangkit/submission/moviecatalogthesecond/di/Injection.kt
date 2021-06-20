package hafizh.bangkit.submission.moviecatalogthesecond.di

import android.app.Application
import hafizh.bangkit.submission.moviecatalogthesecond.data.DetailRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.FavRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.MovieTvShowRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.DetailDataSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.RemoteDataSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.BaseResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.ApiConfig

object Injection {
    fun provideMovieTvShowRepository() : MovieTvShowRepository {
        val dataSource = RemoteDataSource.getInstance(ApiConfig.getApiService())

        return MovieTvShowRepository.getInstance(dataSource)
    }

    fun provideDetailRepository(baseResponse: BaseResponse) : DetailRepository {
        val dataSource = DetailDataSource.getInstance(baseResponse)

        return DetailRepository.getInstance(dataSource)
    }

    fun provideFavRepository(application: Application) = FavRepository.getInstance(application)
}