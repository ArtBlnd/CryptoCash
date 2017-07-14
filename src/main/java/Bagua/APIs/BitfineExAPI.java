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

public class BitfineExAPI implements ICachedSite
{
    final String TargetURL = "https://api.bitfinex.com/v1/pubticker/";

    final private String BidToken = "bid";
    final private String AskToken = "ask";
    final private String HighestIn24Hr = "high";
    final private String LowestIn24Hr = "low";
    
    EnumCoin[] AvailableCoins =
    {
    		EnumCoin.LTC, EnumCoin.ETH, EnumCoin.ETC,
    		EnumCoin.RRT, EnumCoin.ZEC, EnumCoin.XMR,
    		EnumCoin.DASH, EnumCoin.BCC, EnumCoin.BCU,
    		EnumCoin.XRP, EnumCoin.IOT, EnumCoin.EOS,
    		EnumCoin.SAN
    };
    
    Map<EnumCoin, CoinPrice> CachedPrice;
    
    public BitfineExAPI()
    {
        CachedPrice = new HashMap<EnumCoin, CoinPrice>();
    }

    public void Refresh() throws IOException, ParseException
    {
        // Clearing all caches from map.
        CachedPrice.clear();

        for(EnumCoin coin : AvailableCoins)
        {
        	// Because 1:1 
        	String URLpostfix = coin.toString().toLowerCase() + "btc";
        	if(coin == EnumCoin.DASH) {
        		URLpostfix = "dshbtc"; // DASH -> DSH
        	}
            URL                 URLTarget = new URL(TargetURL + URLpostfix);
            InputStreamReader   URLStream = new InputStreamReader(URLTarget.openStream());
            JSONParser          TickerParser = new JSONParser();
            CoinPrice newPrice = new CoinPrice();
            
            JSONObject CoinInfo = (JSONObject) TickerParser.parse(URLStream);
            
            // for safety reason. JSONObject.something -> String -> Double
            newPrice.Bid = Double.parseDouble(CoinInfo.get(BidToken).toString());
            newPrice.Ask = Double.parseDouble(CoinInfo.get(AskToken).toString());
            newPrice.Highest = Double.parseDouble(CoinInfo.get(HighestIn24Hr).toString()); 
            newPrice.Lowest  = Double.parseDouble(CoinInfo.get(LowestIn24Hr).toString());
            
            CachedPrice.put(coin, newPrice);
        }
    }
    
    public CoinPrice getPrice(EnumCoin coin)
    {
    	return CachedPrice.get(coin);
    }
}