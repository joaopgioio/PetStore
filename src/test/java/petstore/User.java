package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class User {
    // 3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/user"; // endereço da entidade Pet

    // 3.2 - Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test // Identifica o metodo ou funcao como um Teste para o TestNG
    public void incluirUser() throws IOException {
        String jsonBody = lerJson("db/user1.json");

        // Sintaxe Gherkin
        // Dado - quando - Então
        // Given - When -  Then

        given()
                .contentType("application/json") // comum em api rest - antigos era "text/xml"
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
        ;
    }
}
