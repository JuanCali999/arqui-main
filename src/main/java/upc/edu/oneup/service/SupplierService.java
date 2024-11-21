package upc.edu.oneup.service;

import upc.edu.oneup.model.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAllSuppliers(); // Obtener todos los suppliers
    Supplier getSupplierById(int id); // Obtener un supplier por ID
    Supplier saveSupplier(Supplier supplier); // Guardar un supplier
    void deleteSupplier(int id); // Eliminar un supplier por ID
}
