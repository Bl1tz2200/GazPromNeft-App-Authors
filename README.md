Нашёл простое AR приложение с рендером 3D модели <br>
<br>
Для работы необходимо добавить следующую зависимость в файл build.gradle на уровне проекта:<br>
<br>
dependencies {<br>
&emsp;&emsp;&emsp;classpath 'com.google.ar.sceneform:plugin:1.5.0'<br>
}<br>
<br>
А эту зависимость добавить в файл build.gradle на уровня приложения:<br>
&emsp;implementation "com.google.ar.sceneform.ux:sceneform-ux:1.5.0"<br>
<br>
Ссылка на источник: https://habr.com/ru/articles/438178/<br>
