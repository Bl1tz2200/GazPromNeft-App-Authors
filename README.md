Нашёл простое AR приложение с рендером 3D модели <br>
<br>
Для работы необходимо добавить следующую зависимость в файл build.gradle на уровне проекта:<br>
<br>
dependencies {
    classpath 'com.google.ar.sceneform:plugin:1.5.0'
}
<br>
А эту зависимость добавьте в файл build.gradle на уровня приложения:<br>
implementation "com.google.ar.sceneform.ux:sceneform-ux:1.5.0"<br>
<br>
Ссылка на источник: https://habr.com/ru/articles/438178/<br>
