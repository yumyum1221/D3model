package com.example.d3model;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.example.d3model.FbxManager;
import com.example.d3model.FbxNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;



class FBXRenderer implements GLSurfaceView.Renderer {
    private Context context;
    private int textureID;
    private int width;
    private int height;
    private FbxManager fbxManager;
    private FbxNode fbxNode;
    private List<Float> vertices = new ArrayList<Float>();
    private List<Short> indices = new ArrayList<Short>();

    public MyRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // OpenGL 초기화
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        // FBX 파일 로드
        fbxManager = FbxManager.getInstance();
        try {
            InputStream is = context.getAssets().open("my_fbx_file.fbx");
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            fbxNode = fbxManager.load(bytes, 0, bytes.length, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 버텍스와 인덱스 데이터 추출
        fbxNode.extractTriangles(vertices, indices);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 뷰 크기 변경 처리
        this.width = width;
        this.height = height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity


    }
