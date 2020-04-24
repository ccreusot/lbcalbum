package fr.cedriccreusot.dataadapter.cache

import android.content.Context
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.reflect.Type

class FileSystemCache(private val context: Context) : Cache {

    private val gson =  GsonBuilder().setLenient().create()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(cacheName: String, typeOf: Type): T? {

        val absolutePath = context.cacheDir.absolutePath
        val file = File("$absolutePath/$cacheName")

        if (!file.exists()) {
            return null
        }
        return FileReader(file).use {
            gson.fromJson(it, typeOf)
        }
    }

    override fun save(cacheName: String, value: Any) {
        val absolutePath = context.cacheDir.absolutePath
        val file = File("$absolutePath/$cacheName")
        if (!file.exists()) {
            val jsonValue = gson.toJson(value)

            FileWriter(file).use {
                it.write(jsonValue)
            }
        }
    }
}