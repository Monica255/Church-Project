package com.example.churchproject.ui.loginsignup

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.churchproject.R
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.RequestLogin
import com.example.churchproject.core.data.source.remote.model.UserData
import com.example.churchproject.databinding.FragmentLoginBinding
import com.example.churchproject.ui.home.HomeActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var email = ""
    private var password = ""
    private var isDataValid=false
    private val viewModel:LoginSignupViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btBlmPunyaAkun.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding.cbShowPass.setOnClickListener {
            if (binding.cbShowPass.isChecked) {
                binding.etMasukPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                binding.etMasukPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }

        val emailStream = RxTextView.textChanges(binding.etMasukEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
//            showEmailExistAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.etMasukPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 6
            }
        passwordStream.subscribe {
//            showPasswordMinimalAlert(it)
        }

        val invalidFieldsStream = Observable.combineLatest(
            emailStream,
            passwordStream
        ) { emailInvalid: Boolean, passwordInvalid: Boolean ->
            !emailInvalid && !passwordInvalid
        }

        invalidFieldsStream.subscribe { isValid ->
            isDataValid = isValid
            if(isValid){
                email=binding.etMasukEmail.text.toString().trim()
                password=binding.etMasukPassword.text.toString().trim()

            }
        }


        viewModel.getToken().observe(requireActivity()) { it ->
            if (it!=""&&it!=null&&isAdded) {
                val intent = Intent(requireActivity(), HomeActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        binding.btMasuk.setOnClickListener {
            if(isDataValid){
                viewModel.login(RequestLogin(email,password)).observe(requireActivity()){
                    when(it){
                        is Resource.Loading->{
                            showLoading(true)
                        }
                        is Resource.Success->{
                            showLoading(false)
                            it.data?.let {
                                if(it.status=="success"){
                                    viewModel.saveToken("$email#${it.user?.role}")
                                }
                                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                            }

                        }
                        is Resource.Error->{
                            showLoading(false)
                            Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.progressBar.visibility = if (isShowLoading) View.VISIBLE else View.GONE
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}