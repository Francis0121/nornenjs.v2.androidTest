package com.nornenjs.android;

import java.nio.*;
import javax.microedition.khronos.opengles.*;
import android.graphics.*;
import android.opengl.*;
import android.util.Log;

public class Quad extends Shape {

    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;
    private FloatBuffer textureBuffer;

    private int[] textureName;

    private float[] vertices = {
            -0.8f	, 0.8f	, 0.0f, // 0, Left Top
            0.8f	, 0.8f	, 0.0f,	// 1, Right Top
            0.8f	, -0.8f	, 0.0f,	// 2, Right Bottom
            -0.8f	, -0.8f	, 0.0f	// 3, Left Bottom
    };

    private short[] index = {
            0, 1, 2,
            0, 2, 3
    };

    private float[] texture = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    public Quad() {
        textureName = new int[1];

        vertexBuffer = getFloatBufferFromFloatArray(vertices);
        indexBuffer = getByteBufferFromByteArray(index);
        textureBuffer = getFloatBufferFromTextureArray(texture);
    }

    /* Draw */
    public void draw(GL10 gl) {

        gl.glFrontFace(GL10.GL_CW);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, index.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

    /* Initialize Texture */
    public void InitTexture(GL10 gl, Bitmap bitmap) {

        if(bitmap == null ){
            Log.e("DEBUG", "setGLTexture : Bitmap is null!!!!!!!!!!");
            return;
        }

        gl.glGenTextures(1, textureName, 0);					// 텍스쳐 포인터 설정
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureName[0]);	// 텍스쳐 사용 연결

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
//
//        bitmap.recycle();
//        bitmap = null;
    }

    public void SetTexture(GL10 gl, Bitmap bitmap) {
        if(bitmap == null ){
            Log.e("DEBUG", "setGLTexture : Bitmap is null!!!!!!!!!!");
            return;
        }
        
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureName[0]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

//        bitmap.recycle();
//        bitmap = null;
    }
}