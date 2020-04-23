package fr.cedriccreusot.dataadapter.cache

interface Cache<T> {
    fun get(): T?
    fun save(value: T)
}