# Users Management App (Proof On Off)

[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.0.20-blue.svg)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Jetpack_Compose-Material_3-green.svg)](https://developer.android.com/jetpack/compose)
[![Hilt](https://img.shields.io/badge/DI-Hilt-orange.svg)](https://dagger.dev/hilt/)
[![Clean Architecture](https://img.shields.io/badge/Architecture-Clean_MVVM-red.svg)]()

## 📝 Resumen Ejecutivo
**Users Management App** es una solución robusta para la gestión de usuarios, diseñada bajo los estándares de **Modern Android Development (MAD)**. El proyecto implementa una arquitectura desacoplada, reactiva y escalable, garantizando la mantenibilidad a largo plazo y la facilidad de pruebas unitarias.

### Puntuación de Robustez Técnica: **8.5 / 10**

---

## 🏗️ Arquitectura y Estructura del Proyecto

El sistema se rige por los principios de **Clean Architecture**, separando las responsabilidades en capas lógicas para proteger la lógica de negocio de cambios en la infraestructura.

### Organización de Paquetes

---

## 🧠 Decisiones Técnicas y Justificación

### 1. Gestión de Datos Segura (`BaseRepository`)
Se ha implementado una infraestructura base para estandarizar la comunicación asíncrona:
- **`safeApiCall`:** Centraliza el manejo de excepciones de red y errores HTTP (401, 404, 500), evitando *crashes* inesperados.
- **`asResource()`:** Transforma flujos de datos en estados reactivos (`Loading`, `Success`, `Error`), permitiendo que la UI responda automáticamente al estado de la carga.

### 2. Procesamiento de Símbolos (KSP)
Se utiliza **KSP (Kotlin Symbol Processing)** en lugar de Kapt para Room e Hilt.
- **Justificación:** KSP ofrece una velocidad de compilación hasta un **25% superior**, optimizando el ciclo de desarrollo (*Developer Experience*).

### 3. UI Declarativa (Jetpack Compose + Material 3)
La interfaz es 100% declarativa, utilizando el **BOM (Bill of Materials)** para asegurar la compatibilidad de versiones y **Navigation Compose** con tipos seguros para evitar errores en el paso de argumentos.

### 4. Networking con Retrofit
- Configuración estricta de `baseUrl` (terminada en `/`) y uso de anotaciones explícitas (`@Body`, `@Path`, `@Query`) para garantizar contratos de API íntegros.
- Inyección dinámica de la URL mediante `BuildConfig` para separar entornos de desarrollo y producción.

---

## 🚀 Stack Tecnológico

- **Lenguaje:** Kotlin 2.0.20 (Strongly Typed).
- **DI:** Hilt (Dagger) para una inyección de dependencias con *scoping* de ciclo de vida.
- **Async:** Coroutines & StateFlow para una reactividad *Main-safe*.
- **Networking:** Retrofit 2.11 + OkHttp (Logging Interceptor).
- **Persistencia:** Room Database con soporte nativo para Flows.
- **Imágenes:** Coil para carga asíncrona eficiente.
- **Calidad:** Detekt (Análisis estático) y Dokka (Generación de documentación KDoc).

---

## 📱 Screenshots (Visual Context)

|           Listado de Usuarios           | Registro/Edición |                  Detalles                   |
|:---------------------------------------:|:----------------:|:-------------------------------------------:|
| ![Lista](http://0.0.0.0:5254/api/users) |  ![Registro](http://0.0.0.0:5254/api/user)   | ![Detalles](http://0.0.0.0:5254/api/user/1) |

---

## 🛠️ Guía de Ejecución y Configuración

### 1. Requisitos Previos
- **Android Studio:** Ladybug (2024.2.1) o superior.
- **JDK:** 17.
- **Memoria:** Configurar `org.gradle.jvmargs=-Xmx2048m` en `gradle.properties`.

### 2. Configuración de API
Edita tu archivo `local.properties` y añade la URL de tu backend (asegúrate de incluir la `/` final):


### 3. Comandos Útiles
- **Analizar Código:** `./gradlew detekt`
- **Generar Docs:** `./gradlew dokkaHtml`
- **Ejecutar Tests:** `./gradlew test`

---

## 📈 Roadmap y Escalabilidad
- **Modularización:** Plan para migrar a *Feature Modules* y reducir tiempos de compilación incremental.
- **Mappers:** Implementación de transformadores estrictos entre DTOs y Modelos de Dominio para desacoplar totalmente la API de la UI.
- **Testing:** Incrementar cobertura visual mediante *Screenshot Testing*.

---
**Lead Android Engineer:** Janes Saenz Puerta  
**Expertise:** Clean Architecture | Jetpack Compose | Enterprise Solutions