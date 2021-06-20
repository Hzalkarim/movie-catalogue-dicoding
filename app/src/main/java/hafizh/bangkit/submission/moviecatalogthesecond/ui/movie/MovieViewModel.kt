package hafizh.bangkit.submission.moviecatalogthesecond.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hafizh.bangkit.submission.moviecatalogthesecond.data.MovieTvShowRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
import hafizh.bangkit.submission.moviecatalogthesecond.utils.EspressoIdlingResource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieViewModel (private val repo: MovieTvShowRepository) : ViewModel() {

    private val _movie = MutableLiveData<ArrayList<MovieResponse>>()
    val movie : LiveData<ArrayList<MovieResponse>> get() = _movie

    private val list = ArrayList<MovieResponse>()

    //testing purpose only
    private var useIdlingResource: Boolean = true
    var setUseIdlingResource
        get() = useIdlingResource
        set(value) { useIdlingResource = value }

    fun getMovie(listId: List<Int>) {
        try {
            repo.getMovieObservable(listId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MovieResponse> {
                    override fun onSubscribe(d: Disposable?) {
                        if (useIdlingResource)
                            EspressoIdlingResource.increment()
                    }

                    override fun onNext(movie: MovieResponse?) {
                        if (movie != null)
                            list.add(movie)
                    }

                    override fun onError(e: Throwable?) {}

                    override fun onComplete() {
                        _movie.value = list
                        if (useIdlingResource && !EspressoIdlingResource.idlingResource.isIdleNow)
                            EspressoIdlingResource.decrement()
                    }

                })
        } catch (e: Exception) {
            Log.e("Gagal Observe Movie", e.message.toString())
        }
    }

}
