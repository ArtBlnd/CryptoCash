package Bagua.Interface;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;
import Bagua.Base.JsonTool;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public abstract class ICachedSite
{
    protected HashMap<String, CoinInfo> Cached;

    protected JSONParser                Parser;
    protected InputStreamReader         SiteReader;
    protected URL                       Site;

    public ICachedSite(String SiteURL) throws Exception
    {
        // Creating new common site parser.
        Parser      = new JSONParser();
        Cached      = new HashMap<String,CoinInfo>();

        // Initialize Reader.
        Site        = new URL(SiteURL);
        SiteReader  = new InputStreamReader(Site.openStream(), "UTF-8");
    }
    
    // ����Ʈ�κ��� ������Ʈ�� �о�ɴϴ�.
    protected Object getObject()
    {
        Object object = null;

        try 
        {
            // Parse Site. and emit on object.
            object = (JSONObject)Parser.parse(SiteReader);
        }
        catch(Exception e)  // TODO : emit exception here.
        {

        }
        return object;
    }

    // ��Ʈ�� �ִ� ������Ʈ�� �����ݴϴ�.
    // ���� : getObject(new String[] {"result", "objects", "currency"})
    //          result : {
    //              objects : {
    //                  currency : {
    //                      bid : 1.23
    //                      ask : 1.22
    //                  }
    //              }
    //          }
    // ask, bid �� ���� ����ִ� Map�� ��ȯ�մϴ�.
    protected Object getObject(String[] Tokens)
    {
        Object object = getObject();
        return JsonTool.ParseWithTokenDepth(object, Tokens);
    }

    // ĳ�õ� ���ε鿡 ���� Ű�� ����ϴ�.
    protected String CreateKey(EnumCoins coin)
    {
        String Token = coin.toString();

        if(coin == EnumCoins._1ST | coin == EnumCoins._2GIVE)
        {
            // �պκ��� _�� �߶���ϴ�.
            Token = Token.substring(1);
        }

        return Token;
    }
}