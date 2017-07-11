package Bagua.Base;

import Bagua.Base.JsonTool;

import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SiteParser
{

    protected   JSONParser                Parser;
    protected   InputStreamReader         SiteReader;
    protected   URL                       Site;

    public SiteParser(String SiteURL) throws Exception
    {
        // Creating new common site parser.
        Parser      = new JSONParser();

        // Initialize Reader.
        Site        = new URL(SiteURL);
        SiteReader  = new InputStreamReader(Site.openStream(), "UTF-8");
    }
    
    // ����Ʈ�κ��� ������Ʈ�� �о�ɴϴ�.
    public Object getObject()
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
    public Object getObject(String[] Tokens)
    {
        Object object = getObject();
        return JsonTool.ParseWithTokenDepth(object, Tokens);
    }
}