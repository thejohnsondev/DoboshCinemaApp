package com.johnsondev.doboshacademyapp.ui.settings.sectionfragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.databinding.FragmentSettingsContentBinding
import com.johnsondev.doboshacademyapp.databinding.FragmentSettingsInterfaceBinding
import com.johnsondev.doboshacademyapp.ui.settings.SettingsViewModel
import com.johnsondev.doboshacademyapp.utilities.appComponent
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import javax.inject.Inject

class SettingsContentFragment : BaseFragment(R.layout.fragment_settings_content) {

    @Inject
    lateinit var factory: SettingsViewModel.Factory
    private val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[SettingsViewModel::class.java]
    }
    private val binding by viewBinding(FragmentSettingsContentBinding::bind)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent().inject(this)
    }

    override fun initFields() {

    }

    override fun loadData() {

    }

    override fun bindViews() {

    }

    override fun initListenersAndObservers() {
        with(binding){

            backToMainViewGroup.setOnClickListener {
                findNavController().popBackStack()
            }

        }
    }

}