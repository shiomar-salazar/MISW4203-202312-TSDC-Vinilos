package com.example.tsdc_vinilos_equipo6.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.databinding.AlbumCreateBinding
import com.example.tsdc_vinilos_equipo6.models.Album
import com.example.tsdc_vinilos_equipo6.viewmodels.AlbumCreateViewModel

class AlbumCreateFragment : Fragment() {
    private var _binding: AlbumCreateBinding? = null
    private lateinit var viewModel: AlbumCreateViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlbumCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        binding.albumCancelButton.setOnClickListener {
            navigateToAlbums()
        }

        binding.albumCreateButton.setOnClickListener {
            val album = Album(
                name = binding.nameAlbumTextField.editText?.text.toString().trim(),
                description = binding.descripcionAlbumTextField.text.toString().trim(),
                cover = binding.imageAlbumTextField.text.toString().trim(),
                genre = binding.generoAlbumSpinner.selectedItem.toString().trim(),
                releaseDate = binding.dateAlbumDatepicker.text.toString().trim(),
                recordLabel = binding.disqueraAlbumSpinner.selectedItem.toString().trim(),
                comments = null,
                performers = null,
                tracks = null
            )
            if (viewModel.addNewAlbum(album)) {
                showMessage("El álbum se registró correctamente.")
                navigateToAlbums()
            } else {
                showMessage("Ocurrió un error en el registro del álbum.")
            }
        }
    }

    private fun navigateToAlbums() {
        val action = AlbumCreateFragmentDirections.actionAlbumCreateFragmentToAlbumFragment(true)
        binding.root.findNavController().navigate(action)
    }

    private fun showMessage(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_LONG).show()
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
        activity.actionBar?.title = getString(R.string.title_albums)
        viewModel = ViewModelProvider(
            this,
            AlbumCreateViewModel.Factory(activity.application)
        )[AlbumCreateViewModel::class.java]
        viewModel.album.observe(viewLifecycleOwner) {
            it.apply {

            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }
}