package com.example.tsdc_vinilos_equipo6.ui.adapters

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.databinding.AlbumDetailTracksItemBinding
import com.example.tsdc_vinilos_equipo6.models.Track

class AlbumTracksAdapter(track_list: List<Track>) :
    RecyclerView.Adapter<AlbumTracksAdapter.AlbumTracksViewHolder>() {

    private var trackList: List<Track> = track_list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumTracksViewHolder {
        val withDataBinding: AlbumDetailTracksItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumTracksViewHolder.LAYOUT,
            parent,
            false
        )
        return AlbumTracksViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumTracksViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.TrackName.text = trackList[position].name
            it.TrackTime.text = trackList[position].duration
        }
        holder.viewDataBinding.root.setOnClickListener {
            // val action = AlbumFragmentDirections.actionAlbumFragmentToCommentFragment(albums[position].albumId)
            // Navigate using that action
            //holder.viewDataBinding.root.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return trackList.size
    }


    class AlbumTracksViewHolder(val viewDataBinding: AlbumDetailTracksItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_detail_tracks_item
        }


    }

}