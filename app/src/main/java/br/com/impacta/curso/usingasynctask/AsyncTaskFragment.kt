package br.com.impacta.curso.usingasynctask

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.impacta.curso.usingasynctask.databinding.FragmentAsyncTaskBinding

class AsyncTaskFragment : Fragment() {

    private var _binding: FragmentAsyncTaskBinding? = null
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAsyncTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            val tarefa = TarefaOutraThread()
            tarefa.execute()
        }
    }

    inner class TarefaOutraThread: AsyncTask<Void, Int, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            binding.textView.text = "Antes da execução"
            binding.progressBar.max = 5
            binding.progressBar.progress = 0
        }

        override fun doInBackground(vararg p0: Void?): String {
            for (i in 1..5) {
                publishProgress(i)
                Thread.sleep(500)
            }
            return "Terminou a execução"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            binding.textView.text = result
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            binding.progressBar.progress = values[0]!!
        }
    }
}