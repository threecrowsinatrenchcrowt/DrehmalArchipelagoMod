package net.threecrows.drehmal_archipelago.util.tracker;

public interface IAbilityCheck {
    int getIntCheckValue(String id);
    boolean getBooleanCheckValue(String id);

    default boolean has(String id) {
        return getBooleanCheckValue(id);
    }

    default boolean has(String id, int count) {
        return getIntCheckValue(id) > (count - 1);
    }
}
