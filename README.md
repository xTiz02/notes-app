# Notes App

Aplicación Android de notas desarrollada con **Kotlin** y **Jetpack Compose**. Permite listar, crear, ver, editar y eliminar notas usando una base de datos local con **Room**.

### Funcionalidades principales

- Listado de notas ordenadas por fecha/ID.
- Creación de nuevas notas.
- Vista de detalle de una nota.
- Edición y eliminación de notas existentes.
- Validación básica de campos antes de guardar.
- Navegación entre pantallas con Navigation Compose.

## Tecnologías usadas

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Navigation Compose**
- **Room** para persistencia local
- **ViewModel** y **StateFlow** para gestión de estado
- **Lifecycle**
- **KSP** para generación de código de Room
- **Coroutines y Flow** para operaciones asíncronas y observación de datos

## Arquitectura y buenas prácticas

El proyecto sigue un enfoque inspirado en **MVVM** con separación de responsabilidades:

- **UI / Compose**: las pantallas solo renderizan estado y disparan eventos.
- **ViewModel**: contiene la lógica de presentación, validación y estado de la interfaz.
- **Capa de datos**: separa el acceso a datos mediante `NotesService`, `NoteRepository` y `NoteDatabase`.
- **Inyección manual de dependencias**: se usa `AppContainer` / `DefaultAppContainer` para centralizar la creación de dependencias.

### Buenas prácticas aplicadas

- **Separación de responsabilidades** entre UI, lógica y persistencia.
- **Estado observable** con `StateFlow` para mantener la UI sincronizada.
- **Uso de `ViewModel`** para sobrevivir a cambios de configuración.
- **Persistencia local con Room** y acceso mediante DAO.
- **Singleton de base de datos** para evitar múltiples instancias de Room.
- **Validación en ViewModel** antes de insertar o actualizar datos.
- **Navegación tipada por rutas** en la capa de navegación.
- **Uso de `SavedStateHandle`** para recuperar parámetros de pantalla, como el ID de la nota.

## Estructura general

```text
app/src/main/java/com/prd/notesapp/
├── MainActivity.kt
├── NotesApplication.kt
├── data/
│   ├── AppContainer.kt
│   ├── database/
│   ├── model/
│   ├── repo/
│   └── service/
├── nav/
└── ui/
    ├── NotesApp.kt
    └── screens/
```

## Dependencias principales

Las dependencias más importantes del proyecto son:

- `androidx.activity:activity-compose`
- `androidx.compose.material3:material3`
- `androidx.compose.ui:ui`
- `androidx.compose.ui:ui-tooling-preview`
- `androidx.navigation:navigation-compose`
- `androidx.lifecycle:lifecycle-runtime-ktx`
- `androidx.lifecycle:lifecycle-viewmodel-compose`
- `androidx.room:room-runtime`
- `androidx.room:room-ktx`
- `androidx.room:room-compiler` vía **KSP**
- `androidx.compose.material:material-icons-extended`
- `kotlinx.coroutines` / `kotlinx.coroutines.flow` (uso indirecto por las APIs del proyecto)

## Configuración del proyecto

- **Namespace / applicationId:** `com.prd.notesapp`
- **Min SDK:** 24
- **Target SDK:** 36
- **Compose:** habilitado
- **Room schema directory:** configurado en `app/build.gradle.kts`

## Punto de entrada

- `MainActivity.kt` carga la app Compose.
- `NotesApplication.kt` inicializa el contenedor de dependencias.
- `AppNavigation.kt` administra la navegación entre pantallas.

## Notas

Este repositorio está orientado a aprendizaje y demostración de buenas prácticas en Android moderno. La app usa una implementación sencilla de inyección de dependencias y una separación clara entre capas para facilitar el mantenimiento.

