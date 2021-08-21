package com.johnsondev.doboshacademyapp.utilities.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutId(), container, false)
        postponeEnterTransition()


        initViews(view)
        loadData()
        bindViews(view)
        initListenersAndObservers(view)

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    abstract fun initViews(view: View)
    abstract fun layoutId(): Int
    abstract fun loadData()
    abstract fun bindViews(view: View)
    abstract fun initListenersAndObservers(view: View)
}