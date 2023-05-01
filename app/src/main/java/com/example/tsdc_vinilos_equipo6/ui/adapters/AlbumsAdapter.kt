package com.example.tsdc_vinilos_equipo6.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import androidx.recyclerview.widget.RecyclerView
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.databinding.AlbumItemBinding
import com.example.tsdc_vinilos_equipo6.models.Album
import com.example.tsdc_vinilos_equipo6.models.Performer

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>(){

    var albums :List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]
        }
        displayPerformers(albums[position].performers, holder.viewDataBinding.AlbumDescription)
        loadUrl(albums[position].cover, holder.viewDataBinding.AlbumCover)
        holder.viewDataBinding.root.setOnClickListener {
            /*
            val action = AlbumFragmentDirections.actionAlbumFragmentToCommentFragment(albums[position].albumId)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
            */
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }


    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }

    fun loadUrl(url: String, imgView : ImageView) {
        try {
            Glide.with(imgView).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.photo).into(imgView)
        }catch (_: Exception){

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

}