package br.edu.ifsp.dmo.whatsapp.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo.whatsapp.data.model.Usuario
import br.edu.ifsp.dmo.whatsapp.data.repository.UsuarioRepository

class MainViewModel:ViewModel() {

    private val repository = UsuarioRepository()

    init {
        loadUsers()
    }

    fun logout(){
        repository.logoff()
    }

    private val _users = MutableLiveData<List<Usuario>>()
    val users: LiveData<List<Usuario>> = _users

    private fun loadUsers() {

        repository.findAll { list ->
            _users.value = list }

    }

}