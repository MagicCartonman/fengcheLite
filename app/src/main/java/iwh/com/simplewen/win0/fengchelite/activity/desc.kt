package iwh.com.simplewen.win0.fengchelite.activity


import android.content.Intent
import android.os.Bundle
import android.os.Handler

import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView

import com.bumptech.glide.Glide
import iwh.com.simplewen.win0.fengchelite.R
import iwh.com.simplewen.win0.fengchelite.adapter.PlayListViewAdapter
import iwh.com.simplewen.win0.fengchelite.app.iwhDataOperator
import iwh.com.simplewen.win0.fengchelite.app.iwhToast
import iwh.com.simplewen.win0.fengchelite.app.saveDM
import iwh.com.simplewen.win0.fengchelite.modal.PreData
import iwh.com.simplewen.win0.fengchelite.net.NetManage
import kotlinx.android.synthetic.main.activity_desc.*
import kotlinx.android.synthetic.main.content_desc.*


/**
 * 动漫详情页
 */
class desc : AppCompatActivity() {
    //封面图片
    lateinit var itemImgUrl: String
    //播放链接
    lateinit var itemId: String
    lateinit var handler: Handler
    //实例化网络操作对象
    lateinit var netM: NetManage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc)
        setSupportActionBar(toolbar)
        //初始化toolbar
        supportActionBar!!.title = intent.getStringExtra("itemName")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        netM = NetManage()
        handler = Handler {
            when (it.what) {
                0x113 -> {
                    iwhToast("网络请求失败！")
                    true
                }
                0x114 -> {
                    //移除加载
                    descLoad.visibility = View.GONE
                    //设置简介
                    descText.text = netM.playInfo[0]
                    //更新列表
                    playListView.adapter = PlayListViewAdapter(netM.playInfo[1].toInt())

                    true
                }
                0x115 ->{
                    Log.d("@@desc:Url",netM.currentPlayUrl)
                    val intent = Intent(this@desc, Play::class.java).apply {
                        putExtra("playUrl",netM.currentPlayUrl)
                    }
                    startActivity(intent)
                    true
                }

                else -> {
                    iwhToast("程序异常！")
                    true
                }

            }
        }
        intent.getStringExtra("itemId")?.let {
            //接收动漫的数据
            itemId = intent.getStringExtra("itemId")
            itemImgUrl = intent.getStringExtra("itemImg")?:""
            //加载封面
            Glide.with(this@desc).load(itemImgUrl).into(descImg)
            netM.getDesc(PreData.router(itemId), handler)

        }
        //收藏
        fab.setOnClickListener {
           // iwhDataOperator.setSHP(PreData.router(itemId),intent.getStringExtra("itemName"),"iwhLike")
            if(saveDM(intent.getStringExtra("itemName"),intent.getStringExtra("itemId"))){
                iwhToast("收藏完成！")
            }else{
                iwhToast("您已收藏过！")
            }

        }
        //播放
        playListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, position ->
            val url = itemId.let {
               "${ it.substring(6,it.length-5)}-${position+1}"
            }
           netM.getPlay(handler,url)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
