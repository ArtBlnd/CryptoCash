package Bagua.APIs;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Bagua.Base.CoinPrice;
import Bagua.Base.EnumCoin;
import Bagua.Interface.ICachedSite;

public class BithumbAPI implements ICachedSite
{
    private final String tokenMinPrice = "min_price";
    private final String tokenMaxPrice = "max_price";
    private final String tokenAvgPrice = "average_price";
    private final String tokenFirstPrice = "opening_price";
    private final String tokenLastPrice = "closing_price";
    private final String tokenSellPrice = "sell_price";
    private final String tokenBuyPrice = "buy_price";

    private final String requestURL = "https://api.bithumb.com/public/ticker/";

    JSONParser jsonParser;
    Map<EnumCoin, CoinPrice> CachedPrice;

    public BithumbAPI()
    {
        jsonParser = new JSONParser();
        CachedPrice = new HashMap<EnumCoin, CoinPrice>();
    }

    private String CoinTypeToToken(EnumCoin type)
    {
        switch(type)
        {
        case BTC : return "BTC";
        case ETH : return "ETH";
        case DASH : return "DASH";
        case LTC : return "LTC";
        case ETC : return "ETC";
        case XRP : return "XRP";
        default : return "";
        }
    }

    public EnumCoin[] getAvailableCoinTypes()
    {
        return new EnumCoin[] 
        { 
            EnumCoin.BTC, 
            EnumCoin.ETC, 
            EnumCoin.DASH, 
            EnumCoin.LTC, 
            EnumCoin.ETC, 
            EnumCoin.XRP
        };
    }

    public void Refresh() throws IOException, ParseException
    {
        for(EnumCoin type : EnumCoin.values())
        {
            Refresh(type);
        }
    }
    public void Refresh(EnumCoin type) throws IOException, ParseException
    {
        String targetUrl = requestURL + CoinTypeToToken(type);

        URL                 BithumbAPIUrl = new URL(targetUrl);
        InputStreamReader   stream        = new InputStreamReader(BithumbAPIUrl.openConnection().getInputStream(), "UTF-8");
        JSONObject          jsonObject    = (JSONObject)((JSONObject)jsonParser.parse(stream)).get("data");

        CoinPrice newPrice = new CoinPrice();
        
        newPrice.Highest = Double.parseDouble((String)jsonObject.get(tokenMaxPrice));
        newPrice.Lowest = Double.parseDouble((String)jsonObject.get(tokenMinPrice));
        /*
        super.get(type).AvgPrice = Double.parseDouble((String)jsonObject.get(tokenAvgPrice));
        super.get(type).FirstPrice = Double.parseDouble((String)jsonObject.get(tokenFirstPrice));
        super.get(type).LastPrice = Double.parseDouble((String)jsonObject.get(tokenLastPrice));
        */
        newPrice.Bid = Double.parseDouble((String)jsonObject.get(tokenSellPrice));
        newPrice.Ask = Double.parseDouble((String)jsonObject.get(tokenBuyPrice));

        CachedPrice.put(type, newPrice);
        stream.close();
    }

    public CoinPrice getPrice(EnumCoin coin)
    {
    	return CachedPrice.get(coin);
    }
}