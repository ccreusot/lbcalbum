package fr.cedriccreusot.dataadapter.cache

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.google.gson.reflect.TypeToken
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.util.*

@RunWith(AndroidJUnit4::class)
class FileSystemCacheTest {

    private lateinit var instrumentationContext: Context

    private lateinit var cache: Cache

    @Before
    fun setUp() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        instrumentationContext.cacheDir.deleteRecursively()

        cache = FileSystemCache(instrumentationContext)
    }

    @Test
    fun testSaveFileSystemCache() {
        val randomObject = ObjectForTest()

        cache.save("cacheTest", randomObject)

        val absolutePath = instrumentationContext.cacheDir.absolutePath
        assertThat(File("$absolutePath/cacheTest").exists()).isTrue()
    }

    @Test
    fun testGetObjectFromFileSystemCache() {
        val type = object: TypeToken<ObjectForTest>() {}.type
        val result = cache.get<ObjectForTest>("cacheTest", type)

        assertThat(result).isNull()
    }

    @Test
    fun testGetObjectFromFileSystemCacheAfterSaving() {
        val randomObject = ObjectForTest()

        cache.save("cacheTest", randomObject)
        val type = object: TypeToken<ObjectForTest>() {}.type
        val result = cache.get<ObjectForTest>("cacheTest", type)

        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(randomObject)
    }

    data class ObjectForTest(val value: String = UUID.randomUUID().toString())
}