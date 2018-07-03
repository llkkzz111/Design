package com.liuz.design.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.liuz.common.mode.ApiResult;
import com.liuz.common.request.ApiPostRequest;
import com.liuz.design.R;
import com.liuz.design.api.WanApiServices;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.bean.HotMoviesBean;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.callback.ACallback;
import com.liuz.lotus.net.core.ApiTransformer;
import com.liuz.lotus.net.subscriber.ApiCallbackSubscriber;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends TranslucentBarBaseActivity {


    @BindView(R.id.login_progress) ProgressBar mProgressView;
    @BindView(R.id.login_form) ScrollView mLoginFormView;
    @BindView(R.id.email) AutoCompleteTextView mEmailView;
    @BindView(R.id.password) EditText mPasswordView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
    }

    @OnClick(R.id.email_sign_in_button)
    void onViewClick() {
        attemptLogin();
    }

    @OnEditorAction(R.id.password)
    boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

        if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
            attemptLogin();
            return true;
        }
        return false;
    }


    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);


            ViseHttp.RETROFIT()
                    .create(WanApiServices.class)
                    .userLogin(email, password)
                    .compose(ApiTransformer.<HotMoviesBean>norTransformer())
                    .subscribe(new ApiCallbackSubscriber<>(new ACallback<HotMoviesBean>() {
                        @Override
                        public void onSuccess(HotMoviesBean data) {

                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    }));


            ViseHttp.BASE(new ApiPostRequest("user/login")
                    .addParam("username", email)
                    .addParam("password", password))
                    .request(
                            new ACallback<ApiResult>() {
                                @Override
                                public void onSuccess(ApiResult data) {
                                    Toast.makeText(mContext, data.getData().toString(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {
                                    Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();
                                }
                            });

        }
    }


    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}

