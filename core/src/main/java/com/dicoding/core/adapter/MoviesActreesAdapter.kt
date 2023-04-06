package com.dicoding.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.core.databinding.ItemMoviesActreesBinding
import com.dicoding.core.domain.model.Actrees
import com.dicoding.core.utils.getImageUrl
import com.dicoding.core.utils.setImageFromUrl

class MoviesActreesAdapter(private val casts: List<Actrees>) :
    RecyclerView.Adapter<MoviesActreesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMoviesActreesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cast = casts[position]
        val context = holder.itemView.context

        holder.bind(context, cast)
    }

    override fun getItemCount(): Int = casts.size

    inner class ViewHolder(private val binding: ItemMoviesActreesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, actrees: Actrees) {
            binding.apply {
                tvActreesName.text = actrees.name
                tvActreesRole.text = actrees.character
                imgActrees.setImageFromUrl(context, getImageUrl(actrees.profilePath))
            }
        }
    }
}