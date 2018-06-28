package com.liuz.design.ui.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liuz.design.R;
import com.liuz.design.ui.MainActivity;
import com.liuz.design.utils.DialogUtils;
import com.liuz.design.utils.PreferencesUtils;
import com.liuz.design.view.Banners;
import com.liuz.lotus.permission.Permission;
import com.liuz.lotus.permission.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;


public class BannerItemFragment extends Fragment {

    private static final String bannerStr = "bannerItem";
    @BindView(R.id.iv_banner) ImageView ivBanner;
    @BindView(R.id.iv_btn) ImageView ivBtn;
    @BindView(R.id.iv_logo) ImageView ivLogo;
    @BindView(R.id.iv_tips) ImageView ivTips;
    @BindView(R.id.iv_des) ImageView ivDes;
    private Banners banner;
    private Unbinder unbinder;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public BannerItemFragment() {
        // Required empty public constructor
    }

    public static BannerItemFragment newInstance(Banners banner) {
        BannerItemFragment fragment = new BannerItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(bannerStr, banner);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            banner = getArguments().getParcelable(bannerStr);
        }
    }

    @OnClick(R.id.iv_btn)
    void onViewClick() {
        getPermission();
    }

    private void goMain() {
        PreferencesUtils.setFirstVisitState(false);
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity() != null) {
            RxPermissions rxPermissions = new RxPermissions(getActivity());
            rxPermissions.requestEach(permissions).subscribe(new Consumer<Permission>() {
                @Override
                public void accept(Permission permission) throws Exception {
                    if (permission.granted) {
                        goMain();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 重新获取权限
                        getPermission();
                    } else {
                        //提示
                        DialogUtils.showTips(getActivity(), R.string.permission_storage_title, R.string.permission_storage_des,
                                R.string.permission_cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getActivity().finish();
                                    }


                                },
                                R.string.permission_ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri packageURI = Uri.parse("package:" + "com.liuz.design");
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }

                                });


                    }
                }
            });
        } else {
            goMain();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banner_item_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        ivBanner.setBackgroundResource(banner.getBannerRes());
        ivTips.setBackgroundResource(banner.getTipsRes());
        if (banner.getTipBtnRes() > 0) {
            ivLogo.setVisibility(View.GONE);
            ivBtn.setBackgroundResource(banner.getTipBtnRes());
            ivDes.setBackgroundResource(banner.getTipDesRes());
        } else {
            ivBtn.setVisibility(View.GONE);
            ivDes.setVisibility(View.GONE);
        }

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }


}
