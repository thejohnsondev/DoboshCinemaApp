package com.johnsondev.doboshacademyapp.ui.settings

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.databinding.FragmentSettingsBinding
import com.johnsondev.doboshacademyapp.ui.settings.sectionfragments.SettingsViewModelFactory
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class SettingsFragment : BaseFragment(R.layout.fragment_settings), KodeinAware {

    override val kodein by kodein()

    private val factory: SettingsViewModelFactory by instance()
    private val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[SettingsViewModel::class.java]
    }

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun initFields() {}

    override fun loadData() {}

    override fun bindViews() {}

    override fun initListenersAndObservers() {
        with(binding) {

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