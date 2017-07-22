package Bagua.APIs;

import Bagua.Interface.ICachedSite;
import Bagua.Base.CoinPrice;
import Bagua.Base.EnumCoin;

import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CoinOneAPI implements ICachedSite
{
    private final String tokenMinPrice = "low";
    private final String tokenMaxPrice = "high";
    private final String tokenFirstPrice = "first";
    private final String tokenLastPrice = "last";
    private final String tokenPrice = "price";

    private final String tickerRequestURL = "https://api.coinone.co.kr/ticker/";
    private final String orderbookRequestURL = "https://api.coinone.co.kr/orderbook/";

    JSONParser jsonParser;
    Map<EnumCoin, CoinPrice> CachedPrice;

    public CoinOneAPI()
    {
        jsonParser = new JSONParser();
        CachedPrice = new HashMap<EnumCoin, CoinPrice>();
    }
    
    private String CoinTypeToToken(EnumCoin type)
    {
        switch(type)
        {
        case BTC : return "btc";
        case ETH : return "eth";
        case ETC : return "etc";
        case XRP : return "xrp";
		default:
			break;
        }
		return "";
    }

    public EnumCoin[] getAvailableCoinTypes()
    {
        return new EnumCoin[] 
        {
            EnumCoin.BTC,
            EnumCoin.ETH,
            EnumCoin.ETC,
            EnumCoin.XRP
        };
    }

    public void Refresh()
    {
        for(EnumCoin type : EnumCoin.values())
        {
        	Refresh(type);
        }   
    }
    public void Refresh(EnumCoin type)
    {
        String targetTickerURL    = tickerRequestURL + CoinTypeToToken(type);
        String targetOrderbookURL = orderbookRequestURL + CoinTypeToToken(type);
        CoinPrice BTCPrice = new CoinPrice();
        {
            final String TargetURL = "https://www.okcoin.com/api/v1/ticker.do?symbol=";
            URL                 URLTarget = new URL(TargetURL);
            InputStreamReader   URLStream = new InputStreamReader(URLTarget.openStream());
            JSONParser          TickerParser = new JSONParser();
            JSONObject ParsedJSON = (JSONObject) TickerParser.parse(URLStream);
            JSONObject ParsedTicker = (JSONObject) ParsedJSON.get("ticker");

            String BTCName = "btc_usd";
            JSONObject BTCInfo = (JSONObject) ParsedTicker.get(BTCName);

            // just for safety reason. JSONObject.something -> String -> Double
            BTCPrice.Bid = Double.parseDouble(BTCInfo.get(BidToken).toString());
            BTCPrice.Ask = Double.parseDouble(BTCInfo.get(AskToken).toString());
            BTCPrice.Highest = Double.parseDouble(BTCInfo.get(HighestIn24Hr).toString());
            BTCPrice.Lowest  = Double.parseDouble(BTCInfo.get(LowestIn24Hr).toString());

        }


        try {
            URL                 CoinOneApiTickerURL     = new URL(targetTickerURL);
            InputStreamReader   stream_ticker           = new InputStreamReader(CoinOneApiTickerURL.openConnection().getInputStream(), "UTF-8");
            JSONObject          jsonObject_ticker;

            URL                 CoinOneApiOrderbookURL  = new URL(targetOrderbookURL);
            InputStreamReader   stream_orderbook        = new InputStreamReader(CoinOneApiOrderbookURL.openConnection().getInputStream(), "UTF-8");
            JSONObject          jsonObject_orderbook;
            JSONObject          jsonObject_orderbook_price_bid;
            JSONObject          jsonObject_orderbook_price_ask;
            JSONArray           jsonArray_orderbook_bid;
            JSONArray           jsonArray_orderbook_ask;
            
            CoinPrice newPrice = new CoinPrice();


            jsonObject_ticker       = (JSONObject) jsonParser.parse(stream_ticker);

            jsonObject_orderbook    = (JSONObject) jsonParser.parse(stream_orderbook);
            jsonArray_orderbook_ask = (JSONArray) jsonObject_orderbook.get("ask");
            jsonArray_orderbook_bid = (JSONArray) jsonObject_orderbook.get("bid");

            
            newPrice.Highest = Double.parseDouble((String)jsonObject_ticker.get(tokenMaxPrice))/ BTCPrice.Highest;
            newPrice.Lowest = Double.parseDouble((String)jsonObject_ticker.get(tokenMinPrice))/ BTCPrice.Lowest;
            /*
            super.get(type).FirstPrice = Double.parseDouble((String)jsonObject_ticker.get(tokenFirstPrice));
            super.get(type).LastPrice = Double.parseDouble((String)jsonObject_ticker.get(tokenLastPrice));
            */
            
            // Parsing Index of JSONArray.size() because last object will return least/most size of bid
            // JSONArray.size()를 index로 파싱하는 이유는. 제일 작거나 제일 큰 값으로 매겨진 값을 얻기 위해서입니다.

            jsonObject_orderbook_price_bid = (JSONObject)jsonArray_orderbook_bid.get(jsonArray_orderbook_ask.size());
            jsonObject_orderbook_price_ask = (JSONObject)jsonArray_orderbook_ask.get(jsonArray_orderbook_ask.size());

            newPrice.Ask = Double.parseDouble((String)jsonObject_orderbook_price_bid.get(tokenPrice))/ BTCPrice.Ask;
            newPrice.Bid = Double.parseDouble((String)jsonObject_orderbook_price_ask.get(tokenPrice))/ BTCPrice.Bid;

            CachedPrice.put(type, newPrice);
            
            stream_ticker.close();
            stream_orderbook.close();
        } catch(Exception e) {

        }
    }

    public CoinPrice getPrice(EnumCoin coin)
    {
    	return CachedPrice.get(coin);
    }
}
