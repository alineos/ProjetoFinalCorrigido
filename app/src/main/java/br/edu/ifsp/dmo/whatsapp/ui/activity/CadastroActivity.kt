package br.edu.ifsp.dmo.whatsapp.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityCadastroBinding
import br.edu.ifsp.dmo.whatsapp.ui.model.CadastroViewModel

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private lateinit var viewModel: CadastroViewModel


    override fun onCreate(savedInstanceState: Bundle?) { //primeiro método executado ao iniciar a activity
        super.onCreate(savedInstanceState) //construtor da classe pai
        binding = ActivityCadastroBinding.inflate(layoutInflater) //objeto responsável pelo mapeamento dos componentes da tela
        setContentView(binding.root) //define para visualização do dispositivo qual activity deve ser exibida
        viewModel = ViewModelProvider(this).get(CadastroViewModel::class.java) //vincula o viewModel a activity atual

        setupListeners() // chama o método que prepara o listener
        setupObservers() //chama o metodo que prepara os observables
    }


    private fun setupListeners() {
        binding.buttonCadastrar.setOnClickListener { //define o método de click do botão Cadastrar Usuário
            validaCadastro() //chama o método que valida se os campos do cadastro do usuário estão preenchidos corretamente
        }
    }


    private fun setupObservers() {

        viewModel.insertedMessage.observe(this, Observer{ //observa mudanças na variavel insertedMessage
            val strMessage = it.toString() //variavel strMessage recebe o callbackMessage do método de inserir usuário
            if (!strMessage.isEmpty() && !strMessage.isBlank()){ //valida se a mensagem não está vazia ou em branco
                Toast.makeText(this, strMessage,Toast.LENGTH_LONG).show() //exibe a mensagem exceção caso ocorra erro ao cadastrar o usuário
            }
        })

        viewModel.inserted.observe(this, Observer {
            if (it) { //retorno do callback
                Toast.makeText(this, "Usuário salvo com sucesso", Toast.LENGTH_LONG).show() //exibe a mensagem de confirmação de cadastro do usuário
                finish() //fecha a acticty atual no view
            }
        })
    }


    private fun validaCadastro() {

        //atribuição dos parametros de entrada dos edit text da tela
        val strNome = binding.editNome.text.toString()
        val strEmail = binding.editEmail.text.toString()
        val strSenha = binding.editSenha.text.toString()

        //valida se os campos estão preenchidos
        if (!strNome.isEmpty()) {
            if (!strEmail.isEmpty()) {
                if (!strSenha.isEmpty()) {
                    cadastrarUsuario(strNome, strEmail, strSenha)
                } else {
                    Toast.makeText(this, "Preencha o Campo Senha ", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha o Campo E-mail ", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Preencha o Campo Nome ", Toast.LENGTH_SHORT).show()
        }
    }


    private fun cadastrarUsuario(strNome: String, strEmali: String, strSenha: String) {
        viewModel.insert(strNome, strEmali, strSenha) //chama o método da viewModel responsável pelo cadastro de usuário
    }

}