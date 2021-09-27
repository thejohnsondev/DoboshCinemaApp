package com.johnsondev.doboshacademyapp.utilities.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.johnsondev.doboshacademyapp.utilities.showMessage

abstract class BaseFragmentBinding(layoutId: Int) : Fragment(layoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.doOnPreDraw { startPostponedEnterTransition() }
        initFields()
    }

    override fun onResume() {
        super.onResume()
        loadData()
        bindViews()
        initListenersAndObservers()
    }

    abstract fun initFields()
    abstract fun loadData()
    abstract fun bindViews()
    abstract fun initListenersAndObservers()

    protected fun onError(errorMessage: String) = Log.d("ERR", errorMessage)

}