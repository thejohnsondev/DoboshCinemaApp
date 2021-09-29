package com.johnsondev.doboshacademyapp.ui.settings

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.databinding.FragmentSettingsBinding
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment


class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun initFields() {}

    override fun loadData() {}

    override fun bindViews() {}

    override fun initListenersAndObservers() {
        with(binding){

            interfaceGroup.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_settingsInterfaceFragment)
            }

            contentGroup.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_settingsContentFragment)
            }

            generalGroup.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_settingsGeneralFragment)
            }

            aboutGroup.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
            }

        }
    }
}