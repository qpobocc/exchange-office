package md.orange.exchangeoffice.service;

import md.orange.exchangeoffice.dto.*;
import md.orange.exchangeoffice.model.*;
import md.orange.exchangeoffice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("exchangeService")
public class ExchangeServiceImpl implements ExchangeService {

    private final String defCurrency = "MDL";

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Override
    public List<String> getCurrencies() {
        return currencyRepository.findAll().stream().map(Currency::getCode).collect(Collectors.toList());
    }

    @Override
    public void saveCurrencies(List<AddCurrencyDTO> currencies) {
        currencyRepository.saveAll(currencies.stream().map(c -> new Currency(c.getCurrencyCode())).collect(Collectors.toList()));
        balanceRepository.saveAll(currencies.stream().map(c -> new Balance(c.getCurrencyCode(), c.getAmountAvailable(), new Date())).collect(Collectors.toList()));
    }

    @Override
    public List<ExchangeRateDTO> getExchangeRates() {
        return exchangeRateRepository.findByIdRateDate(new Date()).stream().map(this::exchangeRateToDto).collect(Collectors.toList());
    }

    @Override
    public ExchangeRateDTO getExchangeRate(String currencyCode) {
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.findById(new ExchangeRate.ExchangeRateId(new Currency(currencyCode), new Date()));

        if (!exchangeRate.isPresent()) {
            throw new RuntimeException("Exchange rate not found for currency = " + currencyCode + " and date = " + new Date());
        }

        return exchangeRateToDto(exchangeRate.get());
    }

    @Override
    public void saveExchangeRates(List<CreateExchangeRateDTO> exchangeRateDTOList) {
        Date today = new Date();
        exchangeRateRepository.saveAll(exchangeRateDTOList.stream()
                .map(createDto -> toExchangeRateModel(createDto, today))
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public AmountDTO buyCurrency(BuyCurrencyDTO buyCurrencyDTO) {
        Currency currencyIn = new Currency(buyCurrencyDTO.getCurrencyCode());
        Currency currencyOut = new Currency(defCurrency);

        CurrencyExchange currencyExchange = new CurrencyExchange();
        currencyExchange.setReceivedCurrency(currencyIn);
        currencyExchange.setReceivedCurrencyAmount(buyCurrencyDTO.getAmount());
        currencyExchange.setReleasedCurrency(new Currency(defCurrency));
        currencyExchange.setReleasedCurrencyAmount(buyCurrencyDTO.getAmount() * buyCurrencyDTO.getRate());
        currencyExchange.setExchangeRate(buyCurrencyDTO.getRate());
        currencyExchange.setEmployee(new Employee(buyCurrencyDTO.getEmployeeCode()));
        currencyExchange.setExchangeDate(new Date());

        currencyExchangeRepository.save(currencyExchange);

        Balance balanceIn = balanceRepository.getOne(currencyIn.getCode());
        balanceIn.setAmount(balanceIn.getAmount() + buyCurrencyDTO.getAmount());
        balanceRepository.save(balanceIn);

        Balance balanceOut = balanceRepository.getOne(currencyOut.getCode());
        balanceOut.setAmount(balanceOut.getAmount() - buyCurrencyDTO.getAmount() * buyCurrencyDTO.getRate());
        balanceRepository.save(balanceOut);

        return new AmountDTO(buyCurrencyDTO.getAmount() * buyCurrencyDTO.getRate());
    }

    @Override
    @Transactional
    public AmountDTO changeCurrency(ChangeCurrencyDTO changeCurrencyDTO) {
        Date today = new Date();
        Currency currencyIn = new Currency(changeCurrencyDTO.getFromCurrencyCode());
        Currency currencyOut = new Currency(changeCurrencyDTO.getToCurrencyCode());

        ExchangeRate sourceRate = exchangeRateRepository.findById(new ExchangeRate.ExchangeRateId(currencyIn, today)).orElse(null);
        ExchangeRate targetRate = exchangeRateRepository.findById(new ExchangeRate.ExchangeRateId(currencyOut, today)).orElse(null);

        if (sourceRate == null || targetRate == null) {
            throw new RuntimeException("Exchange rates not found for date = " + today);
        }

        double rate = (sourceRate.getAmount() * sourceRate.getRate()) / (targetRate.getAmount() * targetRate.getRate());
        double targetAmount = changeCurrencyDTO.getFromCurrencyAmount() * rate;

        CurrencyExchange currencyExchange = new CurrencyExchange();
        currencyExchange.setReceivedCurrency(currencyIn);
        currencyExchange.setReceivedCurrencyAmount(changeCurrencyDTO.getFromCurrencyAmount());
        currencyExchange.setReleasedCurrency(currencyOut);
        currencyExchange.setReleasedCurrencyAmount(targetAmount);
        currencyExchange.setExchangeRate(rate);
        currencyExchange.setEmployee(new Employee(changeCurrencyDTO.getEmployeeCode()));
        currencyExchange.setExchangeDate(today);

        currencyExchangeRepository.save(currencyExchange);

        Balance balanceIn = balanceRepository.getOne(currencyIn.getCode());
        balanceIn.setAmount(balanceIn.getAmount() + changeCurrencyDTO.getFromCurrencyAmount());
        balanceRepository.save(balanceIn);

        Balance balanceOut = balanceRepository.getOne(currencyOut.getCode());
        balanceOut.setAmount(balanceOut.getAmount() - targetAmount);
        balanceRepository.save(balanceOut);

        return new AmountDTO(targetAmount);
    }

    @Override
    public List<BalanceDTO> getBalances() {
        return balanceRepository.findAll().stream().map(b -> {
            BalanceDTO balanceDTO = new BalanceDTO();
            balanceDTO.setCurrencyCode(b.getCurrencyCode());
            balanceDTO.setAmount(b.getAmount());
            return balanceDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public BalanceDTO getBalance(String currencyCode) {
        Optional<Balance> balance = balanceRepository.findById(currencyCode);

        if (!balance.isPresent()) {
            throw new RuntimeException("Balance not found for currencyCode = " + currencyCode);
        }

        Balance b = balance.get();
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setCurrencyCode(b.getCurrencyCode());
        balanceDTO.setAmount(b.getAmount());
        balanceDTO.setTmsLastUpdate((b.getTmsLastUpdate()));

        return balanceDTO;
    }

    @Override
    public void updateBalance(BalanceUpdateDTO balanceUpdateDTO) {
        Optional<Balance> balance = balanceRepository.findById(balanceUpdateDTO.getCurrencyCode());

        if (!balance.isPresent()) {
            throw new RuntimeException("Balance not found for currencyCode = " + balanceUpdateDTO.getCurrencyCode());
        }

        Balance b = balance.get();
        b.setAmount(balanceUpdateDTO.getNewBalanceAmount());
        balanceRepository.save(b);
    }

    @Override
    public List<EmployeeDTO> getEmployees() {
        return employeeRepository.findAll().stream().map(e -> new EmployeeDTO(e.getCode(), e.getFullName())).collect(Collectors.toList());
    }

    @Override
    public void addEmployees(List<EmployeeDTO> employeeDTOList) {
        employeeRepository.saveAll(employeeDTOList.stream().map(dto -> new Employee(dto.getCode(), dto.getFullName())).collect(Collectors.toList()));
    }

    private ExchangeRateDTO exchangeRateToDto(ExchangeRate model) {
        return new ExchangeRateDTO(
                model.getId().getCurrency().getCode(),
                model.getId().getRateDate(),
                model.getAmount(),
                model.getRate());
    }

    private ExchangeRate toExchangeRateModel(CreateExchangeRateDTO createDto, Date rateDate) {
        return new ExchangeRate(
                new ExchangeRate.ExchangeRateId(new Currency(createDto.getCurrencyCode()), rateDate),
                createDto.getAmount(),
                createDto.getRate());
    }
}
