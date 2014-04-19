package com.binaryme.blockStackStructure;

import android.content.Context;
import android.view.View;

public abstract class VisualRepresentation extends View {

	public VisualRepresentation(Context context) {
		super(context);
		this.setId(mUtils.generateUniqueId()); 
	}

}
