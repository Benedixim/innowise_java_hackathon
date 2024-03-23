package com.ua.money24.helper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class InlineKeyboardMarkupWrapper extends InlineKeyboardMarkup {
    private int rowWidth;

    public InlineKeyboardMarkupWrapper(int rowWidth, List<List<InlineKeyboardButton>> inlineKeyboard) {
        if (inlineKeyboard == null) {
            inlineKeyboard = new ArrayList<>();
        }
        this.rowWidth = rowWidth;
        this.setKeyboard(inlineKeyboard);
    }

    public InlineKeyboardMarkupWrapper(int rowWidth) {
        this(rowWidth, null);
    }

    public InlineKeyboardMarkupWrapper add(InlineKeyboardButton... buttons) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int index = 0; index < buttons.length; index++) {
            row.add(buttons[index]);
            if ((index + 1) % rowWidth == 0) {
                getKeyboard().add(row);
                row = new ArrayList<>();
            }
        }
        if (!row.isEmpty()) {
            getKeyboard().add(row);
        }
        return this;
    }

    public InlineKeyboardMarkupWrapper row(InlineKeyboardButton... buttons) {
        getKeyboard().add(List.of(buttons));
        return this;
    }

    public InlineKeyboardMarkupWrapper insert(InlineKeyboardButton button) {
        if (!getKeyboard().isEmpty() && getKeyboard().get(getKeyboard().size() - 1).size() < rowWidth) {
            getKeyboard().get(getKeyboard().size() - 1).add(button);
        } else {
            add(button);
        }
        return this;
    }


}
