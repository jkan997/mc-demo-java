/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adobe.demo.mcdemo;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jakaniew
 */
public class LogHelper {

    public final static Logger logger = Logger.getLogger("com.adobe.demo.mcdemo");

    public static void log(String msg, String... params) {
        String fmtMsg = String.format(msg, params);
        logger.log(Level.INFO, fmtMsg);
    }

    public static void logError(Exception ex) {
        logger.log(Level.INFO, ex.getMessage(), ex);
    }
}
