package com.example.marvel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.data.CharacterUseCase
import com.example.marvel.data.MarvelListState
import com.example.marvel.data.Response
import com.example.marvel.data.SearchCharactersUseCase
import kotlin.text.Typography.dagger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class CharacterViewModel @Inject constructor(
    private val characterUseCase : CharacterUseCase,
    private val searchCharactersUseCase :SearchCharactersUseCase
): ViewModel(){
    private val marvelValue = MutableStateFlow(MarvelListState())
    val _marvelValue : StateFlow<MarvelListState> = marvelValue

    fun getAllCharactersData(offset:Int) = viewModelScope.launch(Dispatchers.IO) {
        characterUseCase(offset = offset).collect{
            when(it){
                is Response.Success ->{
                    marvelValue.value = MarvelListState(characterList = it.data?: emptyList())
                }
                is Response.Loading ->{
                   marvelValue.value = MarvelListState(isLoading = true)
                }
                is Response.Error ->{
                    marvelValue.value = MarvelListState(error = it.message?: "An Unexpected Error")
                }
            }
        }
    }
    fun getSearchedCharacter(search:String) = viewModelScope.launch(Dispatchers.IO){
        searchCharactersUseCase.invoke(search  = search).collect{
            when(it){
                is Response.Success ->{
                    marvelValue.value = MarvelListState(characterList = it.data?: emptyList())
                }
                is Response.Loading ->{
                    marvelValue.value = MarvelListState(isLoading = true)
                }
                is Response.Error ->{
                    marvelValue.value = MarvelListState(error = it.message?: "An Unexpected Error")
                }
            }
        }
    }
}