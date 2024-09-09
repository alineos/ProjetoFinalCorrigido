package br.edu.ifsp.dmo.whatsapp.ui.model

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo.whatsapp.data.model.Usuario
import br.edu.ifsp.dmo.whatsapp.data.repository.UsuarioRepository

class LoginViewModel : ViewModel() {
    private val repository = UsuarioRepository()

private val _logged = MutableLiveData<Boolean>()
var logged: LiveData<Boolean> = _logged


fun login(email: String, senha: String) {
    val usuario = Usuario("", email, senha) //cria um novo usuário
    repository.login(usuario, { result -> _logged.value = result }) //recebe a validação dos parametros de entrada e atribui a variavel _logged
}
fun verificaLogin(){
    repository.verificaSessao { result->_logged.value=result } //recebe a validação de usuario logado e atribui a variavel _logged
}

}