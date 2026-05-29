package com.indivaragroup.lambda.exam.thong;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

import static com.indivaragroup.lambda.exam.property.util.PropertyFormatter.formatRupiah;
import static java.lang.Math.abs;

public class Main {

    public static void main(String[] args) {

        List<Product> productsBagus = new ArrayList<>();
        productsBagus.add(new Product("Karebah Max", "Segitiga", "BESAR", 50000.0));
        productsBagus.add(new Product("GMC Pro", "TRAPESIUM", "KECIL", 10000.0));
        productsBagus.add(new Product("Rider Classic", "Segiempat", "BESAR", 45000.0));
        productsBagus.add(new Product("GT Man Exotic", "Belalai", "BESAR", 85000.0));
        productsBagus.add(new Product("Calvin Klein Luxury", "segitiga", "KECIL", 150000.0));
//    1.
        System.out.println("Scene 1");
        System.out.println("Bagus datang ke toko, melihat-lihat produk di etalase.");
        System.out.println("");
        Consumer<String> printer = System.out::println;
        for (Product prod : productsBagus) {
            String productName = prod.getProductName();
            printer.accept(productName);
        }
        System.out.println(" ");
//2.
        System.out.println("Scene 2");
        System.out.println("Bagus bingung memilih Model....");
        System.out.println("");
        Predicate<String> isChoosen = (choosen) -> "segitiga".equalsIgnoreCase(choosen) || "belalai".equalsIgnoreCase(choosen);
        productsBagus.stream()
                        .filter(model ->isChoosen.test(model.getCategory()))
                                .forEach(model ->printer.accept("Model " + model.getCategory() + " : " + model.getProductName()));
        System.out.println("");
//3.
        System.out.println("Scene 3");
        System.out.println("Bagus menentukan pilihan: Model Segitiga");
        System.out.println("");
        BiPredicate<Product, String> isTriangle = (productA, segitiga) ->
                productA.getCategory().equalsIgnoreCase("Segitiga");
        productsBagus.stream()
                .filter(model ->isTriangle.test(model,"segitiga"))
                .forEach(model ->printer.accept("Model " + model.getCategory() + " : "
                        + model.getProductName() +" Dengan Ukuran " + model.getSize() ));
        System.out.println("");
//4.
        System.out.println("Scene 4");
        System.out.println("Bagus ingin tahu harga Model Segitiga");
        System.out.println("");
        Function<Product, String> value = (segitigaHarga) -> {
            StringBuilder priceString = new StringBuilder();
            for (Product model : productsBagus) {
                if (model.getCategory().equalsIgnoreCase("Segitiga")) {
                    double harga = model.getPrice();
                    String nama = model.getProductName();
                    priceString.append(nama).append(" Dengan Harga ").append(formatRupiah(harga)).append("\n");
                }
            }
            return priceString.toString();
        };
        Product target = productsBagus.get(0);
        System.out.println(value.apply(target));
//5.
        System.out.println("Scene 5");
        System.out.println("Bagus compare harga Model Segitiga vs Model Belalai Ukuran Besar");
        System.out.println("");
        BiFunction<Product, String, String> comparation = (segitiga, belalai) -> {
            double hargaA = segitiga.getPrice();
            String namaA = segitiga.getProductName();
            String kategoriA = segitiga.getCategory();
            return productsBagus.stream()
                    .filter(modelB -> modelB.getCategory().equalsIgnoreCase(belalai))
                    .map(modelB -> {
                        String namaB = modelB.getProductName();
                        String kategoriB = modelB.getCategory();
                        double hargaB = modelB.getPrice();

                        double selisih = hargaA - hargaB;
                        String status = "memiliki harga yang sama dengan ";
                        String kuantitas = "";
                        if (selisih > 0) {
                            status = "lebih mahal dari ";
                            kuantitas = " sebanyak " + formatRupiah(selisih);
                        } else if (selisih < 0) {
                            status = "lebih murah dari ";
                            kuantitas = " sebanyak " + formatRupiah(Math.abs(selisih)); // Gunakan abs agar angka minus tidak tercetak
                        }

                        return namaA + " (" + kategoriA + ") " + status + namaB + " (" + kategoriB + ")" + kuantitas;
                    })
                    .collect(Collectors.joining("\n"));
        };
        System.out.println(comparation.apply(target, "belalai"));
        System.out.println("");
//6.
        System.out.println("Scene 6");
        System.out.println("Bagus memprediksi harga Model Segitiga akan naik di kemudian hari");
        System.out.println("");

        Predicate<Product> isPriceRising = (product) ->
                product.getCategory().equalsIgnoreCase("Segitiga");

        for (Product model : productsBagus) {
            if (isPriceRising.test(model)) {
                Double risingPrice = (model.getPrice() + 25000);
                System.out.println("Model yang diprediksi naik harga: " + model.getProductName()
                        + " Menjadi " + formatRupiah(risingPrice));
            }
        }
        System.out.println("");
//7.
        System.out.println("Scene 7");
        System.out.println("Bagus akhirnya memutuskan untuk beli Model Belalai");
        System.out.println("");
        Supplier<Product> finalDecision = () -> {
            Product chosenProduct = null;
            for (Product model : productsBagus) {
                if (model.getCategory().equalsIgnoreCase("Belalai") &&
                        model.getSize().equalsIgnoreCase("Besar")) {
                    chosenProduct = model;
                }
            }
            return chosenProduct;
        };

        Consumer<Product> buyAndPrintReceipt = (productToBuy) -> {
            if (productToBuy != null) {
                double hargaBulat =  productToBuy.getPrice();
                System.out.println("========== STRUK PEMBELIAN ==========");
                System.out.println("Nama Produk : " + productToBuy.getProductName());
                System.out.println("Kategori    : " + productToBuy.getCategory());
                System.out.println("Ukuran      : " + productToBuy.getSize());
                System.out.println("Total Bayar : Rp. " + hargaBulat);
                System.out.println("=====================================");
            } else {
                System.out.println("Produk tidak ditemukan, pembelian gagal.");
            }
        };

        Product produkYangDibeli = finalDecision.get();
        buyAndPrintReceipt.accept(produkYangDibeli);
        System.out.println("");

        List<Product> productsRena = new ArrayList<>();
        productsRena.add(new Product("Crocodile Comfort", "Tipis", "Belalai", 65000.0));
        productsRena.add(new Product("Speedo Ultra Stealth", "Sedang", "Two-Piece", 1200000.0));
        productsRena.add(new Product("Arena Glow Monokini", "Tebal", "One-Piece", 750000.0));
        productsRena.add(new Product("Roxy Wave Energy", "Tipis", "One-Piece", 450000.0));
        productsRena.add(new Product("Adidas Athleisure", "Sedang", "Two-Piece", 890000.0));
        productsRena.add(new Product("Reebok Aqua Fit", "Tebal", "One-Piece", 350000.0));

//1.
        System.out.println("SCenario Rena \n");
        System.out.println("Scene 1");
        System.out.println("Rena melihat etalase");
        System.out.println("");

        Predicate<Product> isTipis = (product) -> product.getCategory().equalsIgnoreCase("Tipis");
        Function<List<Product>, List<Product>> filterProducts = (originalList) -> {
            List<Product> filteredList = new ArrayList<>();
            for (Product prod : originalList) {
                if (isTipis.test(prod)) {
                    filteredList.add(prod);
                }
            }
            return filteredList;
        };

        List<Product> hasilFilterRena = filterProducts.apply(productsRena);

        System.out.println("Etalase Bahan Tipis");
        for (Product p : hasilFilterRena) {
            System.out.println( p.getProductName() + " (" + p.getCategory() + ")");
        }
        System.out.println("");
//2.
        System.out.println("Scene 2");
        System.out.println("Rena masih bingung setelah filter dan Butuh saran");
        System.out.println("");
        BiConsumer<Product, String> giveSuggestion = (product, alasan) -> {
            System.out.println("Saran Pemilik Toko :");
            System.out.println("Rekomendasi: Beli produk '" + product.getProductName() + "'");
            System.out.println("Alasan     : " + alasan);
        };
        Product produkDisarankan = hasilFilterRena.get(0);
        String rekomendasiToko = "Ini model terbaru tipe " + produkDisarankan.getSize() + ", sangat ringan untuk berenang.";

        giveSuggestion.accept(produkDisarankan, rekomendasiToko);
        System.out.println("");

//3.
        System.out.println("Scene 3");
        System.out.println("Rena setuju dengan saran pemilik toko, memilih produk yang disarankan.");
        System.out.println("");
        Supplier<Product> supplyRecommended = () -> produkDisarankan;
        Consumer<Product> renaTakeProduct = (productReceived) -> {
            System.out.println("Rena setuju dan mengambil produk: " + productReceived.getProductName());
        };

        Product produkDiTanganRena = supplyRecommended.get();
        renaTakeProduct.accept(produkDiTanganRena);
        System.out.println("");

//4.
        System.out.println("Scene 4");
        System.out.println("Rena bertanya: BerapaanKak ?");
        System.out.println("");
        Function<Product, Double> getPriceFunction = (product) -> product.getPrice();
        Consumer<Double> displayPriceToCustomer = (price) -> {
            System.out.println("Pemilik Toko: Harganya " + formatRupiah(price));
        };
        Double hargaProduk = getPriceFunction.apply(produkDiTanganRena);
        displayPriceToCustomer.accept(hargaProduk);

    }
}