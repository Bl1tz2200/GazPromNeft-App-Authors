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
