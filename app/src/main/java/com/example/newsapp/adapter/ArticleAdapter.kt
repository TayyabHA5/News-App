package com.example.newsapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.DetailActivity
import com.example.newsapp.R
import com.example.newsapp.models.Article

class ArticleAdapter(val arrArticles: List<Article>,val context: Context) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.tvTile)
        val description = itemView.findViewById<TextView>(R.id.tvDescription)
        val image = itemView.findViewById<ImageView>(R.id.im)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.articles_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrArticles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentValue = arrArticles[position]
        holder.title.text = currentValue.title
        holder.description.text = currentValue.description

        Glide.with(context).load(currentValue.urlToImage).into(holder.image)
        holder.itemView.setOnClickListener {
            Toast.makeText(context, currentValue.title, Toast.LENGTH_SHORT).show()
            val intent = Intent(context,DetailActivity::class.java)
            intent.putExtra("URL",currentValue.url)
            context.startActivity(intent)
        }
    }
}