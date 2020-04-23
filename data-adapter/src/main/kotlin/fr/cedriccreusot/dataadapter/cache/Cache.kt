package fr.cedriccreusot.dataadapter.cache

import kotlin.reflect.KClass

interface Cache {
    fun <T: Any> get(cacheName: String): T?
    fun save(cacheName: String, value: Any)

}