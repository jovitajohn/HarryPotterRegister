package com.jovita.harrypotter.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jovita.harrypotter.model.MovieCharacter
import com.jovita.harrypotter.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val _characters = MutableStateFlow<List<MovieCharacter>>(emptyList())
    val characters: StateFlow<List<MovieCharacter>> = _characters


    private val _filteredCharacters = MutableStateFlow<List<MovieCharacter>>(emptyList())
    val filteredCharacters: StateFlow<List<MovieCharacter>> = _filteredCharacters

    private val context = getApplication<Application>()
    private val harryPotterApi = ApiService.create(context)

    init {
        fetchCharacters()
    }

    //Getting data from API
    private fun fetchCharacters() {
        viewModelScope.launch {
            try {
                if (harryPotterApi.getCharacters().toString() != null) {
                    val result = harryPotterApi.getCharacters()
                    Log.e("Response", result.body().toString())
                    _characters.value = result.body()!!
                    _filteredCharacters.value = result.body()!!
                }
            } catch (e: Exception) {
                Log.e("API Error", "Error fetching Movie characters", e)
            }

        }
    }

    //Filter option for searchbar
    fun filterCharacters(query: String) {
        val filteredList = if (query.isEmpty()) {
            _characters.value
        } else {
            _characters.value.filter {
                it.name?.contains(query, ignoreCase = true) == true || it.actor?.contains(
                    query,
                    ignoreCase = true
                ) == true
            }
        }
        _filteredCharacters.value = filteredList
    }

    fun getCharacterName( id: String): String? {
        val currentCharacter = characters.value.find { it.id == id }
        return currentCharacter?.name
    }

}