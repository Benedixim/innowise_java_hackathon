package com.ua.money24.constants;

public interface Messages {
    String START = """
            Привет!
            Курс в этом боте мониторится по обменнику [Money24](%s)
            """;

    String RATE_TEMPLATE = """
            %s
                        
            Покупка: %s
            Продажа: %s
            """;
    String RATE_CHANGED = "Курс изменился❗️";

    String SUBSCRIBE_CURRENCIES = "Выбрать валюту для подписки:";

    String CURRENT_RATE = "Последний курс \uD83D\uDCB2";
    String SUBSCRIBE = "Подписаться \uD83D\uDD14";

    String FINISH = "Завершено!";
}
