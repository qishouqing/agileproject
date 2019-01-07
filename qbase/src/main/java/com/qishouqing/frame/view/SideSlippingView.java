package com.qishouqing.frame.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 侧拉控件（如侧拉删除、已读等）
 */
public class SideSlippingView extends FrameLayout {
    public SideSlippingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private View contentView, deleteView;
    private int contentViewWidth, deleteViewHeight, deleteViewWidth;

    private ViewDragHelper viewDragHelper;

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, callback);
    }

    /**
     * 从xml中加载完布局执行，只知道有几个子view，但不知到其宽高，即没有对他们进行测量
     * 一般在这里初始化子view的引用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        deleteView = getChildAt(1);
    }

    /**
     * 测量完子view后调用，在这里直接获取子view宽高
     * getMeasuredWidth() 只要在onMeasure方法执行完毕后，调用该方法都能获取到view的宽
     * getWidth() 必须在onLayout方法调用完毕后，才可以获取
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        contentViewWidth = contentView.getMeasuredWidth();
        deleteViewHeight = deleteView.getMeasuredHeight();
        deleteViewWidth = deleteView.getMeasuredWidth();
    }

    /**
     * 重写布局
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        contentView.layout(0, 0, contentViewWidth, deleteViewHeight);
        deleteView.layout(contentViewWidth, 0, contentViewWidth
                + deleteViewWidth, deleteViewHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    private int lastX, lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                int deltaY = y - lastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    //是滑动删除，拦截该事件，不让listview处理
                    requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        lastX = x;
        lastY = y;
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        /**
         * 触摸时调用，是否捕捉点击的哪个view
         */
        @Override
        public boolean tryCaptureView(View child, int id) {
            return contentView == child || deleteView == child;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return deleteViewWidth;
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        /**
         * 控制view实际滑动了多少
         * 返回值：想让child的left等于多少，
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == contentView) { // 滑动的是content的时候
                // 非法值判断
                if (left > 0) {
                    left = 0;
                }
                if (left < -deleteViewWidth) {
                    left = -deleteViewWidth;
                }
            } else { // 滑动的是delete的时候
                if (left < (contentViewWidth - deleteViewWidth)) {
                    left = contentViewWidth - deleteViewWidth;
                }
                if (left > contentViewWidth) {
                    left = contentViewWidth;
                }
            }

            return left;
        }

        /**
         * view滑动后的回调
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == contentView) {
                deleteView.layout(deleteView.getLeft() + dx, 0,
                        deleteView.getRight() + dx, deleteView.getBottom());
            } else {
                contentView.layout(contentView.getLeft() + dx, 0,
                        contentView.getRight() + dx, contentView.getBottom());
            }

            if (contentView.getLeft() == 0 && mStatus != SwipeStatus.Close) {
                mStatus = SwipeStatus.Close;
                if (onSwipeStatusChangeListener != null) {
                    onSwipeStatusChangeListener.onClose(SideSlippingView.this);
                }
            } else if (contentView.getLeft() == -deleteViewWidth
                    && mStatus != SwipeStatus.Open) {
                mStatus = SwipeStatus.Open;
                if (onSwipeStatusChangeListener != null) {
                    onSwipeStatusChangeListener.onOpen(SideSlippingView.this);
                }
            } else if (mStatus != SwipeStatus.Swiping) {
                mStatus = SwipeStatus.Swiping;
                if (onSwipeStatusChangeListener != null) {
                    onSwipeStatusChangeListener.onSwiping(SideSlippingView.this);
                }
            }
        }

        /**
         * touch up的回调
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (contentView.getLeft() < -deleteViewWidth / 2) {
                open();
            } else {
                close();
            }
        }
    };

    public void open() {
        viewDragHelper.smoothSlideViewTo(contentView, -deleteViewWidth, 0);
        ViewCompat.postInvalidateOnAnimation(SideSlippingView.this);
    }

    public void close() {
        viewDragHelper.smoothSlideViewTo(contentView, 0, 0);
        ViewCompat.postInvalidateOnAnimation(SideSlippingView.this);
    }

    public void fastClose() {
        contentView.layout(0, 0, contentViewWidth, deleteViewHeight);
        deleteView.layout(contentViewWidth, 0, contentViewWidth + deleteViewWidth, deleteViewHeight);
        mStatus = SwipeStatus.Close;
        if (onSwipeStatusChangeListener != null) {
            onSwipeStatusChangeListener.onClose(SideSlippingView.this);
        }
    }

    public void computeScroll() {
        // 动画为结束
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(SideSlippingView.this);
        }
    }


    private SwipeStatus mStatus = SwipeStatus.Close;

    public SwipeStatus getCurrentStatuse() {
        return mStatus;
    }

    public enum SwipeStatus {
        Open, Close, Swiping
    }

    private OnSwipeStatusChangeListener onSwipeStatusChangeListener;

    public OnSwipeStatusChangeListener getOnSwipeStatusChangeListener() {
        return onSwipeStatusChangeListener;
    }

    public void setOnSwipeStatusChangeListener(
            OnSwipeStatusChangeListener onSwipeStatusChangeListener) {
        this.onSwipeStatusChangeListener = onSwipeStatusChangeListener;
    }

    public interface OnSwipeStatusChangeListener {
        void onOpen(SideSlippingView openSwipeView);

        void onClose(SideSlippingView onCloseSwipeView);

        void onSwiping(SideSlippingView onSwipingSwipeView);
    }
}
