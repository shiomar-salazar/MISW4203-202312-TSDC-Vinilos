package com.example.tsdc_vinilos_equipo6.ui

import android.os.Bundle
import android.util.Log
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
import com.example.tsdc_vinilos_equipo6.databinding.CommentFragmentBinding
import com.example.tsdc_vinilos_equipo6.ui.adapters.CommentsAdapter
import com.example.tsdc_vinilos_equipo6.viewmodels.CommentViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CommentFragment : Fragment() {
    private var _binding: CommentFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CommentViewModel
    private var viewModelAdapter: CommentsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CommentFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = CommentsAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.commentsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_comments)
        val args: CommentFragmentArgs by navArgs()
        Log.d("Args", args.albumId.toString())
        viewModel = ViewModelProvider(this, CommentViewModel.Factory(activity.application, args.albumId))[CommentViewModel::class.java]
        viewModel.comments.observe(viewLifecycleOwner) {
            it.apply {
                viewModelAdapter!!.comments = this
                if (this.isEmpty()) {
                    binding.txtNoComments.visibility = View.VISIBLE
                } else {
                    binding.txtNoComments.visibility = View.GONE
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