package org.coastline.one.common.java.proxy;

/**
 * @author Jay.H.Zou
 * @date 2022/1/27
 */
public class MoneyService implements IMoneyService{
    @Override
    public boolean haveMoney() {
        System.out.println("I'm rich.");
        return true;
    }
}
