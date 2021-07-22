package com.tia.ecommerce.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.tia.ecommerce.R;

public abstract class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback{
    private Drawable deleteIcon ;
    private int intrinsicHeight;
    private int intrinsicWidth;
    private Context context;
    private Paint clearPaint = new Paint();
    private ColorDrawable background = new ColorDrawable();
    private int backgroundColor = Color.parseColor("#f44336");

    public SwipeToDeleteCallback(Context context){
        super(0, ItemTouchHelper.RIGHT);
        this.context = context;
        this.clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_vector_white_delete);
        intrinsicHeight = deleteIcon.getIntrinsicHeight();
        intrinsicWidth = deleteIcon.getIntrinsicWidth();

    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if(viewHolder.getAdapterPosition() == 10) return 0;
        return super.getMovementFlags(recyclerView, viewHolder);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getBottom() - itemView.getTop();
        boolean isCanceled = dX == 0f && !isCurrentlyActive;
        if(isCanceled){
            clearCanvas(c, itemView.getLeft() + dX, (float)itemView.getTop(), (float)itemView.getLeft(), (float)itemView.getBottom());
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }
        background.setColor(backgroundColor);
        background.setBounds(Math.round(itemView.getRight() + (int)dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
        background.draw(c);

        int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
        int deleteIconMargin = (itemHeight - intrinsicHeight);
        int deleteIconLeft = itemView.getRight() + deleteIconMargin - intrinsicWidth;
        int deleteIconRight = itemView.getRight() + deleteIconMargin;
        int deleteIconBottom = deleteIconTop + intrinsicHeight;

        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
        deleteIcon.draw(c);
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void clearCanvas(Canvas c, float left, float top, float right, float bottom){
        c.drawRect(left, top, right, bottom, clearPaint);
    }
}
