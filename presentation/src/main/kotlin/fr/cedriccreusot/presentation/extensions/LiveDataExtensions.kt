package fr.cedriccreusot.presentation.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T : Any> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}