package com.example.tsdc_vinilos_equipo6.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.databinding.FragmentAlbumDetailBinding
import com.example.tsdc_vinilos_equipo6.models.Album
import com.example.tsdc_vinilos_equipo6.ui.adapters.AlbumAdapter
import com.example.tsdc_vinilos_equipo6.ui.adapters.AlbumCommentsAdapter
import com.example.tsdc_vinilos_equipo6.ui.adapters.AlbumTracksAdapter
import com.example.tsdc_vinilos_equipo6.ui.adapters.CollectorAlbumsAdapter
import com.example.tsdc_vinilos_equipo6.viewmodels.AlbumViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AlbumDetailFragment : Fragment() {
    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var tracksRecyclerView: RecyclerView
    private lateinit var commentsRecyclerView: RecyclerView

    private lateinit var album: Album
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumDetailRv

        tracksRecyclerView = binding.albumTracksRv
        tracksRecyclerView.layoutManager = LinearLayoutManager(context)

        commentsRecyclerView = binding.albumCommentsRv
        commentsRecyclerView.layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        val args: AlbumDetailFragmentArgs by navArgs()
        val createCommentButton : FloatingActionButton = view.findViewById(R.id.fab_add_comment)
        createCommentButton.setOnClickListener {
            val action = AlbumDetailFragmentDirections.actionAlbumDetailFragmentToCreateAlbumFragment(args.albumId)
            view.findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args: AlbumDetailFragmentArgs by navArgs()
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_albums)
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(activity.application, args.albumId))[AlbumViewModel::class.java]
        viewModel.album.observe(viewLifecycleOwner) {
            it.apply {
                album = it
                viewModelAdapter!!.album = it
                if (it.tracks != null){
                    tracksRecyclerView.adapter = AlbumTracksAdapter(it.tracks!!)
                }
                if (it.comments != null){
                    commentsRecyclerView.adapter = AlbumCommentsAdapter(it.comments!!)
                }
                binding.fabAddComment.isVisible = args.isCollector
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