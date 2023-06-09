package com.example.tsdc_vinilos_equipo6.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.databinding.AlbumItemBinding
import com.example.tsdc_vinilos_equipo6.models.Album
import com.example.tsdc_vinilos_equipo6.models.Performer
import com.example.tsdc_vinilos_equipo6.ui.AlbumsFragmentDirections

class AlbumsAdapter(isCollector: Boolean) : RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>(){

    var _isCollectors = isCollector
    var albums :List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumsViewHolder.LAYOUT,
            parent,
            false)
        return AlbumsViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.albums = albums[position]
        }
        displayPerformers(albums[position].performers, holder.viewDataBinding.AlbumPerformer)
        holder.bind(albums[position])
        holder.viewDataBinding.root.setOnClickListener {
            val action = AlbumsFragmentDirections.actionAlbumFragmentToAlbumDetailFragment(albums[position].albumId, _isCollectors)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }


    class AlbumsViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
        fun bind(album: Album) {
            Glide.with(itemView)
                .load(album.cover.toUri().buildUpon().scheme("https").build())
                .apply(RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.photo))
                .into(viewDataBinding.AlbumCover)
        }
    }

    private fun listPerformersToText(performerslist:List<Performer>?): String? {
        var texto: String? = null
        if (performerslist.isNullOrEmpty())
            texto = "No hay perfomers disponibles"
        else {
            for (p in performerslist) {
                texto = if (texto == null)
                    p.name
                else
                    texto + ", " + p.name
            }
        }
        return texto
    }

    private fun displayPerformers(performerslist:List<Performer>?, textView: TextView) {
        try {
            textView.text = listPerformersToText(performerslist)
        }catch (_: Exception){

        }
    }

}