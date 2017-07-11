package Bagua.Sites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;
import Bagua.Base.JsonTool;
import Bagua.Base.SiteParser;
import Bagua.Interface.ISite;


public class PloniexAPI extends ISite
{
    final private String BidToken = "Bid";
    final private String AskToken = "Ask";
    final private String HighestIn24Hr = "High";
    final private String LowestIn24Hr = "Low";

    SiteParser                  Parser;
    HashMap<String, CoinInfo>   Cached;

    EnumCoins[] AvailableCoins =
    {
        EnumCoins.BCN,   EnumCoins.BELA,  EnumCoins.BLK,  EnumCoins.BTCD, EnumCoins.BTM,  EnumCoins.BTS,  EnumCoins.BURST,
        EnumCoins.CLAM,  EnumCoins.DASH,  EnumCoins.DGB,  EnumCoins.DOGE, EnumCoins.EMC2, EnumCoins.FLDC, EnumCoins.FLO, 
        EnumCoins.GAME,  EnumCoins.GRC,   EnumCoins.HUC,  EnumCoins.LTC,  EnumCoins.MAID, EnumCoins.OMNI, EnumCoins.NAUT,
        EnumCoins.NAV,   EnumCoins.NEOS,  EnumCoins.NMC,  EnumCoins.NOTE, EnumCoins.NXT,  EnumCoins.PINK, EnumCoins.POT,
        EnumCoins.PPC,   EnumCoins.RIC,   EnumCoins.SJCX, EnumCoins.STR,  EnumCoins.SYS,  EnumCoins.VIA,  EnumCoins.XVC, 
        EnumCoins.VTC,   EnumCoins.XBC,   EnumCoins.XCP,  EnumCoins.XEM,  EnumCoins.XMR,  EnumCoins.XPM,  EnumCoins.XRP, 
        EnumCoins.SC,    EnumCoins.BCY,   EnumCoins.EXP,  EnumCoins.FCT,  EnumCoins.RADS, EnumCoins.AMP,  EnumCoins.DCR, 
        EnumCoins.LBC,   EnumCoins.STEEM, EnumCoins.SBD,  EnumCoins.ETC,  EnumCoins.REP,  EnumCoins.ARDR, EnumCoins.ZEC,
        EnumCoins.START, EnumCoins.NXC,   EnumCoins.PASC, EnumCoins.GNT,  EnumCoins.GNO,  EnumCoins.LSK,  EnumCoins.ETH,
        EnumCoins.VRC,
    };

    public PloniexAPI() throws Exception
    {
        // Create As Ploniex Website.
        // This can throws exception when is failed to connect.
        Parser = new SiteParser("https://poloniex.com/public?command=returnTicker");
        Cached = new HashMap<String, CoinInfo>();
    }

    private boolean isaBitcoinVeriusRatio(String token)
    {
        return token.startsWith("BTC_");
    }

    public CoinInfo getCoinInfo(EnumCoins coin)
    {
        return Cached.get(CreateKey(coin));
    }

    public void Refresh()
    {
        // �ٽ� ĳ���ϱ����� ��� ĳ�õ� ���� Ŭ�����մϴ�.
        Cached.clear();

        Map<String, Map<String, String>> InfoObject = (Map)Parser.getObject();

        for(EnumCoins coin : AvailableCoins)
        {
            Map<String, String> CoinInfo = InfoObject.get(CreateKey(EnumCoins.BTC) + "_" + CreateKey(coin));

            // ����Ʈ�κ��� ���� �����ɴϴ�.
            // ����Ʈ�� ������ String���̹Ƿ� Double�� �Ľ��� �ʿ��մϴ�.
            CoinInfo newInfo = new CoinInfo();

            newInfo.Ask         = Double.parseDouble(CoinInfo.get(BidToken));
            newInfo.Bid         = Double.parseDouble(CoinInfo.get(AskToken));
            newInfo.Max24Hr     = Double.parseDouble(CoinInfo.get(HighestIn24Hr));
            newInfo.Min24Hr     = Double.parseDouble(CoinInfo.get(LowestIn24Hr));

            // ĳ�õȰ��� �ֽ��ϴ�.
            Cached.put(CreateKey(coin), newInfo);
        }
    }
}