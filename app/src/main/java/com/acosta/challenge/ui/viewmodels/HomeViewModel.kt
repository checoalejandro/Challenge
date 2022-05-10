package com.acosta.challenge.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acosta.challenge.models.IPCHistoryResponse
import com.acosta.challenge.models.TopTenResponse
import com.acosta.challenge.repositories.ServerRepository
import com.acosta.challenge.repositories.ServerRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

/**
 * Home ViewModel.
 *
 * @author @seacosta
 */
@KoinViewModel
class HomeViewModel(
    private val repository: ServerRepositoryImpl
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.indicesFlow
                .catch { exception ->
                    Log.e(
                        "HomeViewModel",
                        "Exception occur: ${exception.message}",
                        exception
                    )
                }
                .stateIn(viewModelScope)
                .collect {
                    _topTenLiveData.postValue(it)
                }
        }
    }

    // Avoid exposing MutableLiveData is a better practice.
    private val _ipcHistoryLiveData = MutableLiveData<IPCHistoryResponse>()

    private val _topTenLiveData = MutableLiveData<TopTenResponse>()
    val topTenLiveData: LiveData<TopTenResponse> by this::_topTenLiveData

    /**
     * Notifies whenever IPC history is retrieved from server.
     *
     * (No need of a hot flow due to the requirement of simply showing history once.)
     */
    val ipcLiveData: LiveData<IPCHistoryResponse> by this::_ipcHistoryLiveData

    private val _error = MutableLiveData<String>()

    /**
     * Notifies whenever an error occurs.
     * This could be an object instead of a string.
     */
    val errorLiveData: LiveData<String> by this::_error

    /**
     * Fetches IPC list from server.
     * If failure, will trigger error message.
     */
    fun getIPCHistory() {
        viewModelScope.launch(Dispatchers.Default) {
            repository.getIPC()?.let { history ->
                _ipcHistoryLiveData.postValue(history)
            } ?: run {
                notifyError("Unable to fetch")
                // Here you can clear UI if required.
            }
        }
    }

    /**
     * Notifies UI to show an error message.
     */
    private fun notifyError(error: String) {
        _error.postValue(error)
    }

}