package md.orange.exchangeoffice.service;

import md.orange.exchangeoffice.dto.*;

import java.util.List;

/**
 * The interface Exchange service.
 */
public interface ExchangeService {

    /**
     * Gets currencies.
     *
     * @return the currencies
     */
    List<String> getCurrencies();

    /**
     * Save currencies.
     *
     * @param currencies the currencies
     */
    void saveCurrencies(List<AddCurrencyDTO> currencies);

    /**
     * Gets exchange rates.
     *
     * @return the exchange rates
     */
    List<ExchangeRateDTO> getExchangeRates();

    /**
     * Gets exchange rate.
     *
     * @param currencyCode the currency code
     * @return the exchange rate
     */
    ExchangeRateDTO getExchangeRate(String currencyCode);

    /**
     * Save exchange rates.
     *
     * @param exchangeRateDTOList the exchange rate dto list
     */
    void saveExchangeRates(List<CreateExchangeRateDTO> exchangeRateDTOList);

    /**
     * Buy currency amount dto.
     *
     * @param buyCurrencyDTO the buy currency dto
     * @return the amount dto
     */
    AmountDTO buyCurrency(BuyCurrencyDTO buyCurrencyDTO);

    /**
     * Change currency amount dto.
     *
     * @param changeCurrencyDTO the change currency dto
     * @return the amount dto
     */
    AmountDTO changeCurrency(ChangeCurrencyDTO changeCurrencyDTO);

    /**
     * Gets balances.
     *
     * @return the balances
     */
    List<BalanceDTO> getBalances();

    /**
     * Gets balance.
     *
     * @param currencyCode the currency code
     * @return the balance
     */
    BalanceDTO getBalance(String currencyCode);

    /**
     * Update balance.
     *
     * @param balanceUpdateDTO the balance update dto
     */
    void updateBalance(BalanceUpdateDTO balanceUpdateDTO);

    /**
     * Gets employees.
     *
     * @return the employees
     */
    List<EmployeeDTO> getEmployees();

    /**
     * Add employees.
     *
     * @param employeeDTOList the employee dto list
     */
    void addEmployees(List<EmployeeDTO> employeeDTOList);
}
