package com.example.tsdc_vinilos_equipo6.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.viewmodels.AlbumsViewModel
import android.widget.Toast
import androidx.fragment.app.Fragment

class AlbumCreateFragment : Fragment(){
    private lateinit var viewModel: AlbumsViewModel
    private lateinit var viewComment: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewComment = view


    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    companion object {
        fun action_albumFragment_to_createAlbum(): Any {
            TODO()
        }
    }
}