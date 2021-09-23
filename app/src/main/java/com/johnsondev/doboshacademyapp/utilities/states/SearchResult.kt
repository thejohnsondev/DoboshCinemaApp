package com.johnsondev.doboshacademyapp.utilities.states

import com.johnsondev.doboshacademyapp.data.models.SearchResultLists

sealed class SearchResult
class ValidSearchResult(val resultLists: SearchResultLists) : SearchResult()
object EmptySearchResult : SearchResult()
object EmptyQuery : SearchResult()
class ErrorSearchResult(val e: Throwable) : SearchResult()
object TerminalSearchError : SearchResult()