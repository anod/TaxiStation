/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.db.repositories;

import com.station.taxi.model.Receipt;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author alex
 */

public interface ReceiptRepository extends CrudRepository<Receipt, Long> {

	
	@Query("select r from Receipt r where r.mPassengersCount = ?1")
	List<Receipt> findByPassengerNumber(Integer number);
}
