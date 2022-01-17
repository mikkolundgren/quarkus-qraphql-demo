package demo.mikko;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;

@QuarkusTest
public class AlbumResourceTest {
    
    @Test
    public void allAlbums() {
        String query =
                "{\"query\":" +
                        "\"" +
                            "{" +
                                " allAlbums  {" +
                                    " title" +
                                    " artist" +
                                    " year" +
                                  "}" +
                            "}" +
                        "\"" +
                "}";
        given()
            .body(query)
            .post("/graphql/")
            .then()
            .contentType(ContentType.JSON)
            .body("data.allAlbums.size()", greaterThan(0))
            .body("data.allAlbums.artist", hasItem("Elvis Presley"))
            .statusCode(200);
    }
}
