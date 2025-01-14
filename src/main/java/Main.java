import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

            File jsonFile = new File("src/main/resources/carti.json");
            Map<String, Carte> map = mapper.readValue(jsonFile, new TypeReference<>() {});

            map.forEach((key, value) ->
                    System.out.println("ID: " + key + " - Carte: " + value)
            );

        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceti id-ul cartii ce doriti sa stergeti : ");
        String id = scanner.nextLine();
        Carte carte = map.remove(id);
        System.out.println("\n");
        map.forEach((key, value) ->
                System.out.println("ID: " + key + " - Carte: " + value)
        );

        System.out.println("\nIntroduceti detaliile pentru cartea pe care doriti sa o adaugati:");

        System.out.print("ID-ul cartii: ");
        String idCarte = scanner.nextLine();

        System.out.print("Titlul cartii: ");
        String titlul = scanner.nextLine();

        System.out.print("Autorul cartii: ");
        String autorul = scanner.nextLine();

        System.out.print("Anul aparitiei: ");
        int anul = Integer.parseInt(scanner.nextLine());

        Carte carteNoua = new Carte(titlul, autorul, anul);

        Carte carteAdaugata = map.putIfAbsent(idCarte, carteNoua);

        System.out.println("\nColectia dupa adaugare:");
        map.forEach((key, value) ->
                System.out.println("ID: " + key + " - Carte: " + value)
        );

        mapper.writeValue(jsonFile, map);

        Set<Carte> cartiHarari = map.values().stream()
                .filter(c -> "Yuval Noah Harari".equals(c.autorul()))
                .collect(Collectors.toSet());

        System.out.println("Cartile autorului Yuval Noah Harari:\n");
        cartiHarari.forEach(System.out::println);

    }
}
