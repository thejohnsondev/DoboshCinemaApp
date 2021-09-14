package com.johnsondev.doboshacademyapp.utilities.states

import com.johnsondev.doboshacademyapp.data.models.Movie

sealed class MoviesResult
class ValidResult(val resultList: List<Movie>) : MoviesResult()
object EmptyResult : MoviesResult()
object EmptyQuery : MoviesResult()
class ErrorResult(val e: Throwable) : MoviesResult()
object TerminalError : MoviesResult()