package Bagua.APIs;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import Bagua.Base.CoinPrice;
import Bagua.Base.EnumCoin;
import Bagua.Interface.ICachedSite;

public class BitStampAPI implements ICachedSite
{
    private final String BitStampXRP_URL = "https://www.bitstamp.net/api/v2/ticker/xrpbtc";
    private final String BitStampLTC_URL = "https://www.bitstamp.net/api/v2/ticker/ltcbtc";

    final private String BidToken = "bid";
    final private String AskToken = "ask";
    final private String HighestIn24Hr = "high";
    final private String LowestIn24Hr = "low";

    Map<EnumCoin, CoinPrice> CachedPrice;

    public BitStampAPI()
    {
        CachedPrice = new HashMap<EnumCoin, CoinPrice>();
    }

    private void RefreshSite(String target, EnumCoin coin) throws IOException, ParseException
    {
        URL                 URLTarget = new URL(target);
        InputStreamReader   URLReader = new InputStreamReader(URLTarget.openStream());
        JSONParser          URLParser = new JSONParser();   

        Map<String, String> CoinInfo  = (Map)URLParser.parse(URLReader);

        CoinPrice newPrice = new CoinPrice();

        newPrice.Bid = Double.parseDouble(CoinInfo.get(BidToken));
        newPrice.Ask = Double.parseDouble(CoinInfo.get(AskToken));

        newPrice.Highest = Double.parseDouble(CoinInfo.get(HighestIn24Hr)); 
        newPrice.Lowest  = Double.parseDouble(CoinInfo.get(LowestIn24Hr));

        CachedPrice.put(coin, newPrice);
    }

    public void Refresh() throws IOException, ParseException
    {
        RefreshSite(BitStampXRP_URL, EnumCoin.XRP);
        RefreshSite(BitStampLTC_URL, EnumCoin.LTC);
    }
    public CoinPrice getPrice(EnumCoin coin)
    {
        return CachedPrice.get(coin);
    }
}
