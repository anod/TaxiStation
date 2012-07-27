/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.db.repositories;

import com.station.taxi.model.Receipt;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author alex
 */
public interface ReceiptRepository extends CrudRepository<Receipt, Long> {

}
