package com.pdf.service.serviceImpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdf.entity.Invoice;
import com.pdf.entity.Item;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfGeneratorService {

    public String generatePdf(Invoice invoice) {
        String pdfFilePath = null;
        try {
            Document document = new Document();

            String directoryPath = "G:/Spring_Boot Project/Invoice_PDF/";
            new File(directoryPath).mkdirs();

            String pdfFileName = "invoice_" + System.currentTimeMillis() + ".pdf";
            pdfFilePath = directoryPath + pdfFileName;

            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100); // Set the width of the table to 100%
            table.setSpacingAfter(0f); // Remove space after the seller/buyer table


            PdfPCell sellerCell = createCell("Seller:\n" + invoice.getSeller() +
                    "\nGSTIN: " + invoice.getSellerGstin() +
                    "\nAddress: " + invoice.getSellerAddress());
            sellerCell.setPaddingTop(25f);
            sellerCell.setPaddingLeft(25f);
            sellerCell.setFixedHeight(sellerCell.getPhrase().getContent().split("\n").length * 30);

            PdfPCell buyerCell = createCell("Buyer:\n" + invoice.getBuyer() +
                    "\nGSTIN: " + invoice.getBuyerGstin() +
                    "\nAddress: " + invoice.getBuyerAddress());
            buyerCell.setPaddingTop(25f);
            buyerCell.setPaddingLeft(25f);
            buyerCell.setFixedHeight(buyerCell.getPhrase().getContent().split("\n").length * 30);

            table.addCell(sellerCell);
            table.addCell(buyerCell);


            document.add(table);


            PdfPTable itemTable = new PdfPTable(4);
            itemTable.setWidthPercentage(100);


            float[] itemColumnWidths = {2f, 1f, 1f, 1f};
            itemTable.setWidths(itemColumnWidths);


            for (String header : new String[]{"Item Name", "Quantity", "Rate", "Amount"}) {
                PdfPCell headerCell = createHeaderCell(header);
                itemTable.addCell(headerCell);
            }


            for (Item item : invoice.getItems()) {
                itemTable.addCell(createCenteredCell(item.getName()));
                itemTable.addCell(createCenteredCell(item.getQuantity()));
                itemTable.addCell(createCenteredCell(String.valueOf(item.getRate())));
                itemTable.addCell(createCenteredCell(String.valueOf(item.getAmount())));
            }

            document.add(itemTable);


            PdfPTable emptyRowTable = new PdfPTable(1);
            emptyRowTable.setWidthPercentage(100);

            PdfPCell emptyRowCell = new PdfPCell(new Paragraph(" "));
            emptyRowCell.setBorderWidth(1);
            emptyRowCell.setBorderColor(BaseColor.BLACK);
            emptyRowCell.setFixedHeight(40);
            emptyRowCell.setPadding(0);
            emptyRowTable.addCell(emptyRowCell);


            document.add(emptyRowTable);

            document.close();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        return pdfFilePath;
    }

    private PdfPCell createCell(String content) {
        PdfPCell cell = new PdfPCell(new Paragraph(content));
        cell.setBorderWidth(1);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setPadding(10);
        return cell;
    }

    private PdfPCell createHeaderCell(String content) {
        PdfPCell cell = new PdfPCell(new Paragraph(content));
        cell.setBorderWidth(1);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    public PdfPCell createCenteredCell(String content) {
        PdfPCell cell = new PdfPCell(new Paragraph(content));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorderWidth(1);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }
}
