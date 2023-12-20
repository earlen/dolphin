//Main.java

import static spark.Spark.*;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) {
        ResourceHandler resourceHandler = new ResourceHandler();

        // This is required to allow GET and POST requests with the header
        // 'content-type'
        options("/*",
                (request, response) -> {
                    response.header("Access-Control-Allow-Headers",
                            "content-type");

                    response.header("Access-Control-Allow-Methods",
                            "GET, POST");

                    return "OK";
                });

        // This is required to allow the React app to communicate with this API
        before((request, response) -> response.header("Access-Control-Allow-Origin", "http://localhost:3000"));

        // TODO: Return JSON containing the candies for which the stock is less than 25%
        // of it's capacity
        get("/low-stock", (request, response) -> {
            try {
                List<Candy> lowStockCandies = resourceHandler.getLowStockCandies();
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(lowStockCandies);
                response.type("application/json");
                response.status(200);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "An internal server error occured";
            }
        });

        // TODO: Return JSON containing the total cost of restocking candy
        post("/restock-cost", (request, response) -> {

            return null;
        });

    }
}
