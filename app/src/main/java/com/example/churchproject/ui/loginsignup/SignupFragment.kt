package com.example.churchproject.ui.loginsignup

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
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
import com.example.churchproject.core.data.source.remote.model.RequestSignup
import com.example.churchproject.databinding.FragmentLoginBinding
import com.example.churchproject.databinding.FragmentSignupBinding
import com.example.churchproject.ui.home.HomeActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val viewModel: LoginSignupViewModel by viewModels()
    private val binding get() = _binding!!
    private var isDataValid=false
    private var nama = ""
    private var email = ""
    private var password = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btSudahPunyaAkun.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        val nameStream = RxTextView.textChanges(binding.etDaftarNama)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        nameStream.subscribe {
            binding.etDaftarNama.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    showNameAlert(it)
                }
            }
        }

        val emailStream = RxTextView.textChanges(binding.etDaftarEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            binding.etDaftarEmail.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    showEmailAlert(it)
                }
            }
        }

        val passwordStream = RxTextView.textChanges(binding.etDaftarPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 6
            }
        passwordStream.subscribe {
            binding.etDaftarPassword.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    showPasswordAlert(it)
                }
            }
        }
        val passwordConfirmationStream = io.reactivex.Observable.merge(
            RxTextView.textChanges(binding.etDaftarPassword)
                .map { password ->
                    password.toString() != binding.etDaftarPassword.text.toString()
                },
            RxTextView.textChanges(binding.etDaftarCpassword)
                .map { confirmPassword ->
                    confirmPassword.toString() != binding.etDaftarPassword.text.toString()
                }
        )
        passwordConfirmationStream.subscribe {
            showPasswordConfirmationAlert(it)
        }

        val validFieldsStream = io.reactivex.Observable.combineLatest(
            nameStream,
            emailStream,
            passwordStream,
            passwordConfirmationStream
        ) { nameInvalid: Boolean, emailInvalid: Boolean, passwordInvalid: Boolean, passwordConfirmationInvalid: Boolean ->
            !emailInvalid && !passwordInvalid && !passwordConfirmationInvalid && !nameInvalid
        }

        validFieldsStream.subscribe { isValid ->
            isDataValid = isValid
            if(isValid){
                nama=binding.etDaftarNama.text.toString().trim()
                email=binding.etDaftarEmail.text.toString().trim()
                password=binding.etDaftarPassword.text.toString().trim()
            }
        }

        binding.cbShowPass.setOnClickListener {
            if (binding.cbShowPass.isChecked) {
                binding.etDaftarPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.etDaftarCpassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                binding.etDaftarPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.etDaftarCpassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }

        viewModel.getToken().observe(requireActivity()) { it ->
            if (it!=""&&it!=null&&isAdded) {
                val intent = Intent(requireActivity(), HomeActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }


        binding.btDaftar.setOnClickListener {
            if(isDataValid){
                viewModel.signup(RequestSignup(email,nama,password)).observe(requireActivity()){
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
                                Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                            }

                        }
                        is Resource.Error->{
                            showLoading(false)
                            Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility=if(isLoading) View.VISIBLE else View.GONE
    }
    private fun showPasswordConfirmationAlert(isNotValid: Boolean) {
        binding.etDaftarCpassword.error = if (isNotValid) "Password tidak sesuai" else null
    }
    private fun showPasswordAlert(isNotValid: Boolean) {
        binding.etDaftarPassword.error = if (isNotValid) "Jumlah karakter password minimal 6" else null
    }

    private fun showEmailAlert(isNotValid: Boolean) {
        binding.etDaftarEmail.error = if (isNotValid) "Email tidak valid" else null
    }

    private fun showNameAlert(isNotValid: Boolean) {
        binding.etDaftarNama.error = if (isNotValid) "Nama tidak boleh kosong" else null
    }

}