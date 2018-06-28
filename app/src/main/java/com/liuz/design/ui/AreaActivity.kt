package com.liuz.design.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.liuz.db.AreaBean
import com.liuz.db.AreasDatabase
import com.liuz.design.R
import com.liuz.design.api.MTimeApiService
import com.liuz.design.base.TranslucentBarBaseActivity
import com.liuz.design.bean.AreasBean
import com.liuz.design.bindView
import com.liuz.design.ui.adapter.AreasAdapter
import com.liuz.design.ui.adapter.SearchCityAdapter
import com.liuz.design.utils.PreferencesUtils
import com.liuz.design.utils.Utils
import com.liuz.lotus.net.ViseHttp
import com.liuz.lotus.net.callback.ACallback
import com.liuz.lotus.net.subscriber.ApiCallbackSubscriber
import com.vise.log.ViseLog
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

/**
 * date: 2018/5/29 11:06
 * author liuzhao
 */
class AreaActivity : TranslucentBarBaseActivity() {
    private val etCity: EditText by bindView(R.id.et_city)
    private val tvClear: TextView by bindView(R.id.tv_clear)
    private val tvCity: TextView by bindView(R.id.tv_city)
    private val rvAreas: RecyclerView by bindView(R.id.rv_areas)
    private val rvCitys: RecyclerView by bindView(R.id.rv_citys)


    //声明mlocationClient对象
    lateinit var mlocationClient: AMapLocationClient
    //声明mLocationOption对象
    lateinit var mLocationOption: AMapLocationClientOption


    override fun getLayoutResId(): Int {
        return R.layout.activity_area_layout
    }


    override fun initEventAndData() {
        initView();
        getAreas()
        getCacheList()
        initLocation()
    }

    private fun initView() {
        setTitle("城市选择")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true);
        etCity.isCursorVisible = false
        etCity.setOnClickListener {
            etCity.isCursorVisible = true
            if (rvAreas.visibility == View.VISIBLE) {
                rvAreas.visibility = View.GONE
                rvCitys.visibility = View.VISIBLE
            }
            var letter = etCity.text.trim().toString();
            showCitys(letter)
        }
        tvClear.setOnClickListener {
            Utils.hideInput(it)
            etCity.isCursorVisible = false
            rvCitys.visibility = View.GONE
            rvAreas.visibility = View.VISIBLE
        }

        etCity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                var letter = etCity.text.trim().toString();
                showCitys(letter)
            }
        })
    }

    private fun initLocation() {
        // Required empty public constructor
        mlocationClient = AMapLocationClient(baseContext)
        //初始化定位参数
        mLocationOption = AMapLocationClientOption()

        //设置定位监听
        mlocationClient.setLocationListener(AMapLocationListener { amapLocation ->
            if (amapLocation != null) {
                if (amapLocation.errorCode == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.locationType//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.latitude//获取纬度
                    amapLocation.longitude//获取经度
                    amapLocation.accuracy//获取精度信息
                    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val date = Date(amapLocation.time)
                    df.format(date)//定位时间
                    var city = amapLocation.city.substring(0, amapLocation.city.length - 1)
                    tvCity.setText(city)
                    mlocationClient.stopLocation()

                    Observable.create(ObservableOnSubscribe<String> { _ ->
                        var list = AreasDatabase.getInstance(mContext).areaDao().areas
                        for (bean in list) {
                            if (bean.n.equals(city)) {
                                PreferencesUtils.setLocationID(bean.id)
                                break
                            }
                        }
                    }).subscribeOn(Schedulers.io()).subscribe(Consumer { })
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.errorCode + ", errInfo:"
                            + amapLocation.errorInfo)
                }
            }
        })
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000)
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption)
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation()

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
    }

    private fun showCitys(letter: String) {
        Observable.create(ObservableOnSubscribe<List<AreaBean>> { emitter ->
            var list = AreasDatabase.getInstance(mContext).areaDao().getSearchAreasDes(letter)
            emitter.onNext(list)
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { areaBeans ->
                    rvCitys.setLayoutManager(LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false))
                    rvCitys.setAdapter(SearchCityAdapter(mContext, areaBeans))

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
                            if (AreasDatabase.getInstance(mContext).areaDao().areas.size == 0) {
                                AreasDatabase.getInstance(mContext).areaDao().insertAll(authorModel.p)
                                getCacheList()
                            } else {
                                AreasDatabase.getInstance(mContext).areaDao().insertAll(authorModel.p)
                            }

                        }

                    }

                    override fun onFail(errCode: Int, errMsg: String) {
                        ViseLog.e("request errorCode:$errCode,errorMsg:$errMsg")
                    }
                }))
    }

}