package com.example.weatherapp2.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.weatherapp2.R
import com.example.weatherapp2.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var plauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Инициализируем permission launcher здесь
        plauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            Toast.makeText(activity, "Permission is $isGranted", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        if (!isPermissionGranted(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Запрашиваем разрешение
            plauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}