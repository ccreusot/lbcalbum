package fr.cedriccreusot.dataadapter.cache

import android.content.Context
import java.io.*
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

class FileSystemCache(private val context: Context) : Cache {

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(cacheName: String): T? {

        val absolutePath = context.cacheDir.absolutePath
        val file = File("$absolutePath/$cacheName")

        if (!file.exists()) {
            return null
        }
        return ObjectInputStream(FileInputStream(file)).use {
            val readObject = it.readObject()
            readObject as T
        }
    }

    override fun save(cacheName: String, value: Any) {
        val absolutePath = context.cacheDir.absolutePath
        val file = File("$absolutePath/$cacheName")
        if (!file.exists()) {
            ObjectOutputStream(FileOutputStream(file)).use {
                it.writeObject(value)
            }
        }
    }
}