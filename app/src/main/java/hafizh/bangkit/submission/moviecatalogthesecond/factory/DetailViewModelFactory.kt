package hafizh.bangkit.submission.moviecatalogthesecond.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hafizh.bangkit.submission.moviecatalogthesecond.data.DetailRepository
import hafizh.bangkit.submission.moviecatalogthesecond.ui.detail.DetailViewModel

class DetailViewModelFactory(private val repository: DetailRepository) : ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance : DetailViewModelFactory? = null

        fun getInstance(repository: DetailRepository) : DetailViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: DetailViewModelFactory(repository).apply {
                    instance = this
                }
            }
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) ->
                DetailViewModel(repository) as T
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}