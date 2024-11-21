package upc.edu.oneup.controller;

import upc.edu.oneup.exception.ResourceNotFoundException;
import upc.edu.oneup.model.Supplier;
import upc.edu.oneup.service.SupplierService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Suppliers", description = "The Supplier API")
@RestController
@RequestMapping("/api/smart/v1/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // Endpoint: GET /api/oneup/v1/suppliers
    // Obtiene todos los suppliers
    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    // Endpoint: GET /api/oneup/v1/suppliers/{id}
    // Obtiene un supplier por ID
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable int id) {
        Supplier supplier = supplierService.getSupplierById(id);
        if (supplier != null) {
            return new ResponseEntity<>(supplier, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Supplier with ID " + id + " not found");
        }
    }

    // Endpoint: POST /api/oneup/v1/suppliers
    // Crea un nuevo supplier
    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        if (supplier.getName() == null || supplier.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier name is required");
        }
        Supplier newSupplier = supplierService.saveSupplier(supplier);
        return new ResponseEntity<>(newSupplier, HttpStatus.CREATED);
    }

    // Endpoint: DELETE /api/oneup/v1/suppliers/{id}
    // Elimina un supplier por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable int id) {
        Supplier supplier = supplierService.getSupplierById(id);
        if (supplier != null) {
            supplierService.deleteSupplier(id);
            return new ResponseEntity<>("Supplier with ID " + id + " was deleted", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Supplier with ID " + id + " not found");
        }
    }
}
