package hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote

import hafizh.bangkit.submission.moviecatalogthesecond.BuildConfig
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.PopularMovieResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.PopularTvShowResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.TvShowResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieTvShowApiService {

    @GET("movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API
    ) : Observable<MovieResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API
    ) : Single<PopularMovieResponse>

    @GET("tv/{tv_id}")
    fun getTvShow(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API
    ) : Observable<TvShowResponse>

    @GET("tv/popular")
    fun getPopularTvShows(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API
    ) : Single<PopularTvShowResponse>
}