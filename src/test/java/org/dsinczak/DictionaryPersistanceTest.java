package org.dsinczak;

import org.dsinczak.publishedlanguage.Community;
import org.dsinczak.publishedlanguage.Country;
import org.dsinczak.publishedlanguage.Currency;
import org.dsinczak.publishedlanguage.Province;
import org.dsinczak.publishedlanguage.dict.DictionaryRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DictionaryPersistanceTest {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @After
    public void after() {
        dictionaryRepository.deleteAll(Currency.class);
    }

    @Test
    public void testDictionaryPersistenceSave() {
        // When
        Currency persistedCurrency = dictionaryRepository.save(new Currency("USD", "Dollar"));

        // Then
        Assert.assertNotNull(persistedCurrency);
        Assert.assertTrue(persistedCurrency.getId() > 0);
        System.out.println("Persisted entity: " + persistedCurrency);
    }

    @Test
    public void testDictionaryPersistenceFind() {
        // Given
        Currency persistedCurrency = dictionaryRepository.save(new Currency("PLN", "Dollar"));
        System.out.println("Persisted currency: " + persistedCurrency);

        // When
        Currency findCurrency = dictionaryRepository.find("PLN", Currency.class);
        System.out.println("Find currency: " + findCurrency);

        // Then
        Assert.assertEquals(persistedCurrency.getId(), findCurrency.getId());
    }

    @Test
    public void testDictionaryPersistenceDeleteAllAndFindAll() {
        // Given
        dictionaryRepository.save(new Currency("AAA", "aaa"));
        dictionaryRepository.save(new Currency("BBB", "bbb"));
        dictionaryRepository.deleteAll(Currency.class);
        dictionaryRepository.save(new Currency("AAA", "aaa"));
        dictionaryRepository.save(new Currency("BBB", "bbb"));

        // When
        List<Currency> currencies = dictionaryRepository.findAll(Currency.class);

        // Then
        System.out.println("Retrieved currencies: " + currencies);
        Assert.assertEquals(2, currencies.size());
    }

    @Test
    public void testHierarchicalDictionaries() {

        // Given, when
        Country poland = dictionaryRepository.save(new Country("PL", "Polska"));

        Province mazowiedzkie = dictionaryRepository.save(new Province(poland, "WOJ_MAZOW", "Mazowieckie"));
        Community pruszkow = dictionaryRepository.save(new Community(mazowiedzkie, "GM_PRUSZK", "Pruszkow"));
        Community wolomin = dictionaryRepository.save(new Community(mazowiedzkie, "GM_WOLOM", "Wołomin"));

        Province lodzkie = dictionaryRepository.save(new Province(poland, "WOJ_LDZ", "Łódzkie"));
        Community zgierskie = dictionaryRepository.save(new Community(lodzkie, "GM_ZGR", "Zgierz"));
        Community sieradz = dictionaryRepository.save(new Community(lodzkie, "GM_SIERADZ", "Sieradz"));


        // Then

        System.out.println(zgierskie);
        System.out.println(sieradz);

        dictionaryRepository.findAll(Community.class).forEach(
                community -> {
                    Assert.assertNotNull(community.getProvince());
                    Assert.assertNotNull(community.getProvince().getCountry());
                }
        );

        Assert.assertNotNull(wolomin.getId());
        Assert.assertNotNull(wolomin.getProvince().getId());
        Assert.assertNotNull(wolomin.getProvince().getCountry().getId());

        Assert.assertNotNull(pruszkow.getId());
        Assert.assertNotNull(pruszkow.getProvince().getId());
        Assert.assertNotNull(pruszkow.getProvince().getCountry().getId());
    }

}
