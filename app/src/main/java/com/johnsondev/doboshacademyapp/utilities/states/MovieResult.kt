package com.johnsondev.doboshacademyapp.utilities.states

import com.johnsondev.doboshacademyapp.data.models.SearchResultLists

sealed class SearchResult
class ValidResult(val resultLists: SearchResultLists) : SearchResult()
object EmptyMoviesResult : SearchResult()
object EmptyActorsResult : SearchResult()
object EmptyResult: SearchResult()
object EmptyQuery : SearchResult()
class ErrorResult(val e: Throwable) : SearchResult()
object TerminalError : SearchResult()