package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

}
