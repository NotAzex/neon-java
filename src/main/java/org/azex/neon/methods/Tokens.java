package org.azex.neon.methods;

import org.azex.neon.Neon;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Tokens {

    public final Set<UUID> requestedToken = new HashSet<>();

    private final YmlManager ymlManager;

    public Tokens(YmlManager ymlManager) {
        this.ymlManager = ymlManager;
    }

    public int getTokens(UUID uuid) {
        return ymlManager.getTokensFile().getInt(uuid.toString(), 0);
    }

    public void setTokens(UUID uuid, int amount) {
        ymlManager.getTokensFile().set(uuid.toString(), amount);
        ymlManager.saveTokensFile();
    }

}
