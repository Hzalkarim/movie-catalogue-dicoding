package hafizh.bangkit.submission.moviecatalogthesecond.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hafizh.bangkit.submission.moviecatalogthesecond.data.FavRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.FavDataSource
import hafizh.bangkit.submission.moviecatalogthesecond.ui.fav.FavViewModel

class FavViewModelFactory(private val favRepository: FavRepository) : ViewModelProvider.Factory {

    companion object {

        @Volatile
        private var instance : FavViewModelFactory? = null

        fun getInstance(favRepository: FavRepository) : FavViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavViewModelFactory(favRepository).apply {
                    instance = this
                }
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(FavViewModel::class.java) -> {
                return FavViewModel(favRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}