package java.androidatc.thetodoapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import java.androidatc.thetodoapp.data.TodoDatabase
import java.androidatc.thetodoapp.data.TodoRepository
import java.androidatc.thetodoapp.data.TodoRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            context = app,
            TodoDatabase::class.java,
            "todo_db"
        ).fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db.todoDao())

    }


}