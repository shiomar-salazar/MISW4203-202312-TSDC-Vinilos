package com.example.tsdc_vinilos_equipo6.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tsdc_vinilos_equipo6.databinding.FragmentCreateCommentBinding
import com.example.tsdc_vinilos_equipo6.models.Comment
import com.example.tsdc_vinilos_equipo6.viewmodels.CommentCreateViewModel

class CommentCreateFragment : Fragment() {
    private var _binding: FragmentCreateCommentBinding? = null
    private lateinit var viewModel: CommentCreateViewModel
    private val binding get() = _binding!!
    private val args: CommentCreateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelButton.setOnClickListener {
            navigateToAlbumDetail()
        }

        binding.saveButton.setOnClickListener {
            val description = binding.commentTextField.editText?.text.toString()
            val ratingBar = binding.commentRating.rating.toString()
            val argsArray: ArrayList<String> = arrayListOf(description, ratingBar)
            if (this.formIsValid(argsArray)) {
                val comment = Comment(
                    description = binding.commentTextField.editText?.text.toString().trim(),
                    rating = binding.commentRating.rating.toString(),
                    albumId = args.albumId,
                    collectorID = 1
                )
                if (viewModel.addNewComment(args.albumId, comment)) {
                    showMessage("El comentario se registró correctamente.")
                    navigateToAlbumDetail()
                } else {
                    showMessage("Ocurrió un error en el registro del comentario.")
                }

            } else {
                showMessage("La descripción del comentario y la valoración deben ser diligenciados.")
            }
        }
    }

    private fun formIsValid(array: ArrayList<String>): Boolean {
        for (elem in array) {
            if (TextUtils.isEmpty(elem) || elem == "0.0" || elem.length < 2) {
                return false
            }
        }
        return true
    }

    private fun showMessage(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_LONG).show()
    }

    private fun navigateToAlbumDetail() {
        val action =
            CommentCreateFragmentDirections.actionCommentCreateFragmentToAlbumDetailFragment(
                args.albumId,
                true
            )
        binding.root.findNavController().navigate(action)
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(
            this,
            CommentCreateViewModel.Factory(activity.application)
        )[CommentCreateViewModel::class.java]
        viewModel.comment.observe(viewLifecycleOwner) {
            it.apply {

            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }
}