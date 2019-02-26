package com.jason.rxsearchexample

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

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
//            getResultsBasedOnQuery(searchView)
        }
//        return super.onCreateOptionsMenu(menu)
        return true
    }

    private fun getResultsBasedOnQuery(searchView: SearchView) {
        val publishSubject = PublishSubject.create<String>()
        val compositeDisposable = CompositeDisposable()

        compositeDisposable.add(publishSubject
            .filter { return@filter it.isNotEmpty() }
            .debounce(600, TimeUnit.MILLISECONDS)
            .switchMap { string -> return@switchMap Observable.just(string) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show() }
        )

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                publishSubject.onNext(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                publishSubject.onNext(newText)
                return true
            }
        })
    }
}
