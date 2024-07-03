package com.ayusch.arcorefirst;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    ArFragment arFragment; // задаем AR фрагмент из xml файла
    ModelRenderable lampPostRenderable; // задаем 3D модель

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkIsSupportedDeviceOrFinish(this)) { // Проверяем совместимость телефона через функцию
            return;
        }
        setContentView(R.layout.activity_main);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("LampPost.sfb")) // УКАЗЫВАЕМ ТУТ СВОЮ 3D МОДЕЛЬ (вместо LampPost.sfb)
                .build()
                .thenAccept(renderable -> lampPostRenderable = renderable)
                .exceptionally(throwable -> {
                    Toast toast =
                            Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return null;
                });

            arFragment.setOnTapArPlaneListener(
                    (HitResult hitresult, Plane plane, MotionEvent motionevent) -> {
                        if (lampPostRenderable == null){
                            return;
                        }

                        Anchor anchor = hitresult.createAnchor();
                        AnchorNode anchorNode = new AnchorNode(anchor);
                        anchorNode.setParent(arFragment.getArSceneView().getScene());

                        TransformableNode lamp = new TransformableNode(arFragment.getTransformationSystem());
                        lamp.setParent(anchorNode);
                        lamp.setRenderable(lampPostRenderable);
                        lamp.select();
                    }
            );

    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) { // Функция проверяет телефон на совместимость с AR приложением, если телефон не подходит, то приложение не запустится
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Необходим Android API 27 уровня 27 или выше");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false; // Если Android API <27 уровня, возвращаем false
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Необходим OpenGL ES версии 3.0 или выше");
            Toast.makeText(activity, "Необходим OpenGL ES версии 3.0 или выше", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false; // Если OpenGL ES ниже версии 3.0, возвращаем false
        }
        return true; // Если телефон совместим с приложением, возвращаем  true
    }
}
