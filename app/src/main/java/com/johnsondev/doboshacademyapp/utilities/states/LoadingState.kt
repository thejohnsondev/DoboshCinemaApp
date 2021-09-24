package com.johnsondev.doboshacademyapp.utilities.states

sealed class LoadingState
object Loading : LoadingState()
object Ready : LoadingState()