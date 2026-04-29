# Walmart Automation

Proyecto base de automatizacion UI usando Java, Selenium WebDriver, Cucumber y TestNG.

## Estrategia de Testing

Se definio una estrategia de testing enfocada en validar los flujos criticos del negocio mediante automatizacion end-to-end, priorizando cobertura funcional, mantenibilidad y escalabilidad.

## Enfoque

Se adopto un enfoque end-to-end (E2E) para validar el comportamiento del sistema desde la perspectiva del usuario final, cubriendo el flujo completo de compra.

Se priorizan funcionalidades criticas del negocio, considerando:

- Alta frecuencia de uso.
- Impacto en la conversion.
- Riesgo asociado a fallas.

## Tipos de pruebas implementadas

Se implementaron pruebas funcionales automatizadas que validan:

- Navegacion entre paginas.
- Interaccion con elementos de UI.
- Validaciones de formularios.
- Comportamiento del carrito.
- Flujo completo de checkout.

## Cobertura de escenarios

Se definieron distintos escenarios dentro de los flujos principales para aumentar la cobertura:

- Guest Checkout: compra sin registro.
- Existing User Checkout: reutilizacion de datos existentes.
- Register User Checkout: creacion de cuenta durante la compra.

Esto permite validar distintos tipos de usuario y caminos del sistema.

## Diseno del framework

Se aplica el patron Page Object Model (POM) para:

- Separar logica de negocio de la interaccion con UI.
- Mejorar mantenibilidad.
- Facilitar reutilizacion de codigo.

El proyecto se organiza en capas:

- `pages`: interaccion con la UI.
- `steps`: definicion de pasos de negocio con Cucumber.
- `features`: definicion de escenarios.
- `utils`: manejo de datos CSV.
- `base`: metodos comunes reutilizables.

## Estrategia de sincronizacion

Se evita el uso de esperas estaticas (`Thread.sleep`) y se utilizan:

- Esperas explicitas (`waitForVisibility`, `waitUntil`).
- Validaciones de estado del DOM.

Esto ayuda a asegurar estabilidad y reducir flakiness en los tests.

## Manejo de datos de prueba

Se utilizan archivos CSV para desacoplar datos de la logica de test:

- `products.csv`: datos de productos.
- `checkoutData.csv`: datos de checkout guest.
- `users.csv`: usuarios existentes.
- `registerUserData.csv`: datos para registro dinamico.

Tambien se generan datos dinamicos, por ejemplo email con timestamp, para evitar conflictos entre ejecuciones.

## Principios aplicados

Se aplicaron buenas practicas de desarrollo:

- Single Responsibility: cada clase tiene una unica responsabilidad.
- Reutilizacion de codigo: se evita duplicacion innecesaria.
- Escalabilidad: facilidad para agregar nuevos escenarios.
- Legibilidad: codigo claro y entendible.

## Estrategia de ejecucion

Se permite ejecucion flexible mediante tags de Cucumber:

- `@checkout`: ejecucion completa del flujo.
- `@existingUser`: ejecucion aislada de usuario existente.
- `@registerUser`: validacion especifica de registro.

Esto facilita ejecucion por:

- Desarrollo local.
- Validaciones parciales.
- Ejecucion en pipelines CI/CD.

## Requisitos

- Java 17 o superior
- Maven 3.9+
- Navegador disponible (Chrome, Firefox o Edge)

## Ejecutar pruebas

```bash
mvn clean test
```

## Configuracion por propiedades

```bash
mvn clean test -Dbrowser=chrome -Dheadless=true -DbaseUrl=https://opencart.abstracta.us
```

Propiedades soportadas:

- `browser`: `chrome`, `firefox`, `edge` (default: `chrome`)
- `headless`: `true` o `false` (default: `false`)
- `baseUrl`: URL base de la aplicacion (default: `https://opencart.abstracta.us`)

## Comandos de ejecucion

Para ejecutar (PowerShell), desde `walmart-automation`:

```powershell
# Todos los tests
mvn clean test -Dbrowser=chrome -Dheadless=true

# Smoke
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@smoke"

# Regression
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@regression"

# Solo Cart
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@cart"

# Solo Checkout
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@checkout"

# Solo Search
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@search"

# Solo Existing User
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@existingUser"

# Solo Register User
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@registerUser"
```

Ejemplos utiles de combinaciones:

```powershell
# Regression sin register user
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@regression and not @registerUser"

# Smoke solo home
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@smoke and @home"
```

## Datos sensibles

El archivo `src/test/resources/testdata/users.csv` se usa para credenciales de usuario existente y no se versiona.
Para crear datos locales, tomar como base:

```bash
src/test/resources/testdata/users.example.csv
```

## Estructura

- `base`: utilidades comunes de paginas.
- `config`: lectura centralizada de configuracion de ejecucion.
- `factory`: creacion y cierre del WebDriver.
- `hooks`: setup y teardown de Cucumber.
- `model`: modelos tipados para datos de prueba.
- `pages`: Page Objects.
- `steps`: definiciones de pasos Gherkin.
- `utils`: readers y soporte de parsing CSV.
- `resources/features`: escenarios en Gherkin.
