# Mobile Cross Platform Lab — Serenity BDD + Screenplay + Cucumber + Appium + LambdaTest

Laboratorio de automatización móvil que ejecuta **el mismo flujo de autenticación en Android
y en iOS** con una sola base de código, sobre una app propia (`lab_auth`, Flutter). Las
capabilities viven en archivos `serenity.properties` y el perfil de plataforma se elige en la
línea de comandos.

La arquitectura replica la del proyecto **AppNegocios** (Screenplay con fábricas `Ready` /
`Validate`, localizadores centralizados en `userinterfaces`, la interacción `Login` recibiendo
las credenciales desde una tabla de datos) y le agrega la capa multiplataforma.

---

## 1. La app bajo prueba es nuestra

El laboratorio corre contra **Lab Negocios**, la app Flutter del proyecto `lab_auth`. Eso
significa que los localizadores no son una suposición sobre una app de terceros: están
definidos en el código de la app y documentados en su `LOCATORS.md`.

Cada elemento automatizable lleva un `Semantics(identifier: 'x')`, que Flutter traduce a
`resource-id` en Android y a `accessibilityIdentifier` en iOS. Un solo identificador sirve
para las dos plataformas.

Datos de prueba:

| Usuario | Clave | Resultado |
|---|---|---|
| `1032456789` | `Clave1234` | Entra al home |
| `1032456700` | cualquiera | `Tu usuario esta bloqueado` |
| cualquier otro | cualquiera | `Los datos no coinciden` |

---

## 2. Qué demuestra

| Tema | Dónde verlo |
|---|---|
| Autenticación: éxito, credenciales inválidas, usuario bloqueado | `features/authentication/authentication.feature` |
| Un solo código para Android e iOS | `utils/PlatformTarget.java` |
| Capabilities fuera del código | `serenity.properties`, `serenity-ios.properties` |
| Gesto nativo de scroll | `interactions/ScrollUntilVisible.java` |
| Menú que vive en otra ruta | `interactions/SelectDocumentType.java` |
| Espera por servicio lento (3 s) | `interactions/Login.java` |
| Manejo del teclado por plataforma | `interactions/TypeText.java` |
| Escenarios exclusivos de una plataforma | tags `@AndroidOnly` / `@IosOnly` + dos runners |
| Integración con LambdaTest | `serenity*.properties`, `stepdefinitions/Hooks.java` |
| Ejecución en CI | `azure-pipelines.yml` |

---

## 3. Estructura

```
src/main/java/co/com/lab/certificacion/mobile/
├── interactions/      Login, SelectDocumentType, ScrollUntilVisible, TypeText, PressBackKey
├── questions/         TheHomeGreeting, TheLoginErrorMessage, TheLoginFormIsVisible
├── tasks/             Authenticate, OpenTheApplication
│   └── factories/     Ready (tasks) y Validate (questions)
├── userinterfaces/    OnboardingPage, LoginPage, HomePage   ← localizadores
└── utils/             DevicePlatform, PlatformTarget, MobileDriver, Constant

src/test/java/.../
├── PreflightTest      valida la configuración sin dispositivo
├── runners/           AndroidRunner, IosRunner
└── stepdefinitions/   Hooks, AuthenticationStepDefinitions
```

---

## 4. Cómo funciona lo multiplataforma

**`DevicePlatform`** lee `appium.platformName` del mismo archivo de properties que carga
Serenity (`serenity.properties`, o el indicado con `-Dproperties=...`). La plataforma se define
en un solo lugar: la configuración.

**`PlatformTarget.byIdentifier`** convierte un identificador de la app en el XPath que
corresponde a cada plataforma:

```java
public static Target byIdentifier(String name, String identifier) {
    return the(name,
            "//*[@resource-id='" + identifier + "']",   // Android
            "//*[@name='" + identifier + "']");         // iOS
}
```

Por eso `LoginPage` se lee así, sin duplicar nada:

```java
public static final Target INPUT_USER = byIdentifier("Numero de documento", "login_user_field");
```

Se usa XPath en vez de `By.id` porque el `Target` así admite parámetros dinámicos con `.of()`,
que es como se eligen las opciones del dropdown:

```java
Click.on(DOCUMENT_TYPE_OPTION.of("nit"))
```

Cuando la diferencia no es un locator sino el comportamiento (el `hideKeyboard()` que lanza
excepción en iOS, el botón físico atrás que no existe allá), se encapsula en una `Interaction`
y los escenarios no se enteran.

---

## 5. Compilar y subir la app

Desde el proyecto Flutter `lab_auth`:

```bash
flutter pub get

# Android
flutter build apk --release
# -> build/app/outputs/flutter-apk/app-release.apk

# iOS sin firma (LambdaTest refirma la app al subirla)
flutter build ios --release --no-codesign
mkdir -p Payload && cp -r build/ios/iphoneos/Runner.app Payload/
zip -r lab_auth.ipa Payload && rm -rf Payload
```

Súbelas a LambdaTest y guarda el `lt://APP...` de cada una:

```bash
export LT_USERNAME=tu_usuario
export LT_ACCESS_KEY=tu_access_key

curl -u "$LT_USERNAME:$LT_ACCESS_KEY" \
  --location --request POST 'https://manual-api.lambdatest.com/app/upload/realDevice' \
  --form 'name="LabNegocios_Android"' \
  --form 'appFile=@"build/app/outputs/flutter-apk/app-release.apk"'

curl -u "$LT_USERNAME:$LT_ACCESS_KEY" \
  --location --request POST 'https://manual-api.lambdatest.com/app/upload/realDevice' \
  --form 'name="LabNegocios_iOS"' \
  --form 'appFile=@"lab_auth.ipa"'
```

Reemplaza `appium.app` en cada perfil (`lt://APP_ID_ANDROID` y `lt://APP_ID_IOS`) y ajusta
`appium.deviceName` / `appium.platformVersion` a un dispositivo de tu plan.

> El `appium.hub` de los archivos trae un placeholder a propósito: los scripts arman la URL
> desde las variables de entorno y la inyectan con `-Dappium.hub`, que tiene prioridad sobre el
> archivo. Así las credenciales nunca se versionan.

---

## 6. Ejecución

```bash
./gradlew compileJava compileTestJava              # 1. que compile
./gradlew test --tests "*PreflightTest"            # 2. que la configuración esté completa
./gradlew test --tests "*AndroidRunner" -Dcucumber.options="--dry-run"   # 3. que el glue case

./run-android.sh     # perfil Android → tags @CrossPlatform + @AndroidOnly
./run-ios.sh         # perfil iOS     → tags @CrossPlatform + @IosOnly
```

Reporte: `target/site/serenity/index.html`.

---

## 7. Matriz de escenarios

| Escenario | Tag | Android | iOS |
|---|---|:--:|:--:|
| Login exitoso → saludo en el home | `@CrossPlatform @Smoke` | ✔ | ✔ |
| Clave incorrecta → error | `@CrossPlatform @Regression` | ✔ | ✔ |
| Usuario inexistente → error | `@CrossPlatform @Regression` | ✔ | ✔ |
| Usuario bloqueado → mensaje propio | `@CrossPlatform @Regression` | ✔ | ✔ |
| Ocultar teclado con el botón físico atrás | `@AndroidOnly` | ✔ | — |

---

## 8. Decisiones técnicas

- **Mismo stack que AppNegocios** (Serenity 2.1.2 + serenity-cucumber 1.9.51 + java-client
  transitivo). Es deliberado: compila con las versiones que ya manejas. Por eso los steps usan
  expresiones regulares y no Cucumber Expressions, y la tabla de datos se lee con
  `cucumber.api.DataTable` + `transpose().asMap(...)`.
- **Dos archivos de properties.** Serenity carga un único archivo, el indicado en la system
  property `properties`. Migrando a `serenity.conf`, un bloque `environments { ... }` evitaría
  duplicar las capabilities comunes.
- **Scroll con `TouchAction` sobre coordenadas relativas** en vez de `mobile: scrollGesture` o
  `mobile: scroll`, que son comandos distintos en UiAutomator2 y XCUITest. Con coordenadas el
  gesto es el mismo en las dos plataformas.
- **Flutter sobre Appium es más frágil que una app nativa.** Aunque `ensureSemantics()` fuerce
  el árbol, algunos nodos aparecen genéricos. Si un localizador no responde, revísalo con
  Appium Inspector y ajústalo en `userinterfaces`: son tres archivos.

---

## 9. Siguientes pasos sugeridos

- Ejecución en paralelo por dispositivo (`maxParallelForks` + `appium.name` dinámico).
- Datos de prueba desde JSON/Excel en lugar de estar en el feature.
- Comparar esta suite con la de `integration_test` del mismo proyecto: misma app, dos enfoques.
