//Main.java

import static spark.Spark.*;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {

        // Singleton use of resourceHandler used since its methods are thread safe
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

        get("/low-stock", (request, response) -> {

            // getLowStockCandies() and writeValueAsString() can throw exceptions
            try {

                List<Candy> lowStockCandies = resourceHandler.getLowStockCandies();
                String json = MAPPER.writeValueAsString(lowStockCandies);

                response.type("application/json");
                response.status(200);
                return json;
            }
            // catch the excetpion
            catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "An internal server error occured";
            }
        });

        post("/restock-cost", (request, response) -> {

            // findLowestPrices(), readValue() and writeValueAsString() can throw exceptions
            try {

                // Get orderAmounts from request body
                Map<Integer, Integer> orderAmounts = MAPPER.readValue(request.body(),
                        new TypeReference<Map<Integer, Integer>>() {
                        });

                // Isolate the keys, and init a new map
                List<Integer> orderIDs = new ArrayList<>(orderAmounts.keySet());
                Map<Integer, Double> lowestPrices = new HashMap<>();

                // Find lowest prices for relavant IDS
                lowestPrices = resourceHandler.findLowestPrices(orderIDs);

                System.out.print("Lowest prices: ");
                System.out.println(lowestPrices);
                System.out.print("Order amounts: ");
                System.out.println(orderAmounts);

                // Multiply maps and add product to total
                Double restockCost = 0.0;
                for (Map.Entry<Integer, Integer> entry : orderAmounts.entrySet()) {

                    Integer id = entry.getKey();
                    Integer amount = entry.getValue();

                    if (lowestPrices.containsKey(id)) {
                        restockCost += (lowestPrices.get(id) * amount);
                    }
                }

                System.out.print("Total Restocking Cost: ");
                System.out.println(restockCost);

                String json = MAPPER.writeValueAsString(restockCost);

                response.type("application/json");
                response.status(200);
                return json;
            }
            // catch the excetpion
            catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "An internal server error occured";
            }
        });
    }
}
