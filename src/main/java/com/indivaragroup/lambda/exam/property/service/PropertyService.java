package com.indivaragroup.lambda.exam.property.service;

import com.indivaragroup.lambda.exam.property.model.PropertyAsset;
import com.indivaragroup.lambda.exam.property.util.PropertyFormatter;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PropertyService {

    private final List<PropertyAsset> products;

    public PropertyService(List<PropertyAsset> products) {
        this.products = products;
    }

    public void displayAllProperties() {
        products.forEach(PropertyAsset::cetak);
    }

    public void filterUnsoldProperties() {
        List<PropertyAsset> copyList = new ArrayList<>(products);
        copyList.removeIf(PropertyAsset::isWasSold);
        copyList.forEach(PropertyAsset::cetak);
    }

    public void sortPriceAscending() {
        List<PropertyAsset> copyList = new ArrayList<>(products);
        copyList.sort(Comparator.comparingDouble(PropertyAsset::getPrice));
        copyList.forEach(PropertyAsset::cetak);
    }

    public void sortPriceDescending() {
        List<PropertyAsset> copyList = new ArrayList<>(products);
        copyList.sort(Comparator.comparingDouble(PropertyAsset::getPrice).reversed());
        copyList.forEach(PropertyAsset::cetak);
    }

    public void filterStreamScenarios() {
        System.out.println("1. HOUSE Type Only:");
        products.stream().filter(property -> property.getPropertyType().equalsIgnoreCase("RUMAH")).forEach(PropertyAsset::cetak);

        System.out.println("\n2. South Bekasi Location:");
        products.stream().filter(property -> property.getLocation().equalsIgnoreCase("Bekasi Selatan")).forEach(PropertyAsset::cetak);

        System.out.println("\n3. Price Under 1 Billion:");
        products.stream().filter(property -> property.getPrice() < 1000000000.0).forEach(PropertyAsset::cetak);

        System.out.println("\n4. Land Area > 100m² AND Unsold:");
        Predicate<PropertyAsset> landAreaOver100 = property -> property.getAreaSize() > 100;
        Predicate<PropertyAsset> isUnsold = property -> !property.isWasSold();
        products.stream().filter(landAreaOver100.and(isUnsold)).forEach(PropertyAsset::cetak);
    }

    public void transformData() {
        System.out.println("1 & 2. Property Name UPPERCASE:");
        products.stream()
                .map(PropertyAsset::getPropertyName)
                .map(String::toUpperCase)
                .forEach(System.out::println);

        System.out.println("\n3. Price Format to Rupiah:");
        products.stream()
                .map(PropertyAsset::getPrice)
                .map(PropertyFormatter::formatRupiah)
                .forEach(System.out::println);
    }

    public void calculateTotalAssetValue() {
        double totalAvailable = products.stream()
                .filter(property -> !property.isWasSold())
                .mapToDouble(PropertyAsset::getPrice)
                .sum();
        System.out.println("Total price of unsold properties: " + PropertyFormatter.formatRupiah(totalAvailable));

        System.out.println("\nAverage price per type:");
        Map<String, Double> averageByType = products.stream()
                .collect(Collectors.groupingBy(
                        PropertyAsset::getPropertyType,
                        Collectors.averagingDouble(PropertyAsset::getPrice)
                ));
        averageByType.forEach((type, avg) ->
                System.out.println(type + " : " + PropertyFormatter.formatRupiah(avg)));
    }

    public void groupWithCollectors() {
        System.out.println("1. Group by Type:");
        Map<String, List<PropertyAsset>> byType = products.stream().collect(Collectors.groupingBy(PropertyAsset::getPropertyType));
        byType.forEach((type, list) -> {
            System.out.println("[" + type + "]");
            list.forEach(PropertyAsset::cetak);
        });

        System.out.println("\n2. Group by Location (Count):");
        Map<String, Long> countByLocation = products.stream().collect(Collectors.groupingBy(PropertyAsset::getLocation, Collectors.counting()));
        countByLocation.forEach((location, count) -> System.out.println(location + " : " + count + " properties"));

        System.out.println("\n3. Group by Sold Status:");
        Map<Boolean, List<PropertyAsset>> byStatus = products.stream().collect(Collectors.groupingBy(PropertyAsset::isWasSold));
        System.out.println("[SOLD]");
        byStatus.getOrDefault(true, Collections.emptyList()).forEach(PropertyAsset::cetak);
        System.out.println("[AVAILABLE / UNSOLD]");
        byStatus.getOrDefault(false, Collections.emptyList()).forEach(PropertyAsset::cetak);
    }

    public PropertyAsset createNewProperty(Supplier<PropertyAsset> supplier) {
        return supplier.get();
    }

    public void customChainedComparator() {
        List<PropertyAsset> copyList1 = new ArrayList<>(products);
        copyList1.sort(
                Comparator.comparing(PropertyAsset::getLocation)
                        .thenComparing(Comparator.comparingDouble(PropertyAsset::getPrice).reversed())
        );
        System.out.println("1. Sort Location (Asc) -> Price (Desc):");
        copyList1.forEach(PropertyAsset::cetak);

        List<PropertyAsset> copyList2 = new ArrayList<>(products);
        copyList2.sort(
                Comparator.comparing(PropertyAsset::getPropertyType)
                        .thenComparing(Comparator.comparingInt(PropertyAsset::getYearBuilt).reversed())
        );
        System.out.println("\n2. Sort Type (Asc) -> Year Built (Desc):");
        copyList2.forEach(PropertyAsset::cetak);
    }

    public void generateReport() {
        long totalProperties = products.size();
        long soldCount = products.stream().filter(PropertyAsset::isWasSold).count();
        long availableCount = totalProperties - soldCount;

        double totalAvailableValue = products.stream()
                .filter(property -> !property.isWasSold())
                .mapToDouble(PropertyAsset::getPrice)
                .sum();

        Map<String, Long> locationDistribution = products.stream()
                .collect(Collectors.groupingBy(PropertyAsset::getLocation, Collectors.counting()));

        System.out.println("========================================");
        System.out.println("   PROPERTY ASSET REPORT - BEKASI REALTY GROUP");
        System.out.println("========================================");
        System.out.println("Total Properties     : " + totalProperties);
        System.out.println("Properties Sold      : " + soldCount);
        System.out.println("Properties Available : " + availableCount);
        System.out.println("\n--- AVAILABLE PROPERTIES (Sorted by Price) ---");

        List<PropertyAsset> availableList = products.stream()
                .filter(property -> !property.isWasSold())
                .sorted(Comparator.comparingDouble(PropertyAsset::getPrice))
                .collect(Collectors.toList());

        final int[] index = {1};
        availableList.forEach(property -> System.out.println((index[0]++) + ". [" + property.getId() + "] " + property.getPropertyName() + " | " + PropertyFormatter.formatRupiah(property.getPrice())));

        System.out.println("\n--- TOTAL VALUE OF AVAILABLE ASSETS ---");
        System.out.println("Total: " + PropertyFormatter.formatRupiah(totalAvailableValue));
        System.out.println("\n--- DISTRIBUTION BY LOCATION ---");
        locationDistribution.forEach((location, count) -> System.out.println(String.format("%-15s : %d properties", location, count)));
        System.out.println("========================================");
    }

    public List<PropertyAsset> filterByTypeAndLocation(String type, String location) {
        BiFunction<String, String, List<PropertyAsset>> filterBiFunc = (t, l) -> products.stream()
                .filter(property -> property.getPropertyType().equalsIgnoreCase(t) && property.getLocation().equalsIgnoreCase(l))
                .collect(Collectors.toList());
        return filterBiFunc.apply(type, location);
    }

    public Map<String, DoubleSummaryStatistics> getPriceStatisticsByType() {
        return products.stream()
                .collect(Collectors.groupingBy(
                        PropertyAsset::getPropertyType,
                        Collectors.summarizingDouble(PropertyAsset::getPrice)
                ));
    }

    public Optional<PropertyAsset> findById(String id) {
        return products.stream()
                .filter(property -> property.getId().equalsIgnoreCase(id))
                .findFirst();
    }
}