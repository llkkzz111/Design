package com.liuz.design.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import com.liuz.db.AreaBean
import com.liuz.db.AreasDatabase
import com.liuz.design.R
import com.liuz.design.api.MTimeApiService
import com.liuz.design.base.TranslucentBarBaseActivity
import com.liuz.design.bean.AreasBean
import com.liuz.design.bindView
import com.liuz.design.ui.adapter.AreasAdapter
import com.liuz.design.utils.Utils
import com.liuz.lotus.net.ViseHttp
import com.liuz.lotus.net.callback.ACallback
import com.liuz.lotus.net.subscriber.ApiCallbackSubscriber
import com.vise.log.ViseLog
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * date: 2018/5/29 11:06
 * author liuzhao
 */
class AreaActivity : TranslucentBarBaseActivity() {
    private val etCity: EditText by bindView(R.id.et_city)
    private val tvCkear: TextView by bindView(R.id.tv_clear)
    private val rvAreas: RecyclerView by bindView(R.id.rv_areas)


    override fun getLayoutResId(): Int {
        return R.layout.activity_area_layout
    }


    override fun initEventAndData() {
        setTitle("城市选择")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true);
        getAreas()
        getCacheList()
    }

    private fun getCacheList() {
        val limeits = "abcdefghijklmnopqrstuvwxyz"
        val citysList = ArrayList<List<AreaBean>>()
        Observable.create(ObservableOnSubscribe<List<List<AreaBean>>> { emitter ->
            var list = AreasDatabase.getInstance(mContext).areaDao().areas12
            citysList.add(list)
            for (a in limeits.split("".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                if (!TextUtils.isEmpty(a)) {
                    list = AreasDatabase.getInstance(mContext).areaDao().getAreasDes(a)
                    if (list != null && list.size > 0)
                        citysList.add(list)
                }
            }
            emitter.onNext(citysList)
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { areaBeans ->
                    var adapter = AreasAdapter(mContext, areaBeans)
                    val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    rvAreas.setLayoutManager(linearLayoutManager)
                    rvAreas.setAdapter(adapter)
                }

        tvCkear.setOnClickListener {
            Utils.hideInput(it)
        }

    }

    private fun getAreas() {
        ViseHttp.RETROFIT<Any>()
                .addHeader("Host", "api-m.mtime.cn")
                .create(MTimeApiService::class.java)
                .areas
                .subscribeOn(Schedulers.io())
                .subscribe(ApiCallbackSubscriber(object : ACallback<AreasBean>() {
                    override fun onSuccess(authorModel: AreasBean?) {
                        if (authorModel == null) {
                            return
                        } else {
                            AreasDatabase.getInstance(mContext).areaDao().insertAll(authorModel.p)

                        }

                    }

                    override fun onFail(errCode: Int, errMsg: String) {
                        ViseLog.e("request errorCode:$errCode,errorMsg:$errMsg")
                    }
                }))
    }

}