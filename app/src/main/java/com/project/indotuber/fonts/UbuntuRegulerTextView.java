package com.project.indotuber.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by yoasfs on 12/8/15.
 */
public class UbuntuRegulerTextView extends TextView {
    public UbuntuRegulerTextView(Context ctx, AttributeSet attributeSet) {
        super(ctx, attributeSet);
        this.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "fonts/Ubuntu-Regular.ttf"));
    }
}
