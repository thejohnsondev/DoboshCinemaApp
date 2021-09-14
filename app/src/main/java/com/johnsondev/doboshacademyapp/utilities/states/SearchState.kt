package com.johnsondev.doboshacademyapp.utilities.states

sealed class SearchState
object Loading : SearchState()
object Ready : SearchState()