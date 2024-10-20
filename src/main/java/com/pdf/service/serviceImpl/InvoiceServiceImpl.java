package com.pdf.service.serviceImpl;

import com.pdf.entity.Invoice;
import com.pdf.repository.InvoiceRepository;
import com.pdf.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Override
    public Invoice saveInvoice(Invoice invoice) throws IOException {

        String pdfFilePath = pdfGeneratorService.generatePdf(invoice);
        invoice.setPdfFilePath(pdfFilePath);

        return invoiceRepository.save(invoice);
    }


}
