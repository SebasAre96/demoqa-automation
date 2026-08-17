package ar.net.brania.DemoQA.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigReader — lee la configuración desde config.properties.
 * <p>
 * Prioridad de valores:
 * 1. Propiedades del sistema (-Dbrowser=chrome desde Jenkins/Maven)
 * 2. Variables de entorno (BROWSER=chrome)
 * 3. config.properties (valor por defecto local)
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try {
            // ClassLoader busca en el classpath — funciona en IntelliJ,
            // Maven y Jenkins sin importar el working directory
            InputStream input = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");

            if (input == null) {
                throw new RuntimeException("No se encontró config.properties en el classpath");
            }

            properties.load(input);
            input.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al cargar config.properties: " + e.getMessage());
        }
    }

    /**
     * Browser a usar: chrome o firefox.
     * Jenkins lo pasa como: mvn test -Dbrowser=chrome
     */
    public static String getBrowser() {
        return System.getProperty("browser",
                        System.getenv().getOrDefault("BROWSER",
                                properties.getProperty("browser", "chrome")))
                .toLowerCase().trim();
    }

    /**
     * Modo headless: true en Jenkins/Docker, false en local.
     * Jenkins lo pasa como: mvn test -Dheadless=true
     */
    public static boolean isHeadless() {
        String value = System.getProperty("headless",
                System.getenv().getOrDefault("HEADLESS",
                        properties.getProperty("headless", "false")));
        return Boolean.parseBoolean(value);
    }

    /**
     * URL base del sitio.
     * Jenkins lo pasa como: mvn test -Dbase.url=https://demoqa.com
     */
    public static String getBaseUrl() {
        return System.getProperty("base.url",
                System.getenv().getOrDefault("BASE_URL",
                        properties.getProperty("base.url", "https://demoqa.com")));
    }

    /**
     * Timeout global para los waits en segundos.
     */
    public static int getTimeout() {
        String value = System.getProperty("timeout",
                properties.getProperty("timeout", "15"));
        return Integer.parseInt(value);
    }
}