//ResourceHandler.java

import org.apache.poi.ss.usermodel.*;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//import static spark.Spark.redirect;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ResourceHandler {

    public List<Candy> getLowStockCandies() throws IOException, FileNotFoundException {
        // get candies from inventory with less than 25% capacity
        List<Candy> lowStockCandies = new ArrayList<>();

        File file = new File("server/resources/Inventory.xlsx");

        try (InputStream iS = new FileInputStream(file);
                Workbook workbook = WorkbookFactory.create(iS)) {

            Sheet sheet = workbook.getSheetAt(0); // data must be stored in the first sheet
            for (Row row : sheet) {
                if (row.getRowNum() == 0)
                    continue;

                String name = row.getCell(0).getStringCellValue();
                int inStock = (int) row.getCell(1).getNumericCellValue();
                int maxCapacity = (int) row.getCell(2).getNumericCellValue();
                int id = (int) row.getCell(3).getNumericCellValue();

                double lowStockThreshold = maxCapacity * 0.25;
                if (inStock < lowStockThreshold) {
                    lowStockCandies.add(new Candy(name, inStock, maxCapacity, id));
                }
            }
        } catch (Exception e) {
            System.err.println("--getLowStockCandies() error:");
            e.printStackTrace();
            System.err.println("---end error output---");
            throw e;
        }

        return lowStockCandies;
    }

    public Map<Integer, Double> findLowestPrices(List<Integer> orderIDs) throws IOException, FileNotFoundException {

        Map<Integer, Double> lowestPrices = new HashMap<>();

        // since doubles are primative, setting max value doesn't use more memory
        for (Integer id : orderIDs) {
            lowestPrices.put(id, Double.MAX_VALUE);
        }

        File file = new File("server/resources/Distributors.xlsx");

        try (InputStream iS = new FileInputStream(file);
                Workbook workbook = WorkbookFactory.create(iS)) {

            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);

                for (Row row : sheet) {
                    if (row.getRowNum() == 0)
                        continue; // skipping first row (headers)

                    Cell idCell = row.getCell(1);
                    if (idCell != null) {
                        int id = (int) row.getCell(1).getNumericCellValue();
                        if (lowestPrices.containsKey((Integer) id)) {
                            Double cost = (Double) row.getCell(2).getNumericCellValue();
                            if (cost < lowestPrices.get(id)) {
                                lowestPrices.put(id, cost);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("--findLowestPrices() error:");
            e.printStackTrace();
            System.err.println("---end---");
            throw e;
        }

        return lowestPrices;
    }

}
