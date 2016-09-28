package org.dsinczak;

import org.dsinczak.domain.*;
import org.dsinczak.domain.dict.DictionaryRepository;
import org.dsinczak.domain.vo.Account;
import org.dsinczak.domain.vo.ValueDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DictionaryPersistanceApplicationTests {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Test
    public void testTransactionPersistence() {
        TransactionParticipant credit = new TransactionParticipant(
                new Account("12345678", "43223455"),
                new ValueDate());

        TransactionParticipant debit = new TransactionParticipant(
                new Account("98765233", "22433253"),
                new ValueDate());

        Currency currency = new Currency("EUR", "Euro");

        TransactionType transactionType = new TransactionType("DEPOSIT", "Add funds to an account by any method");

        Transaction transaction = new Transaction(credit, debit, new BigDecimal("121"), currency, transactionType);

        transaction = transactionRepository.save(transaction);

        Assert.assertNotNull(transaction);

        System.out.println("Created transaction: " + transaction);

        transactionRepository.deleteAll();
    }

    @Test
    public void testDictionaryPersistenceSave() {
        Currency persistedCurrency = dictionaryRepository.save(new Currency("USD", "Dollar"));

        Assert.assertNotNull(persistedCurrency);
        Assert.assertTrue(persistedCurrency.getId() > 0);
        System.out.println("Persisted entity: " + persistedCurrency);
    }

    @Test
    public void testDictionaryPersistenceFind() {
        Currency persistedCurrency = dictionaryRepository.save(new Currency("PLN", "Dollar"));
        System.out.println("Persisted currency: " + persistedCurrency);

        Currency findCurrency = dictionaryRepository.find("PLN", Currency.class);
        System.out.println("Find currency: " + findCurrency);

        Assert.assertEquals(persistedCurrency.getId(),findCurrency.getId());
    }

    @Test
    public void testDictionaryPersistenceDeleteAllAndFindAll() {
        dictionaryRepository.save(new Currency("AAA", "aaa"));
        dictionaryRepository.save(new Currency("BBB", "bbb"));
        dictionaryRepository.deleteAll(Currency.class);
        dictionaryRepository.save(new Currency("AAA", "aaa"));
        dictionaryRepository.save(new Currency("BBB", "bbb"));

        List<Currency> currencies = dictionaryRepository.findAll(Currency.class);

        System.out.println("Retrieved currencies: " + currencies);

        Assert.assertEquals(2,currencies.size());
    }


}
