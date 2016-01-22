package com.project.indotuber.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dss-13 on 12/8/15.
 */
public class MontserratBoldTextView extends TextView {
    public MontserratBoldTextView(Context ctx, AttributeSet attributeSet){
        super(ctx,attributeSet);
        this.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "fonts/Montserrat-Bold.ttf"));
    }
}
