package com.liuz.design.ui

import com.liuz.db.AreaBean
import com.liuz.db.AreasDatabase
import com.liuz.design.R
import com.liuz.design.api.MTimeApiService
import com.liuz.design.base.TranslucentBarBaseActivity
import com.liuz.design.bean.AreasBean
import com.liuz.lotus.net.ViseHttp
import com.liuz.lotus.net.callback.ACallback
import com.liuz.lotus.net.subscriber.ApiCallbackSubscriber
import com.vise.log.ViseLog
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * date: 2018/5/29 11:06
 * author liuzhao
 */
class AreaActivity : TranslucentBarBaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_area_layout
    }

    override fun initEventAndData() {
        setTitle("城市选择")

        initData()
    }

    private fun initData() {


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true);


        val citysList = ArrayList<List<AreaBean>>()


//        citysList.add(AreasDatabase.getInstance(mContext).areaDao().getAreas12())

        ViseHttp.RETROFIT<Any>()
                .addHeader("Host", "api-m.mtime.cn")
                .create(MTimeApiService::class.java)
                .areas
                .subscribeOn(Schedulers.io())

                .subscribe(ApiCallbackSubscriber(object : ACallback<AreasBean>() {
                    override fun onSuccess(authorModel: AreasBean?) {
                        ViseLog.i("request onSuccess!")
                        if (authorModel == null) {
                            return
                        } else {
                            AreasDatabase.getInstance(baseContext).areaDao().deleteAllAreas()
                            AreasDatabase.getInstance(mContext).areaDao().insertAll(authorModel.p)

                        }

                    }

                    override fun onFail(errCode: Int, errMsg: String) {
                        ViseLog.e("request errorCode:$errCode,errorMsg:$errMsg")
                    }
                }))
    }

}