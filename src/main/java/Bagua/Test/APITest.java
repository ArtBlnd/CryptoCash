package Bagua.Test;

import java.io.IOException;
import java.util.EnumMap;

import org.json.simple.parser.ParseException;

import Bagua.Base.CoinPrice;
import Bagua.Base.EnumCoin;
import Bagua.Base.EnumSite;
import Bagua.Interface.ICachedSite;
import Bagua.APIs.*;

public class APITest {
	EnumMap<EnumSite, ICachedSite> Sites;

    public APITest() throws Exception
    {
    	Sites = new EnumMap<EnumSite, ICachedSite>(EnumSite.class);
    	Sites.put(EnumSite.Bittrex, new BittrexAPI());
    	Sites.put(EnumSite.Bithumb, new BithumbAPI());
    	Sites.put(EnumSite.BitStamp, new BitStampAPI());
    	Sites.put(EnumSite.CoinOne, new CoinOneAPI());
    	Sites.put(EnumSite.OkCoin, new OkCoinAPI());
    	Sites.put(EnumSite.Poloniex, new PloniexAPI());
    	Sites.put(EnumSite.Yunbi, new YunbiAPI());
    }
    public void Refresh(EnumSite site) throws IOException, ParseException
    {
        Sites.get(site).Refresh();
    }
    public CoinPrice getCoinInfo(EnumSite site, EnumCoin coin)
    {
        return Sites.get(site).getPrice(coin);
    }
	public static void main(String[] args) throws Exception {
		APITest fucked = new APITest();
		for(EnumSite site : EnumSite.values()) {
			fucked.Refresh(site);
		}
	}

}