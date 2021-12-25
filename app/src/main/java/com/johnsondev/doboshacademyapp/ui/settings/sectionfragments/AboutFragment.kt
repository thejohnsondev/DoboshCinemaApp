package com.johnsondev.doboshacademyapp.ui.settings.sectionfragments

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.databinding.FragmentAboutBinding
import com.johnsondev.doboshacademyapp.ui.settings.SettingsViewModel
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class AboutFragment : BaseFragment(R.layout.fragment_about), KodeinAware {

    override val kodein by kodein()

    private val factory: SettingsViewModelFactory by instance()
    private val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[SettingsViewModel::class.java]
    }

    private val binding by viewBinding(FragmentAboutBinding::bind)

    override fun initFields() {

    }

    override fun loadData() {

    }

    override fun bindViews() {

    }

    override fun initListenersAndObservers() {
        with(binding) {

            backToMainViewGroup.setOnClickListener {
                findNavController().popBackStack()
            }

        }
    }

}