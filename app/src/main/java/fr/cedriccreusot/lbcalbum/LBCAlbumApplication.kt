package fr.cedriccreusot.lbcalbum

import android.app.Application
import androidx.navigation.NavController
import fr.cedriccreusot.dataadapter.cache.Cache
import fr.cedriccreusot.dataadapter.cache.FileSystemCache
import fr.cedriccreusot.dataadapter.repositories.AlbumsRepositoryAdapter
import fr.cedriccreusot.dataadapter.retrofit.AlbumsService
import fr.cedriccreusot.domain.repositories.AlbumsRepository
import fr.cedriccreusot.domain.usecases.FetchAlbumsUseCase
import fr.cedriccreusot.domain.usecases.FetchTracksForAlbumUseCase
import fr.cedriccreusot.presentation.detail.viewmodels.TrackListViewModel
import fr.cedriccreusot.presentation.list.viewmodels.AlbumListViewModel
import fr.cedriccreusot.presentation.routes.Router
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

class LBCAlbumApplication : Application() {

    private val appModule = module {
        // Network and Cache
        single { AlbumsService.createService() }
        single<Cache> { FileSystemCache(get()) }
        single<AlbumsRepository> { AlbumsRepositoryAdapter(get(), get()) }

        single { (navController: NavController) -> Router.create(navController) }

        // UseCase
        single { FetchAlbumsUseCase.create(get()) }
        single { FetchTracksForAlbumUseCase.create(get()) }

        // ViewModel
        viewModel { (navController: NavController) -> AlbumListViewModel(get(), get { parametersOf(navController) }) }
        viewModel { (albumId : String) -> TrackListViewModel(get(), albumId) }
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