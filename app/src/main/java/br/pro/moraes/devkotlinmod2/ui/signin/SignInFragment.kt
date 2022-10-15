package br.pro.moraes.devkotlinmod2.ui.signin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.pro.moraes.devkotlinmod2.R
import java.io.File

class SignInFragment : Fragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var viewModel: SignInViewModel
    private lateinit var editTextSignInEmail: EditText
    private lateinit var editTextSignInPassword: EditText
    private lateinit var checkBoxSignInLembrar: CheckBox
    private lateinit var buttonSignIn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        editTextSignInEmail = view.findViewById(R.id.editTextSignInEmail)
        checkBoxSignInLembrar = view.findViewById(R.id.checkBoxSignInLembrar)
        buttonSignIn = view.findViewById(R.id.buttonSignIn)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        verificarPrefUserEmail()


        buttonSignIn.setOnClickListener {
            if(checkBoxSignInLembrar.isChecked){ //Verifica se o checkbox está marcado e retorna um bool
                //Armazena preferência
                salvarPrefUserEmail()
                val context = activity?.applicationContext
                //como está em um fragmento é necessario o contexto, mas em uma activity só fileDir() da conta
                var file = File(context?.filesDir, "teste.txt")
                //criado uma instancia de uma representação daquele arquivo
                //nesse caso a extensão não importa (.txt), poderia ser qualquer uma
                //var cache = File(context?.cacheDir, "teste.abcd") // acesso diretorio cache
            }
        }
    }

    private fun salvarPrefUserEmail() {
        //getPreferences(), está contido no contexto da activity
        //como está contido no contexto activity, precisa se referir a activity para chamar o metodo
        activity?.getPreferences(Context.MODE_PRIVATE)?.edit().apply {
            val userEmail = editTextSignInEmail.text.toString()
            this?.putString("user_email", userEmail)
            //por ser um sistema da chave-valor, é necessário passar dois valores diferentes
            //chave = "user_email", valor = "valor do edittext do email"
        }
    }

    private fun verificarPrefUserEmail() {
        //Lê o armazenamento de preferência para valorar o campo de email
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        //preferencias são tipos de armazenamentos muito rapidos de serem lidos, não importa muito
        // em qual thread ele será lido.
        val userEmail = sharedPref?.getString("user_email", null)
        //recebe dois valores, a chave que vai ser lida e o valor default caso não for encontrado nenhum valor
        editTextSignInEmail.setText(userEmail)
    }

}