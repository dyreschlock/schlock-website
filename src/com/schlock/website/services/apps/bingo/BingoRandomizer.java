package com.schlock.website.services.apps.bingo;

import java.util.List;

public interface BingoRandomizer
{
    public List<String> createOrder();

    public List<String> createOrder(String parameter);
}
