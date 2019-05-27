package com.backsofangels.justreadit.ui.linkhistoryfragment;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import static android.support.v7.widget.helper.ItemTouchHelper.*;

import javax.annotation.Nonnull;

public class LinkHistorySwipeController extends Callback {
    private boolean swipeBack = false;
    private LinkHistoryButtonState buttonState = LinkHistoryButtonState.GONE;
    private static final float BUTTON_WIDTH = 300;
    private RectF buttonInstance = null;
    private RecyclerView.ViewHolder currentItemHolder = null;
    private SwipeControllerActions actions;

    LinkHistorySwipeController(SwipeControllerActions swipeControllerActions) {
        this.actions = swipeControllerActions;
    }

    @Override
    public int getMovementFlags(@Nonnull RecyclerView rv, @Nonnull RecyclerView.ViewHolder holder) {
        return makeMovementFlags(0, LEFT);
    }

    @Override
    public boolean onMove(@Nonnull RecyclerView rv, @Nonnull RecyclerView.ViewHolder holder, @Nonnull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@Nonnull RecyclerView.ViewHolder holder, int direction) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = buttonState != LinkHistoryButtonState.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(@Nonnull Canvas c, @Nonnull RecyclerView recyclerView, @Nonnull RecyclerView.ViewHolder holder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonState != LinkHistoryButtonState.GONE) {
                if (buttonState == LinkHistoryButtonState.RIGHT_VISIBLE) dX = Math.min(dX, -BUTTON_WIDTH);
                super.onChildDraw(c, recyclerView, holder, dX, dY, actionState, isCurrentlyActive);
            } else {
                setTouchListener(c, recyclerView, holder, dX, dY, actionState, isCurrentlyActive);
            }
        }
        if (buttonState == LinkHistoryButtonState.GONE) {
            super.onChildDraw(c, recyclerView, holder, dX, dY, actionState, isCurrentlyActive);
        }
        currentItemHolder = holder;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder holder,
                                  final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
                if (swipeBack) {
                    if (dX < -BUTTON_WIDTH) buttonState = LinkHistoryButtonState.RIGHT_VISIBLE;
                    else if (dX > BUTTON_WIDTH) buttonState = LinkHistoryButtonState.LEFT_VISIBLE;
                    if (buttonState != LinkHistoryButtonState.GONE) {
                        setTouchDownListener(c, recyclerView, holder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder holder,
                                      final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, holder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder holder,
                                    final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    LinkHistorySwipeController.super.onChildDraw(c, recyclerView, holder, 0F, dY, actionState, isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    setItemsClickable(recyclerView, true);
                    swipeBack = false;

                    if (actions != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())) {
                        if (buttonState == LinkHistoryButtonState.LEFT_VISIBLE) {
                            actions.onLeftClicked(holder.getAdapterPosition());
                        } else if (buttonState == LinkHistoryButtonState.RIGHT_VISIBLE) {
                            actions.onRightClicked(holder.getAdapterPosition());
                        }
                    }
                    buttonState = LinkHistoryButtonState.GONE;
                    currentItemHolder = null;
                }
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder holder) {
        float buttonWidthWithoutPadding = BUTTON_WIDTH - 20;
        float corners = 8;
        View itemView = holder.itemView;
        Paint p = new Paint();

        RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithoutPadding, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        p.setColor(Color.parseColor("#bd1a1a"));
        c.drawRoundRect(rightButton, corners, corners, p);
        drawText("Delete", c, rightButton, p);

        buttonInstance = null;
        if (buttonState == LinkHistoryButtonState.RIGHT_VISIBLE) {
            buttonInstance = rightButton;
        }
    }

    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 60;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize/2), p);
    }

    void onDraw(Canvas c) {
        if (currentItemHolder != null) {
            drawButtons(c, currentItemHolder);
        }
    }
}
