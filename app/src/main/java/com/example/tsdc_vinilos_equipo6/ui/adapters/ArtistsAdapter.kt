package com.example.tsdc_vinilos_equipo6.ui.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.databinding.ArtistItemBinding
import com.example.tsdc_vinilos_equipo6.models.Artist
import com.example.tsdc_vinilos_equipo6.ui.ArtistsFragmentDirections

class ArtistsAdapter : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>(){

    var artists :List<Artist> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val withDataBinding: ArtistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistViewHolder.LAYOUT,
            parent,
            false)
        return ArtistViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.artist = artists[position]
        }
        holder.bind(artists[position])
        holder.viewDataBinding.root.setOnClickListener {
            val action = ArtistsFragmentDirections.actionArtistFragmentToArtistDetailFragment(artists[position].artistId)
            // Navigate using that action
            Log.d("ArtistID", artists[position].artistId.toString())
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return artists.size
    }
    class ArtistViewHolder(val viewDataBinding: ArtistItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_item
        }
        fun bind(artist: Artist) {
            Glide.with(itemView)
                .load(artist.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.photo))
                .into(viewDataBinding.artistImage)
        }
    }
}