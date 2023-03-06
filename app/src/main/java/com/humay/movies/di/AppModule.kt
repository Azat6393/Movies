package com.humay.movies.di

import android.content.Context
import androidx.room.Room
import com.humay.movies.data.local.MoviesDao
import com.humay.movies.data.local.MoviesDatabase
import com.humay.movies.data.remote.MoviesApi
import com.humay.movies.data.repository.MoviesRepositoryImpl
import com.humay.movies.domain.repository.MoviesRepository
import com.humay.movies.domain.use_case.GetPopularMoviesUseCase
import com.humay.movies.domain.use_case.SearchMoviesUseCase
import com.humay.movies.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MoviesDatabase::class.java,
        "movies_database",
    ).build()

    @Provides
    @Singleton
    fun provideMoviesApi(): MoviesApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesDao(database: MoviesDatabase) = database.moviesDao()

    @Provides
    @Singleton
    fun provideMoviesRepo(
        api: MoviesApi,
        dao: MoviesDao
    ): MoviesRepository {
        return MoviesRepositoryImpl(dao, api)
    }

    @Provides
    @Singleton
    fun provideGetPopularMoviesUseCase(
        repo: MoviesRepository
    ) = GetPopularMoviesUseCase(repo)

    @Provides
    @Singleton
    fun provideSearchMoviesUseCase(
        repo: MoviesRepository
    ) = SearchMoviesUseCase(repo)
}