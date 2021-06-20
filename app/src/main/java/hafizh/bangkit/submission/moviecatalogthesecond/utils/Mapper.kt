package hafizh.bangkit.submission.moviecatalogthesecond.utils

import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.entity.MovieTvShowEntity
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.BaseResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.TvShowResponse

class Mapper {
    companion object {
        fun baseResponseToEntity(baseResponse: BaseResponse) : MovieTvShowEntity? {
            return when (baseResponse) {
                is MovieResponse -> {
                    MovieTvShowEntity(
                        "movie-${baseResponse.id}",
                        baseResponse.id,
                        baseResponse.title,
                        baseResponse.overview,
                        baseResponse.posterPath,
                        baseResponse.tagline,
                        baseResponse.voteAverage,
                        baseResponse.popularity,
                        true
                    )
                }
                is TvShowResponse -> {
                    MovieTvShowEntity(
                        "tvshow-${baseResponse.id}",
                        baseResponse.id,
                        baseResponse.title,
                        baseResponse.overview,
                        baseResponse.posterPath,
                        baseResponse.tagline,
                        baseResponse.voteAverage,
                        baseResponse.popularity,
                        false
                    )
                }
                else -> null
            }
        }

        fun entityToBaseResponse(entity: MovieTvShowEntity) : BaseResponse {
            return if (entity.isMovie) {
                MovieResponse(
                    entity.title!!,
                    entity.overview!!,
                    entity.posterPath,
                    entity.popularity!!,
                    "",
                    0,
                    entity.tagline,
                    entity.voteAverage,
                    entity.id
                )
            } else {
                TvShowResponse(
                    entity.title!!,
                    entity.overview!!,
                    entity.posterPath,
                    entity.popularity!!,
                    "",
                    entity.tagline,
                    entity.voteAverage,
                    0,
                    0,
                    entity.id
                )
            }
        }
    }
}