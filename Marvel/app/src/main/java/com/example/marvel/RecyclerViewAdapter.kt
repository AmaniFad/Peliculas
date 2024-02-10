package com.example.marvel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.data.Character



class RecyclerViewAdapter(private val context: Context, var charList : ArrayList<Character>) : RecyclerView.Adapter<RecycleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecycleViewHolder(layoutInflater.inflate(R.layout.item_layout, parent, false))
    }
    override fun getItemCount(): Int {
        return charList.size
    }
    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val item = charList[position]
        holder.charactersTitle.text = item.name
        val imageUrl = "${item.thumbnail.replace("http", "http")}/portrait_xlarge.${item.thumbnailExt}"
        Glide.with(context).load(imageUrl).into(holder.charactersPoster)
        holder.charactersLayout.setOnClickListener{
            val intent = Intent(context, MoveData::class.java)
            intent.putExtra("script", item.description)
            context.startActivity(intent)
        }
    }
    fun setData(charactracterList: ArrayList<Character>){
        this.charList.addAll(charactracterList)
        notifyDataSetChanged()
    }
}