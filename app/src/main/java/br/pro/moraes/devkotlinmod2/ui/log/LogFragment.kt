package br.pro.moraes.devkotlinmod2.ui.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.pro.moraes.devkotlinmod2.R

class LogFragment : Fragment() {

    companion object {
        fun newInstance() = LogFragment()
    }

    private lateinit var viewModel: LogViewModel
    private lateinit var textViewLog: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log, container, false)
        textViewLog = view.findViewById(R.id.textViewLog)
        textViewLog.text = lerConteudoLog()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LogViewModel::class.java)
        // TODO: Use the ViewModel
    }


    fun lerConteudoLog() = requireContext().openFileInput("logsys.log")
        .bufferedReader().readText()


    /* Leitura de Dados

     fun lerConteudoLog(){
        val context = requireContext()
        val fis = context.openFileInput("logsys.log")
        val br = fis.bufferedReader()
        br.readText()

        //FileInputStream responsável pela leitura de arquivo
        //OpenFileInput Acessa um arquivo no armazenamento interno do diretorio files para ler

        openFileInput() retorna um objeto FileInputStream
        função do objeto: bufferedReader()
        bufferedReader() retorna um objeto do tipo bufferedReader, responsavel por fazer um buffer

        .readText() lê todo o conteúdo de uma vez
        */


}