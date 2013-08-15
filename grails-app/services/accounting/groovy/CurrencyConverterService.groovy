package accounting.groovy

import com.ibm.icu.util.Currency as Currency
import de.gzockoll.types.money.Money
import grails.plugin.cache.Cacheable
import org.apache.http.client.HttpClient
import org.apache.http.client.ResponseHandler
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.DefaultHttpClient

class CurrencyConverterService {
    private static final String google = "http://www.google.com/ig/calculator?hl=en&q="
    private static final String CHARSET = "UTF-8"
    public static final List<String> CURRENCY_CODES = java.util.Currency.availableCurrencies.collect { it.currencyCode }.sort()

    Money convert(Money source, Currency currency) {
        def factor=getExchangeRate(source.currency.currencyCode,currency.currencyCode)
        Money.fromMajor(source.value*factor,currency)
    }

    @Cacheable('exchangerates')
    BigDecimal getExchangeRate(String currencyFrom, String currencyTo) throws IOException {

        assert CURRENCY_CODES.contains(currencyFrom), "Unsupported Currency: $currencyFrom"
        assert CURRENCY_CODES.contains(currencyTo), "Unsupported Currency: $currencyTo"

        HttpClient httpclient = new DefaultHttpClient()
        HttpGet httpGet = new HttpGet("http://quote.yahoo.com/d/quotes.csv?s=" + currencyFrom + currencyTo + "=X&f=l1&e=.csv")
        ResponseHandler<String> responseHandler = new BasicResponseHandler()
        String responseBody = httpclient.execute(httpGet, responseHandler)
        httpclient.getConnectionManager().shutdown()
        return new BigDecimal(responseBody.trim())
    }
}

