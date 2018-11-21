/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.exceptions;

/**
 *
 * @author sa
 */
public class SessaoNaoEncontradaException extends AppException {
    public String er = null;

    /**
     * Creates a new instance of <code>SessaoNaoEncontrada</code> without detail
     * message.
     */
    public SessaoNaoEncontradaException() {
    }

    /**
     * Constructs an instance of <code>SessaoNaoEncontrada</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SessaoNaoEncontradaException(String msg) {
        super(msg);
    }
    
    public SessaoNaoEncontradaException(String msg,String er) {
        super(msg);
        this.er = er;
    }
}

