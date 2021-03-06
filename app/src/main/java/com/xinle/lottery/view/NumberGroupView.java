package com.xinle.lottery.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.xinle.lottery.R;

import java.util.ArrayList;

/**
 * 用于显示彩票选择时的数字栏
 * Created by Alashi on 2016/1/13.
 */
public class NumberGroupView extends View {
    private static final String TAG = NumberGroupView.class.getSimpleName();

    private TextPaint paint;
    private Drawable checkedDrawable;
    private Drawable uncheckedDrawable;
    private int itemHeight;
    private int itemWidth;
    private int horizontalGap;
    private int verticalGap;
    /**
     * true 数字显示成“1”，false 数字显示成“01” null 显示文字
     */
    private Boolean numberStyle;
    /**
     * true 默认为单选 false 为多选
     */
    private boolean chooseMode;
    private int maxNumber;
    private int minNumber;
    private String[] displayText;
    private int column;

    private SparseBooleanArray checkedArray;
    private GestureDetector gestureDetector;
    private float textSize;
    private int checkedTextColor;
    private int uncheckedTextColor;

    private OnChooseItemClickListener chooseItemListener;

    private ArrayList<Integer> pickList;
    private int lastPick;

    public NumberGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NumberGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.NumberGroupView);
        itemHeight = attribute.getDimensionPixelSize(R.styleable.NumberGroupView_itemHeight, 48);
        itemWidth = attribute.getDimensionPixelSize(R.styleable.NumberGroupView_itemWidth, 48);
        verticalGap = attribute.getDimensionPixelSize(R.styleable.NumberGroupView_verticalGap, 16);
        checkedTextColor = attribute.getColor(R.styleable.NumberGroupView_checkedTextColor, Color.WHITE);
        uncheckedTextColor = attribute.getColor(R.styleable.NumberGroupView_uncheckedTextColor, Color.BLACK);
        textSize = attribute.getDimension(R.styleable.NumberGroupView_textSize, 36);
        checkedDrawable = attribute.getDrawable(R.styleable.NumberGroupView_checkedDrawable);
        uncheckedDrawable = attribute.getDrawable(R.styleable.NumberGroupView_uncheckedDrawable);
        numberStyle = attribute.getBoolean(R.styleable.NumberGroupView_numberStyle, true);
        chooseMode = attribute.getBoolean(R.styleable.NumberGroupView_chooseMode, false);
        maxNumber = attribute.getInt(R.styleable.NumberGroupView_maxNumber, 9);
        minNumber = attribute.getInt(R.styleable.NumberGroupView_minNumber, 0);
        column = attribute.getInt(R.styleable.NumberGroupView_column, 5);
        int textArrayId = attribute.getResourceId(R.styleable.NumberGroupView_textArray, 0);
        attribute.recycle();

        if (textArrayId != 0) {
            displayText = getResources().getStringArray(textArrayId);
            numberStyle = null;
            maxNumber = displayText.length - 1;
            minNumber = 0;
        }

        paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        checkedArray = new SparseBooleanArray(maxNumber - minNumber + 1);
        pickList = new ArrayList<>();

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                calculateClick((int) e.getX(), (int) e.getY());
                //Log.d(TAG, "onSingleTapUp: " + Arrays.deepToString(getCheckedNumber().toArray()));
                return true;
            }
        });
    }

    /**
     * 获取选中的数字；文字模式时，获取到的是参考值({@link #setNumber(int, int)})
     */
    public ArrayList<Integer> getCheckedNumber() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0, count = maxNumber - minNumber + 1; i < count; i++) {
            if (checkedArray.get(i + minNumber)) {
                list.add(i + minNumber);
            }
        }
        return list;
    }

    public int getNumberCount() {
        return maxNumber - minNumber + 1;
    }

    /**
     * 设置可以选中的数字的范围[min, max]
     */
    public NumberGroupView setNumber(int minNumber, int maxNumber) {
        if (this.minNumber == minNumber && this.maxNumber == maxNumber) {
            return this;
        }
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        checkedArray = new SparseBooleanArray(maxNumber - minNumber + 1);
        requestLayout();
        return this;
    }

    /**
     * 设置需要显示的文字集合
     **/
    public NumberGroupView setDisplayText(String[] displayText) {
        this.displayText = displayText;
        numberStyle = null;
        setNumber(0, displayText.length - 1);
        return this;
    }

    public void setItemWidth(int itemWidth) {
        if (this.itemWidth == itemWidth) {
            return;
        }
        this.itemWidth = itemWidth;
        requestLayout();
    }

    /**
     * 设置数字显示的尺寸大小，单位像素
     */
    public void setItemHeight(int itemHeight) {
        if (this.itemHeight == itemHeight) {
            return;
        }
        this.itemHeight = itemHeight;
        requestLayout();
    }

    /**
     * 设置一行显示多少个数字
     */
    public NumberGroupView setColumn(int column) {
        if (this.column == column) {
            return this;
        }
        this.column = column;
        requestLayout();
        return this;
    }

    /**
     * 设置选中的数字显示的背景图
     */
    public NumberGroupView setCheckedDrawable(Drawable checkedDrawable) {
        this.checkedDrawable = checkedDrawable;
        return this;
    }

    /**
     * 设置未选中的数字显示的背景图
     */
    public NumberGroupView setUncheckedDrawable(Drawable uncheckedDrawable) {
        this.uncheckedDrawable = uncheckedDrawable;
        return this;
    }

    /**
     * 行间垂直间隔，单位像素
     */
    public void setVerticalGap(int verticalGap) {
        if (this.verticalGap == verticalGap) {
            return;
        }
        this.verticalGap = verticalGap;
        requestLayout();
    }

    /**
     * 选择模式 true 单选模式 false 多选模式
     */
    public NumberGroupView setChooseMode(boolean chooseMode) {
        this.chooseMode = chooseMode;
        return this;
    }

    private boolean isSingle() {
        return chooseMode;
    }

    /**
     * 数字显示的风格，true 数字显示成“1”，false 数字显示成“01” null显示文字
     */
    public NumberGroupView setNumberStyle(Boolean numberStyle) {
        this.numberStyle = numberStyle;
        return this;
    }

    /**
     * 数字显示尺寸
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    /**
     * 数字显示颜色
     */
    public void setUncheckedTextColor(int uncheckedTextColor) {
        this.uncheckedTextColor = uncheckedTextColor;
    }

    public void setCheckedTextColor(int checkedTextColor) {
        this.checkedTextColor = checkedTextColor;
    }

    public OnChooseItemClickListener getChooseItemListener() {
        return chooseItemListener;
    }

    public void setChooseItemListener(OnChooseItemClickListener chooseItemListener) {
        this.chooseItemListener = chooseItemListener;
    }

    /**
     * 设置被选中的数字(如0-9)，文字模式时为参考值({@link #setNumber(int, int)})
     */
    public void setCheckNumber(ArrayList<Integer> checkNumber) {
        checkedArray.clear();
        pickList.clear();
        for (int number : checkNumber) {
            checkedArray.put(number, true);
            pickList.add(number);
            lastPick = number;
        }
        invalidate();
    }

    private void calculateClick(int eventX, int eventY) {
        int x, y;
        Rect rect = new Rect();
        for (int i = 0, count = maxNumber - minNumber + 1; i < count; i++) {
            x = i % column * (itemWidth + horizontalGap);
            y = i / column * (itemHeight + verticalGap);
            rect.set(x, y, x + itemWidth, y + itemHeight);

            if (rect.contains(eventX, eventY)) {
                if (isSingle()) {
                    Log.d(TAG, checkedArray.toString());
                    checkedArray.clear();
                }
                lastPick = i + minNumber;
                checkedArray.put(lastPick, !checkedArray.get(i + minNumber));
                if (checkedArray.get(lastPick)) {
                    pickList.add(lastPick);
                } else {
                    pickList.remove(Integer.valueOf(lastPick));
                }
                if (chooseItemListener != null) {
                    chooseItemListener.onChooseItemClick(Integer.valueOf(lastPick));
                }
                invalidate();
                return;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        horizontalGap = (specSize - column * itemWidth) / (column - 1);
        int itemCount = maxNumber - minNumber + 1;
        int line = (itemCount) / column + ((itemCount) % column != 0 ? 1 : 0);
        int specHeight = line * itemHeight + (line - 1) * verticalGap;
        setMeasuredDimension(specSize, specHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setTextSize(textSize);

        checkedDrawable.setBounds(0, 0, itemWidth, itemHeight);
        uncheckedDrawable.setBounds(0, 0, itemWidth, itemHeight);

        float x, y;
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        float offTextY = (itemHeight - fontMetrics.bottom - fontMetrics.top) / 2;
        for (int i = 0, count = maxNumber - minNumber + 1; i < count; i++) {
            x = i % column * (itemWidth + horizontalGap);
            y = i / column * (itemHeight + verticalGap);
            canvas.save();
            canvas.translate(x, y);
            if (checkedArray.get(i + minNumber)) {
                checkedDrawable.draw(canvas);
                paint.setColor(checkedTextColor);
            } else {
                uncheckedDrawable.draw(canvas);
                paint.setColor(uncheckedTextColor);
            }
            String text;
            if (numberStyle == null) {
                if (i < displayText.length)
                    text = displayText[i];
                else {
                    Log.e(TAG, "Less than the array size required to display text");
                    text = "";
                }
            } else {
                text = String.format(numberStyle ? "%d" : "%02d", i + minNumber);
            }
            float offTextX = itemWidth / 2;
            canvas.drawText(text, offTextX, offTextY, paint);
            canvas.restore();
        }
    }

    public int getMinNumber() {
        return minNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public SparseBooleanArray getCheckedArray() {
        return checkedArray;
    }

    public ArrayList<Integer> getPickList() {
        return pickList;
    }

    public int getLastPick() {
        return lastPick;
    }

    public interface OnChooseItemClickListener {
        void onChooseItemClick(int position);
    }
}
