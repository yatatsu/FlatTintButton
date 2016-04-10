package com.yatatsu.flattintbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


public class FlatTintButton extends AppCompatButton {

  public FlatTintButton(Context context) {
    this(context, null);
  }

  public FlatTintButton(Context context, AttributeSet attrs) {
    this(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
  }

  public FlatTintButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr);
  }

  private void init(Context context, AttributeSet attrs, int defStyle) {
    final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlatTintButton, defStyle, 0);
    int tintColor = a.getColor(R.styleable.FlatTintButton_flat_tint_background, 0);
    int layerColor = a.getColor(R.styleable.FlatTintButton_flat_tint_layer,
        ContextCompat.getColor(context, R.color.layer_default));
    // TODO radius
    a.recycle();

    StateListDrawable layeredStateDrawable = new StateListDrawable();
    GradientDrawable tintBackground = new GradientDrawable();
    tintBackground.setColor(tintColor);
    GradientDrawable layer = new GradientDrawable();
    layer.setColor(layerColor);

    LayerDrawable pressedOrFocused = new LayerDrawable(new Drawable[]{ tintBackground, layer });

    layeredStateDrawable.addState(new int[]{ android.R.attr.state_pressed }, pressedOrFocused);
    layeredStateDrawable.addState(new int[]{ android.R.attr.state_focused }, pressedOrFocused);
    // TODO disabled
    layeredStateDrawable.addState(new int[]{}, tintBackground);

    setBackgroundCompat(layeredStateDrawable);
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
