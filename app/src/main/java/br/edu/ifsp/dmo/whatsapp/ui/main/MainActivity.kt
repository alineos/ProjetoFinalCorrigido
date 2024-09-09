package br.edu.ifsp.dmo.whatsapp.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.dmo.whatsapp.R
import br.edu.ifsp.dmo.whatsapp.data.model.Usuario
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.whatsapp.ui.activity.ChatActivity
import br.edu.ifsp.dmo.whatsapp.ui.adapter.ContatosAdapter
import br.edu.ifsp.dmo.whatsapp.ui.listeners.ContatoClickListener
import br.edu.ifsp.dmo.whatsapp.ui.model.MainViewModel

class MainActivity : AppCompatActivity(),ContatoClickListener {

    private lateinit var binding: ActivityMainBinding //mapeia os componentes
    private lateinit var viewModel: MainViewModel //manipula as operações lógicas da tela
    private lateinit var listUsuarios:List<Usuario> //lista de usuário
    private var adapterContatos = ContatosAdapter(this) //manipula os itens do reciclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        configuraToolbar() //método que seta na tela a toolbar criada como toolbar padrão
        setupRecyclerView() //configura o recyclerView setando o layout e o adapter
        setupObservers() //observa as alterações no banco de dados
    }


    private fun setupObservers() {
        viewModel.users.observe(this, Observer { usuarios -> //observa a lista de usuarios retornada do banco de dados
            adapterContatos.submitDataset(usuarios) // atribui a lista retornada ao Dataset do adapter
            adapterContatos.notifyDataSetChanged() //notifica o recyclerView alteração na lista de exibição (lista de usuario)
            listUsuarios = usuarios //seta para a lista local o valor atualizado da lista
        })
    }


    private fun setupRecyclerView() {

        var recycler = binding.recyclerContatos //indica para a variavel recycler o recyclerView dessa tela

        recycler.layoutManager = LinearLayoutManager(this) //seta o layout do recycler como LinearLayout
        recycler.adapter = adapterContatos //seta que o adapter desse recyclerView é o adapterContatos
    }


    override fun clickContatoItem(position: Int) { //método que indica o comportamento a partir do click no adapter
        val mIntent = Intent(this, ChatActivity::class.java) //define uma Intent como ChatActivity
        mIntent.putExtra("chatContato",listUsuarios.get(position)) //indica o contato destinatário do chat
        startActivity(mIntent) //abre a tela de chat
    }


    private fun configuraToolbar() {
        val toolbarPrincipal = binding.tolbarIncluso.toolbarPrincipal
        toolbarPrincipal.setTitle("Zap Zap")
        toolbarPrincipal.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbarPrincipal) //define essa toolbar como a toolbar desta tela

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean { //mapeia o menu suspenso na toolbar da tela inicial
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu) //define que o menu da tela principal será o menu criado no componente menu.xml
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean { //define os itens selecionaveis no menu
        when (item.itemId) {
            R.id.item_sair -> sair() //componente sair
            R.id.item_pesquisa -> pesquisar() //componente pesquisa
        }
        return super.onOptionsItemSelected(item)
    }


    private fun pesquisar() {
        Toast.makeText(this, "Pesquisar", Toast.LENGTH_LONG).show()
    }


    private fun sair() {
        viewModel.logout() //chama o método logout do viewModel
        finish() //fecha a tela
    }
}