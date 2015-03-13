package com.nornenjs.android;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by pi on 15. 3. 13.
 */

public class Shape {

    protected FloatBuffer getFloatBufferFromFloatArray(float array[]) {
        ByteBuffer tempBuffer = ByteBuffer.allocateDirect(array.length * 4);
        tempBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = tempBuffer.asFloatBuffer();
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }

    protected ShortBuffer getByteBufferFromByteArray(short[] index) {
        ByteBuffer b = ByteBuffer.allocateDirect(index.length * 2);
        b.order(ByteOrder.nativeOrder());
        ShortBuffer buffer = b.asShortBuffer();
        buffer.put(index);
        buffer.position(0);
        return buffer;
    }

    protected FloatBuffer getFloatBufferFromTextureArray(float texture[]) {
        ByteBuffer tbb = ByteBuffer.allocateDirect(texture.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = tbb.asFloatBuffer();
        buffer.put(texture);
        buffer.position(0);
        return buffer;
    }
}