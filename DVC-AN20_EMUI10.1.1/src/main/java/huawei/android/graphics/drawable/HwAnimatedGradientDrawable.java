package huawei.android.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.animation.PathInterpolator;

public class HwAnimatedGradientDrawable extends GradientDrawable {
    private static final long ANIM_DURATION = 100;
    private static final float DECISION = 1.0E-7f;
    private static final int DEFAULT_COLOR = 201326592;
    private static final float DEFAULT_CORNER_RADIUS = 12.0f;
    private static final float DEFAULT_CORNER_RADIUS_IN_DP = 4.0f;
    private static final float DEFAULT_RECT_ALPHA_BIG = 1.0f;
    private static final float DEFAULT_RECT_ALPHA_SMALL = 0.0f;
    private static final float DEFAULT_RECT_SCALE_BIG = 1.0f;
    private static final float DEFAULT_RECT_SCALE_SMALL = 0.9f;
    private static final float FLOAT_TWO_DIV = 2.0f;
    private static final int MAX_ALPHA_INT = 255;
    private static final TimeInterpolator STANDARD_INTERPOLATOR = new PathInterpolator(0.2f, DEFAULT_RECT_ALPHA_SMALL, 0.4f, 1.0f);
    private static final String TAG = "HwAnimatedGradientDrawable";
    private Animator mEnterAnim;
    private Animator mExitAnim;
    private boolean mIsEffectActive;
    private boolean mIsForceDoScaleAnim;
    private float mMaxRectAlpha;
    private float mMaxRectScale;
    private float mMinRectScale;
    private float mRectAlpha;
    private float mRectScale;

    public HwAnimatedGradientDrawable() {
        this((int) DEFAULT_COLOR, 1.0f, (float) DEFAULT_CORNER_RADIUS);
    }

    public HwAnimatedGradientDrawable(int color, float maxAlpha, float cornerRadius) {
        init(color, maxAlpha, cornerRadius);
    }

    public HwAnimatedGradientDrawable(Context context) {
        this((int) DEFAULT_COLOR, 1.0f, context);
    }

    public HwAnimatedGradientDrawable(int color, float maxAlpha, Context context) {
        this(color, maxAlpha, DEFAULT_CORNER_RADIUS_IN_DP, context);
    }

    public HwAnimatedGradientDrawable(int color, float maxAlpha, float cornerRadiusInDp, Context context) {
        if (context != null) {
            init(color, maxAlpha, cornerRadiusInDp * context.getResources().getDisplayMetrics().density);
        } else {
            init(color, maxAlpha, DEFAULT_CORNER_RADIUS);
        }
    }

    private void init(int color, float maxAlpha, float cornerRadius) {
        setShape(0);
        setColor(color);
        setCornerRadius(cornerRadius);
        this.mIsEffectActive = false;
        this.mMaxRectAlpha = maxAlpha;
        this.mRectAlpha = DEFAULT_RECT_ALPHA_SMALL;
        this.mMaxRectScale = 1.0f;
        this.mMinRectScale = DEFAULT_RECT_SCALE_SMALL;
        this.mIsForceDoScaleAnim = false;
    }

    public void setMaxRectAlpha(float maxRectAlpha) {
        if (maxRectAlpha < DEFAULT_RECT_ALPHA_SMALL || maxRectAlpha > 1.0f) {
            Log.w(TAG, "illegal params: maxRectAlpha = " + maxRectAlpha);
            return;
        }
        this.mMaxRectAlpha = maxRectAlpha;
    }

    public float getMaxRectAlpha() {
        return this.mMaxRectAlpha;
    }

    public void setMaxRectScale(float maxRectScale) {
        if (maxRectScale < DEFAULT_RECT_ALPHA_SMALL || maxRectScale > 1.0f) {
            Log.w(TAG, "illegal params: maxRectScale = " + maxRectScale);
            return;
        }
        this.mMaxRectScale = maxRectScale;
    }

    public float getMaxRectScale() {
        return this.mMaxRectScale;
    }

    public void setMinRectScale(float minRectScale) {
        if (minRectScale < DEFAULT_RECT_ALPHA_SMALL || minRectScale > 1.0f) {
            Log.w(TAG, "illegal params: minRectScale = " + minRectScale);
            return;
        }
        this.mMinRectScale = minRectScale;
    }

    public float getMinRectScale() {
        return this.mMinRectScale;
    }

    public void setRectAlpha(float rectAlpha) {
        if (rectAlpha < DEFAULT_RECT_ALPHA_SMALL || rectAlpha > 1.0f) {
            Log.w(TAG, "illegal params: rectAlpha = " + rectAlpha);
            return;
        }
        this.mRectAlpha = rectAlpha;
        invalidateSelf();
    }

    public float getRectAlpha() {
        return this.mRectAlpha;
    }

    public void setRectScale(float rectScale) {
        if (rectScale < DEFAULT_RECT_ALPHA_SMALL || rectScale > 1.0f) {
            Log.w(TAG, "illegal params: rectScale = " + rectScale);
            return;
        }
        this.mRectScale = rectScale;
        invalidateSelf();
    }

    public float getRectScale() {
        return this.mRectScale;
    }

    public void setForceDoScaleAnim(boolean isForceDoScaleAnim) {
        this.mIsForceDoScaleAnim = isForceDoScaleAnim;
    }

    public boolean isForceDoScaleAnim() {
        return this.mIsForceDoScaleAnim;
    }

    public void draw(Canvas canvas) {
        float alpha = this.mRectAlpha;
        if (alpha >= DECISION) {
            float scale = this.mRectScale;
            setAlpha((int) (255.0f * alpha));
            canvas.save();
            canvas.scale(scale, scale, ((float) canvas.getWidth()) / FLOAT_TWO_DIV, ((float) canvas.getHeight()) / FLOAT_TWO_DIV);
            super.draw(canvas);
            canvas.restore();
        }
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] stateSet) {
        boolean isEnabled = false;
        boolean z = false;
        boolean isPressed = false;
        for (int state : stateSet) {
            if (state == 16842910) {
                isEnabled = true;
            } else if (state == 16842919) {
                isPressed = true;
            }
        }
        if (isEnabled && isPressed) {
            z = true;
        }
        setEffectActive(z);
        return true;
    }

    private void setEffectActive(boolean isActive) {
        if (this.mIsEffectActive != isActive) {
            this.mIsEffectActive = isActive;
            if (isActive) {
                Animator animator = this.mEnterAnim;
                if (animator == null || !animator.isRunning()) {
                    Animator animator2 = this.mExitAnim;
                    if (animator2 != null && animator2.isRunning()) {
                        this.mExitAnim.cancel();
                    }
                    startEnterAnim();
                    return;
                }
                return;
            }
            Animator animator3 = this.mExitAnim;
            if (animator3 == null || !animator3.isRunning()) {
                Animator animator4 = this.mEnterAnim;
                if (animator4 != null && animator4.isRunning()) {
                    this.mEnterAnim.cancel();
                }
                startExitAnim();
            }
        }
    }

    public boolean setVisible(boolean isVisible, boolean isRestart) {
        boolean isChanged = super.setVisible(isVisible, isRestart);
        if (!isVisible) {
            clearEffect();
        } else if (isChanged) {
            if (this.mIsEffectActive) {
                this.mRectAlpha = this.mMaxRectAlpha;
                this.mRectScale = this.mMaxRectScale;
            } else {
                this.mRectAlpha = DEFAULT_RECT_ALPHA_SMALL;
            }
        }
        return isChanged;
    }

    private void clearEffect() {
        Animator animator = this.mEnterAnim;
        if (animator != null && animator.isRunning()) {
            this.mEnterAnim.end();
        }
        Animator animator2 = this.mExitAnim;
        if (animator2 != null && animator2.isRunning()) {
            this.mExitAnim.end();
        }
        this.mEnterAnim = null;
        this.mExitAnim = null;
        this.mIsEffectActive = false;
        this.mRectAlpha = DEFAULT_RECT_ALPHA_SMALL;
        invalidateSelf();
    }

    private void startEnterAnim() {
        Animator scaleAnimator;
        AnimatorSet animatorSet = new AnimatorSet();
        Animator alphaAnimator = ObjectAnimator.ofFloat(this, "rectAlpha", this.mMaxRectAlpha);
        alphaAnimator.setDuration(ANIM_DURATION);
        alphaAnimator.setInterpolator(STANDARD_INTERPOLATOR);
        if (getCornerRadius() > DEFAULT_RECT_ALPHA_SMALL || this.mIsForceDoScaleAnim) {
            if (getRectAlpha() < DECISION) {
                scaleAnimator = ObjectAnimator.ofFloat(this, "rectScale", this.mMinRectScale, this.mMaxRectScale);
            } else {
                scaleAnimator = ObjectAnimator.ofFloat(this, "rectScale", this.mMaxRectScale);
            }
            scaleAnimator.setDuration(ANIM_DURATION);
            scaleAnimator.setInterpolator(STANDARD_INTERPOLATOR);
            animatorSet.playTogether(alphaAnimator, scaleAnimator);
        } else {
            setRectScale(1.0f);
            animatorSet.play(alphaAnimator);
        }
        this.mEnterAnim = animatorSet;
        this.mEnterAnim.start();
    }

    private void startExitAnim() {
        AnimatorSet animatorSet = new AnimatorSet();
        Animator alphaAnimator = ObjectAnimator.ofFloat(this, "rectAlpha", 0.0f);
        alphaAnimator.setDuration(ANIM_DURATION);
        alphaAnimator.setInterpolator(STANDARD_INTERPOLATOR);
        animatorSet.playTogether(alphaAnimator);
        this.mExitAnim = animatorSet;
        this.mExitAnim.start();
    }

    public void getOutline(Outline outline) {
        outline.setRect(getBounds());
        outline.setAlpha(DEFAULT_RECT_ALPHA_SMALL);
    }

    public boolean isStateful() {
        return true;
    }
}
