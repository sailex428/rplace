package me.sailex.rplace.scoreboard;

public enum EntryName {

    ENTRY_0(0, "§1"),
    ENTRY_1(1, "§2"),
    ENTRY_2(2, "§3"),
    ENTRY_3(3, "§4"),
    ENTRY_4(4, "§5");

    private final int entry;
    private final String entryName;

    EntryName(int entry, String entryName) {
        this.entry = entry;
        this.entryName = entryName;
    }

    public int getEntry() {
        return entry;
    }

    public String getEntryName() {
        return entryName;
    }

}
