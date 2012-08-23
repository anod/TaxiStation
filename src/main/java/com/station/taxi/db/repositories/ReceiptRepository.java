package com.station.taxi.db.repositories;

import com.station.taxi.model.Receipt;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * JPA Receipt Repository
 * @author alex
 */
public interface ReceiptRepository extends CrudRepository<Receipt, Long> {
 
	//@Query("select r from Receipt r where r.mPassengersCount = ?1")
        List<Receipt> findBymPassengersCount(Integer count);
        
        List<Receipt> findBymCabID(Integer id);
        
        @Query("select r from Receipt r where r.mCabID = ?1 and r.mStartTime>= ?2")
        List<Receipt> findBymCabIDandmStartTime(Integer id,Date starttime);
        
        @Query("select r from Receipt r where r.mCabID = ?1 and r.mStartTime<=?2 and r.mEndTime>=$3")
        List<Receipt> findBymCabID(Integer id,Date startTime,Date endTime);
        
        @Query("select r from Receipt r where r.mStartTime<=?1")
        List<Receipt> findAllinTimeRange(Date startTime);
     
 
}
