package Bagua.Sites;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;
import Bagua.Base.SiteParser;
import Bagua.Interface.ISite;

import java.util.HashMap;

/**
 * Created by benjioh5 on 12/07/2017.
 */
public class OkCoinAPI extends ISite {

    private String URL = "https://www.okcoin.com/api/v1/ticker.do?symbol="; // + XXXBTC

    private HashMap<String, CoinInfo> Cached;
    private EnumCoins[] AvailableCoins = {
        EnumCoins.ETH, EnumCoins.BTC, EnumCoins.LTC,
    };
    SiteParser Parser;

    public BitfineExAPI() {
        Cached = new HashMap<String, CoinInfo>();
    }

    public CoinInfo getCoinInfo(EnumCoins coin)
    {
        return Cached.get(CreateKey(coin));
    }

    public void Refresh()
    {
        Cached.clear();
        try{

            for(EnumCoins coin : AvailableCoins) {
                CoinInfo newInfo = new CoinInfo();
                Parser = new SiteParser(URL + coin.toString() + "BTC");
                JSONObject need_to_parse = (JSONObject) Parser.getObject();

                newInfo.Ask         = Double.parseDouble(need_to_parse.getDouble("buy"));
                newInfo.Bid         = Double.parseDouble(need_to_parse.getDouble("sell"));
                newInfo.Max24Hr     = Double.parseDouble(need_to_parse.getDouble("high"));
                newInfo.Min24Hr     = Double.parseDouble(need_to_parse.getDouble("low"));
                
                Cached.put(CreateKey(coin), newInfo);
            }
        }
        catch (Exception Ect){
            return ;
        }


    }


}
