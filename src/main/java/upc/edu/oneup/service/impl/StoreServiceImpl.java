package upc.edu.oneup.service.impl;

import upc.edu.oneup.model.Store;
import upc.edu.oneup.repository.StroreRepository;
import upc.edu.oneup.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StroreRepository storeRepository;

    @Autowired
    public StoreServiceImpl(StroreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<Store> getStoresByUserId(int userId) {
        return storeRepository.findByUserId(userId);
    }

    @Override
    public Store getStoreById(int storeId) {
        return storeRepository.findById(storeId).orElse(null);
    }

    @Override
    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public void deleteStore(int storeId) {
        storeRepository.deleteById(storeId);
    }
}
