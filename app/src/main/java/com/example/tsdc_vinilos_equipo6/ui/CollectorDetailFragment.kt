package com.example.tsdc_vinilos_equipo6.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.databinding.FragmentCollectorDetailBinding
import com.example.tsdc_vinilos_equipo6.models.Collector
import com.example.tsdc_vinilos_equipo6.ui.adapters.CollectorAdapter
import com.example.tsdc_vinilos_equipo6.ui.adapters.CollectorAlbumsAdapter
import com.example.tsdc_vinilos_equipo6.viewmodels.CollectorViewModel


class CollectorDetailFragment : Fragment() {
    private var _binding: FragmentCollectorDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var albumsRecyclerView: RecyclerView

    private lateinit var collector: Collector
    private lateinit var viewModel: CollectorViewModel
    private var viewModelAdapter: CollectorAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = CollectorAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.collectorDetailRv

        albumsRecyclerView = binding.albumsRv
        albumsRecyclerView.layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args: CollectorDetailFragmentArgs by navArgs()
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_collectors)
        viewModel = ViewModelProvider(this, CollectorViewModel.Factory(activity.application, args.collectorId))[CollectorViewModel::class.java]
        viewModel.collector.observe(viewLifecycleOwner) {
            it.apply {
                collector = it
                viewModelAdapter!!.collector = it
                if (it.albums != null){
                    albumsRecyclerView.adapter = CollectorAlbumsAdapter(it.albums!!)
                }
            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}