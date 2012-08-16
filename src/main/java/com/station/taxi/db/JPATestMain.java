package com.station.taxi.db;

import com.station.taxi.db.repositories.ReceiptRepository;
import com.station.taxi.gui.GuiMain;
import com.station.taxi.model.Receipt;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Test file for JPA Storage
 * @author alex
 */
public class JPATestMain {

	private static final String CONFIG_PATH = "GuiXMLConfig.xml";

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_PATH, GuiMain.class);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TaxiStationPU");
		EntityManager em = emf.createEntityManager();
		Receipt r1 = em.find(Receipt.class, 1L);
		System.out.println(r1);
		
		ReceiptRepository repository = applicationContext.getBean(ReceiptRepository.class);

		Receipt newReceipt1 = new Receipt(new Date(), new Date(), 16.5f, 2);
		Receipt newReceipt2 = new Receipt(new Date(), new Date(), 15.5f, 1);
		Receipt newReceipt3 = new Receipt(new Date(), new Date(), 17.5f, 3);

	//	repository.save(newReceipt1);
//		repository.save(newReceipt2);
//		repository.save(newReceipt3);
	
			System.out.println("====-11111-=====");
			
		Iterable<Receipt> all = repository.findAll();
		for(Receipt r: all) {
			System.out.println(r);
		}

			System.out.println("====-22222-====");
		
		all = repository.findByPassengersCount(2);
		for(Receipt r: all) {
			System.out.println(r);
		}
		
		
	}
}
