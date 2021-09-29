package com.johnsondev.doboshacademyapp.ui.settings.sectionfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.databinding.FragmentAboutBinding
import com.johnsondev.doboshacademyapp.databinding.FragmentSettingsInterfaceBinding
import com.johnsondev.doboshacademyapp.ui.settings.SettingsViewModel
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment

class AboutFragment : BaseFragment(R.layout.fragment_about) {

    private val viewModel by viewModels<SettingsViewModel>()
    private val binding by viewBinding(FragmentAboutBinding::bind)

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