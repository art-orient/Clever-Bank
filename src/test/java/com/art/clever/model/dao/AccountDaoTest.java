package com.art.clever.model.dao;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.exception.ConnectionPoolException;
import com.art.clever.model.dao.impl.AccountDaoJdbc;
import com.art.clever.model.dao.impl.BankDaoJdbc;
import com.art.clever.model.dao.impl.UserDaoJdbc;
import com.art.clever.model.entity.Account;
import com.art.clever.model.entity.Bank;
import com.art.clever.model.entity.Currency;
import com.art.clever.model.entity.User;
import com.art.clever.model.pool.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountDaoTest {
    private static AccountDao accountDao;
    private static UserDao userDao;
    private static BankDao bankDao;
    private static final ConnectionPool POOL = ConnectionPool.INSTANCE;
    private static Account account;
    private static User user;
    private static Bank bank;

    @BeforeAll
    static void init() throws ConnectionPoolException {
        POOL.initPool();
        accountDao = AccountDaoJdbc.getInstance();
        userDao = UserDaoJdbc.getInstance();
        bankDao = BankDaoJdbc.getInstance();
        bank = new Bank();
        bank.setCodeBic("MMGN");
        bank.setName("SPORT-BANK");

        user = new User();
        user.setPassportId("3110570A053BT5");
        user.setLastName("Романов");
        user.setFirstName("Алексей");
        user.setSurName("Алексеевмч");

        account = new Account();
        account.setCodeIBAN("BY07 MMGN 3012 0000 3220 0000 0000");
        account.setBank(bank);
        account.setUser(user);
        account.setCurrency(Currency.BYN);
        account.setCreatedAt(Instant.now());
        account.setBalance(BigDecimal.valueOf(500.54));
    }

    @AfterAll
    static void teardown() throws ConnectionPoolException {
        POOL.destroyPool();
    }

    @Test
    void addAccountTest() throws CleverDatabaseException {
        userDao.add(user);
        bankDao.add(bank);
        accountDao.add(account);
        Account accountFromDb = accountDao.getByCodeIBAN(account.getCodeIBAN()).orElse(null);
        assert accountFromDb != null;
        assertEquals(user, accountFromDb.getUser(), "Users must be equals");
        assertEquals(bank, accountFromDb.getBank(), "Banks must be equals");
        assertEquals(account.getBalance(), accountFromDb.getBalance(), "Balances must be equals");
        accountDao.delete(account.getCodeIBAN());
        userDao.delete(user.getPassportId());
        bankDao.delete(bank.getCodeBic());
    }

    @Test
    void getAllAccountsTest() throws CleverDatabaseException {
        userDao.add(user);
        bankDao.add(bank);
        accountDao.add(account);
        List<Account> accounts = accountDao.getAll();
        assertTrue(accounts.size() > 0);
        assertEquals(accounts.get(accounts.size() - 1).getCodeIBAN(), account.getCodeIBAN());
        accountDao.delete(account.getCodeIBAN());
        userDao.delete(user.getPassportId());
        bankDao.delete(bank.getCodeBic());
    }

    @Test
    void getByCodeIBANTest() throws CleverDatabaseException {
        userDao.add(user);
        bankDao.add(bank);
        accountDao.add(account);
        Account accountFromDb = accountDao.getByCodeIBAN(account.getCodeIBAN()).orElse(null);
        assert accountFromDb != null;
        assertEquals(user, accountFromDb.getUser(), "Users must be equals");
        assertEquals(bank, accountFromDb.getBank(), "Banks must be equals");
        assertEquals(account.getBalance(), accountFromDb.getBalance(), "Balances must be equals");
        accountDao.delete(account.getCodeIBAN());
        userDao.delete(user.getPassportId());
        bankDao.delete(bank.getCodeBic());
    }

    @Test
    void getAllForUserTest() throws CleverDatabaseException {
        userDao.add(user);
        bankDao.add(bank);
        accountDao.add(account);
        List<Account> accounts = accountDao.getAllForUser(user);
        assertTrue(accounts.size() > 0);
        assertEquals(accounts.get(accounts.size() - 1).getCodeIBAN(), account.getCodeIBAN());
        accountDao.delete(account.getCodeIBAN());
        userDao.delete(user.getPassportId());
        bankDao.delete(bank.getCodeBic());
    }

    @Test
    void getAllForBankTest() throws CleverDatabaseException {
        userDao.add(user);
        bankDao.add(bank);
        accountDao.add(account);
        List<Account> accounts = accountDao.getAllForBank(bank);
        assertTrue(accounts.size() > 0);
        assertEquals(accounts.get(accounts.size() - 1).getBank().getCodeBic(), account.getBank().getCodeBic());
        accountDao.delete(account.getCodeIBAN());
        userDao.delete(user.getPassportId());
        bankDao.delete(bank.getCodeBic());
    }

    @Test
    void updateAccountTest() throws CleverDatabaseException {
        userDao.add(user);
        bankDao.add(bank);
        accountDao.add(account);
        account.setBalance(BigDecimal.valueOf(777.77));
        accountDao.update(account);
        Account accountFromDb = accountDao.getByCodeIBAN(account.getCodeIBAN()).orElse(null);
        assert accountFromDb != null;
        assertEquals("777.77", accountFromDb.getBalance().toString(), "Balances must be equals");
        accountDao.delete(account.getCodeIBAN());
        userDao.delete(user.getPassportId());
        bankDao.delete(bank.getCodeBic());
    }

    @Test
    void deleteAccountTest() throws CleverDatabaseException {
        userDao.add(user);
        bankDao.add(bank);
        accountDao.add(account);
        Account accountFromDb = accountDao.getByCodeIBAN(account.getCodeIBAN()).orElse(null);
        assert accountFromDb != null;
        accountDao.delete(account.getCodeIBAN());
        assertTrue(accountDao.getByCodeIBAN(account.getCodeIBAN()).isEmpty(), "Account should be null");
        userDao.delete(user.getPassportId());
        bankDao.delete(bank.getCodeBic());
    }
}
