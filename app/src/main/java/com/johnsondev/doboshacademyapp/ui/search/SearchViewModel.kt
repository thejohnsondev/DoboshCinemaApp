package com.johnsondev.doboshacademyapp.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.base.BaseViewModel
import com.johnsondev.doboshacademyapp.utilities.states.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class SearchViewModel(
    private val moviesRepository: MoviesRepository
) : BaseViewModel() {

    init {
        Log.d("TAG", "Create new SearchViewModel")
    }

    @ExperimentalCoroutinesApi
    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    private val _searchState = MutableLiveData<LoadingState>()
//
//    @FlowPreview
//    @ExperimentalCoroutinesApi
//    private val _searchResult = queryChannel
//        .asFlow()
//        .debounce(500)
//        .onEach {
//            _searchState.value = Loading
//        }
//        .mapLatest {
//            if (it.isEmpty()) {
//                EmptyQuery
//            } else {
//                try {
//                    val result = moviesRepository.search(it)
//                    when {
//                        result.actors.isEmpty() && result.movies.isEmpty() -> {
//                            EmptySearchResult
//                        }
//                        else -> {
//                            ValidSearchResult(result)
//                        }
//                    }
//                } catch (e: Throwable) {
//                    if (e is CancellationException) {
//                        throw e
//                    } else {
//                        ErrorSearchResult(e)
//                    }
//                }
//            }
//        }
//        .onEach {
//            _searchState.value = Ready
//        }
//        .catch { emit(TerminalSearchError) }
//        .asLiveData(viewModelScope.coroutineContext)
//
//
//    @ExperimentalCoroutinesApi
//    @FlowPreview
//    val searchResultMap: LiveData<SearchResult>
//        get() = _searchResult

    val searchState: LiveData<LoadingState> get() = _searchState

    class Factory @Inject constructor(
        private val moviesRepository: MoviesRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchViewModel(moviesRepository) as T
        }

    }

}