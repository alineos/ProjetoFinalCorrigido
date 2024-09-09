package br.edu.ifsp.dmo.whatsapp.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo.whatsapp.data.model.Mensagem
import br.edu.ifsp.dmo.whatsapp.data.repository.MensagemRepository

class ChatViewModel(emailDestinatario: String) : ViewModel() {

    private val repository = MensagemRepository()

    private val _inserted = MutableLiveData<Boolean>()
    val inserted: LiveData<Boolean> = _inserted

    private val _mensagens = MutableLiveData<List<Mensagem>>()
    val mensagens: LiveData<List<Mensagem>> = _mensagens


    init {
        findMensagem(emailDestinatario) // busca as mensagens quando a ChatViewModel é instanciada
    }

    fun insert(emailDestinatario: String, textoMensagem: String) { //instancia uma mensagem e envia para o banco de dados
        val mensagem = Mensagem(emailDestinatario, "", textoMensagem) //cria uma nova mensagem
        repository.insert(mensagem, { result -> _inserted.value = result }) //envia a mensagem para o banco de dados e seta o retorno do envio na _inserted
    }

    fun findMensagem(emailDestinatario: String) { //chama o metodo de mensagem do repository
        repository.findMensagem(emailDestinatario, { list -> _mensagens.value = list }) // atribui o retorno a lista _mensagens

    }


}