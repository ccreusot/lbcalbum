package fr.cedriccreusot.dataadapter.cache

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.util.*

@RunWith(AndroidJUnit4::class)
class FileSystemCacheTest {

    lateinit var instrumentationContext: Context

    @Before
    fun setUp() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun testSaveFileSystemCache() {
        val randomString = UUID.randomUUID().toString()
        val fileSystemCache = FileSystemCache(instrumentationContext)

        fileSystemCache.save("cacheTest", randomString)

        val absolutePath = instrumentationContext.cacheDir.absolutePath
        assertThat(File("$absolutePath/cacheTest").exists()).isTrue()
    }

    @Test
    fun testGetObjectFromFileSystemCache() {
        val fileSystemCache = FileSystemCache(instrumentationContext)

        val result = fileSystemCache.get<String>("cacheTest")

        assertThat(result).isNull()
    }

    @Test
    fun testGetObjectFromFileSystemCacheAfterSaving() {
        val randomString = UUID.randomUUID().toString()
        val fileSystemCache = FileSystemCache(instrumentationContext)

        fileSystemCache.save("cacheTest", randomString)
        val result = fileSystemCache.get<String>("cacheTest")

        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(randomString)
    }

}