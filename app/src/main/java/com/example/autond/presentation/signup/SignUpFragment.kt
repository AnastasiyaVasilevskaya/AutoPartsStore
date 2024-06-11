package com.example.autond.presentation.signup

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.autond.R
import com.example.autond.databinding.FragmentSignupBinding
import com.example.autond.presentation.login.LoginViewModel

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUp()
        initClickListeners()
        phoneNumberTextWatcher()
        emailTextWatcher()
        passwordTextWatcher()
    }




    private fun signUp() {
        binding.signUpButton.setOnClickListener {
            val email = binding.emailEditText.text?.trim().toString()
            val phoneNumber = binding.phoneNumberEditText.text?.trim().toString()
            val password = binding.passwordEditText.text?.trim().toString()
            viewModel.signUp(email, phoneNumber, password)
        }

        viewModel.signUpResult.observe(viewLifecycleOwner) { result ->
            if (result) {
                findNavController().navigate(R.id.action_signUpFragment_to_dashboardFragment)
            } else {
                viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                    Toast.makeText(requireContext(),errorMessage ?: "Invalid credentials",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initClickListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

    // Валидация почты
    private fun emailTextWatcher() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                emailHelperText()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun validEmail(): Boolean {
        val enteredEmail = binding.emailEditText.text.toString().trim()
        return Patterns.EMAIL_ADDRESS.matcher(enteredEmail).matches()
    }

    private fun emailHelperText() {
        if (validEmail()) {
            binding.emailContainer.helperText = getString(R.string.correct)
            val colorGreen = ContextCompat.getColor(requireContext(), R.color.green)
            binding.emailContainer.setHelperTextColor(ColorStateList.valueOf(colorGreen))
        } else {
            binding.emailContainer.helperText = getString(R.string.invalid_email)
            val colorRed = ContextCompat.getColor(requireContext(), R.color.red)
            binding.emailContainer.setHelperTextColor(ColorStateList.valueOf(colorRed))
        }
        updateButtonState()
    }
    // Валидация номера телефона
    private fun phoneNumberTextWatcher() {
        binding.phoneNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                phoneNumberHelperText()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun validPhoneNumber(): Boolean {
        val enteredPhoneNumber = binding.phoneNumberEditText.text.toString().trim()
        val regex = "^((\\+7|7|8)+([0-9]){10})|((\\+375|375|80)+(29|25|44|33)+([0-9]){7})\$".toRegex()
        return regex.matches(enteredPhoneNumber)
    }

    private fun phoneNumberHelperText() {
        if (validPhoneNumber()) {
            binding.phoneNumberContainer.helperText = getString(R.string.correct)
            val colorGreen = ContextCompat.getColor(requireContext(), R.color.green)
            binding.phoneNumberContainer.setHelperTextColor(ColorStateList.valueOf(colorGreen))
        } else {
            binding.phoneNumberContainer.helperText = getString(R.string.invalid_phone_number)
            val colorRed = ContextCompat.getColor(requireContext(), R.color.red)
            binding.phoneNumberContainer.setHelperTextColor(ColorStateList.valueOf(colorRed))
        }
        updateButtonState()
    }


    // Валидация пароля
    private fun passwordTextWatcher() {
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                passwordHelperText()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun validPassword(): Boolean {
        return binding.passwordEditText.text.toString().trim().length > 5
    }

    private fun passwordHelperText() {
        if (validPassword()) {
            binding.passwordContainer.helperText = getString(R.string.correct)
            val colorGreen = ContextCompat.getColor(requireContext(), R.color.green)
            binding.passwordContainer.setHelperTextColor(ColorStateList.valueOf(colorGreen))
        } else {
            binding.passwordContainer.helperText = getString(R.string.invalid_password_mess)
            val colorRed = ContextCompat.getColor(requireContext(), R.color.red)
            binding.passwordContainer.setHelperTextColor(ColorStateList.valueOf(colorRed))
        }
        updateButtonState()
    }

    // Активация кнопки
    private fun updateButtonState() {
        binding.signUpButton.isEnabled = validEmail() && validPassword()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
