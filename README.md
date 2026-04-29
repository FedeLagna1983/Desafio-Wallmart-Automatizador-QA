# Walmart Automation

Proyecto base de automatizacion UI usando Java, Selenium WebDriver, Cucumber y TestNG.

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
