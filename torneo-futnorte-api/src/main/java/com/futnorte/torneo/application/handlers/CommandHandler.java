package com.futnorte.torneo.application.handlers;

public interface CommandHandler <T, R> {
    R handle(T command);
}
