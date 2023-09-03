package com.art.clever.service;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.exception.ServiceException;
import com.art.clever.model.dao.BankDao;
import com.art.clever.model.entity.Bank;
import com.art.clever.service.impl.BankServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {
    @Mock
    private BankDao bankDao;
    @InjectMocks
    private BankService bankService;
    private static Bank bank;
    private static List<Bank> banks;

    @BeforeClass
    public void setUp() {
        bankDao = mock(BankDao.class);
        bankService = new BankServiceImpl(bankDao);
        bank = new Bank();
        bank.setCodeBic("3BT5");
        bank.setName("SPORT-BANK");
        banks = Arrays.asList(bank, new Bank(), new Bank(), new Bank());
    }

    @Test
    public void addBankPositiveTest() throws CleverDatabaseException, ServiceException {
        when(bankDao.getByBicCode(bank.getCodeBic())).thenReturn(Optional.empty());
        when(bankDao.getByBankName(bank.getName())).thenReturn(Optional.empty());
        when(bankDao.add(bank)).thenReturn(true);
        assertTrue(bankService.addBank(bank));
    }

    @Test
    public void addBankNegativeTest() throws CleverDatabaseException {
        when(bankDao.getByBicCode(bank.getCodeBic())).thenReturn(Optional.of(bank));
        when(bankDao.add(bank)).thenReturn(true);
        assertThrows(ServiceException.class, () -> bankService.addBank(bank));
    }

    @Test
    public void findAllBankTest() throws CleverDatabaseException, ServiceException {
        when(bankDao.getAll()).thenReturn(banks);
        List<Bank> banksFromMock = bankService.findAllBanks();
        assertEquals(banks.size(), banksFromMock.size());
        assertEquals(banks, banksFromMock);
    }

    @Test
    public void findBankByBicCodeTest() throws CleverDatabaseException, ServiceException {
        when(bankDao.getByBicCode(bank.getCodeBic())).thenReturn(Optional.of(bank));
        Optional<Bank> optionalBank = bankService.findBankByBicCode(bank.getCodeBic());
        Bank bankFromMock = optionalBank.orElse(null);
        assert bankFromMock != null;
        assertEquals(bank.getCodeBic(), bankFromMock.getCodeBic());
        assertEquals(bank.getName(), bankFromMock.getName());
    }

    @Test
    public void findBankByBankName() throws CleverDatabaseException, ServiceException {
        when(bankDao.getByBankName(bank.getName())).thenReturn(Optional.of(bank));
        Optional<Bank> optionalBank = bankService.findBankByBankName(bank.getName());
        Bank bankFromMock = optionalBank.orElse(null);
        assert bankFromMock != null;
        assertEquals(bank.getCodeBic(), bankFromMock.getCodeBic());
        assertEquals(bank.getName(), bankFromMock.getName());
    }

    @Test
    public void updateBankTest() throws CleverDatabaseException, ServiceException {
        when(bankDao.update(bank)).thenReturn(true);
        assertTrue(bankService.updateBank(bank));
    }

    @Test
    public void deleteBankTest() throws CleverDatabaseException, ServiceException {
        when(bankDao.delete(bank.getCodeBic())).thenReturn(true);
        assertTrue(bankService.deleteBank(bank.getCodeBic()));
    }
}
