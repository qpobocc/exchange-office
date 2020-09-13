package md.orange.exchangeoffice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import md.orange.exchangeoffice.dto.*;
import md.orange.exchangeoffice.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchange_office/api/v1")
@Api(value = "Service for the exchange office", tags = "Currency exchange office v1.0")
@Log4j2
public class ExchangeOfficeController {

    private ExchangeService exchangeService;

    @Autowired
    public ExchangeOfficeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping(value = "/currencies")
    @ApiOperation(value = "Get list of all currencies", nickname = "getCurrencies")
    public List<String> getCurrencies() {
        return exchangeService.getCurrencies();
    }

    @PostMapping(value = "/currencies")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "Add new currencies", nickname = "saveCurrencies")
    public void saveCurrencies(@RequestBody List<AddCurrencyDTO> currencies) {
        exchangeService.saveCurrencies(currencies);
    }

    @GetMapping(value = "/exchange/rates")
    @ApiOperation(value = "Get list of all exchange rates for current date", nickname = "getExchangeRates")
    public List<ExchangeRateDTO> getExchangeRates() {
        return exchangeService.getExchangeRates();
    }

    @PostMapping(value = "/exchange/rates")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "Add new exchange rates for current date", nickname = "saveExchangeRates")
    public void saveExchangeRates(@RequestBody List<CreateExchangeRateDTO> exchangeRates) {
        exchangeService.saveExchangeRates(exchangeRates);
    }

    @GetMapping(value = "/exchange/rates/{currencyCode}")
    @ApiOperation(value = "Get exchange rate for specified currency code", nickname = "getCurrencyExchangeRate")
    public ExchangeRateDTO getCurrencyExchangeRate(@PathVariable @ApiParam(value = "Currency Code", example = "EUR") String currencyCode) {
        return exchangeService.getExchangeRate(currencyCode);
    }

    @PostMapping(value = "/exchange/buy")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "The buying of currency", nickname = "buyCurrency")
    public AmountDTO buyCurrency(@RequestBody BuyCurrencyDTO buyCurrencyDTO) {
        return exchangeService.buyCurrency(buyCurrencyDTO);
    }

    @PostMapping(value = "/exchange/change")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "Changes the amount of one currency to another", nickname = "buyCurrency")
    public AmountDTO buyCurrency(@RequestBody ChangeCurrencyDTO changeCurrencyDTO) {
        return exchangeService.changeCurrency(changeCurrencyDTO);
    }

    @GetMapping(value = "/balances")
    @ApiOperation(value = "Get balances of all currencies", nickname = "getBalances")
    public List<BalanceDTO> getBalances() {
        return exchangeService.getBalances();
    }

    @GetMapping(value = "/balances/{currencyCode}")
    @ApiOperation(value = "Get balance for specified currency code", nickname = "getCurrencyBalance")
    public BalanceDTO getCurrencyBalance(@PathVariable @ApiParam(value = "Currency Code", example = "EUR") String currencyCode) {
        return exchangeService.getBalance(currencyCode);
    }

    @PutMapping(value = "/balances")
    @ApiOperation(value = "Update balance for specified currency code", nickname = "updateBalance")
    public void updateBalance(@RequestBody BalanceUpdateDTO balanceUpdateDTO) {
        exchangeService.updateBalance(balanceUpdateDTO);
    }

    @GetMapping(value = "/employees")
    @ApiOperation(value = "Get list of all employees", nickname = "getCurrencyBalance")
    public List<EmployeeDTO> getEmployees() {
        return exchangeService.getEmployees();
    }

    @PostMapping(value = "/employees")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "Add new employees", nickname = "addEmployees")
    public void addEmployees(@RequestBody List<EmployeeDTO> employees) {
        exchangeService.addEmployees(employees);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleException(RuntimeException e) {
        log.error(e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Generic error: " + (e.getMessage() != null ? e.getMessage() : "Generic Exception"));
    }

}
