package com.liuz.mvvm.m;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.liuz.lotus.net.mode.ApiResult;

/**
 * Created by changxing on 2017/12/3.
 */

public abstract class AbsDataSource<ResultType, RequestType> {
    private final MediatorLiveData<ResultType> result = new MediatorLiveData<>();

    @MainThread
    public AbsDataSource() {
        final LiveData<ResultType> dbSource = loadFromDb();
        result.setValue(dbSource.getValue());

        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType resultType) {
                result.removeSource(dbSource);
                if (shouldFetch(resultType)) {
                    fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType resultType) {
                            result.setValue(resultType);
                        }
                    });
                }
            }
        });
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    // Called to get the cached getDate from the database
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResult<RequestType>> createCall();

    @MainThread
    protected abstract void onFetchFailed();

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        final LiveData<ApiResult<RequestType>> apiResponse = createCall();


        result.addSource(apiResponse, new Observer<ApiResult<RequestType>>() {
            @Override
            public void onChanged(@Nullable final ApiResult<RequestType> requestTypeRequestApi) {
                result.removeSource(apiResponse);
                //noinspection ConstantConditions
                if (requestTypeRequestApi.getCode() == 200) {
                    saveResultAndReInit(requestTypeRequestApi);
                } else {
                    onFetchFailed();

                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType resultType) {
                            result.setValue(resultType);
                        }
                    });
                }
            }
        });

    }

    @MainThread
    private void saveResultAndReInit(final ApiResult<RequestType> response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response.getData());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // we specially request a new live getDate,
                // otherwise we will get immediately last cached value,
                // which may not be updated with latest results received from network.

                result.addSource(loadFromDb(), new Observer<ResultType>() {
                    @Override
                    public void onChanged(@Nullable ResultType resultType) {
                        result.setValue(resultType);
                    }
                });
            }
        }.execute();
    }

    public final MediatorLiveData<ResultType> getAsLiveData() {
        return result;
    }
}
