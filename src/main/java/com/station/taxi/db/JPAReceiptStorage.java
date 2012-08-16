package com.station.taxi.db;

import com.station.taxi.db.repositories.ReceiptRepository;
import com.station.taxi.model.Receipt;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * JPA Receipt Storage implementation
 * @author alex
 */
public class JPAReceiptStorage implements ReceiptStorage {
	
	@Autowired
	private ReceiptRepository repository;
	
	@Override
	public Iterable<Receipt> load() {
		return repository.findAll();
	}

	@Override
	public void save(Receipt receipt) {
		repository.save(receipt);
	}

}
