package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.items.custom.ToolTipItem;
import net.deadlydiamond98.magiclib.items.MagicItemData;

public class MasterKey extends ToolTipItem implements MagicItemData {
    public MasterKey(Settings settings) {
        super(settings);
    }

    @Override
    public int getManaCost() {
        return 25;
    }
}
