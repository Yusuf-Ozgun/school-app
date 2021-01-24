package com.example.school.overview

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.school.domain.Item
import com.example.school.repository.SchoolRepository
import com.example.school.room.SchoolRoomDatabase.Companion.getDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class ApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel(application: Application) : ViewModel() {


    private val context = application.applicationContext

    private val database = getDatabase(application)
    private val _schoolRepo = SchoolRepository(database)

    private val _status = MutableLiveData<ApiStatus>()

    val status: LiveData<ApiStatus>
        get() = _status

    private val _navigateToSelectedItem = MutableLiveData<Item>()

    val navigateToSelectedItem: LiveData<Item>
        get() = _navigateToSelectedItem

    val record = _schoolRepo.record

    private var viewModelJob = Job()

    init {

        val list = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _status.value = ApiStatus.LOADING
            }
            if (isOnline(context.applicationContext)) {
                _schoolRepo.refreshSchool(list.random())
                withContext(Dispatchers.Main) {
                    _status.value = ApiStatus.DONE
                }
            }
        }

    }

    fun updateFilter(query: String) {
        if (query != "") {

            viewModelScope.launch(Dispatchers.IO) {

                if (isOnline(context.applicationContext)) {
                    withContext(Dispatchers.Main) {
                        _status.value = ApiStatus.LOADING
                    }
                    _schoolRepo.refreshSchool(query)
                    withContext(Dispatchers.Main) {
                        _status.value = ApiStatus.DONE
                    }
                }
            }

        }

    }

    private fun isOnline(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    fun displayItemDetails(item: Item) {
        _navigateToSelectedItem.value = item
    }

    fun displayItemDetailsComplete() {
        _navigateToSelectedItem.value = null
    }



}
