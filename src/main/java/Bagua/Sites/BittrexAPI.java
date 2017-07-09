package Bagua.Sites;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONObject;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;
import Bagua.Interface.ICachedSite;


public class BittrexAPI extends ICachedSite
{
    final private String MarketToken = "MarketName";
    final private String BidToken = "Bid";
    final private String AskToken = "Ask";
    final private String HighestIn24Hr = "High";
    final private String LowestIn24Hr = "Low";


    public BittrexAPI() throws Exception
    {
        // Create As Bittrex Website;
        // This can throws exception when is failed to connect.
        super("https://bittrex.com/api/v1.1/public/getmarketsummaries");
    }

    private boolean isaBitcoinVeriusRatio(String token)
    {
        return token.startsWith("BTC-");
    }

    public EnumCoins[] getAvailableCoins()
    {
        // ��� ���ε��� �����մϴ�.
        return EnumCoins.values();
    }

    public CoinInfo getCoinInfo(EnumCoins coin)
    {
        return Cached.get(CreateKey(coin));
    }

    public void Refresh()
    {
        // �ٽ� ĳ���ϱ����� ��� ĳ�õ� ���� Ŭ�����մϴ�.
        Cached.clear();

        ArrayList<JSONObject> InfoArray = (ArrayList<JSONObject>) getObject(
            // Get into depth...
            new String[] { "result" }
        );

        for(Map<String, String> InfoObject : InfoArray)
        {
            // ������ �̸��� �����ɴϴ�.
            String marketName   = InfoObject.get(MarketToken);

            // BTC �� �������� �� ������ ���� �ƴ϶�� �������� ���մϴ�.
            if(isaBitcoinVeriusRatio(marketName) == false) 
            {
                continue;
            }
            
            // ����Ʈ�κ��� ���� �����ɴϴ�.
            // ����Ʈ�� ������ String���̹Ƿ� Double�� �Ľ��� �ʿ��մϴ�.
            CoinInfo newInfo = new CoinInfo();

            newInfo.Ask         = Double.parseDouble(InfoObject.get(BidToken));
            newInfo.Bid         = Double.parseDouble(InfoObject.get(AskToken));
            newInfo.Max24Hr     = Double.parseDouble(InfoObject.get(HighestIn24Hr));
            newInfo.Min24Hr     = Double.parseDouble(InfoObject.get(LowestIn24Hr));

            // �պκ��� "BTC-"" �κ��� �߶���ϴ�.
            String TargetCoin   = marketName.substring(4);

            // ĳ�õȰ��� �ֽ��ϴ�.
            Cached.put(TargetCoin, newInfo);
        }
    }
}