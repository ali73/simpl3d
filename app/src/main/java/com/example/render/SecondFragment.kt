package com.example.render

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.arcore.HelloArRenderer
import com.example.arcore.helpers.ARCoreSessionLifecycleHelper
import com.example.arcore.helpers.DepthSettings
import com.example.arcore.helpers.InstantPlacementSettings
import com.example.arcore.helpers.TapHelper
import com.example.arcore.samplerender.SampleRender
import com.example.render.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var arcoreSessionLifeCycleHelper: ARCoreSessionLifecycleHelper
    private lateinit var instantPlacementSettings: InstantPlacementSettings
    lateinit var tapHelper : TapHelper

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantPlacementSettings = InstantPlacementSettings()

//        lateinit var view: HelloArView
        lateinit var renderer: HelloArRenderer

        val instantPlacementSettings = InstantPlacementSettings()
        val depthSettings = DepthSettings()


        arcoreSessionLifeCycleHelper = ARCoreSessionLifecycleHelper(
            activity as Activity,
            instantPlacementSettings = instantPlacementSettings
        )

        lifecycle.addObserver(arcoreSessionLifeCycleHelper)

        renderer = HelloArRenderer(activity as Activity,
            arcoreSessionLifeCycleHelper.session,
            depthSettings,
            instantPlacementSettings,
            null)
        lifecycle.addObserver(renderer)

        // Set up Hello AR UI.
//        view = HelloArView(this)
//        lifecycle.addObserver(view)

        // Sets up an example renderer using our HelloARRenderer.
        SampleRender(binding.glSurface, renderer, (activity as Activity).assets)

        depthSettings.onCreate(context)
        instantPlacementSettings.onCreate(context)
        tapHelper = TapHelper(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.glSurface.setOnTouchListener(tapHelper)
    }

    override fun onResume() {
        super.onResume()
        binding.glSurface.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.glSurface.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}