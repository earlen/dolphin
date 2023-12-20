//ResourceHandler.java

import org.apache.poi.ss.usermodel.*;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.File;

public class ResourceHandler {

    public List<Candy> getLowStockCandies() {
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
            System.out.println("--getLowStockCandies() error:");
            e.printStackTrace();
            System.out.println("---end---");
        }
        System.out.println(lowStockCandies);
        return lowStockCandies;
    }

    public double calculateRestockCost() {
        // calculate and return the total cost of restocking candy
        // access distributors map

        return 0.0;
    }

    private Workbook openWorkbook(String resourcePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            return WorkbookFactory.create(is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
