package com.indivaragroup.lambda.exam.property;

import com.indivaragroup.lambda.exam.property.data.PropertyDataProvider;
import com.indivaragroup.lambda.exam.property.model.PropertyAsset;
import com.indivaragroup.lambda.exam.property.service.PropertyService;
import com.indivaragroup.lambda.exam.property.util.PropertyFormatter;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class PropertyMain {
    public static void main(String[] args) {
        PropertyDataProvider provider = new PropertyDataProvider();
        List<PropertyAsset> initialData = provider.get();
        PropertyService service = new PropertyService(initialData);

        System.out.println("=== TASK 1: DISPLAY ALL PROPERTIES ===");
        service.displayAllProperties();
        System.out.println("\n");

        System.out.println("=== TASK 2: FILTER UNSOLD PROPERTIES ===");
        service.filterUnsoldProperties();
        System.out.println("\n");

        System.out.println("=== TASK 3: SORT PRICE ASCENDING ===");
        service.sortPriceAscending();
        System.out.println("\n--- PRICE DESCENDING ---");
        service.sortPriceDescending();
        System.out.println("\n");

        System.out.println("=== TASK 4: FILTER PROPERTIES WITH STREAM ===");
        service.filterStreamScenarios();
        System.out.println("\n");

        System.out.println("=== TASK 5: TRANSFORM DATA ===");
        service.transformData();
        System.out.println("\n");

        System.out.println("=== TASK 6: CALCULATE TOTAL ASSET VALUE ===");
        service.calculateTotalAssetValue();
        System.out.println("\n");

        System.out.println("=== TASK 7: GROUPING WITH COLLECTORS ===");
        service.groupWithCollectors();
        System.out.println("\n");

        System.out.println("=== TASK 8: CONSTRUCTOR REFERENCE & SUPPLIER ===");
        PropertyAsset newAsset = service.createNewProperty(PropertyAsset::new);
        System.out.println("New object successfully created via Supplier constructor reference: " + newAsset);

        Function<String, PropertyAsset> factoryId = PropertyAsset::new;
        PropertyAsset assetWithId = factoryId.apply("P09");
        System.out.println("New object successfully created via Function constructor reference with ID: " + assetWithId.getId());
        System.out.println("\n");

        System.out.println("=== TASK 9: CUSTOM CHAINED COMPARATOR ===");
        service.customChainedComparator();
        System.out.println("\n");

        System.out.println("=== TASK 10: FINAL REPORTING ===");
        service.generateReport();
        System.out.println("\n");

        System.out.println("1. BiFunction (HOUSE & South Bekasi):");
        List<PropertyAsset> bonusResult1 = service.filterByTypeAndLocation("RUMAH", "Bekasi Selatan");
        bonusResult1.forEach(PropertyAsset::cetak);

        System.out.println("\n2. Map DoubleSummaryStatistics Price Per Type:");
        Map<String, DoubleSummaryStatistics> statsMap = service.getPriceStatisticsByType();
        statsMap.forEach((type, stats) -> {
            System.out.println("[" + type + "]");
            System.out.println("   Count   : " + stats.getCount());
            System.out.println("   Min     : " + PropertyFormatter.formatRupiah(stats.getMin()));
            System.out.println("   Max     : " + PropertyFormatter.formatRupiah(stats.getMax()));
            System.out.println("   Average : " + PropertyFormatter.formatRupiah(stats.getAverage()));
        });

        System.out.println("\n3. Handle Optional ID Search:");
        Optional<PropertyAsset> foundProperty = service.findById("P03");
        foundProperty.ifPresent(PropertyAsset::cetak);

        Optional<PropertyAsset> propertyNotFound = service.findById("P99");
        if (propertyNotFound.isEmpty()) {
            System.out.println("Property ID P99 not found (Handled by Optional Safe).");
        }
    }
}