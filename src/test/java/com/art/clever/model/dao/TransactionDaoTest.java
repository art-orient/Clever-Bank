package com.art.clever.model.dao;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.exception.ConnectionPoolException;
import com.art.clever.model.dao.impl.AccountDaoJdbc;
import com.art.clever.model.dao.impl.BankDaoJdbc;
import com.art.clever.model.dao.impl.TransactionDaoJdbc;
import com.art.clever.model.dao.impl.UserDaoJdbc;
import com.art.clever.model.entity.Account;
import com.art.clever.model.entity.Bank;
import com.art.clever.model.entity.Currency;
import com.art.clever.model.entity.Transaction;
import com.art.clever.model.entity.TransactionType;
import com.art.clever.model.entity.User;
import com.art.clever.model.pool.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionDaoTest {
    private static TransactionDao transactionDao;
    private static AccountDao accountDao;
    private static UserDao userDao;
    private static BankDao bankDao;
    private static final ConnectionPool POOL = ConnectionPool.INSTANCE;
    private static Transaction transaction;
    private static Account firstAccount;
    private static Account secondAccount;
    private static User user;
    private static Bank bank;

    @BeforeAll
    static void init() throws ConnectionPoolException {
        POOL.initPool();
        transactionDao = TransactionDaoJdbc.getInstance();
        accountDao = AccountDaoJdbc.getInstance();
        userDao = UserDaoJdbc.getInstance();
        bankDao = BankDaoJdbc.getInstance();
        bank = new Bank();
        bank.setCodeBic("MMGN");
        bank.setName("Clever-Bank");

        user = new User();
        user.setPassportId("3110570A053BT5");
        user.setLastName("Романов");
        user.setFirstName("Алексей");
        user.setSurName("Алексеевмч");

        firstAccount = new Account();
        firstAccount.setCodeIBAN("BY07 MMGN 3012 0000 3220 0000 0001");
        firstAccount.setBank(bank);
        firstAccount.setUser(user);
        firstAccount.setCurrency(Currency.BYN);
        firstAccount.setCreatedAt(Instant.now());
        firstAccount.setBalance(BigDecimal.valueOf(500.54));

        secondAccount = new Account();
        secondAccount.setCodeIBAN("BY07 MEFD 3012 0000 3220 0000 0002");
        secondAccount.setBank(bank);
        secondAccount.setUser(user);
        secondAccount.setCurrency(Currency.BYN);
        secondAccount.setCreatedAt(Instant.now());
        secondAccount.setBalance(BigDecimal.valueOf(1700));

        transaction = new Transaction();
        transaction.setTime(Instant.now());
        transaction.setTransactionType(TransactionType.ADDING);
        transaction.setFromAccountId(firstAccount.getCodeIBAN());
        transaction.setToAccountId(firstAccount.getCodeIBAN());
        transaction.setCurrency(firstAccount.getCurrency());
        transaction.setAmount(BigDecimal.valueOf(150.65));
    }

    @AfterAll
    static void teardown() throws ConnectionPoolException {
        POOL.destroyPool();
    }

    @Test
    void addTransactionalTest() throws CleverDatabaseException {
        userDao.add(user);
        bankDao.add(bank);
        accountDao.add(firstAccount);
        accountDao.add(secondAccount);
        transactionDao.add(transaction);
        Account accountFromDb = accountDao.getByCodeIBAN(firstAccount.getCodeIBAN()).orElse(null);
        assert accountFromDb != null;
        assertEquals(user, accountFromDb.getUser(), "Users must be equals");
        assertEquals(bank, accountFromDb.getBank(), "Banks must be equals");
        assertEquals(firstAccount.getBalance(), accountFromDb.getBalance(), "Balances must be equals");
        accountDao.delete(firstAccount.getCodeIBAN());
        accountDao.delete(secondAccount.getCodeIBAN());
        userDao.delete(user.getPassportId());
        bankDao.delete(bank.getCodeBic());
    }
}
