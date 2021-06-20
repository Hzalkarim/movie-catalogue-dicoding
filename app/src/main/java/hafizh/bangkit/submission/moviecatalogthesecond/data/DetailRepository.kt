package hafizh.bangkit.submission.moviecatalogthesecond.data

import hafizh.bangkit.submission.moviecatalogthesecond.data.source.DetailDataSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.BaseResponse

class DetailRepository(private val detailDataSource: DetailDataSource) {

    companion object {
        @Volatile
        private var instance: DetailRepository? = null

        fun getInstance(dataSource: DetailDataSource) : DetailRepository =
            instance ?: synchronized( this) {
                instance ?: DetailRepository(dataSource).apply { instance = this }
            }
    }

    fun getTvShow() = detailDataSource.getTvShow()
    fun getMovie() = detailDataSource.getMovie()
    fun getBase() = detailDataSource.getBase()
    fun setBase(baseResponse: BaseResponse) = detailDataSource.setBase(baseResponse)
}