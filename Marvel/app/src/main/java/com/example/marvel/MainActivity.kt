package com.example.marvel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel.data.Character
import com.example.marvel.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    //val service = RetrofitServiceFacroty.provideMarvelApi()
    private lateinit var  adapter: RecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var  layoutManager: GridLayoutManager
    private val viewModel :CharacterViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var  searchTerm :String
    var pagineValue = 0
    var flag = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerViewFilms
        layoutManager = GridLayoutManager(this, 2)

        recycleViewCharacter()

        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(layoutManager.findFirstCompletelyVisibleItemPosition() == layoutManager.itemCount-1){

                    pagineValue +=20
                    viewModel.getAllCharactersData(pagineValue)
                    callApi()
                }
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val search = menu.findItem(R.id.menuSearch)
        val searchView = search?.actionView as androidx.appcompat.widget.SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return true
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchTerm = query
        }
        if(searchTerm.isNotEmpty()){
            search()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchTerm = newText
        }
        if(searchTerm.isNotEmpty()){
            search()
        }
        return true
    }
    private fun search(){
        viewModel.getSearchedCharacter(searchTerm)
        CoroutineScope(Dispatchers.Main).launch {
            viewModel._marvelValue.collect{
                when{
                    it.isLoading ->{

                    }
                    it.error.isNotBlank() ->{
                        Toast.makeText(this@MainActivity, it.error,Toast.LENGTH_LONG).show()
                    }
                    it.characterList.isNotEmpty() ->{
                        flag = 0
                        adapter.setData(it.characterList as ArrayList<Character>)
                    }
                }
            }
        }
    }
    override fun onStart(){
        super.onStart()
        viewModel.getAllCharactersData(pagineValue)
        callApi()
    }

    private fun callApi(){
        CoroutineScope(Dispatchers.Main).launch {
            repeat(flag){
                viewModel._marvelValue.collect{
                    when{
                        it.isLoading ->{

                        }
                        it.error.isNotBlank() ->{
                            flag = 0
                            Toast.makeText(this@MainActivity, it.error,Toast.LENGTH_LONG).show()
                        }
                        it.characterList.isNotEmpty() ->{
                            flag = 0
                            adapter.setData(it.characterList as ArrayList<Character>)
                        }
                    }
                    delay(1000)
                }
            }
        }
    }

    private fun recycleViewCharacter(){
        adapter = RecyclerViewAdapter(this, ArrayList())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

    }

    /*private fun showRecyclerView(){
        val decoration = DividerItemDecoration(this, GridLayoutManager(this, 2).orientation )
        recyclerView = findViewById(R.id.recyclerViewFilms)

        recyclerView.layoutManager =  GridLayoutManager(this, 2)
        print("hola1")
        recyclerView.addItemDecoration(decoration)


        CoroutineScope(Dispatchers.IO).launch {
            val charList = showCharacters()
            runOnUiThread {
                recyclerView.adapter = RecyclerViewAdapter(charList)
            }
        }
    }

    private suspend fun showCharacters() : List<Result> {
        val charList = mutableListOf<Result>()

        try {

            var API_KEY = "35fe75710471f389d484caacf9b4826b"
            var PRIVET_KEY = "b50c65f43b9aa2affe99d83c4fb0011406c58d3a"
            val characters = service.getAllCharacters()
            val offset = 0
            for (char in service.getAllCharacters()) {
                val c = Result(
                    name = char.name,
                    description = char.description,
                    id = char.id,
                    resourceURI = "http://gateway.marvel.com/v1/public/characters/${char.id}",
                    urlImage = "${char.thumbnail?.path}.${char.thumbnail?.extension}",
                    thumbnail = char.thumbnail
                )
                charList.add(c)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        print(charList)
        return charList
    }*/

}