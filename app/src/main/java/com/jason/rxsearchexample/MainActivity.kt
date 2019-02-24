package com.jason.rxsearchexample

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.menu_search, menu)
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchView = menu.findItem(R.id.action_search).actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.maxWidth = Int.MAX_VALUE
            searchView.queryHint = "Input..."
            getResultsBasedOnQuery(searchView)
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun getResultsBasedOnQuery(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("Jason", "MainActivity.onQueryTextSubmit (line 30): $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("Jason", "MainActivity.onQueryTextSubmit (line 35): $newText")
                return true
            }
        })
    }
}
