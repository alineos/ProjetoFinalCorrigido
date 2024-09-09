package br.edu.ifsp.dmo.whatsapp.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo.whatsapp.data.model.Usuario
import br.edu.ifsp.dmo.whatsapp.data.repository.UsuarioRepository

class CadastroViewModel: ViewModel()  {

    private val repository = UsuarioRepository()

    private val _inserted = MutableLiveData<Boolean>()
    val inserted: LiveData<Boolean> = _inserted

    private val _insertedMessage = MutableLiveData<String>()
    val insertedMessage: LiveData<String> = _insertedMessage


    fun insert(nome: String, email: String, senha: String) { //método responsável por invocar a classe de manipulação de dados para a inclusão de um novo usuário no banco de dados
        val usuario = Usuario(nome, email, senha) //instancia um novo usuário com os parametros passados na activity

        // chama o método de inserção da classe de manipulação de dados, passando o resultado para as variáveis _inserted e _insertedMessage
        repository.insert(usuario, { result -> _inserted.value = result }, {resultMessage -> _insertedMessage.value = resultMessage})
    }


}