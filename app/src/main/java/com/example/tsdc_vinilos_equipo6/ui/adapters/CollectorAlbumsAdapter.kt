package com.example.tsdc_vinilos_equipo6.ui.adapters

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.databinding.CollectorAlbumItemBinding
import com.example.tsdc_vinilos_equipo6.models.Album
import com.example.tsdc_vinilos_equipo6.models.Collector

class CollectorAlbumsAdapter(collector_albums_list: List<Album>?) :
    RecyclerView.Adapter<CollectorAlbumsAdapter.CollectorAlbumsViewHolder>() {

    private var AlbumList: List<Album>? = collector_albums_list


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorAlbumsViewHolder {
        val withDataBinding: CollectorAlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorAlbumsViewHolder.LAYOUT,
            parent,
            false
        )
        return CollectorAlbumsViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorAlbumsViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.albums = AlbumList!![position]
        }
        holder.bind(AlbumList?.get(position))
    }

    override fun getItemCount(): Int {
        return AlbumList?.size!!
    }


    class CollectorAlbumsViewHolder(val viewDataBinding: CollectorAlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_album_item
        }
        fun bind(album: Album?) {
            if (album != null) {
                Glide.with(itemView)
                    .load(album.cover.toUri().buildUpon().scheme("https").build())
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.photo))
                    .into(viewDataBinding.AlbumCover)
            }
        }
    }

}