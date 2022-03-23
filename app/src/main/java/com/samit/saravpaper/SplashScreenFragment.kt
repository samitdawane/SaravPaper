package com.samit.saravpaper

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.samit.saravpaper.utility.jetpack_datastore.UserDataStore
import kotlinx.coroutines.*
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class SplashScreenFragment : Fragment(), CoroutineScope {
    private lateinit var userPreferences: UserDataStore
    private var isStandardSelected : Boolean? =  false
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val imageView: ImageView = view.findViewById(R.id.ivSplash)
        Glide.with(this).load(R.drawable.splash_bg).into(imageView)

        userPreferences = UserDataStore(requireActivity())

        userPreferences.standard.asLiveData().observe(this.viewLifecycleOwner, Observer {
            it?.let {
                if(it!!.length > 0)
                isStandardSelected = true
            }

        })


        //Log.e(">>>>","json>>>"+str)
        launch {
            delay(2500)
            withContext(Dispatchers.Main){
                if(isStandardSelected == true){
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_selectPaperFragment)
                }else{
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_selectStandardFragment)
                }

            }
        }
    }

    @Throws(IOException::class)
    fun Context.readJsonAsset(fileName: String): String {
        val inputStream = assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }
}