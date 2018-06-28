package com.liuz.lotus.loader;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @Description: 使用Glide框架加载图片
 * @author: jeasinlee
 * @date: 2016-12-19 15:16
 */
public class GlideLoader implements ILoader {
    @Override
    public void init(Context context) {

    }

    @Override
    public void loadNet(ImageView target, String url, Options options) {
        load(getRequestManager(target.getContext()).load(url), target, options);
    }

    @Override
    public void loadResource(ImageView target, int resId, Options options) {
        load(getRequestManager(target.getContext()).load(resId), target, options);
    }

    @Override
    public void loadAssets(ImageView target, String assetName, Options options) {
        load(getRequestManager(target.getContext()).load("file:///android_asset/" + assetName), target, options);
    }

    @Override
    public void loadFile(ImageView target, File file, Options options) {
        load(getRequestManager(target.getContext()).load(file), target, options);
    }

    @Override
    public void clearMemoryCache(Context context) {
        GlideApp.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(Context context) {
        GlideApp.get(context).clearDiskCache();
    }

    private GlideRequests getRequestManager(Context context) {
        return GlideApp.with(context);
    }

    private void load(GlideRequest request, ImageView target, Options options) {

        if (options == null) options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }
        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }
        request.transition(withCrossFade()).into(target);
    }
}
