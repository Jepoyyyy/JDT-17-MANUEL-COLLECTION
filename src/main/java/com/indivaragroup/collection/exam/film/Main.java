package com.indivaragroup.collection.exam.film;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {

    private static final Map<String, Map<String, Map<String, Map<String, List<Film>>>>> database = new LinkedHashMap<>();

    public static void main(String[] args) {
        initData();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Browse Category");
            System.out.println("2. Search Film by KODE_AV (Bonus)");
            System.out.println("3. Count Total Film per Category (Bonus)");
            System.out.println("4. Exit");
            System.out.print("Pilih: ");

            String choice = scanner.nextLine();

            if ("1".equals(choice)) {
                browseCategory(scanner);
            } else if ("2".equals(choice)) {
                searchFilm(scanner);
            } else if ("3".equals(choice)) {
                countFilm();
            } else if ("4".equals(choice)) {
                break;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }

        scanner.close();
    }

    private static void initData() {
        Map<String, Map<String, Map<String, List<Film>>>> teenYo = new LinkedHashMap<>();

        Map<String, Map<String, List<Film>>> schoolGirl = new LinkedHashMap<>();
        Map<String, List<Film>> uniform = new LinkedHashMap<>();
        List<Film> summerEditionFilms = new ArrayList<>();
        summerEditionFilms.add(new Film("Artist A", "ABX-001", LocalDate.parse("2026-01-15"), "TEEN YO", "SUMMER EDITION"));
        summerEditionFilms.add(new Film("Artist B", "ABX-002", LocalDate.parse("2026-02-20"), "TEEN YO", "SUMMER EDITION"));
        uniform.put("SUMMER EDITION", summerEditionFilms);
        schoolGirl.put("UNIFORM", uniform);

        Map<String, List<Film>> casual = new LinkedHashMap<>();
        casual.put("WEEKEND EDITION", new ArrayList<>());
        schoolGirl.put("CASUAL", casual);
        teenYo.put("SCHOOL GIRL", schoolGirl);

        Map<String, Map<String, List<Film>>> college = new LinkedHashMap<>();
        Map<String, List<Film>> freshman = new LinkedHashMap<>();
        freshman.put("DEBUT 2026", new ArrayList<>());
        college.put("FRESHMAN", freshman);

        Map<String, List<Film>> senior = new LinkedHashMap<>();
        senior.put("GRADUATION SPECIAL", new ArrayList<>());
        college.put("SENIOR", senior);
        teenYo.put("COLLEGE", college);

        database.put("TEEN YO", teenYo);

        Map<String, Map<String, Map<String, List<Film>>>> beautifulGirl = new LinkedHashMap<>();
        Map<String, Map<String, List<Film>>> officeLady = new LinkedHashMap<>();
        Map<String, List<Film>> suit = new LinkedHashMap<>();
        suit.put("WINTER EDITION", new ArrayList<>());
        officeLady.put("SUIT", suit);
        beautifulGirl.put("OFFICE LADY", officeLady);
        database.put("BEAUTIFUL GIRL", beautifulGirl);

        Map<String, Map<String, Map<String, List<Film>>>> newComer = new LinkedHashMap<>();
        Map<String, Map<String, List<Film>>> idol = new LinkedHashMap<>();
        Map<String, List<Film>> stage = new LinkedHashMap<>();
        List<Film> liveFilms = new ArrayList<>();
        liveFilms.add(new Film("Artist C", "NWC-099", LocalDate.parse("2026-05-10"), "NEW COMER 2026", "LIVE CONCERT"));
        stage.put("LIVE CONCERT", liveFilms);
        idol.put("STAGE", stage);
        newComer.put("IDOL", idol);
        database.put("NEW COMER 2026", newComer);
    }

    private static void browseCategory(Scanner scanner) {
        String level1Key = selectKey(database, scanner, "FILM CATEGORY", "Pilih kategori");
        if (level1Key == null) return;
        Map<String, Map<String, Map<String, List<Film>>>> level1Map = database.get(level1Key);

        String level2Key = selectKey(level1Map, scanner, "SUB CATEGORY: " + level1Key, "Pilih sub kategori");
        if (level2Key == null) return;
        Map<String, Map<String, List<Film>>> level2Map = level1Map.get(level2Key);

        String level3Key = selectKey(level2Map, scanner, "SUB SUB CATEGORY: " + level2Key, "Pilih sub sub kategori");
        if (level3Key == null) return;
        Map<String, List<Film>> level3Map = level2Map.get(level3Key);

        String level4Key = selectKey(level3Map, scanner, "LEVEL 4: " + level3Key, "Pilih");
        if (level4Key == null) return;
        List<Film> films = level3Map.get(level4Key);

        System.out.println("=== DATA FILM: " + level4Key + " ===");
        printTable(films);
    }

    private static <K, V> K selectKey(Map<K, V> map, Scanner scanner, String title, String prompt) {
        System.out.println("\n=== " + title + " ===");
        List<K> keys = new ArrayList<>(map.keySet());
        for (int i = 0; i < keys.size(); i++) {
            System.out.println((i + 1) + ". " + keys.get(i));
        }
        System.out.print(prompt + ": ");
        try {
            int input = Integer.parseInt(scanner.nextLine());
            if (input < 1 || input > keys.size()) {
                System.out.println("Pilihan di luar jangkauan.");
                return null;
            }
            return keys.get(input - 1);
        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka.");
            return null;
        }
    }

    private static void searchFilm(Scanner scanner) {
        System.out.print("\nMasukkan KODE_AV yang dicari: ");
        String keyword = scanner.nextLine().trim();
        List<Film> foundFilms = new ArrayList<>();

        for (Map<String, Map<String, Map<String, List<Film>>>> l1 : database.values()) {
            for (Map<String, Map<String, List<Film>>> l2 : l1.values()) {
                for (Map<String, List<Film>> l3 : l2.values()) {
                    for (List<Film> l4 : l3.values()) {
                        for (Film film : l4) {
                            if (film.getAvCode().equalsIgnoreCase(keyword)) {
                                foundFilms.add(film);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("=== HASIL PENCARIAN ===");
        printTable(foundFilms);
    }

    private static void countFilm() {
        System.out.println("\n=== JUMLAH FILM PER CATEGORY ===");
        for (Map.Entry<String, Map<String, Map<String, Map<String, List<Film>>>>> entry1 : database.entrySet()) {
            int count = 0;
            for (Map<String, Map<String, List<Film>>> l2 : entry1.getValue().values()) {
                for (Map<String, List<Film>> l3 : l2.values()) {
                    for (List<Film> l4 : l3.values()) {
                        count += l4.size();
                    }
                }
            }
            System.out.println(entry1.getKey() + " : " + count + " film");
        }
    }

    private static void printTable(List<Film> films) {
        System.out.println("+----+---------------+----------+----------------+-----------------+------------------+");
        System.out.printf("| %-2s | %-13s | %-8s | %-14s | %-15s | %-16s |\n",
                "No", "NAMA_ARTIST", "KODE_AV", "TGL_RELEASE", "CATEGORY", "SUB_CATEGORY");
        System.out.println("+----+---------------+----------+----------------+-----------------+------------------+");

        if (films == null || films.isEmpty()) {
            System.out.printf("| %-81s |\n", "Tidak ada data film di kategori ini.");
        } else {
            int index = 1;
            for (Film film : films) {
                System.out.printf("| %-2d | %-13s | %-8s | %-14s | %-15s | %-16s |\n",
                        index++,
                        film.getArtistName(),
                        film.getAvCode(),
                        film.getReleaseDate().toString(),
                        film.getCategory(),
                        film.getSubCategory());
            }
        }
        System.out.println("+----+---------------+----------+----------------+-----------------+------------------+");
    }
}
