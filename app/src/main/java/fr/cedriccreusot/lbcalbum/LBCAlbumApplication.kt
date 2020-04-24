package fr.cedriccreusot.lbcalbum

import android.app.Application
import fr.cedriccreusot.dataadapter.cache.Cache
import fr.cedriccreusot.dataadapter.cache.FileSystemCache
import fr.cedriccreusot.dataadapter.repositories.AlbumsRepositoryAdapter
import fr.cedriccreusot.dataadapter.retrofit.AlbumsService
import fr.cedriccreusot.domain.repositories.AlbumsRepository
import fr.cedriccreusot.domain.usecases.FetchAlbumsUseCase
import fr.cedriccreusot.presentation.list.viewmodels.AlbumListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class LBCAlbumApplication : Application() {

    private val appModule = module {
        // Network and Cache
        single { AlbumsService.createService() }
        single<Cache> { FileSystemCache(get()) }
        single<AlbumsRepository> { AlbumsRepositoryAdapter(get(), get()) }

        // UseCase
        single { FetchAlbumsUseCase.create(get()) }

        // ViewModel
        viewModel { AlbumListViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@LBCAlbumApplication)
            modules(appModule)
        }
    }
}