package android.tvz.hr.listadobrinic

//noinspection SuspiciousImport
import android.R
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment

class CarListFragment : ListFragment() {

    private val STATE_ACTIVATED_POSITION = "activated_position"
    var mActivatedPosition = ListView.INVALID_POSITION

    interface Callbacks {
        fun onItemSelected(id: String?)
    }

    private val sDummyCallbacks: Callbacks =
        object : Callbacks {
            override fun onItemSelected(id: String?) {}
        }

    var mCallbacks: Callbacks = sDummyCallbacks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listAdapter = ArrayAdapter(
            requireActivity(),
            R.layout.simple_list_item_activated_1,
            R.id.text1,
            getCars())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Restore the previously serialized activated item position
        if(savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION))
        }
    }

    private fun setActivatedPosition(position: Int) {
        if (position == ListView.INVALID_POSITION) {
            listView.setItemChecked(mActivatedPosition, false)
        } else {
            listView.setItemChecked(position, true)
        }

        mActivatedPosition = position
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Activities containing this fragment must implement its callbacks
        check(context is Callbacks) { "Activity must implement fragment's callbacks." }

        mCallbacks = context
    }

    override fun onDetach() {
        super.onDetach()
        mCallbacks = sDummyCallbacks
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        mCallbacks.onItemSelected(getCars().getOrNull(position)?.id)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition)
        }
    }

    fun setActivateOnItemClick(activateOnItemClick: Boolean) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        listView.choiceMode =
            if (activateOnItemClick) ListView.CHOICE_MODE_SINGLE else ListView.CHOICE_MODE_NONE
    }

    private fun getCars(): ArrayList<Car>{
        // mock data
        return arrayListOf<Car>(
            Car("1","Mercedes","W124","Green","https://i.pinimg.com/736x/85/5f/38/855f38f134cb18e421c013b7420984a7.jpg"),
            Car("2","Mercedes", "W210", "Silver", "https://i.pinimg.com/736x/9e/5f/37/9e5f37048fe10a4856e68516c4d895f3.jpg"),
            Car("3","Toyota", "Corolla", "Red", "https://m1.secondhandapp.at/2.0/59b272586df44f0d9cace632?height=128&width=128"),
            Car("4","Honda", "Civic", "Blue", "https://i.imgur.com/qB87aOG.jpg"),
            Car("5","Ford", "Mustang", "Black", "https://i.imgur.com/PTjK26L.jpg"),
            Car("6","Chevrolet", "Camaro", "Yellow", "https://i.imgur.com/iO4FvZu.jpg"),
            Car("7","Nissan", "GT-R", "Silver", "https://i.imgur.com/vJbCkEN.jpg"),
            Car("8","Audi", "R8", "White", "https://i.imgur.com/vnqDfXK.jpg"),
            Car("9","BMW", "M3", "Blue", "https://i.imgur.com/NVjhZtG.jpg"),
            Car("10","Volkswagen", "Golf", "Red", "https://i.imgur.com/NNNIuYN.jpg"),
            Car("11","Kia", "Optima", "Black", "https://i.imgur.com/u4I2Ayr.jpg"),
            Car("12","Tesla", "Model S", "White", "https://i.imgur.com/XVsoM2L.jpg"),
            Car("13","Ferrari", "488 GTB", "Red", "https://i.imgur.com/yF2KSLb.jpg"),
            Car("14","Lamborghini", "Aventador", "Yellow", "https://i.imgur.com/7uH2cnH.jpg"),
            Car("15","Porsche", "911", "Black", "https://i.imgur.com/2PNzONs.jpg"),
            Car("16","Dodge", "Challenger", "Orange", "https://i.imgur.com/YnnT4s7.jpg"),
            Car("17","Jeep", "Wrangler", "Green", "https://i.imgur.com/u3ouY9N.jpg"),
            Car("18","Land Rover", "Defender", "Silver", "https://i.imgur.com/7yN2K0M.jpg"),
            Car("19","Hyundai", "Sonata", "Gray", "https://i.imgur.com/yo8ONjU.jpg"),
            Car("20","Volvo", "XC90", "White", "https://i.imgur.com/yZ49CNh.jpg"),
        )
    }
}