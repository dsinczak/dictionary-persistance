package org.dsinczak;

import org.dsinczak.publishedlanguage.*;
import org.dsinczak.publishedlanguage.dict.DictionaryRepository;
import org.dsinczak.transaction.domain.Transaction;
import org.dsinczak.transaction.domain.TransactionParticipant;
import org.dsinczak.transaction.domain.TransactionRepository;
import org.dsinczak.transaction.domain.vo.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionPersistenceTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @After
    public void after() {
        transactionRepository.deleteAll();
        dictionaryRepository.deleteAll(Currency.class);
        dictionaryRepository.deleteAll(TransactionType.class);


        dictionaryRepository.deleteAll(City.class);
        dictionaryRepository.deleteAll(Community.class);
        dictionaryRepository.deleteAll(Province.class);
        dictionaryRepository.deleteAll(Country.class);
    }

    @Before
    public void before() throws Exception {
        dictionaryRepository.save(new Currency("EUR", "Euro"));
        dictionaryRepository.save(new Currency("PLN", "Zloty"));
        dictionaryRepository.save(new TransactionType("ATM", "ATM Deposit"));

        Country poland = dictionaryRepository.save(new Country("PL", "Polska"));
        Province mazowiedzkie = dictionaryRepository.save(new Province(poland, "WOJ_MAZOW", "Mazowieckie"));
        Community pruszkow = dictionaryRepository.save(new Community(mazowiedzkie, "GM_PRUSZK", "Pruszkow"));
        Community wolomin = dictionaryRepository.save(new Community(mazowiedzkie, "GM_WOLOM", "Wołomin"));
        dictionaryRepository.save(new City(wolomin, "CIT_TUROW", "Turów"));
        dictionaryRepository.save(new City(pruszkow, "CIT_PRUSZK", "Pruszków"));
    }

    @Test
    public void testTransactionPersistence() {
        // Given
        TransactionParticipant credit = new TransactionParticipant(
                new Account("12345678", "43223455"),
                new ValueDate());

        TransactionParticipant debit = new TransactionParticipant(
                new Account("98765233", "22433253"),
                new ValueDate());

        Currency currency = dictionaryRepository.find("EUR", Currency.class);
        TransactionType transactionType = dictionaryRepository.find("ATM", TransactionType.class);
        Transaction transaction = new Transaction(credit, debit, new BigDecimal("121"), currency, transactionType);

        // When
        transaction = transactionRepository.save(transaction);

        // Then
        System.out.println("Persisted transaction " + transaction);
        Assert.assertNotNull(transaction);
        Assert.assertNotNull(transaction.getMoney());
        Assert.assertNotNull(transaction.getMoney().getCurrency());
        Assert.assertNotNull(transaction.getMoney().getCurrency().getId());
        Assert.assertNotNull(transaction.getTransactionType());
        Assert.assertNotNull(transaction.getTransactionType().getId());
    }

    @Test
    public void testTransactionAddressPersistence() {
        // Given
        TransactionParticipant credit = new TransactionParticipant(
                new Account("12345678", "43223455"),
                new ValueDate(),
                new Address("Lipna 1/10", null,
                        new PostalCode("95-321"),
                        dictionaryRepository.find("CIT_PRUSZK", City.class))
        );

        TransactionParticipant debit = new TransactionParticipant(
                new Account("98765233", "22433253"),
                new ValueDate(),
                new Address("Zenon Szemrany", "Ciemny zakątek 9",
                        new PostalCode("98-121"),
                        dictionaryRepository.find("CIT_TUROW", City.class))
        );

        Currency currency = dictionaryRepository.find("EUR", Currency.class);
        TransactionType transactionType = dictionaryRepository.find("ATM", TransactionType.class);

        // When
        transactionRepository.save(new Transaction(credit, debit, new BigDecimal("1000000"), currency, transactionType));

        // Then
        Optional<Transaction> transaction = transactionRepository.findAll().stream().findFirst();
        Assert.assertTrue(transaction.isPresent());

        System.out.println(transaction.get());
        // TODO add more assertions
    }

}
