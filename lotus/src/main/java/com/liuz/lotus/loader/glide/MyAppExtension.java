package com.liuz.lotus.loader.glide;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;

/**
 * date: 2018/9/14 18:52
 * author liuzhao
 */
@GlideExtension
public class MyAppExtension {
    // Size of mini thumb in pixels.
    private static final int MINI_THUMB_SIZE = 100;

    private MyAppExtension() {
    } // utility class

    @GlideOption
    public static void miniThumb(RequestOptions options) {
        options
                .fitCenter()
                .override(MINI_THUMB_SIZE);
    }
}