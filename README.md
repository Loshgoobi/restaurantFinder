# restaurant Finder

Restaurant Finder est une application mobile de recherche de restaurant par plats.

Le backend utilise Laravel et [ce trouve ici](https://github.com/dartdz/APIMobile)

## Librairies utilisées

    implementation 'com.android.support:design:28.0.0'
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation "com.android.support:appcompat-v7:$support_version"
    implementation "com.android.support:recyclerview-v7:$support_version"
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    implementation "com.android.support:support-v4:$support_version"
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'com.google.android.gms:play-services-maps:16.1.0'
    implementation 'com.google.android.gms:play-services-location:16.0.0'
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
    implementation "io.reactivex.rxjava2:rxjava:2.1.9"
    implementation "io.reactivex.rxjava2:rxkotlin:2.2.0"
    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation 'com.squareup.okhttp3:okhttp:3.12.1'
    implementation "org.jetbrains.anko:anko:$anko_version"
    implementation "org.jetbrains.anko:anko-appcompat-v7:$anko_version"
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'com.jakewharton.rxbinding2:rxbinding-kotlin:2.1.1'
    implementation "android.arch.lifecycle:extensions:1.1.1"
    
 Les librairies seront expliqué avec les activitées
    
    
## Activitées

L'application est composé de 4 activitées principales :
1. L'accueil
2. La page de recherche
3. Les details d'un restaurants
4. La Carte
  
  
### L'acceuil (MainActivity)

La page d'accueil est une page qui donne les restaurants "du moments" dans un recycler view
Pour récuperer les données a mettre dans le recycler view


### Page de recherche (SearchActivity)

La page de recherche possède un recycler view filtré a l'aide des librairies

    implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
    implementation "io.reactivex.rxjava2:rxjava:2.1.9"
    implementation "io.reactivex.rxjava2:rxkotlin:2.2.0"
 
A chaque fois que l'editeur de text est modifié il recherche l'item qui a le contenu correspondant


### Details du restaurant (PhotoActivity)

On récupère les information du restaurants et on les affiches. On peut aussi appeler directement le restaurant grace a la librairie Anko

Il y a un recyclerView qui affiche les plats du restaurants avec une note pour chaque plats


### Details du restaurant (MapActivity)

Une carte généré grace a l'API google qui repère la localisation et pointe dessus.


## Récupération des données

La récupération des données se fait par URL de l'api qui nous renvoit un chaine JSON
On convertit la chaine grace à Gson pour ensuite mettre les données dans les data class des restaurants

```
var restaurant: MutableList<Restaurant>
restaurant = gson.fromJson(photoJSON, object : TypeToken<MutableList<Restaurant>>() {}.type)
```

