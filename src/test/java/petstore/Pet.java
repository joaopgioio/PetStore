// 1 - Pacote
package petstore;

// 2 - Biblioteca


import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

// 3 - Classe
public class Pet {
    // 3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // endereço da entidade Pet
    String petId = "198520211410";

    // 3.2 - Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test(priority = 1) // Identifica o metodo ou funcao como um Teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

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
                .body("name", is("Suzi"))
                .body("status", is("disponível"))
                .body("category.name", is("AXEERAXFRV12323"))
                .body("tags.name", contains("data"))
        ;
    }
    // Consulta - Read - Get
    @Test(priority = 2)
    public void consultarPet(){

        // Sintaxe Gherkin
        // Dado - quando - Então
        // Given - When -  Then

        String token =
        given()
                .contentType("application/json") // comum em api rest - antigos era "text/xml"
                .log().all()
                //.body()
        .when()
                .get(uri+"/"+petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Suzi"))
                .body("category.name", is("AXEERAXFRV12323"))
                .body("status", is("disponível"))

        .extract()
                .path("category.name")

        ;
        System.out.println("O token é: " +token);
    }
    @Test(priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Suzi"))
                .body("status", is("indisponível"))
                .body("category.name", is("AXEERAXFRV12323"))
                .body("tags.name", contains("data"))
        ;
    }

    @Test(priority = 4)
    public void deletarPet(){

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri+"/"+petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("message", is(petId))
        ;
    }

    @Test(priority = 5)
    public void consultarPetAposDel() {

        // Sintaxe Gherkin
        // Dado - quando - Então
        // Given - When -  Then

        given()
                .contentType("application/json") // comum em api rest - antigos era "text/xml"
                .log().all()
                //.body()
        .when()
                .get(uri+"/"+petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("message", is("Pet not found"))
        ;
    }
}
