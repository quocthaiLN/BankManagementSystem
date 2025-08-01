package com.bank.app.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentVariablesManager {
    private static final String KEYSTORE_PATH = "src/main/resources/key";
    private static final Dotenv dotenv = Dotenv.configure().directory(KEYSTORE_PATH).load();

    public static String get(String nameVarEnv) {
        String varEnv = dotenv.get(nameVarEnv);
        if (varEnv == null) {
            throw new IllegalStateException("Invalid name of variable environment");
        }
        return varEnv;
    }
}