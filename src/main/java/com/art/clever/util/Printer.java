package com.art.clever.util;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.model.dao.AccountDao;
import com.art.clever.model.dao.impl.AccountDaoJdbc;
import com.art.clever.model.entity.Transaction;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Class for printing checks after transactions
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public class Printer {

    private static final String END = " |\n";
    private static final AccountDao accountDao = AccountDaoJdbc.getInstance();
    private static final String PATTERN_FORMAT = "dd-MM-yyyy                  hh:mm:ss";

    public void printCheck(Transaction transaction) throws CleverDatabaseException {
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------------------\n");
        sb.append("|           Банковский чек             |\n");
        sb.append("| Чек:                           ")
            .append(String.format("%5d", transaction.getActionId() + 12000)).append(END);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());
        String formattedInstant = formatter.format(transaction.getTime());
        sb.append("| ").append(formattedInstant).append(END);
        sb.append("| Тип транзакции: ")
                .append(String.format("%20s", transaction.getTransactionType().getTitle())).append(END);
        sb.append("| Банк отправителя:    ")
            .append(String.format("%15s", accountDao.getByCodeIBAN(transaction.getFromAccountId())
                .get().getBank().getName())).append(END);
        sb.append("| Банк получателя:     ")
            .append(String.format("%15s", accountDao.getByCodeIBAN(transaction.getToAccountId())
                .get().getBank().getName())).append(END);
        sb.append("| Счет отправителя:       ")
                .append(String.format("%40s", transaction.getFromAccountId()).substring(25)
                        .replace(" ","")).append(END);
        sb.append("| Счет получателя:        ")
                .append(String.format("%40s", transaction.getToAccountId()).substring(25)
                        .replace(" ","")).append(END);
        sb.append("| Сумма:      ")
                .append(String.format("%20s", transaction.getAmount()
                        .setScale(2, RoundingMode.HALF_EVEN).toString()))
                .append(" ").append(transaction.getCurrency().toString()).append(END);
        sb.append("|______________________________________|\n");
        System.out.println(sb);
        PdfPrinter pdfPrinter = new PdfPrinter();
        try {
            pdfPrinter.printPdf(sb.toString(), transaction.getActionId());
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
