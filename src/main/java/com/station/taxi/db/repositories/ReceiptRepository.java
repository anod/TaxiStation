package com.station.taxi.db.repositories;

import com.station.taxi.model.Receipt;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * JPA Receipt Repository
 * @author alex
 */
public interface ReceiptRepository extends CrudRepository<Receipt, Long> {
 
	/**
	 * Load receipts by passengers count
	 * @param count
	 * @return 
	 */
	@Query("select r from Receipt r where r.mPassengersCount = ?1")
	List<Receipt> findByPassengersCount(Integer count);
}
