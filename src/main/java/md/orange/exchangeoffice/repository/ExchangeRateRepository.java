package md.orange.exchangeoffice.repository;

import md.orange.exchangeoffice.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, ExchangeRate.ExchangeRateId> {

    List<ExchangeRate> findByIdRateDate(Date rateDate);
}
