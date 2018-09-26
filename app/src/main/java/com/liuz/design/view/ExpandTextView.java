package com.liuz.design.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.Layout;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.liuz.design.utils.CustomMovementMethod;

/**
 * date: 2018/9/18 14:43
 * author liuzhao
 */
public class ExpandTextView extends AppCompatTextView {


    /**
     * 展开状态 true：展开，false：收起
     */
    boolean expandState = false;
    /**
     * 源文字内容
     */
    String mText = "";
    /**
     * 最多展示的行数
     */
    int maxLineCount = 2;
    /**
     * 省略文字
     */
    String ellipsizeText = "...";
    // 重新计算高度
    int lineHeight = 0;
    private int originalHeight = 0; //原始高度
    private int originalLineCount = 0; //原始行数
    private float textHeight;
    private float textLineSpaceExtra = 0;
    private float lineSpacingMultiplier = 1.0f;
    private float lineSpacingAdd = 0.0f;
    private float textWidth = 0;

    public ExpandTextView(Context context) {
        super(context);
        initTextView();
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int[] attributes = new int[]{android.R.attr.lineSpacingExtra, android.R.attr.lineSpacingMultiplier};
        TypedArray arr = context.obtainStyledAttributes(attrs, attributes);
        lineSpacingAdd = arr.getDimensionPixelSize(0, 0);
        lineSpacingMultiplier = arr.getFloat(1, 1.0f);
        arr.recycle();
        initTextView();
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int[] attributes = new int[]{android.R.attr.lineSpacingExtra, android.R.attr.lineSpacingMultiplier};
        TypedArray arr = context.obtainStyledAttributes(attrs, attributes);
        lineSpacingAdd = arr.getDimensionPixelSize(0, 0);
        lineSpacingMultiplier = arr.getFloat(1, 1.0f);
        arr.recycle();
        initTextView();
    }

    public boolean isExpandState() {
        return expandState;
    }

    private void initTextView() {
        setMovementMethod(CustomMovementMethod.getInstance());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 文字计算辅助工具
        if (Html.fromHtml(mText).toString().isEmpty()) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
        }
        //StaticLayout对象
        StaticLayout sl = new StaticLayout(Html.fromHtml(mText).toString().trim(), getPaint(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        // 总计行数
        int lineCount = sl.getLineCount();
        //总行数大于最大行数
        if (lineCount > maxLineCount) {
            if (!expandState) {
                lineCount = maxLineCount;
            }
            Spanned newEndLineText = Html.fromHtml(mText);
            setText(newEndLineText);
        } else {
            setText(Html.fromHtml(mText));
        }
        if (lineHeight == 0) {
            for (int i = 0; i < lineCount; i++) {
                Rect lineBound = new Rect();
                sl.getLineBounds(i, lineBound);
                lineHeight += lineBound.height();
            }
            lineHeight = (int) (getPaddingTop() + getPaddingBottom() + lineHeight * lineSpacingMultiplier + lineSpacingAdd * (lineCount - 1));
            setMeasuredDimension(getMeasuredWidth(), lineHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!expandState) {
            TextPaint paint = getPaint();
            int textColor = paint.getColor();
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(textWidth, (textHeight + lineSpacingAdd) * (maxLineCount - 1), textWidth + textHeight, (textHeight + lineSpacingAdd) * (maxLineCount - 1) + textHeight, paint);
            paint.setColor(textColor);
            paint.drawableState = getDrawableState();
            canvas.drawText(ellipsizeText, textWidth + 3, (textHeight + lineSpacingAdd) * (maxLineCount - 1) + textHeight / 2 + getPaddingTop(), paint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //使用替代textview计算原始高度与行数
        TextPaint paint = getPaint();
        measureTextViewHeight(mText, paint.getTextSize(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
        //获取行高
        textHeight = 1.0f * originalHeight / originalLineCount;
        textLineSpaceExtra = textHeight * (lineSpacingMultiplier - 1) + lineSpacingAdd;

    }


    /**
     * 获取文本实际所占高度，辅助用于计算行高,行数
     *
     * @param text        文本
     * @param textSize    字体大小
     * @param deviceWidth 屏幕宽度
     */
    private void measureTextViewHeight(String text, float textSize, int deviceWidth) {
        TextView textView = new TextView(getContext());
        textView.setText(Html.fromHtml(text));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(deviceWidth, MeasureSpec.EXACTLY);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        originalLineCount = textView.getLineCount();
        originalHeight = textView.getMeasuredHeight();
        int start = textView.getLayout().getLineStart(maxLineCount - 1);
        int end = textView.getLayout().getLineEnd(maxLineCount - 1);
        String lineText = Html.fromHtml(mText).toString().substring(start, end);
        float dotWidth = getPaint().measureText(ellipsizeText);

        int endIndex = 0;
        for (int i = lineText.length() - 1; i > 0; i--) {
            String str = lineText.substring(i, lineText.length());
            // 找出文字宽度大于 ellipsizeText 的字符
            if (getPaint().measureText(str) > dotWidth) {
                endIndex = i;
                break;
            }
        }
        // 新的文字
        textWidth = getPaint().measureText(lineText.substring(0, endIndex));

    }

    /**
     * 设置要显示的文字以及状态
     *
     * @param text
     * @param expanded true：展开，false：收起
     */
    public void setText(String text, boolean expanded) {
        lineHeight = 0;
        mText = text;
        expandState = expanded;
        setText(Html.fromHtml(text));
        if (!expanded) {
            setMaxLines(maxLineCount);
            setEllipsize(TextUtils.TruncateAt.END);
        } else {
            setMaxLines(Integer.MAX_VALUE);
            setEllipsize(TextUtils.TruncateAt.END);
        }
        setIncludeFontPadding(false);

        requestLayout();
        invalidate();
    }


    /**
     * 展开收起状态变化
     *
     * @param expanded
     */
    void setChanged(boolean expanded) {
        expandState = expanded;
        requestLayout();
    }

}