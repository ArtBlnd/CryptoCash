package Bagua.Interface;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;


public interface ISite
{
        // �����Ǵ� ������ ������ ��� ���մϴ�.
    public abstract EnumCoins[] getAvailableCoins();

    // Bitcoin : coin = Value : 1 ���·� ���;��մϴ�.
    // coin�� ������ 1�϶��� ��Ʈ���� ������ �����ּ���.
    public abstract CoinInfo getCoinInfo(EnumCoins coin);

    // ��� ��Ʈ���� �����͸� ĳ���մϴ�.
    public abstract void Refresh();
}