package com.example.tsdc_vinilos_equipo6.ui.adapters

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.databinding.AlbumDetailCommentsItemBinding
import com.example.tsdc_vinilos_equipo6.models.Comment

class AlbumCommentsAdapter(comments_list: List<Comment>) :
    RecyclerView.Adapter<AlbumCommentsAdapter.AlbumCommentsViewHolder>() {

    private var commentsList: List<Comment> = comments_list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumCommentsViewHolder {
        val withDataBinding: AlbumDetailCommentsItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumCommentsViewHolder.LAYOUT,
            parent,
            false
        )
        return AlbumCommentsViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumCommentsViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.CommentDescription.text = commentsList[position].description
            it.CommentRating.rating = commentsList[position].rating.toFloat()
            it.CommentRating.invalidate()
            it.CommentRating.setIsIndicator(true)
        }
        holder.viewDataBinding.root.setOnClickListener {
            // val action = AlbumFragmentDirections.actionAlbumFragmentToCommentFragment(albums[position].albumId)
            // Navigate using that action
            //holder.viewDataBinding.root.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return commentsList.size
    }


    class AlbumCommentsViewHolder(val viewDataBinding: AlbumDetailCommentsItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_detail_comments_item
        }


    }

}