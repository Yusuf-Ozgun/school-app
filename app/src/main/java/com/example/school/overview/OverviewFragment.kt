package com.example.school.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.school.databinding.FragmentOverviewBinding
import com.example.school.overview.OverviewFragmentDirections.actionShowDetail
import com.iammert.library.ui.multisearchviewlib.MultiSearchView

class OverviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = OverviewModelFactory(application)

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(OverviewViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayItemDetails(it)
        })

        viewModel.navigateToSelectedItem.observe(this, Observer {
            if ( null != it ) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(actionShowDetail(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayItemDetailsComplete()
            }
        })

        binding.multiSearchView.setSearchViewListener(object : MultiSearchView.MultiSearchViewListener{
            override fun onItemSelected(index: Int, s: CharSequence) {

                viewModel.updateFilter(s.toString())

            }

            override fun onSearchComplete(index: Int, s: CharSequence) {

                viewModel.updateFilter(s.toString())

            }

            override fun onSearchItemRemoved(index: Int) {
            }

            override fun onTextChanged(index: Int, s: CharSequence) {

                viewModel.updateFilter(s.toString())

            }

        })


        return binding.root
    }

}
