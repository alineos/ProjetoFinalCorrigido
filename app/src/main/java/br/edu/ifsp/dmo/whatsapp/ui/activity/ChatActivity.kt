package br.edu.ifsp.dmo.whatsapp.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.dmo.whatsapp.R
import br.edu.ifsp.dmo.whatsapp.data.model.Usuario
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityChatBinding
import br.edu.ifsp.dmo.whatsapp.ui.adapter.MensagemAdapter
import br.edu.ifsp.dmo.whatsapp.ui.factory.ChatViewModelFactory
import br.edu.ifsp.dmo.whatsapp.ui.model.ChatViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ChatActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityChatBinding
    private lateinit var destinatario:Usuario
    private lateinit var viewModel:ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openBundle() //método que verifica as informações enviadas da tela principal para a Chat

        val factory = ChatViewModelFactory(destinatario.email) //cria uma factory para garantir que a viewModel seja criada  com o e-mail do destinatário
        viewModel = ViewModelProvider(this,factory).get(ChatViewModel::class.java) //instancia viewModel passando a factory

        configuraToolbar()
        setupListeners()
        configuraReciclerView()
    }

    private fun configuraReciclerView() { //metodo que configura o ReciclerView da tela
        val adapter = MensagemAdapter(destinatario.email) //instancia o MensagemAdapter

        binding.contentChat.recyclerMensagens.layoutManager = LinearLayoutManager(this)
        binding.contentChat.recyclerMensagens.adapter = adapter

        viewModel.mensagens.observe(this, Observer{ mensagens ->
            adapter.submitDataset(mensagens) // atribui a lista retornada ao Dataset do adapter
            adapter.notifyDataSetChanged() //notifica o recyclerView alteração na lista de exibição (lista de mensagens)
        })
    }



    private fun setupListeners() {
        binding.contentChat.fabEnviar.setOnClickListener { //define o método executado no click do botão
            enviarMensagem() //chama o método de envio de mensagens
        }
    }


    private fun enviarMensagem() {
        val editText =  binding.contentChat.editMensagem //mapeia o editText da tela Chat
        viewModel.insert(destinatario.email, editText.text.toString()) //passa para o método insert da view model o destinatário e o texto a ser enviado
        editText.setText("") //limpa o editText
    }


    private fun openBundle() { //método que recebe as informações que foram passadas da Main para a Chat
        val extras = intent.extras //verifica as informações recebidas da tela principal
        if (extras!=null){ //verifica o objeto é null
            destinatario = extras.getSerializable("chatContato") as Usuario //seta o objeto usuario passado da Main no objeto destinatario criado localmente
            binding.textNomeUsuarioConversa.setText(destinatario.nome) //seta o nome do usuario destinatario na tela Chat
        }
    }


    private fun configuraToolbar() { //método que configura a toolbar
        val toolbarPrincipal = binding.toolbar

        toolbarPrincipal.setTitle("")
        toolbarPrincipal.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbarPrincipal)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean { //configura o botão de retorno a tela principal (<-)
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}