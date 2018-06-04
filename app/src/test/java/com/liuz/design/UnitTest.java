package com.liuz.design;

import com.vise.log.ViseLog;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static org.junit.Assert.assertEquals;

/**
 * date: 2018/5/31 15:22
 * author liuzhao
 */
public class UnitTest {


    @Test
   public  void  addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    private void  rxMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result" +integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                ViseLog.e(s);
            }
        });
    }

    private void  rxFlatMap(){

    }

    private void  rxConcatMapMap(){

    }
}
