package br.com.impacta.curso.usingasynctask

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.impacta.curso.usingasynctask.databinding.FragmentApiBinding
import java.net.HttpURLConnection
import java.net.URL

class ApiFragment : Fragment() {

    private var _binding: FragmentApiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button2.setOnClickListener {
            val cepDigitado = binding.editTextTextPersonName.text.toString()
            val chamadaApi = ApiPostmon()
            chamadaApi.execute(cepDigitado)
        }

    }

    inner class ApiPostmon: AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String {
            val cepDigitado = p0[0]!!
            val url = URL("https://api.postmon.com.br/v1/cep/${cepDigitado}")

            (url.openConnection() as? HttpURLConnection)?.let {conexao ->
                conexao.requestMethod = "GET"
                conexao.connect()

                val inputStream = if (conexao.responseCode == HttpURLConnection.HTTP_OK) {
                    conexao.inputStream
                } else {
                    conexao.errorStream
                }

                val resposta = inputStream.reader().readText()
                return resposta
            }
            return "ERRO DE CASTING"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            binding.textView2.text = result!!
        }
    }


}