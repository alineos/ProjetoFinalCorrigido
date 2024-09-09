package br.edu.ifsp.dmo.whatsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityLoginBinding
import br.edu.ifsp.dmo.whatsapp.ui.main.MainActivity
import br.edu.ifsp.dmo.whatsapp.ui.model.LoginViewModel

private lateinit var binding: ActivityLoginBinding
private lateinit var viewModel: LoginViewModel

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.verificaLogin() //chama o método verificaLogin que valida se existe um usuário logado

        setupObservers()
        setupListeners()
    }


    private fun setupObservers() {
        viewModel.logged.observe(this, Observer { //observa o retorno da verificação de usuário logado
            if (it){
                abrirTelaPrincipal() //abre a tela principal caso tenha um usuario logado
            } else {
                Toast.makeText(this,  "Erro ao logar Usuário.", Toast.LENGTH_SHORT).show() //exibe mensagem de erro caso não exista um usuário logado ou ocorra erro ao realizar o login
            }
        })
    }


    private fun abrirTelaPrincipal() {
        val mIntent = Intent(this, MainActivity::class.java)
        startActivity(mIntent) //inicia a tela principal
    }


    private fun setupListeners() {
        binding.buttonLogar.setOnClickListener { //escuta o evento clique no botão logar
            validarLogin() //chama o método de validação de login
        }
        binding.buttonCadastrar.setOnClickListener { //escuta o evento clique no botão Cadastrar
            abrirCadastro() //chama o método para abrir a tela de cadastro
        }
    }


    private fun validarLogin() {

        //recebe os parametros da activity
        val strEmail = binding.editLoginEmail.text.toString()
        val strSenha = binding.editLoginSenha.text.toString()

        //valida se o campo senha está vazio
        if (!strEmail.isEmpty()) {
            if (!strSenha.isEmpty()) { //se a variavel não for vazia, chama o método logarUsuario passando os parametros de entrada
                logarUsuario(strEmail,strSenha)

                //se algum parametro for vazio, apresenta mensagem de erro
            } else {
                Toast.makeText(this, "Preencha o Campo Senha ", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Preencha o Campo E-mail ", Toast.LENGTH_SHORT).show()
        }
    }


    private fun logarUsuario(strEmail: String, strSenha: String) {
        viewModel.login(strEmail,strSenha) //envia os parametros de entrada para a viewModel
    }


    private fun abrirCadastro() {
        val mIntent = Intent(this, CadastroActivity::class.java) //inicia a tela de cadastro
        startActivity(mIntent)
    }
}