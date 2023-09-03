package com.art.clever.service;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.exception.ServiceException;
import com.art.clever.model.dao.AccountDao;
import com.art.clever.model.entity.Account;
import com.art.clever.model.entity.Bank;
import com.art.clever.model.entity.Currency;
import com.art.clever.model.entity.User;
import com.art.clever.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountDao accountDao;
    @InjectMocks
    private AccountService accountService;
    private static Account account;
    private static List<Account> accounts;
    private static User user;
    private static Bank bank;

    @BeforeClass
    public void setUp() {
        accountDao = mock(AccountDao.class);
        accountService = new AccountServiceImpl(accountDao);

        bank = new Bank();
        bank.setCodeBic("MMBN");
        bank.setName("SPORT-BANK");

        user = new User();
        user.setPassportId("3220570A053BT5");
        user.setLastName("Романов");
        user.setFirstName("Алексей");
        user.setSurName("Алексеевмч");

        account = new Account();
        account.setCodeIBAN("BY07 MMBN 3012 0000 3220 0000 0000");
        account.setBank(bank);
        account.setUser(user);
        account.setCurrency(Currency.BYN);
        account.setCreatedAt(Instant.now());
        account.setBalance(BigDecimal.valueOf(500.00));

        accounts = Arrays.asList(account, new Account(), new Account(), new Account());
    }

    @Test
    public void addAccountPositiveTest() throws CleverDatabaseException, ServiceException {
        when(accountDao.getByCodeIBAN(account.getCodeIBAN())).thenReturn(Optional.empty());
        when(accountDao.add(account)).thenReturn(true);
        assertTrue(accountService.addAccount(account));
    }

    @Test
    public void addAccountNegativeTest() throws CleverDatabaseException {
        when(accountDao.getByCodeIBAN(account.getCodeIBAN())).thenReturn(Optional.of(account));
        assertThrows(ServiceException.class, () -> accountService.addAccount(account));
    }

    @Test
    public void findAllAccountTest() throws CleverDatabaseException, ServiceException {
        when(accountDao.getAll()).thenReturn(accounts);
        List<Account> accountsFromMock = accountService.findAllAccounts();
        assertEquals(accounts.size(), accountsFromMock.size());
        assertEquals(accounts, accountsFromMock);
    }

    @Test
    public void findByCodeIBANTest() throws CleverDatabaseException, ServiceException {
        when(accountDao.getByCodeIBAN(account.getCodeIBAN())).thenReturn(Optional.of(account));
        Optional<Account> optionalAccount = accountService.findByCodeIBAN(account.getCodeIBAN());
        Account accountFromMock = optionalAccount.orElse(null);
        assert accountFromMock != null;
        assertEquals(account.getBalance(), accountFromMock.getBalance());
        assertEquals(account.getBank(), accountFromMock.getBank());
    }

    @Test
    public void findAllForUserTest() throws CleverDatabaseException, ServiceException {
        when(accountDao.getAllForUser(user)).thenReturn(accounts);
        List<Account> accountsFromMock = accountService.findAllForUser(user);
        assertEquals(accounts.size(), accountsFromMock.size());
        assertEquals(accounts, accountsFromMock);
    }

    @Test
    public void findAllForBankTest() throws CleverDatabaseException, ServiceException {
        when(accountDao.getAllForBank(bank)).thenReturn(accounts);
        List<Account> accountsFromMock = accountService.findAllForBank(bank);
        assertEquals(accounts.size(), accountsFromMock.size());
        assertEquals(accounts, accountsFromMock);
    }

    @Test
    public void updateAccountTest() throws CleverDatabaseException, ServiceException {
        when(accountDao.update(account)).thenReturn(true);
        assertTrue(accountService.updateAccount(account));
    }

    @Test
    public void deleteAccountTest() throws CleverDatabaseException, ServiceException {
        when(accountDao.delete(account.getCodeIBAN())).thenReturn(true);
        assertTrue(accountService.deleteAccount(account.getCodeIBAN()));
    }
}
