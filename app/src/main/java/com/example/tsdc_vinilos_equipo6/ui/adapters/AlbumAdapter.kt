package com.example.tsdc_vinilos_equipo6.ui.adapters

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tsdc_vinilos_equipo6.databinding.AlbumDetailItemBinding
import com.example.tsdc_vinilos_equipo6.models.Album
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.models.Performer

class AlbumAdapter: RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    var album : Album? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = album
        }
        displayPerformers(album?.performers, holder.viewDataBinding.albumArtist)
        displayDate(album?.releaseDate, holder.viewDataBinding.albumReleasedDate)
        holder.bind(album)
        holder.viewDataBinding.root.setOnClickListener {
            // val action = AlbumFragmentDirections.actionAlbumFragmentToCommentFragment(albums[position].albumId)
            // Navigate using that action
            //holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return 1
    }


    class AlbumViewHolder(val viewDataBinding: AlbumDetailItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_detail_item
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
                    .into(viewDataBinding.albumImage)
            }
        }
    }

    fun listPerformersToText(performerslist:List<Performer>?): String? {
        var texto: String? = null
        if (performerslist.isNullOrEmpty())
            texto = "No hay perfomers disponibles"
        else {
            for (p in performerslist) {
                if (texto == null)
                    texto = p.name
                else
                    texto = texto + ", " + p.name
            }
        }
        return texto
    }

    fun displayPerformers(performerslist:List<Performer>?, textView: TextView) {
        try {
            textView.text = listPerformersToText(performerslist)
        }catch (_: Exception){

        }
    }

    fun cleanDate(dateStr: String?): String? {
        try {
            return dateStr?.substring(0,10)
        }catch (_: Exception){
            return dateStr
        }
    }

    fun displayDate(dateText: String?, textView: TextView) {
        try {
            textView.text = cleanDate(dateText)
        }catch (_: Exception){

        }
    }


}