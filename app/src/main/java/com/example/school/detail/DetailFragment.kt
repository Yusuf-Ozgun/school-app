package com.example.school.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.school.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val item = DetailFragmentArgs.fromBundle(arguments!!).selectedItem
        val viewModelFactory = DetailViewModelFactory(item, application)
        binding.viewModel = ViewModelProviders.of(
                this, viewModelFactory).get(DetailViewModel::class.java)

        return binding.root
    }
}