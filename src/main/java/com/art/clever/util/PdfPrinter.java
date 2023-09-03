package com.art.clever.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PdfPrinter {

    public void printPdf(String string, long actionId) throws FileNotFoundException, DocumentException {
        File file = new File("checks/" + actionId + ".pdf");
        Document pdfDoc = new Document(PageSize.A4);
        PdfWriter.getInstance(pdfDoc, new FileOutputStream(file))
                .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
        pdfDoc.open();

        Font font = FontFactory.getFont("src/main/resources/courier.ttf", "cp1251", BaseFont.EMBEDDED, 10);
        pdfDoc.add(new Paragraph("\n"));

        Paragraph para = new Paragraph(string, font);
        para.setAlignment(Element.ALIGN_JUSTIFIED);
        pdfDoc.add(para);
        pdfDoc.close();
    }
}
