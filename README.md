# Inventory Management — Product / GTIN / Batch (Spring Boot + JPA)

## Project overview
This project demonstrates a simple inventory backend using Spring Boot, JPA (Hibernate), and H2 database.  
It models `Product`, `Gtin`, and `Batch`, and exposes REST APIs to:
1. Insert sample rows in all tables (`POST /api/data/init`)
2. Query batches by GTIN (`GET /api/gtins/{gtin}`)
3. Filter GTINs such that:
   ----To get all the gtins containing batches with positive available quantity
   --- For batches containing negative or zero available quantity, I want just
      latest batch (based on inwardedOn filter)
---

## Folder structure (where to write code)

src/main/java/com/example/inventory/
│
├── InventoryApplication.java          <-- Main class (starts app)
│
├── entity/                            <-- database tables as Java classes
│   ├── Product.java
│   ├── Gtin.java
│   └── Batch.java
│
├── repository/                        <-- For database queries
│   ├── ProductRepository.java
│   ├── GtinRepository.java
│   └── BatchRepository.java
│
├── service/                           <-- Business logic
│   ├── DataInitService.java
│   └── GtinService.java
│
├── controller/                        <-- REST API endpoints
│   └── GtinController.java
│
└── dto/                               <-- Objects to send as API response
    ├── BatchDto.java
    └── GtinResponseDto.java


   ***** API's

**PostRequest

http://localhost:8080/api/data/init

---saves aall the sample data 

**PostRequest

http://localhost:8080/api/gtins/{gtin}/batches

---Add batch to the specific Gtin


{
  "batchCode": "B8",
  "availableQuantity": 250,
  "inwardedOn": "2025-11-12"
}


**GetRequest

http://localhost:8080/api/gtins/G1

---Get the details of specific Gtins

**GetRequest

http://localhost:8080/api/gtins/filtered

--- get the Gtins filtered

Global trade item number


****STEP BY STEP APPROACH ****


1.Design database model (entity/)

      Product: productId, productName, createdOn
      
      Gtin: id, gtin, product (@ManyToOne)
      
      Batch: batchId, batchCode, availableQuantity, inwardedOn, gtin (@ManyToOne)

2.Repositories (repository/)

      ProductRepository extends JpaRepository<Product,Long>
      
      GtinRepository extends JpaRepository<Gtin,Long> with Optional<Gtin> findByGtin(String gtin)
      
      BatchRepository extends JpaRepository<Batch,Long> with methods:
      
                List<Batch> findByGtin_GtinAndAvailableQuantityGreaterThan(String gtin, int qty);
                Optional<Batch> findTopByGtin_GtinAndAvailableQuantityLessThanEqualOrderByInwardedOnDesc(String gtin, int qty);
                List<Batch> findByGtin(Gtin gtin);
                List<Batch> findByGtin_GtinOrderByInwardedOnDesc(String gtin);


3.DTOs (dto/)

          BatchDto with toEntity(Gtin) and fromEntity(Batch)
          
          GtinResponseDto contains String gtin and List<BatchDto> batches

4.Service layer (service/GtinService.java)

        initData() — seed sample data (save products -> save gtins -> save batches)
        
        createBatch(String gtin, BatchDto dto) — find GTIN, convert DTO→entity, save batch
        
        getBatchesByGtin(String gtin) — find GTIN, fetch batches, convert to DTOs
        
        getFilteredGtins() — implement the filtering logic described above

5.Controller (controller/GtinController.java)

        Expose endpoints: POST /data/init, POST /gtins/{gtin}/batches, GET /gtins/{gtin}, GET /gtins/filtered

6.Testing

        Use Postman or curl to call endpoints
        
        Optionally verify data in H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:inventorydb)



VEDIO EXPLINATION : https://drive.google.com/file/d/1l9uSCRIxwI2e-KZbEchoZPla4o4GLGmU/view?usp=sharing



CONCLUSION

The project models Product → Gtin → Batch properly using JPA relationships.

Implemented APIs:

              POST /api/data/init — seed sample data
              
              POST /api/gtins/{gtin}/batches — add batches to a GTIN
              
              GET /api/gtins/{gtin} — fetch batches for a GTIN
              
              GET /api/gtins/filtered — returns GTINs with positive batches plus the latest zero/negative batch



