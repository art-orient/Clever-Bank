package com.art.clever.model.dao;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.exception.ConnectionPoolException;
import com.art.clever.model.dao.impl.BankDaoJdbc;
import com.art.clever.model.entity.Bank;
import com.art.clever.model.pool.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BankDaoTest {
    private static BankDao bankDao;
    private static final ConnectionPool POOL = ConnectionPool.INSTANCE;
    private static Bank bank;

    @BeforeAll
    static void init() throws ConnectionPoolException {
        POOL.initPool();
        bankDao = BankDaoJdbc.getInstance();
        bank = new Bank();
        bank.setCodeBic("ABXX");
        bank.setName("Clever-Bank");
    }

    @AfterAll
    static void teardown() throws ConnectionPoolException {
        POOL.destroyPool();
    }

    @Test
    void addBankTest() throws CleverDatabaseException {
        bankDao.add(bank);
        Bank bankFromDb = bankDao.getByBicCode(bank.getCodeBic()).get();
        assertEquals("ABXX", bankFromDb.getCodeBic(), "BIC codes must be equals");
        bankDao.delete(bank.getCodeBic());
    }

    @Test
    void getAllBanksTest() throws CleverDatabaseException {
        bankDao.add(bank);
        List<Bank> banks = bankDao.getAll();
        assertTrue(banks.size() > 0);
        assertTrue(banks.contains(bank));
        bankDao.delete(bank.getCodeBic());
    }

    @Test
    void getBankByBicCodeTest() throws CleverDatabaseException {
        bankDao.add(bank);
        Bank bankFromDb = bankDao.getByBicCode(bank.getCodeBic()).get();
        assertEquals(bank.getCodeBic(), bankFromDb.getCodeBic());
        assertEquals(bank.getName(), bankFromDb.getName());
        bankDao.delete(bank.getCodeBic());
    }

    @Test
    void getBankByNameTest() throws CleverDatabaseException {
        bankDao.add(bank);
        Bank bankFromDb = bankDao.getByBankName(bank.getName()).get();
        assertEquals(bank.getCodeBic(), bankFromDb.getCodeBic());
        assertEquals(bank.getName(), bankFromDb.getName());
        bankDao.delete(bank.getCodeBic());
    }

    @Test
    void updateBankTest() throws CleverDatabaseException {
        bankDao.add(bank);
        bank.setName("The best big bank");
        bankDao.update(bank);
        Bank bankFromDb = bankDao.getByBicCode(bank.getCodeBic()).get();
        assertEquals("The best big bank", bankFromDb.getName(), "Updated names must be equal.");
        bankDao.delete(bank.getCodeBic());
    }

    @Test
    void deleteUserTest() throws CleverDatabaseException {
        bankDao.add(bank);
        Bank bankFromDb = bankDao.getByBicCode(bank.getCodeBic()).get();
        assertNotNull(bankFromDb, "User must be saved");
        bankDao.delete(bank.getCodeBic());
        assertTrue(bankDao.getByBicCode(bank.getCodeBic()).isEmpty(), "Bank should be deleted");
    }
}
