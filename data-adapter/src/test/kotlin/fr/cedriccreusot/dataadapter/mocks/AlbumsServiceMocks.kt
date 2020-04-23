package fr.cedriccreusot.dataadapter.mocks

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import fr.cedriccreusot.dataadapter.retrofit.AlbumsService
import fr.cedriccreusot.dataadapter.retrofit.model.Track
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.mock.Calls
import retrofit2.mock.MockRetrofit
import java.io.FileReader
import java.io.IOException

object AlbumsServiceMocks {
    private val retrofit = Retrofit.Builder()
        .baseUrl(AlbumsService.BASE_URL)
        .build()

    private val behaviorDelegate = MockRetrofit.Builder(retrofit)
        .build()
        .create(AlbumsService::class.java)

    fun createServiceThatFail() =
        object : AlbumsService {
            override fun getTracks(): Call<List<Track>> {
                return behaviorDelegate.returning(Calls.failure<IOException>(IOException()))
                    .getTracks()
            }
        }

    fun createServiceWithResponse() =
        object : AlbumsService {
            override fun getTracks(): Call<List<Track>> {
                return behaviorDelegate.returningResponse(
                    createListTracks()
                ).getTracks()
            }
        }

    private fun createListTracks(): List<Track> {
        val listType = object : TypeToken<List<Track>>() {}.type
        return GsonBuilder().create().fromJson(
            FileReader(javaClass.classLoader?.getResource("technical-test.json")?.file!!),
            listType
        )
    }
}

