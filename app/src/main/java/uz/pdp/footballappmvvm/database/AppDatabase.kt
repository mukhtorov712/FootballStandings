package uz.pdp.footballappmvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.pdp.footballappmvvm.database.dao.MatchDao
import uz.pdp.footballappmvvm.database.dao.ScorerDao
import uz.pdp.footballappmvvm.database.dao.TeamDao
import uz.pdp.footballappmvvm.database.entity.MatchEntity
import uz.pdp.footballappmvvm.database.entity.ScorerEntity
import uz.pdp.footballappmvvm.database.entity.TeamEntity

@Database(entities = [TeamEntity::class, MatchEntity::class, ScorerEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun teamDao(): TeamDao
    abstract fun matchDao(): MatchDao
    abstract fun scorerDao(): ScorerDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
                    .allowMainThreadQueries()
                    .build()

            }
            return instance!!
        }
    }
}