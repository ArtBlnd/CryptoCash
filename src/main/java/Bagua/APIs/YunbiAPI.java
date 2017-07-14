package Bagua.APIs;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Bagua.Base.CoinPrice;
import Bagua.Base.EnumCoin;
import Bagua.Interface.ICachedSite;

public class YunbiAPI implements ICachedSite
{
    final String TargetURL = "https://yunbi.com//api/v2/tickers.json";

    final private String BidToken = "sell";
    final private String AskToken = "buy";
    final private String HighestIn24Hr = "high";
    final private String LowestIn24Hr = "low";
    
    EnumCoin[] AvailableCoins =
    {
    		EnumCoin.ETH, EnumCoin.DGD, EnumCoin.BTS,
    		EnumCoin.SC, EnumCoin._1ST, EnumCoin.REP,
    		EnumCoin.ANS, EnumCoin.ZEC, EnumCoin.ZMC,
    		EnumCoin.GNT, EnumCoin.QTUM, EnumCoin.GXS,
    		EnumCoin.EOS
    };
    
    Map<EnumCoin, CoinPrice> CachedPrice;
    
    public YunbiAPI()
    {
        CachedPrice = new HashMap<EnumCoin, CoinPrice>();
    }

    public void Refresh() throws IOException, ParseException
    {
        // Clearing all caches from map.
        CachedPrice.clear();
        
        URL                 URLTarget = new URL(TargetURL);
        InputStreamReader   URLStream = new InputStreamReader(URLTarget.openStream());
        JSONParser          TickerParser = new JSONParser();
        JSONObject ParsedTicker = (JSONObject) TickerParser.parse(URLStream);
        
        // parse btc/cny price for calculating altercoin/btc
        String BTCName = "btccny";
        CoinPrice BTCPrice = new CoinPrice();
        JSONObject BTCInfo = (JSONObject) ParsedTicker.get(BTCName);
        
        // just for safety reason. JSONObject.something -> String -> Double
        BTCPrice.Bid = Double.parseDouble(BTCInfo.get(BidToken).toString());
        BTCPrice.Ask = Double.parseDouble(BTCInfo.get(AskToken).toString());
        BTCPrice.Highest = Double.parseDouble(BTCInfo.get(HighestIn24Hr).toString()); 
        BTCPrice.Lowest  = Double.parseDouble(BTCInfo.get(LowestIn24Hr).toString());

        for(EnumCoin coin : AvailableCoins)
        {
        	String CoinName = coin.toString().toLowerCase() + "cny";
            CoinPrice newPrice = new CoinPrice();
            JSONObject CoinInfo = (JSONObject) ParsedTicker.get(CoinName);
            
            // just for safety reason. JSONObject.something -> String -> Double
            // (coin/cny) / (btc/cny) = coin/cny * cny/btc = coin/btc
            // simple math. but there are no consideration of fee rate. be careful to use this method.
            newPrice.Bid = Double.parseDouble(CoinInfo.get(BidToken).toString()) / BTCPrice.Bid;
            newPrice.Ask = Double.parseDouble(CoinInfo.get(AskToken).toString()) / BTCPrice.Ask;
            newPrice.Highest = Double.parseDouble(CoinInfo.get(HighestIn24Hr).toString()) / BTCPrice.Highest; 
            newPrice.Lowest  = Double.parseDouble(CoinInfo.get(LowestIn24Hr).toString()) / BTCPrice.Lowest;
            
            CachedPrice.put(coin, newPrice);
        }
    }
    
    public CoinPrice getPrice(EnumCoin coin)
    {
    	return CachedPrice.get(coin);
    }
}