package com.example.tsdc_vinilos_equipo6.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.tsdc_vinilos_equipo6.R

import com.example.tsdc_vinilos_equipo6.placeholder.PlaceholderContent.PlaceholderItem
import com.example.tsdc_vinilos_equipo6.databinding.FragmentArtistListBinding
import com.example.tsdc_vinilos_equipo6.models.Artist
import com.example.tsdc_vinilos_equipo6.ui.CollectorFragmentDirections

class ArtistsAdapter : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>(){

    var artists :List<Artist> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistsAdapter.ArtistViewHolder {
        val withDataBinding: FragmentArtistListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistsAdapter.ArtistViewHolder.LAYOUT,
            parent,
            false)
        return ArtistViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistsAdapter.ArtistViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.artist = artists[position]
        }
        holder.viewDataBinding.root.setOnClickListener {
            //val action = CollectorFragmentDirections.actionCollectorFragmentToAlbumFragment()
            // Navigate using that action
            //holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return artists.size
    }
    class ArtistViewHolder(val viewDataBinding: FragmentArtistListBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.fragment_artist_list
        }
    }

}