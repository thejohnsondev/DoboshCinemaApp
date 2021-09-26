package com.johnsondev.doboshacademyapp.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.base.BaseViewModel
import com.johnsondev.doboshacademyapp.utilities.states.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class SearchViewModel(application: Application) : BaseViewModel(application) {

    @ExperimentalCoroutinesApi
    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    private val _searchState = MutableLiveData<LoadingState>()

    @FlowPreview
    @ExperimentalCoroutinesApi
    private val _searchResult = queryChannel
        .asFlow()
        .debounce(500)
        .onEach {
            _searchState.value = Loading
        }
        .mapLatest {
            if (it.isEmpty()) {
                EmptyQuery
            } else {
                try {
                    val result = MoviesRepository.search(it)
                    when {
                        result.actors.isEmpty() && result.movies.isEmpty() -> {
                            EmptySearchResult
                        }
                        else -> {
                            ValidSearchResult(result)
                        }
                    }
                } catch (e: Throwable) {
                    if (e is CancellationException) {
                        throw e
                    } else {
                        ErrorSearchResult(e)
                    }
                }
            }
        }
        .onEach {
            _searchState.value = Ready
        }
        .catch { emit(TerminalSearchError) }
        .asLiveData(viewModelScope.coroutineContext)


    @ExperimentalCoroutinesApi
    @FlowPreview
    val searchResultMap: LiveData<SearchResult>
        get() = _searchResult

    val searchState: LiveData<LoadingState> get() = _searchState

}