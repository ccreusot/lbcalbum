package fr.cedriccreusot.dataadapter.cache

import java.lang.reflect.Type

interface Cache {
    fun <T: Any> get(cacheName: String, typeOf: Type): T?
    fun save(cacheName: String, value: Any)

}