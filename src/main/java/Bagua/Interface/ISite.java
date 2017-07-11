package Bagua.Interface;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;


public abstract class ISite
{
    // Bitcoin : coin = Value : 1 ���·� ���;��մϴ�.
    // coin�� ������ 1�϶��� ��Ʈ���� ������ �����ּ���.
    public abstract CoinInfo getCoinInfo(EnumCoins coin);

    // ��� ��Ʈ���� �����͸� ĳ���մϴ�.
    public abstract void Refresh();

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