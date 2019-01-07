package com.qishouqing.frame.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ListAdapter;

/**
 * 作者：qishouqing
 * 时间：2018/2/11 09:56
 * 邮箱：qi_shouqing@163.com
 */

public class BaseRefreshLoadmoreLayout extends ViewGroup {
    private static final int[] LAYOUT_ATTRS = new int[]{16842766};
    private int animationDuration;
    private int sucessOrFailedDuration;
    private DecelerateInterpolator mDecelerateInterpolator;
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2.0F;
    private float speedBeforeEnable;
    private float speedAfterEnable;
    private int defaultBeforeDistance;
    private int mTouchSlop;
    private View refreshView;
    private View contentView;
    private View loadmoreView;
    private int contentIndex;
    private int refreshIndex;
    private int loadmoreIndex;
    private float downY;
    private float downX;
    private float firstY;
    private boolean pullDown;
    private int mCurrentContentOffsetTop;
    private boolean isRefreshOrLoading;
    private boolean isRefreshable;
    private boolean isLoadmoreable;
    private final ToPositionForRefreshAnimation refreshAnimation;
    private final ToPositionForLoadmoreAnimation loadmoreAnimation;
    private RefreshViewListener refreshViewListener;
    private LoadmoreViewListener loadmoreViewListener;
    private OnStartListener onStartListener;

    public BaseRefreshLoadmoreLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public BaseRefreshLoadmoreLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.animationDuration = 500;
        this.sucessOrFailedDuration = 500;
        this.speedBeforeEnable = 0.6F;
        this.speedAfterEnable = 0.3F;
        this.defaultBeforeDistance = 50;
        this.contentIndex = 0;
        this.refreshIndex = 0;
        this.loadmoreIndex = 0;
        this.isRefreshOrLoading = false;
        this.isRefreshable = false;
        this.isLoadmoreable = false;
        this.refreshAnimation = new ToPositionForRefreshAnimation();
        this.loadmoreAnimation = new ToPositionForLoadmoreAnimation();
        this.init(context, attrs);
    }

    public BaseRefreshLoadmoreLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.animationDuration = 500;
        this.sucessOrFailedDuration = 500;
        this.speedBeforeEnable = 0.6F;
        this.speedAfterEnable = 0.3F;
        this.defaultBeforeDistance = 50;
        this.contentIndex = 0;
        this.refreshIndex = 0;
        this.loadmoreIndex = 0;
        this.isRefreshOrLoading = false;
        this.isRefreshable = false;
        this.isLoadmoreable = false;
        this.refreshAnimation = new ToPositionForRefreshAnimation();
        this.loadmoreAnimation = new ToPositionForLoadmoreAnimation();
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.setWillNotDraw(false);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mDecelerateInterpolator = new DecelerateInterpolator(2.0F);
        float density = this.getResources().getDisplayMetrics().density;
        this.defaultBeforeDistance = (int) ((float) this.defaultBeforeDistance * density);
        TypedArray a = context.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
        this.setEnabled(a.getBoolean(0, true));
        a.recycle();
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = this.getChildCount();
        if (this.refreshView != null) {
            --count;
        }

        if (this.loadmoreView != null) {
            --count;
        }

        if (count != 1 && !this.isInEditMode()) {
            throw new IllegalStateException("BaseRefreshLoadmoreLayout must host only one direct child");
        } else {
            this.contentView = this.getChildAt(this.contentIndex);
            if (this.refreshView != null) {
                this.measureChild(this.refreshView, widthMeasureSpec, heightMeasureSpec);
            }

            if (this.contentView != null) {
                int childWidthMeasureSpec = this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
                int childHeightMeasureSpec = this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom();
                this.contentView.measure(MeasureSpec.makeMeasureSpec(childWidthMeasureSpec, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(childHeightMeasureSpec, 1073741824));
            }
            if (this.loadmoreView != null) {
                this.measureChild(this.loadmoreView, widthMeasureSpec, heightMeasureSpec);
            }

        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.layoutRefreshView();
        this.layoutContentView();
        this.layoutLoadmoreView();
    }

    public void setVisibility(int v) {
        if (this.getVisibility() != v) {
            super.setVisibility(v);
        }

    }

    private void layoutRefreshView() {
        if (this.refreshView != null) {
            int width = this.getMeasuredWidth();
            int childHeight = this.refreshView.getMeasuredHeight();
            int childLeft = this.getPaddingLeft();
            int childTop = this.getPaddingTop() - childHeight;
            if (this.mCurrentContentOffsetTop > 0) {
                childTop += this.mCurrentContentOffsetTop;
            }

            int childWidth = width - this.getPaddingLeft() - this.getPaddingRight();
            this.refreshView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        }

    }

    private void layoutContentView() {
        if (this.contentView != null) {
            int width = this.getMeasuredWidth();
            int height = this.getMeasuredHeight();
            int childLeft = this.getPaddingLeft();
            int childTop = this.mCurrentContentOffsetTop + this.getPaddingTop();
            int childWidth = width - this.getPaddingLeft() - this.getPaddingRight();
            int childHeight = height - this.getPaddingTop() - this.getPaddingBottom();
            this.contentView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        }

    }

    private void layoutLoadmoreView() {
        if (this.loadmoreView != null) {
            int width = this.getMeasuredWidth();
            int height = this.getMeasuredHeight();
            int childLeft = this.getPaddingLeft();
            int childTop = height - this.getPaddingBottom();
            if (this.mCurrentContentOffsetTop < 0) {
                childTop += this.mCurrentContentOffsetTop;
            }

            int childWidth = width - this.getPaddingLeft() - this.getPaddingRight();
            int childHeight = this.loadmoreView.getMeasuredHeight();
            this.loadmoreView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        }

    }

    public boolean canContentScrollUp() {
        if (this.contentView == null) {
            return false;
        } else if (Build.VERSION.SDK_INT < 14) {
            if (!(this.contentView instanceof AbsListView)) {
                return this.contentView.getScrollY() > 0;
            } else {
                AbsListView absListView = (AbsListView) this.contentView;
                return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            }
        } else {
            return ViewCompat.canScrollVertically(this.contentView, -1);
        }
    }

    public boolean canContentScrollDown() {
        if (this.contentView == null) {
            return false;
        } else if (Build.VERSION.SDK_INT < 14) {
            if (this.contentView instanceof AbsListView) {
                AbsListView absListView = (AbsListView) this.contentView;
                ListAdapter adapter = (ListAdapter) absListView.getAdapter();
                boolean can = adapter != null;
                if (can) {
                    int lastP = absListView.getLastVisiblePosition();
                    int count = adapter.getCount();
                    can = lastP < count - 1;
                    if (!can) {
                        View lastView = absListView.getChildAt(absListView.getChildCount() - 1);
                        can = lastView.getBottom() > this.getMeasuredHeight() - this.getPaddingBottom();
                    }
                }

                return can;
            } else {
                View childView = null;
                if (this.contentView instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) this.contentView;
                    childView = viewGroup.getChildAt(0);
                }

                if (childView == null) {
                    return false;
                } else {
                    int height = childView.getHeight();
                    return this.contentView.getScrollY() + this.getHeight() < height;
                }
            }
        } else {
            return ViewCompat.canScrollVertically(this.contentView, 1);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.isRefreshOrLoading) {
            switch (ev.getAction()) {
                case 0:
                    this.downY = ev.getY();
                    this.downX = ev.getX();
                    this.firstY = 0.0F;
                case 1:
                default:
                    break;
                case 2:
                    float moveY = ev.getY() - this.downY;
                    float moveX = ev.getX() - this.downX;
                    float absY = Math.abs(moveY);
                    float absX = Math.abs(moveX);
                    if (absY > absX && absY > (float) this.mTouchSlop) {
                        if (moveY > 0.0F && !this.canContentScrollUp()) {
                            this.pullDown = true;
                            return true;
                        }

                        if (moveY < 0.0F && !this.canContentScrollDown()) {
                            this.pullDown = false;
                            return true;
                        }
                    }
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.isRefreshOrLoading) {
            int action = ev.getAction();
            switch (action) {
                case 0:
                    return true;
                case 1:
                case 3:
                    int move = this.contentView == null ? 0 : this.contentView.getTop();
                    if (this.pullDown) {
                        if (move >= this.getDistanceToRefresh() && this.isRefreshable) {
                            this.startRefresh();
                        } else {
                            this.animateToPositionForRefresh(0);
                        }
                    } else if (move <= -this.getDistanceToLoadmore() && this.isLoadmoreable) {
                        this.startLoadmore();
                    } else {
                        this.animateToPositionForLoadmore(0);
                    }
                    break;
                case 2:
                    this.onInterceptTouchEvent(ev);
                    if (this.firstY == 0.0F) {
                        this.firstY = ev.getY();
                    }

                    float moveY = ev.getY() - this.firstY;
                    if (this.pullDown) {
                        this.offsetRefresh((int) moveY);
                    } else {
                        this.offsetLoadmore(-((int) moveY));
                    }

                    return true;
            }
        }

        return super.onTouchEvent(ev);
    }

    private int getDistanceToRefresh() {
        return this.refreshView == null ? this.defaultBeforeDistance : this.refreshView.getMeasuredHeight();
    }

    private int getDistanceToLoadmore() {
        return this.loadmoreView == null ? this.defaultBeforeDistance : this.loadmoreView.getMeasuredHeight();
    }

    private void startRefresh() {
        this.isRefreshOrLoading = true;
        this.animateToPositionForRefresh(this.getDistanceToRefresh());
        if (this.refreshViewListener != null) {
            this.refreshViewListener.onRefresh(this.refreshView);
        }

        if (this.onStartListener != null) {
            this.onStartListener.onStartRefresh(this);
        }

    }

    public void stopRefresh() {
        this.isRefreshOrLoading = false;
        this.animateToPositionForRefresh(0);
        if (this.refreshViewListener != null) {
            this.postDelayed(new Runnable() {
                public void run() {
                    BaseRefreshLoadmoreLayout.this.refreshViewListener.onReset(BaseRefreshLoadmoreLayout.this.refreshView);
                }
            }, (long) this.animationDuration);
        }

    }

    public void refreshSuccess() {
        this.refreshViewListener.onSuccess(this.refreshView);
        this.postDelayed(new Runnable() {
            public void run() {
                BaseRefreshLoadmoreLayout.this.stopRefresh();
            }
        }, (long) this.sucessOrFailedDuration);
    }

    public void refreshFailed() {
        this.refreshViewListener.onFailed(this.refreshView);
        this.postDelayed(new Runnable() {
            public void run() {
                BaseRefreshLoadmoreLayout.this.stopRefresh();
            }
        }, (long) this.sucessOrFailedDuration);
    }

    private void startLoadmore() {
        this.isRefreshOrLoading = true;
        this.animateToPositionForLoadmore(this.getDistanceToLoadmore());
        if (this.loadmoreViewListener != null) {
            this.loadmoreViewListener.onLoadmore(this.refreshView);
        }

        if (this.onStartListener != null) {
            this.onStartListener.onStartLoadmore(this);
        }

    }

    public void stopLoadmore() {
        this.isRefreshOrLoading = false;
        this.animateToPositionForLoadmore(0);
        if (this.loadmoreViewListener != null) {
            this.postDelayed(new Runnable() {
                public void run() {
                    BaseRefreshLoadmoreLayout.this.loadmoreViewListener.onReset(BaseRefreshLoadmoreLayout.this.loadmoreView);
                }
            }, (long) this.animationDuration);
        }

    }

    public void loadmoreSuccess() {
        this.loadmoreViewListener.onSuccess(this.loadmoreView);
        this.postDelayed(new Runnable() {
            public void run() {
                BaseRefreshLoadmoreLayout.this.stopLoadmore();
            }
        }, (long) this.sucessOrFailedDuration);
    }

    public void loadmoreFailed() {
        this.loadmoreViewListener.onFailed(this.loadmoreView);
        this.postDelayed(new Runnable() {
            public void run() {
                BaseRefreshLoadmoreLayout.this.stopLoadmore();
            }
        }, (long) this.sucessOrFailedDuration);
    }

    private void animateToPositionForRefresh(int target) {
        this.refreshAnimation.reset(target);
        this.refreshAnimation.setDuration((long) this.animationDuration);
        this.refreshAnimation.setInterpolator(this.mDecelerateInterpolator);
        this.startAnimation(this.refreshAnimation);
    }

    private void animateToPositionForLoadmore(int target) {
        this.loadmoreAnimation.reset(target);
        this.loadmoreAnimation.setDuration((long) this.animationDuration);
        this.loadmoreAnimation.setInterpolator(this.mDecelerateInterpolator);
        this.startAnimation(this.loadmoreAnimation);
    }

    private void offsetRefresh(int offTop) {
        if (this.contentView != null) {
            int currentOffTopTop = this.contentView.getTop();
            int distanceToRefresh = this.getDistanceToRefresh();
            int offset;
            if (offTop < 0) {
                offTop = 0;
            } else if (currentOffTopTop <= distanceToRefresh) {
                offTop = (int) ((float) offTop * this.speedBeforeEnable);
            } else {
                offset = (int) ((float) offTop - (float) distanceToRefresh / this.speedBeforeEnable);
                offTop = (int) ((float) distanceToRefresh + (float) offset * this.speedAfterEnable);
            }

            offset = offTop - currentOffTopTop;
            if (this.contentView != null) {
                this.contentView.offsetTopAndBottom(offset);
                this.mCurrentContentOffsetTop = this.contentView.getTop();
            }

            if (this.refreshView != null) {
                this.refreshView.offsetTopAndBottom(offset);
            }

            if (this.refreshViewListener != null && !this.refreshAnimation.isRunning) {
                float percent = (float) offTop / (float) distanceToRefresh;
                this.refreshViewListener.onPulling(this.refreshView, Math.abs(percent));
            }

            this.invalidate();
        }
    }

    private int getScorllDistanceForRefresh(int offTop) {
        if (offTop < 0) {
            return 0;
        } else {
            int dis = this.getDistanceToRefresh();
            return offTop <= dis ? (int) ((float) offTop / this.speedBeforeEnable) : (int) ((float) dis / this.speedBeforeEnable + (float) (offTop - dis) / this.speedAfterEnable);
        }
    }

    private void offsetLoadmore(int offBottom) {
        if (this.contentView != null) {
            int currentOffBottom = -this.contentView.getTop();
            int distanceToLoadmore = this.getDistanceToLoadmore();
            int offset;
            if (offBottom < 0) {
                offBottom = 0;
            } else if (currentOffBottom <= distanceToLoadmore) {
                offBottom = (int) ((float) offBottom * this.speedBeforeEnable);
            } else {
                offset = (int) ((float) offBottom - (float) distanceToLoadmore / this.speedBeforeEnable);
                offBottom = (int) ((float) distanceToLoadmore + (float) offset * this.speedAfterEnable);
            }

            offset = offBottom - currentOffBottom;
            if (this.contentView != null) {
                this.contentView.offsetTopAndBottom(-offset);
                this.mCurrentContentOffsetTop = this.contentView.getTop();
            }

            if (this.loadmoreView != null) {
                this.loadmoreView.offsetTopAndBottom(-offset);
            }

            if (this.loadmoreViewListener != null && !this.loadmoreAnimation.isRunning) {
                float percent = (float) offBottom / (float) distanceToLoadmore;
                this.loadmoreViewListener.onPulling(this.loadmoreView, Math.abs(percent));
            }

            this.invalidate();
        }
    }

    private int getScorllDistanceForLoadmore(int offBottom) {
        if (offBottom < 0) {
            return 0;
        } else {
            int dis = this.getDistanceToLoadmore();
            return offBottom <= dis ? (int) ((float) offBottom / this.speedBeforeEnable) : (int) ((float) dis / this.speedBeforeEnable + (float) (offBottom - dis) / this.speedAfterEnable);
        }
    }

    public void setRefreshView(int viewLayoutId, RefreshViewListener l) {
        this.setRefreshView(viewLayoutId);
        this.setRefreshViewListener(l);
    }

    public void setRefreshView(View v, RefreshViewListener l) {
        this.setRefreshView(v);
        this.setRefreshViewListener(l);
    }

    public void setRefreshView(int viewLayoutId) {
        View v = LayoutInflater.from(this.getContext()).inflate(viewLayoutId, (ViewGroup) null);
        this.setRefreshView(v);
    }

    public void setRefreshView(View v) {
        if (!this.isInEditMode()) {
            if (this.refreshView == null) {
                if (this.contentView == null) {
                    ++this.contentIndex;
                } else {
                    ++this.refreshIndex;
                }

                if (this.loadmoreView != null) {
                    ++this.refreshIndex;
                }
            } else {
                this.removeViewAt(this.refreshIndex);
            }

            this.refreshView = v;
            this.addView(this.refreshView, this.refreshIndex);
        }
    }

    public void setLoadmoreView(int viewLayoutId, LoadmoreViewListener l) {
        this.setLoadmoreView(viewLayoutId);
        this.setLoadmoreViewListener(l);
    }

    public void setLoadmoreView(View v, LoadmoreViewListener l) {
        this.setLoadmoreView(v);
        this.setLoadmoreViewListener(l);
    }

    public void setLoadmoreView(int viewLayoutId) {
        View v = LayoutInflater.from(this.getContext()).inflate(viewLayoutId, (ViewGroup) null);
        this.setLoadmoreView(v);
    }

    public void setLoadmoreView(View v) {
        if (!this.isInEditMode()) {
            if (this.loadmoreView == null) {
                if (this.contentView == null) {
                    ++this.contentIndex;
                } else {
                    ++this.loadmoreIndex;
                }

                if (this.refreshView != null) {
                    ++this.loadmoreIndex;
                }
            } else {
                this.removeViewAt(this.loadmoreIndex);
            }

            this.loadmoreView = v;
            this.addView(this.loadmoreView, this.loadmoreIndex);
        }
    }

    public RefreshViewListener getRefreshViewListener() {
        return this.refreshViewListener;
    }

    public void setRefreshViewListener(RefreshViewListener refreshViewListener) {
        this.refreshViewListener = refreshViewListener;
    }

    public LoadmoreViewListener getLoadmoreViewListener() {
        return this.loadmoreViewListener;
    }

    public void setLoadmoreViewListener(LoadmoreViewListener loadmoreViewListener) {
        this.loadmoreViewListener = loadmoreViewListener;
    }

    public OnStartListener getOnStartListener() {
        return this.onStartListener;
    }

    public void setOnStartListener(OnStartListener onStartListener) {
        this.setRefreshable(true);
        this.setLoadmoreable(true);
        this.onStartListener = onStartListener;
    }

    public boolean isRefreshable() {
        return this.isRefreshable;
    }

    public void setRefreshable(boolean isRefreshable) {
        this.isRefreshable = isRefreshable;
        if (this.refreshView != null) {
            this.refreshView.setVisibility(isRefreshable ? VISIBLE : INVISIBLE);
        }

    }

    public boolean isLoadmoreable() {
        return this.isLoadmoreable;
    }

    public void setLoadmoreable(boolean isLoadmoreable) {
        this.isLoadmoreable = isLoadmoreable;
        if (this.loadmoreView != null) {
            this.loadmoreView.setVisibility(isLoadmoreable ? VISIBLE : INVISIBLE);
        }

    }

    public void setSpeedBefore(float speedBeforeEnable) {
        if (speedBeforeEnable < 0.0F || speedBeforeEnable > 1.0F) {
            speedBeforeEnable = 0.6F;
        }

        this.speedBeforeEnable = speedBeforeEnable;
    }

    public void setSpeedAfterEnable(float speedAfterEnable) {
        if (speedAfterEnable < 0.0F || speedAfterEnable > 1.0F) {
            speedAfterEnable = 0.3F;
        }

        this.speedAfterEnable = speedAfterEnable;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    public void setSucessOrFailedDuration(int sucessOrFailedDuration) {
        this.sucessOrFailedDuration = sucessOrFailedDuration;
    }

    public interface LoadmoreViewListener {
        void onPulling(View var1, float var2);

        void onReset(View var1);

        void onLoadmore(View var1);

        void onSuccess(View var1);

        void onFailed(View var1);
    }

    public interface OnStartListener {
        void onStartRefresh(BaseRefreshLoadmoreLayout var1);

        void onStartLoadmore(BaseRefreshLoadmoreLayout var1);
    }

    public interface RefreshViewListener {
        void onPulling(View var1, float var2);

        void onReset(View var1);

        void onRefresh(View var1);

        void onSuccess(View var1);

        void onFailed(View var1);
    }

    private class ToPositionForLoadmoreAnimation extends Animation {
        private int from;
        private int target;
        private boolean isRunning;
        private AnimationListener listener;

        private ToPositionForLoadmoreAnimation() {
            this.isRunning = false;
            this.listener = new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    ToPositionForLoadmoreAnimation.this.isRunning = true;
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    ToPositionForLoadmoreAnimation.this.isRunning = false;
                }
            };
        }

        public void applyTransformation(float interpolatedTime, Transformation t) {
            if (this.isRunning) {
                int offset = (int) ((float) this.target + (float) (this.from - this.target) * (1.0F - interpolatedTime));
                BaseRefreshLoadmoreLayout.this.offsetLoadmore(offset);
                if (offset <= this.target) {
                    this.isRunning = false;
                }
            }

        }

        public void reset(int target) {
            this.reset();
            this.target = BaseRefreshLoadmoreLayout.this.getScorllDistanceForLoadmore(target);
            this.from = BaseRefreshLoadmoreLayout.this.getScorllDistanceForLoadmore(-BaseRefreshLoadmoreLayout.this.mCurrentContentOffsetTop);
            this.setAnimationListener(this.listener);
        }
    }

    private class ToPositionForRefreshAnimation extends Animation {
        private int from;
        private int target;
        private boolean isRunning;
        private AnimationListener listener;

        private ToPositionForRefreshAnimation() {
            this.isRunning = false;
            this.listener = new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    ToPositionForRefreshAnimation.this.isRunning = true;
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    ToPositionForRefreshAnimation.this.isRunning = false;
                }
            };
        }

        public void applyTransformation(float interpolatedTime, Transformation t) {
            if (this.isRunning) {
                int offset = (int) ((float) this.target + (float) (this.from - this.target) * (1.0F - interpolatedTime));
                BaseRefreshLoadmoreLayout.this.offsetRefresh(offset);
                if (offset <= this.target) {
                    this.isRunning = false;
                }
            }

        }

        public void reset(int target) {
            this.reset();
            this.target = BaseRefreshLoadmoreLayout.this.getScorllDistanceForRefresh(target);
            this.from = BaseRefreshLoadmoreLayout.this.getScorllDistanceForRefresh(BaseRefreshLoadmoreLayout.this.mCurrentContentOffsetTop);
            this.setAnimationListener(this.listener);
        }
    }
}
