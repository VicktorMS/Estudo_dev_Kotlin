package br.pro.moraes.devkotlinmod2.ui.signin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.pro.moraes.devkotlinmod2.R
import com.google.android.material.snackbar.Snackbar
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

        editTextSignInPassword = view.findViewById(R.id.editTextSignInPassword)
        editTextSignInEmail = view.findViewById(R.id.editTextSignInEmail)
        checkBoxSignInLembrar = view.findViewById(R.id.checkBoxSignInLembrar)
        buttonSignIn = view.findViewById(R.id.buttonSignIn)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        verificarPrefUserEmail(sharedPref)
        verificarArquivoLog()

        buttonSignIn.setOnClickListener {
            val userEmail = editTextSignInEmail.text.toString()
            val password = editTextSignInPassword.text.toString()
            escreverLogNoArquivo("Tentativa de Acesso:${userEmail}\n")
            if(userEmail == "victor.moraesrj@gmail.com" && password == "654321") {
                escreverLogNoArquivo("Tentativa de Acesso: ok\n")
                if(checkBoxSignInLembrar.isChecked){ //Verifica se o checkbox est?? marcado e retorna um bool
                    //Armazena prefer??ncia
                    salvarPrefUserEmail(sharedPref, userEmail)
                    //como est?? em um fragmento ?? necessario o contexto, mas em uma activity s?? fileDir() da conta
            }
                findNavController().navigate(R.id.dashboardFragment)
            }else{
                escreverLogNoArquivo("Tentativa de Acesso: Negada\n")
            }
        }
    }



    private fun verificarArquivoLog () {
        val filesdir = requireContext().filesDir //path at?? armazenamento interno
        val arqLog = File(filesdir, "logsys.log") //poderia ser qualquer extens??o
        if (!arqLog.exists()) arqLog.createNewFile()
        if (arqLog.canWrite())
            Log.d("Log File Read", "OK")
        else
            Log.d("Log File Read", "Permiss??o negada")

        /*var file = File(context?.filesDir, "teste.txt")
        if (!file.exists()) file.createNewFile()
        //criado uma instancia de uma representa????o daquele arquivo
        //nesse caso a extens??o n??o importa (.txt), poderia ser qualquer uma



        //var cache = File(context?.cacheDir, "teste.abcd") // acesso diretorio cache

        //file.exists()                 Esse arquivo existe?
        //N??o muito usados:
        //file.createNewFile()          Cria novo arquivo
        //file.canRead()                Posso ler esse arquivo
        //file.canWrite                 Posso escrever?
        //file.canExecute               Posso Executar?

        //OBS: quando tenta-se escrever em um arquivo que n??o existe, o SO cria para gente*/
    }

    private fun escreverLogNoArquivo(msgLog: String){
        requireContext()
            .openFileOutput("logsys.log", Context.MODE_APPEND).use{
            it.write(msgLog.toByteArray())
        }

        /*
        Como escrever em arquivo:

        FileOutputStream para fazer a escrita.
        OpenFileOutput aponta para o armazenamento externo no Diretorio Files.

                                  Na activity exemplo:
        openFileOutput(nome do arquivo: String, modo de abertura do arquivo: Int)
        retorna um FileOutputStream

        val fos = openFileOutput("syslog.log", Context.MODE_PRIVATE)
        fos.write() // Executa a escrita de fato

                                  No Fragment exemplo:
        A diferen??a ?? que no fragment ?? necess??rio passar o contexto antes

        requireContext().openFileOutput("logsys.log", Context.MODE_PRIVATE)

        Linha acima passa qual arquivo vai ser escrito, se n??o existe nenhum "logsys.log" para
        escrever por cima, cria um arquivo.
        Isso torna "if (!arqLog.exists()) arqLog.createNewFile()" irrelevante.

        OBS: Se o app tem permiss??o de escrita, ele te a permiss??o de criar, ler e escrever no arqv.

        val info = "Victor ?? muito bonito"
        requireContext().openFileOutput("logsys.log", Context.MODE_PRIVATE).use{
            it.write(info.toByteArray())
        }

        .use{} abre um bloco de contexto, como referencia contextual o FileOutputStream gerado no
        retorno da fun????o.

        o tipo de em que o .write() permite escrever ?? "byteArray", porque byteArray e n??o String?
        porque FileOutput ?? do tipo Stream, que faz a escrita sob demanda

        OBS: Fun????o "openFileOutput" especifica parasalvar no armazenamento interno, no diretorio
        file

        OBS: MODE_PRIVATE sobreescreve o arquivo, APPEND adiciona ao fim do arquivo

        */
    }

    private fun showSnackbar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }

    private fun salvarPrefUserEmail(sharedPref: SharedPreferences, email: String) {
        val editPref = sharedPref.edit()
        editPref.putString("user_email", email)
        Log.d("Email Preference", "set user_email")
        editPref.apply()

        /*Sobre Preferencias
        //getPreferences(), est?? contido no contexto da activity
        //como est?? contido no contexto activity, precisa se referir a activity para chamar o metodo
        //por ser um sistema da chave-valor, ?? necess??rio passar dois valores diferentes
        //chave = "user_email", valor = "valor do edittext do email"*/
    }

    private fun verificarPrefUserEmail(sharedPref: SharedPreferences) {
        //L?? o armazenamento de prefer??ncia para valorar o campo de email
        val userEmail = sharedPref.getString("user_email", null)
        Log.d("Email", userEmail ?: "null")
        if (!userEmail.isNullOrBlank()) editTextSignInEmail.setText(userEmail)

        /* Sobre Prefer??ncias
        //preferencias s??o tipos de armazenamentos muito rapidos de serem lidos, n??o importa muito
        // em qual thread ele ser?? lido.
        //recebe dois valores, a chave que vai ser lida e o valor default caso n??o for encontrado nenhum valor*/
    }

    }

