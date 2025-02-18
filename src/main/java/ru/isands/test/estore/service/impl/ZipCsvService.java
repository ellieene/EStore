package ru.isands.test.estore.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.isands.test.estore.model.entity.ElectroItem;
import ru.isands.test.estore.model.entity.ElectroShop;
import ru.isands.test.estore.model.entity.Employee;
import ru.isands.test.estore.model.entity.Purchase;
import ru.isands.test.estore.model.entity.directory.ElectroType;
import ru.isands.test.estore.model.entity.directory.Position;
import ru.isands.test.estore.model.entity.directory.PurchaseType;
import ru.isands.test.estore.model.entity.directory.Shop;
import ru.isands.test.estore.repository.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZipCsvService {

    private final ShopRepository shopRepository;
    private final EmployeeRepository employeeRepository;
    private final ElectroShopRepository electroShopRepository;
    private final ElectroTypeRepository electroTypeRepository;
    private final JobTitleRepository positionRepository;
    private final ElectroItemRepository electroItemRepository;
    private final PurchaseTypeRepository purchaseTypeRepository;
    private final PurchaseRepository purchaseRepository;

    public void processZipFile(MultipartFile zipFile) throws IOException {
        File tempFile = File.createTempFile("uploaded-", ".zip");
        zipFile.transferTo(tempFile);

        List<ZipArchiveEntry> entriesList = new ArrayList<>();

        try (ZipFile zip = new ZipFile(tempFile, StandardCharsets.UTF_8.name())) {
            Enumeration<ZipArchiveEntry> entries = zip.getEntries();

            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(".csv")) {
                    entriesList.add(entry);
                }
            }

            entriesList.sort((e1, e2) -> {
                String name1 = e1.getName().replace(".csv", "");
                String name2 = e2.getName().replace(".csv", "");

                int priority1 = getPriority(name1);
                int priority2 = getPriority(name2);

                return Integer.compare(priority1, priority2);
            });

            for (ZipArchiveEntry entry : entriesList) {
                processCsvFile(zip.getInputStream(entry), entry.getName());
            }
        }
        tempFile.delete();
    }

    private int getPriority(String fileName) {
        switch (fileName) {
            case "PositionType":
                return 1;
            case "PurchaseType":
                return 2;
            case "ElectroType":
                return 3;
            case "Shop":
                return 4;
            case "Employee":
                return 5;
            case "ElectroItem":
                return 6;
            case "ElectroShop":
                return 7;
            case "Purchase":
                return 8;
            default:
                return 9;
        }
    }

    private void processCsvFile(InputStream inputStream, String fileName) throws IOException {
        Charset windows1251 = Charset.forName("Windows-1251");
        try (InputStreamReader reader = new InputStreamReader(inputStream, windows1251)) {
            CSVFormat format = CSVFormat.DEFAULT
                    .withDelimiter(';')
                    .withFirstRecordAsHeader()
                    .withAllowMissingColumnNames(true);

            Iterable<CSVRecord> records = format.parse(reader);

            if (!records.iterator().hasNext()) return;

            String entityName = fileName.replace(".csv", "");
            switch (entityName) {
                case "Shop":
                    saveShops(records);
                    break;
                case "PurchaseType":
                    savePurchaseTypes(records);
                    break;
                case "PositionType":
                    savePositionsTypes(records);
                    break;
                case "ElectroType":
                    saveElectroTypes(records);
                    break;
                case "Purchase":
                    savePurchases(records);
                    break;
                case "ElectroItem":
                    saveElectroItems(records);
                    break;
                case "Employee":
                    saveEmployees(records);
                    break;
                case "ElectroShop":
                    saveElectroShops(records);
                    break;
                default:
                    System.out.println("Пропущен файл: " + fileName);
            }
        }
    }

    private void saveShops(Iterable<CSVRecord> records) {
        List<Shop> shops = new ArrayList<>();
        for (CSVRecord record : records) {
            try {
                Shop shop = new Shop();
                shop.setId(Long.parseLong(record.get(0)));
                shop.setName(record.get(1));
                shop.setAddress(record.get(2));
                shops.add(shop);
            } catch (Exception e) {
                System.out.println("Ошибка при обработке записи Shop: " + e.getMessage());
            }
        }
        shopRepository.saveAll(shops);
    }

    private void saveElectroItems(Iterable<CSVRecord> records) {
        List<ElectroItem> electroItems = new ArrayList<>();
        for (CSVRecord record : records) {
            try {
                ElectroItem electroItem = new ElectroItem();
                electroItem.setId(Long.parseLong(record.get(0)));
                electroItem.setName(record.get(1));

                String typeIdStr = record.get(2);
                if (typeIdStr != null && !typeIdStr.isEmpty()) {
                    Long typeId = Long.parseLong(typeIdStr);
                    ElectroType electroType = electroTypeRepository.findById(typeId)
                            .orElseThrow(() -> new RuntimeException("ElectroType не найден: " + typeId));
                    electroItem.setType(electroType);
                }

                electroItems.add(electroItem);
            } catch (Exception e) {
                System.out.println("Ошибка при обработке записи ElectroItem: " + e.getMessage());
            }
        }
        electroItemRepository.saveAll(electroItems);
    }

    private void saveEmployees(Iterable<CSVRecord> records) {
        List<Employee> employees = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); // Формат даты

        for (CSVRecord record : records) {
            try {
                Employee employee = new Employee();
                employee.setId(Long.parseLong(record.get(0)));
                employee.setFirstName(record.get(1));
                employee.setLastName(record.get(2));
                employee.setPatronymic(record.get(3));

                String dateStr = record.get(4);
                LocalDate birthDate = LocalDate.parse(dateStr, formatter);
                employee.setBirthDate(birthDate);

                Long positionId = Long.parseLong(record.get(5));
                Position position = positionRepository.findById(positionId)
                        .orElseThrow(() -> new RuntimeException("Position не найдена: " + positionId));
                employee.setPosition(position);

                Long shopId = Long.parseLong(record.get(6));
                Shop shop = shopRepository.findById(shopId)
                        .orElseThrow(() -> new RuntimeException("Shop не найден: " + shopId));
                employee.setShop(shop);

                employee.setGender(Boolean.parseBoolean(record.get(7)));

                List<ElectroType> electroTypes = new ArrayList<>();
                if (!record.get(8).isEmpty()) {
                    String[] electroTypeIds = record.get(8).split(",");
                    for (String id : electroTypeIds) {
                        Long electroId = Long.parseLong(id.trim());
                        ElectroType type = electroTypeRepository.findById(electroId)
                                .orElseThrow(() -> new RuntimeException("ElectroType не найден: " + electroId));
                        electroTypes.add(type);
                    }
                }
                employee.setElectroTypes(electroTypes);

                employees.add(employee);
            } catch (Exception e) {
                System.out.println("Ошибка при обработке записи Employee: " + e.getMessage());
            }
        }
        employeeRepository.saveAll(employees);
    }

    private void saveElectroShops(Iterable<CSVRecord> records) {
        List<ElectroShop> electroShops = new ArrayList<>();
        for (CSVRecord record : records) {
            try {
                Long shopId = Long.parseLong(record.get(0));
                Shop shop = shopRepository.findById(shopId)
                        .orElseThrow(() -> new RuntimeException("Shop не найден: " + shopId));

                Long electroItemId = Long.parseLong(record.get(1));
                ElectroItem electroItem = electroItemRepository.findById(electroItemId)
                        .orElseThrow(() -> new RuntimeException("ElectroItem не найден: " + electroItemId));


                ElectroShop electroShop = new ElectroShop();
                electroShop.setShop(shop);
                electroShop.setElectroItem(electroItem);

                electroShops.add(electroShop);
            } catch (Exception e) {
                System.out.println("Ошибка при обработке записи ElectroShop: " + e.getMessage());
            }
        }
        electroShopRepository.saveAll(electroShops);
    }

    private void savePositionsTypes(Iterable<CSVRecord> records) {
        List<Position> positions = new ArrayList<>();
        for (CSVRecord record : records) {
            try {
                Position position = new Position();
                position.setId(Long.parseLong(record.get(0)));
                position.setName(record.get(1));
                positions.add(position);
            } catch (Exception e) {
                System.out.println("Ошибка при обработке записи Position: " + e.getMessage());
            }
        }
        positionRepository.saveAll(positions);
    }

    private void saveElectroTypes(Iterable<CSVRecord> records) {
        List<ElectroType> electroTypes = new ArrayList<>();
        for (CSVRecord record : records) {
            try {
                ElectroType electroType = new ElectroType();
                electroType.setId(Long.parseLong(record.get(0)));
                electroType.setName(record.get(1));
                electroTypes.add(electroType);
            } catch (Exception e) {
                System.out.println("Ошибка при обработке записи ElectroType: " + e.getMessage());
            }
        }
        electroTypeRepository.saveAll(electroTypes);
    }

    private void savePurchases(Iterable<CSVRecord> records) {
        List<Purchase> purchases = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"); // Формат даты и времени

        for (CSVRecord record : records) {
            try {
                Long electroItemId = Long.parseLong(record.get(1));
                ElectroItem electroItem = electroItemRepository.findById(electroItemId)
                        .orElseThrow(() -> new RuntimeException("ElectroItem не найден: " + electroItemId));

                Long employeeId = Long.parseLong(record.get(2));
                Employee employee = employeeRepository.findById(employeeId)
                        .orElseThrow(() -> new RuntimeException("Employee не найден: " + employeeId));

                String dateStr = record.get(3);
                LocalDateTime date = LocalDateTime.parse(dateStr, formatter);

                Long typeId = Long.parseLong(record.get(4));
                PurchaseType purchaseType = purchaseTypeRepository.findById(typeId)
                        .orElseThrow(() -> new RuntimeException("PurchaseType не найден: " + typeId));

                Long shopId = Long.parseLong(record.get(5));
                Shop shop = shopRepository.findById(shopId)
                        .orElseThrow(() -> new RuntimeException("Shop не найден: " + shopId));

                Purchase purchase = Purchase.builder()
                        .id(Long.parseLong(record.get(0)))
                        .electroItem(electroItem)
                        .employee(employee)
                        .date(date)
                        .purchaseType(purchaseType)
                        .shop(shop)
                        .build();

                purchases.add(purchase);
            } catch (Exception e) {
                System.out.println("Ошибка при обработке записи Purchase: " + e.getMessage());
            }
        }
        purchaseRepository.saveAll(purchases);
    }

    private void savePurchaseTypes(Iterable<CSVRecord> records) {
        List<PurchaseType> purchaseTypes = new ArrayList<>();
        for (CSVRecord record : records) {
            try {
                PurchaseType purchaseType = new PurchaseType();
                purchaseType.setId(Long.parseLong(record.get(0)));
                purchaseType.setName(record.get(1).trim());
                purchaseTypes.add(purchaseType);
            } catch (Exception e) {
                System.out.println("Ошибка при обработке записи PurchaseType: " + e.getMessage());
            }
        }
        purchaseTypeRepository.saveAll(purchaseTypes);
    }
}