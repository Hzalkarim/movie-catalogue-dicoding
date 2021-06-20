package hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.entity.MovieTvShowEntity

@AutoMigration(from = 2, to = 3)
@Database(entities = [MovieTvShowEntity::class], version = 3)
abstract class FavMovieTvShowRoomDatabase : RoomDatabase() {

    abstract fun favMovieTvShowDao() : FavMovieTvShowDao

    companion object {
        @Volatile
        private var instance : FavMovieTvShowRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context) : FavMovieTvShowRoomDatabase {
            if (instance == null) {
                synchronized(FavMovieTvShowRoomDatabase::class.java) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        FavMovieTvShowRoomDatabase::class.java, "fav_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance as FavMovieTvShowRoomDatabase
        }
    }

}