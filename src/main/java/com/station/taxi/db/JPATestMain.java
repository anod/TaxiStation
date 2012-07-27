package com.station.taxi.db;

import com.station.taxi.db.repositories.ReceiptRepository;
import com.station.taxi.gui.GuiMain;
import com.station.taxi.model.Receipt;
import java.util.Date;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author alex
 */
public class JPATestMain {

	private static final String CONFIG_PATH = "GuiXMLConfig.xml";

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_PATH, GuiMain.class);
		
		ReceiptRepository repository = applicationContext.getBean(ReceiptRepository.class);

		Receipt newReceipt1 = new Receipt(new Date(), new Date(), 16.5f, 2);
		Receipt newReceipt2 = new Receipt(new Date(), new Date(), 15.5f, 1);
		Receipt newReceipt3 = new Receipt(new Date(), new Date(), 17.5f, 3);

		repository.save(newReceipt1);
		repository.save(newReceipt2);
		repository.save(newReceipt3);
		
		Iterable<Receipt> all = repository.findAll();
		for(Receipt r: all) {
			System.out.println(r);
		}

			System.out.println("TEST");
		
		all = repository.findByPassengerNumber(2);
		for(Receipt r: all) {
			System.out.println(r);
		}
		
		
	}
}
