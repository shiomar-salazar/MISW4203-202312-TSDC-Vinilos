# MISW4203-202312-TSDC-Vinilos
Espacio de trabajo para el equipo #6 de la Materia de MISW4203

## Estructura del Proyecto
```bash
├── .idea
│   ├── ...
├── APK
│   ├── app-debug.apk
│   ├── output-metadata.json
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── java/com/example/tsdc_vinilos_equipo6
│   │   │   │   ├── models
│   │   │   │   │   ├── Album.kt
│   │   │   │   │   ├── Artist.kt
│   │   │   │   │   ├── Collector.kt
│   │   │   │   │   ├── Comment.kt
│   │   │   │   ├── network
│   │   │   │   │   ├── NetworkServiceAdapter.kt
│   │   │   │   ├── placeholder
│   │   │   │   │   ├── PlaceholderContent.kt
│   │   │   │   ├── ui
│   │   │   │   │   ├── adapters
│   │   │   │   │   │   ├── AlbumsAdapter.kt
│   │   │   │   │   │   ├── ArtistsAdapter.kt
│   │   │   │   │   │   ├── CollectorsAdapter.kt
│   │   │   │   │   │   ├── CommentsAdapter.kt
│   │   │   │   │   ├── AlbumFragment.kt
│   │   │   │   │   ├── ArtistFragment.kt
│   │   │   │   │   ├── CollectorFragment.kt
│   │   │   │   │   ├── CommentFragment.kt
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   ├── MainFragment.kt
│   │   │   │   │   ├── UserFragment.kt
│   │   │   │   ├── viewmodels
│   │   │   │   │   ├── AlbumViewModel.kt
│   │   │   │   │   ├── ArtistViewModel.kt
│   │   │   │   │   ├── CollectorViewModel.kt
│   │   │   │   │   ├── CommentViewModel.kt
│   │   │   ├── res
│   │   │   │   ├── layout
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── album_fragment.xml
│   │   │   │   │   ├── artist_fragment.xml
│   │   │   │   │   ├── collector_fragment.xml
│   │   │   │   │   ├── collector_item.xml
│   │   │   │   │   ├── comment_fragment.xml
│   │   │   │   │   ├── comment_item.xml
│   │   │   │   │   ├── fragment_main.xml
│   │   │   │   │   ├── fragment_user.xml
│   │   │   │   ├── navigation
│   │   │   │   │   ├── nav_graph.xml
│   │   │   │   ├── values
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── dimens.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── themes.xml
│   │   │   │   ├── ...
│   │   │   ├── AndroidManifest.xml
│   │   ├── test/java/com/example/tsdc_vinilos_equipo6
│   │   │   ├── ExampleInstrumentedTest.kt
│   │   ├── androidTest/java/com/example/tsdc_vinilos_equipo6/
│   │   │   ├── ExampleInstrumentedTest.kt
│   ├── .gitignore
│   ├── build.gradle
│   ├── proguard-rules.pro
├── .gitignore
├── README.md
├── build.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle
```

## Pasos para hacer Build
1. En Android Studio con el proyecto abierto, hacer click en el boton del martillo, que aparecera en la parte superior derecha:
![image](https://user-images.githubusercontent.com/111320185/233865682-1ca9af38-93d0-4431-9a5b-7d350207dcf3.png)

2. Esperar una salida de la consola similar a la siguiente imagen:
![image](https://user-images.githubusercontent.com/111320185/233865668-8f7fb211-7f3d-466b-bf92-f685cb5f418c.png)

## Pasos para Generar APK
1. En Android Studio con el proyecto abierto, hacer click en Build -> Build Bundle(s)/APK(s) -> Build APK(s)
![image](https://user-images.githubusercontent.com/111320185/233865758-dbd50bf5-ea78-4ace-b8c2-d7c3729fb458.png)

2. Esperar hasta que Android Studio avise que termine la generacion y hacer click en "Locate":
![image](https://user-images.githubusercontent.com/111320185/233865815-5c5aff7b-c984-44c7-b1c2-52ca6c66c060.png)

3. Te dirigirá a la ubicacion donde se genero al APK:
![image](https://user-images.githubusercontent.com/111320185/233865897-c63c9890-b809-4566-b918-b48255c763e1.png)

## Pasos para Ejecutar en Dispositvo Fisico de Pruebas
1. En Android Studio con el proyecto abierto y el dispositvo a usar conectado (con la opcion de desarrollador activada):
![image](https://user-images.githubusercontent.com/111320185/233866031-0e14953a-3d34-4ee2-8408-bdea278d41b3.png)

2. Hacer click en la flecha verde del menu superior derecho:
![image](https://user-images.githubusercontent.com/111320185/233866050-726b2b33-1d10-4a1e-80ac-6466c5dc2c81.png)

3. Esperar a que en la terminal aparezca el siguiente mensaje:
![image](https://user-images.githubusercontent.com/111320185/233866097-3baf22e0-ac9e-461b-a4e3-43d5608373d0.png)

## Pasos para Ejecutar en Dispositivo Emulado de Pruebas
***
TBA
***

## Pasos para Ejecutar Pruebas Automatizadas
***
TBA
***
