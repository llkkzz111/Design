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

import com.liuz.common.ApiResultTransformer;
import com.liuz.common.subscriber.ApiResultSubscriber;
import com.liuz.db.WanDataBase;
import com.liuz.db.wan.AccountBean;
import com.liuz.design.R;
import com.liuz.design.api.WanApiServices;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.exception.ApiException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

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
                    .compose(ApiResultTransformer.<AccountBean>norTransformer())
                    .subscribe(new ApiResultSubscriber<AccountBean>() {
                        @Override
                        protected void onError(ApiException e) {

                        }

                        @Override
                        public void onSuccess(AccountBean data) {
                            saveAccount(data);
                            Toast.makeText(mContext, data.getUsername(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

        }
    }

    private void saveAccount(final AccountBean data) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                WanDataBase.getInstance(mContext).accountDao().insertAccount(data);
            }
        }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).subscribe();
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

