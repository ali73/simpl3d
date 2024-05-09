package com.example.arcore.samplerender

import android.content.Context
import com.google.ar.core.Session

class ARSession: Session {

    constructor(context: Context): super(context)
    constructor(context: Context, features: Set<Feature>): super(context, features)
}