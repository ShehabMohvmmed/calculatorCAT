package com.projects.cattask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.projects.cattask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel
    private var lastNum = false
    private var lastDot = false
    private val myAdapter = MyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        setUpRecyclerView()

        binding.txtInput.text = viewModel.inputText


        viewModel.logList.observe(this) { result ->
            result.let {
                myAdapter.setData(it)
            }
        }



    }


    fun onDigit(view: View) {
        binding.txtInput.append((view as Button).text)
        viewModel.inputText = binding.txtInput.text.toString()
        lastNum = true
        lastDot = false
    }

    fun onDot(view: View) {
        if (!lastDot){
            binding.txtInput.append((view as Button).text)
        }
        lastNum = false
        lastDot = true
        viewModel.inputText = binding.txtInput.text.toString()
    }

    fun onClear(view: View) {
        binding.txtInput.text = ""
        viewModel.inputText = binding.txtInput.text.toString()
    }

    fun onOperator(view: View) {
        if (lastNum && !(isOperatorUsed(binding.txtInput.text.toString()))){
            binding.txtInput.append(" " + (view as Button).text + " ")
        }
        lastNum = false
        lastDot = false
        viewModel.inputText = binding.txtInput.text.toString()
    }

    private fun isOperatorUsed(value: String): Boolean {
        return if (value.startsWith("-")){
            false
        }else
        (value.contains("-") ||
                value.contains("+") ||
                value.contains("÷") ||
                value.contains("x"))
    }

    fun onEqual(view: View) {
        var prefix = ""
        var value = binding.txtInput.text.toString()

        if (lastNum) {

            if (value.startsWith("-")){
                value = value.substring(1)
                prefix = "-"
            }

            if (value.contains("-")) {
                val splitValue = value.split("-")
                var one = splitValue[0]
                val two = splitValue[1]
                if (prefix.isNotEmpty()){
                    one = prefix + one
                }
                binding.txtInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
              //  logList.value?.add(value + "=" + binding.txtInput.text.toString())
                viewModel.addToLog(value + "=" + binding.txtInput.text.toString())

            } else if (value.contains("+")) {
                val splitValue = value.split("+")
                val one = splitValue[0]
                val two = splitValue[1]
                binding.txtInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                viewModel.addToLog(value + "=" + binding.txtInput.text.toString())

            }else if (value.contains("x")) {
                val splitValue = value.split("x")
                val one = splitValue[0]
                val two = splitValue[1]
                binding.txtInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())

                viewModel.addToLog(value + "=" + binding.txtInput.text.toString())

            } else if (value.contains("÷")) {
                val splitValue = value.split("÷")
                val one = splitValue[0]
                val two = splitValue[1]
                binding.txtInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                viewModel.addToLog(value + "=" + binding.txtInput.text.toString())
            }
        }



        binding.txtSolution.text = value
    }

    private fun removeZeroAfterDot(result:String):String{
        var value = result
        if (result.contains(".0")){
            value = result.substring(0,result.length - 2)
        }
        return value
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }



}