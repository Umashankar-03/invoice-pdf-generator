package com.pdf.service;

import com.pdf.entity.Invoice;
import java.util.Optional;
import java.io.IOException;

public interface InvoiceService {
    Invoice saveInvoice(Invoice invoice) throws IOException;

}

