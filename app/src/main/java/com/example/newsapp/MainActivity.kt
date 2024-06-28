package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.ArticleAdapter
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.models.Article
import com.example.newsapp.models.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val apiKey = "6c0bdcf212634c8c8f5dbb4cd3933e8c"
    private lateinit var adapter: ArticleAdapter
    private val arrArticles = ArrayList<Article>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapter = ArticleAdapter(arrArticles, this)
        binding.rvArticles.adapter = adapter
        binding.rvArticles.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        fetchNews()
    }

    private fun fetchNews() {
        val call = RetrofitClient.newsApiService.getArticles("us", "business", apiKey)
        call.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    newsResponse?.articles?.let {
                        arrArticles.clear()
                        arrArticles.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Log.e("MainActivity", "Response not successful: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.e("MainActivity", "Failed to fetch news: ${t.message}")
            }
        })
    }
}
