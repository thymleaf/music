package com.thymleaf.music.ui

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.PlaybackStateCompat.STATE_PLAYING
import android.view.SurfaceView
import android.view.animation.LinearInterpolator
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentPlayerBinding
import com.thymleaf.music.player.PlayerWrapper
import com.thymleaf.music.uamp.utils.InjectorUtils
import com.thymleaf.music.uamp.viewmodels.NowPlayingFragmentViewModel
import me.bogerchan.niervisualizer.NierVisualizerManager
import me.bogerchan.niervisualizer.renderer.IRenderer
import me.bogerchan.niervisualizer.renderer.circle.CircleBarRenderer
import me.bogerchan.niervisualizer.renderer.circle.CircleRenderer
import me.bogerchan.niervisualizer.renderer.circle.CircleSolidRenderer
import me.bogerchan.niervisualizer.renderer.circle.CircleWaveRenderer
import me.bogerchan.niervisualizer.renderer.columnar.ColumnarType1Renderer
import me.bogerchan.niervisualizer.renderer.columnar.ColumnarType2Renderer
import me.bogerchan.niervisualizer.renderer.columnar.ColumnarType3Renderer
import me.bogerchan.niervisualizer.renderer.columnar.ColumnarType4Renderer
import me.bogerchan.niervisualizer.renderer.line.LineRenderer
import me.bogerchan.niervisualizer.renderer.other.ArcStaticRenderer
import me.bogerchan.niervisualizer.util.NierAnimator

class PlayerFragment : BaseSimpleFragment() {

    private lateinit var binding: FragmentPlayerBinding

    private lateinit var svWave: SurfaceView
    private var mVisualizerManager: NierVisualizerManager? = null

    private val nowPlayingViewModel by viewModels<NowPlayingFragmentViewModel> {
        InjectorUtils.provideNowPlayingFragmentViewModel(requireContext())
    }


    override fun setViewBinding(): ViewBinding {
        binding = FragmentPlayerBinding.inflate(layoutInflater)

        return binding
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        svWave = binding.svWave

        svWave.setZOrderOnTop(true)
        svWave.holder.setFormat(PixelFormat.TRANSLUCENT)

        nowPlayingViewModel.playState.observe(viewLifecycleOwner, {
            createNewVisualizerManager(it)
            when (it.state) {
                STATE_PLAYING -> {
                    useStyle()
                }
                else -> {

                }
            }

        })
    }

    private fun createNewVisualizerManager(stateCompat: PlaybackStateCompat) {
        mVisualizerManager?.release()
        mVisualizerManager = NierVisualizerManager().apply {
            when (stateCompat.state) {
                STATE_PLAYING -> {
                    PlayerWrapper.getPlayerInstance(requireContext()).audioComponent?.audioSessionId?.let { init(it) }
                }
                else -> {

                }
            }
        }
    }

    private fun useStyle(idx: Int = 9) {
        mVisualizerManager?.start(svWave, mRenderers[idx % mRenderers.size])
    }


    private val mRenderers = arrayOf<Array<IRenderer>>(
            arrayOf(ColumnarType1Renderer()),
            arrayOf(ColumnarType2Renderer()),
            arrayOf(ColumnarType3Renderer()),
            arrayOf(ColumnarType4Renderer()),
            arrayOf(LineRenderer(true)),
            arrayOf(CircleBarRenderer()),
            arrayOf(CircleRenderer(true)),
            arrayOf(
                    CircleRenderer(true),
                    CircleBarRenderer(),
                    ColumnarType4Renderer()
            ),
            arrayOf(CircleRenderer(true), CircleBarRenderer(), LineRenderer(true)),
            arrayOf(
                    ArcStaticRenderer(
                            paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                                color = Color.parseColor("#cfa9d0fd")
                            }),
                    ArcStaticRenderer(
                            paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                                color = Color.parseColor("#dad2eafe")
                            },
                            amplificationOuter = .83f,
                            startAngle = -90f,
                            sweepAngle = 225f
                    ),
                    ArcStaticRenderer(
                            paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                                color = Color.parseColor("#7fa9d0fd")
                            },
                            amplificationOuter = .93f,
                            amplificationInner = 0.8f,
                            startAngle = -45f,
                            sweepAngle = 135f
                    ),
                    CircleSolidRenderer(
                            paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                                color = Color.parseColor("#d2eafe")
                            },
                            amplification = .45f
                    ),
                    CircleBarRenderer(
                            paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                                strokeWidth = 4f
                                color = Color.parseColor("#efe3f2ff")
                            },
                            modulationStrength = 1f,
                            type = CircleBarRenderer.Type.TYPE_A_AND_TYPE_B,
                            amplification = 1f, divisions = 8
                    ),
                    CircleBarRenderer(
                            paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                                strokeWidth = 5f
                                color = Color.parseColor("#e3f2ff")
                            },
                            modulationStrength = 0.1f,
                            amplification = 1.2f,
                            divisions = 8
                    ),
                    CircleWaveRenderer(
                            paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                                strokeWidth = 6f
                                color = Color.WHITE
                            },
                            modulationStrength = 0.2f,
                            type = CircleWaveRenderer.Type.TYPE_B,
                            amplification = 1f,
                            animator = NierAnimator(
                                    interpolator = LinearInterpolator(),
                                    duration = 20000,
                                    values = floatArrayOf(0f, -360f)
                            )
                    ),
                    CircleWaveRenderer(
                            paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                                strokeWidth = 6f
                                color = Color.parseColor("#7fcee7fe")
                            },
                            modulationStrength = 0.2f,
                            type = CircleWaveRenderer.Type.TYPE_B,
                            amplification = 1f,
                            divisions = 8,
                            animator = NierAnimator(
                                    interpolator = LinearInterpolator(),
                                    duration = 20000,
                                    values = floatArrayOf(0f, -360f)
                            )
                    )
            )
    )

    override fun onStart() {
        super.onStart()
        mVisualizerManager?.apply {
            resume()
        }
    }

    override fun onResume() {
        super.onResume()
        val activity = activity as MusicContainerActivity
        arguments?.let {
            activity.hideBottom(it.getBoolean(KEY_IS_HIDE_BOTTOM, false))
            activity.hideToolBar(it.getBoolean(KEY_IS_HIDE_TOOLBAR, false))
        }
    }

    override fun onStop() {
        super.onStop()
        mVisualizerManager?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mVisualizerManager?.release()
        mVisualizerManager = null
    }


    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
                PlayerFragment().apply {
                    arguments = bundle
                }
    }
}

const val PLAYER_FRAGMENT_TAG = "player_fragment_tag"