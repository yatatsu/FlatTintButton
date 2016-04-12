package com.yatatsu.flattintbutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


public class FlatTintButton extends AppCompatButton {

  public FlatTintButton(Context context) {
    this(context, null);
  }

  public FlatTintButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0);
  }

  public FlatTintButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr);
  }

  private void init(Context context, AttributeSet attrs, int defStyle) {
    final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlatTintButton,
        defStyle, R.style.FlatTintButton);
    ColorStateList tint = a.getColorStateList(R.styleable.FlatTintButton_ftb_backgroundTint);
    int layerColor = a.getColor(R.styleable.FlatTintButton_ftb_layerTint, 0);
    float radius = a.getDimension(R.styleable.FlatTintButton_ftb_buttonRadius, 0);
    a.recycle();
    if (tint != null) {
      int normalTint = tint.getColorForState(new int[]{ android.R.attr.state_enabled }, 0);
      int disabledTint = tint.getColorForState(new int[]{ -android.R.attr.state_enabled }, 0);
      StateListDrawable layeredStateDrawable = new StateListDrawable();
      // normal
      GradientDrawable normalTintDrawable = new GradientDrawable();
      normalTintDrawable.setColor(normalTint);
      normalTintDrawable.setCornerRadius(radius);
      // disabled
      GradientDrawable disabledDrawable = new GradientDrawable();
      disabledDrawable.setColor(disabledTint);
      disabledDrawable.setCornerRadius(radius);
      // layer
      GradientDrawable layerDrawable = new GradientDrawable();
      layerDrawable.setColor(layerColor);
      layerDrawable.setCornerRadius(radius);

      LayerDrawable pressedOrFocused = new LayerDrawable(new Drawable[]{
          normalTintDrawable, layerDrawable });

      layeredStateDrawable.addState(new int[]{ android.R.attr.state_pressed }, pressedOrFocused);
      layeredStateDrawable.addState(new int[]{ android.R.attr.state_focused }, pressedOrFocused);
      layeredStateDrawable.addState(new int[]{ -android.R.attr.state_enabled }, disabledDrawable);
      layeredStateDrawable.addState(new int[]{}, normalTintDrawable);

      setBackgroundCompat(layeredStateDrawable);
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      setStateListAnimator(null);
    }
  }

  void setBackgroundCompat(Drawable drawable) {
    int paddingTop = getPaddingTop();
    int paddingBottom = getPaddingBottom();
    int paddingLeft = getPaddingLeft();
    int paddingRight = getPaddingRight();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      setBackground(drawable);
    } else {
      setBackgroundDrawable(drawable);
    }
    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
  }
}
