package hafizh.bangkit.submission.moviecatalogthesecond.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hafizh.bangkit.submission.moviecatalogthesecond.data.DetailRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.BaseResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.TvShowResponse

class DetailViewModel(private val detailRepository: DetailRepository) : ViewModel() {

    private val _data = MutableLiveData<BaseResponse>()
    val data : LiveData<BaseResponse> get() = _data

    var isFavorite = false

    fun setLiveData() {
        when (detailRepository.getBase()) {
            is MovieResponse -> _data.value = detailRepository.getMovie()
            is TvShowResponse -> _data.value = detailRepository.getTvShow()
        }
    }

    fun setLiveData(baseResponse: BaseResponse) {
        detailRepository.setBase(baseResponse)
        setLiveData()
    }
}