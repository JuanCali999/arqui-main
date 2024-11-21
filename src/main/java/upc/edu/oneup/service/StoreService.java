package upc.edu.oneup.service;

import upc.edu.oneup.model.Store;

import java.util.List;

public interface StoreService {
    List<Store> getStoresByUserId(int userId); // Obtener tiendas por userId
    Store getStoreById(int storeId); // Obtener una tienda por ID
    Store saveStore(Store store); // Guardar o actualizar una tienda
    void deleteStore(int storeId); // Eliminar una tienda por ID
}
