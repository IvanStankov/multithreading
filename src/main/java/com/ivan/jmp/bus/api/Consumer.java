package com.ivan.jmp.bus.api;

/**
 * Created by Иван on 07.02.2016.
 */
@FunctionalInterface
public interface Consumer {

    void handle(String message);

}
