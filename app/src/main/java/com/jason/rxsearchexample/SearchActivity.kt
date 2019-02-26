package com.jason.rxsearchexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.SearchManager
import android.content.Intent
import android.widget.Toast


class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
            Toast.makeText(this,query,Toast.LENGTH_LONG).show()
        }
    }
}