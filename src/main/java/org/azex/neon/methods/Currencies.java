package org.azex.neon.methods;

import java.util.UUID;

public class Currencies {

    private final YmlManager ymlManager;

    public Currencies(YmlManager ymlManager) {
        this.ymlManager = ymlManager;
    }

    public int getTokens(UUID uuid) {
        return ymlManager.getTokensFile().getInt(uuid.toString(), 0);
    }

    public void setTokens(UUID uuid, int amount) {
        ymlManager.getTokensFile().set(uuid.toString(), amount);
        ymlManager.saveTokensFile();
    }

    public int getWins(UUID uuid) {
        return ymlManager.getWinsFile().getInt(uuid.toString(), 0);
    }

    public void setWins(UUID uuid, int amount) {
        ymlManager.getWinsFile().set(uuid.toString(), amount);
        ymlManager.saveWinsFile();
    }

}
