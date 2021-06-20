package hafizh.bangkit.submission.moviecatalogthesecond.ui.tvshow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hafizh.bangkit.submission.moviecatalogthesecond.data.MovieTvShowRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.TvShowResponse
import hafizh.bangkit.submission.moviecatalogthesecond.utils.EspressoIdlingResource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TvShowViewModel (private val repository: MovieTvShowRepository) : ViewModel() {

    private val _tvShow = MutableLiveData<ArrayList<TvShowResponse>>()
    val tvShow : LiveData<ArrayList<TvShowResponse>> get() = _tvShow

    private val list = ArrayList<TvShowResponse>()

    //testing purpose only
    private var useIdlingResource: Boolean = true
    var setUseIdlingResource
        get() = useIdlingResource
        set(value) { useIdlingResource = value }

    fun getTvShows(listId: List<Int>) {

        try {
            repository.getTvShowObservable(listId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<TvShowResponse>{
                    override fun onSubscribe(d: Disposable?) {
                        if (useIdlingResource)
                            EspressoIdlingResource.increment()
                    }

                    override fun onNext(tvShow: TvShowResponse?) {
                        if (tvShow != null)
                            list.add(tvShow)
                    }

                    override fun onError(e: Throwable?) {
                        //_tvShow.postValue(list)
                    }

                    override fun onComplete() {
                        _tvShow.postValue(list)

                        if (useIdlingResource && !EspressoIdlingResource.idlingResource.isIdleNow)
                            EspressoIdlingResource.decrement()
                    }

                })
        } catch (e: Exception) {
            Log.e("Gagal Observe TV", e.message.toString())
        }
    }
}