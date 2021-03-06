package iwh.com.simplewen.win0.fengchelite.adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import iwh.com.simplewen.win0.fengchelite.app.App

class SearchListAdapter(val searchList:ArrayList<Map<String,Any>?>):BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


        return  LinearLayout(App.getContext()).apply{
            layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            orientation = LinearLayout.HORIZONTAL
            setPadding(10,10,10,10)
            addView(  TextView(App.getContext()).apply {
                text = "${position+1}."
                setPadding(10,10,10,10)
                setTextColor(Color.BLACK)
                layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            },0)
            addView(  TextView(App.getContext()).apply {
                setPadding(10,10,10,10)
                setTextColor(Color.BLACK)
                text = searchList[position]?.get("itemName").toString()
                layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            },1)


        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getCount(): Int {
        return searchList.size
    }
}