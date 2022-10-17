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
                if(checkBoxSignInLembrar.isChecked){ //Verifica se o checkbox está marcado e retorna um bool
                    //Armazena preferência
                    salvarPrefUserEmail(sharedPref, userEmail)
                    //como está em um fragmento é necessario o contexto, mas em uma activity só fileDir() da conta
            }
                findNavController().navigate(R.id.dashboardFragment)
            }else{
                escreverLogNoArquivo("Tentativa de Acesso: Negada\n")
            }
        }
    }



    private fun verificarArquivoLog () {
        val filesdir = requireContext().filesDir //path até armazenamento interno
        val arqLog = File(filesdir, "logsys.log") //poderia ser qualquer extensão
        if (!arqLog.exists()) arqLog.createNewFile()
        if (arqLog.canWrite())
            Log.d("Log File Read", "OK")
        else
            Log.d("Log File Read", "Permissão negada")

        /*var file = File(context?.filesDir, "teste.txt")
        if (!file.exists()) file.createNewFile()
        //criado uma instancia de uma representação daquele arquivo
        //nesse caso a extensão não importa (.txt), poderia ser qualquer uma



        //var cache = File(context?.cacheDir, "teste.abcd") // acesso diretorio cache

        //file.exists()                 Esse arquivo existe?
        //Não muito usados:
        //file.createNewFile()          Cria novo arquivo
        //file.canRead()                Posso ler esse arquivo
        //file.canWrite                 Posso escrever?
        //file.canExecute               Posso Executar?

        //OBS: quando tenta-se escrever em um arquivo que não existe, o SO cria para gente*/
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
        A diferença é que no fragment é necessário passar o contexto antes

        requireContext().openFileOutput("logsys.log", Context.MODE_PRIVATE)

        Linha acima passa qual arquivo vai ser escrito, se não existe nenhum "logsys.log" para
        escrever por cima, cria um arquivo.
        Isso torna "if (!arqLog.exists()) arqLog.createNewFile()" irrelevante.

        OBS: Se o app tem permissão de escrita, ele te a permissão de criar, ler e escrever no arqv.

        val info = "Victor é muito bonito"
        requireContext().openFileOutput("logsys.log", Context.MODE_PRIVATE).use{
            it.write(info.toByteArray())
        }

        .use{} abre um bloco de contexto, como referencia contextual o FileOutputStream gerado no
        retorno da função.

        o tipo de em que o .write() permite escrever é "byteArray", porque byteArray e não String?
        porque FileOutput é do tipo Stream, que faz a escrita sob demanda

        OBS: Função "openFileOutput" especifica parasalvar no armazenamento interno, no diretorio
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
        //getPreferences(), está contido no contexto da activity
        //como está contido no contexto activity, precisa se referir a activity para chamar o metodo
        //por ser um sistema da chave-valor, é necessário passar dois valores diferentes
        //chave = "user_email", valor = "valor do edittext do email"*/
    }

    private fun verificarPrefUserEmail(sharedPref: SharedPreferences) {
        //Lê o armazenamento de preferência para valorar o campo de email
        val userEmail = sharedPref.getString("user_email", null)
        Log.d("Email", userEmail ?: "null")
        if (!userEmail.isNullOrBlank()) editTextSignInEmail.setText(userEmail)

        /* Sobre Preferências
        //preferencias são tipos de armazenamentos muito rapidos de serem lidos, não importa muito
        // em qual thread ele será lido.
        //recebe dois valores, a chave que vai ser lida e o valor default caso não for encontrado nenhum valor*/
    }

    }

