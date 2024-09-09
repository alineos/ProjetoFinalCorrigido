package br.edu.ifsp.dmo.whatsapp.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.whatsapp.ui.model.ChatViewModel

class ChatViewModelFactory(private val someParameter: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(someParameter) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
