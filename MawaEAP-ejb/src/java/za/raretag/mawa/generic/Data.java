/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

/**
 *
 * @author tebogom
 */
public class Data<T> {

    private T data;

    public void set(T data) {
        this.data = data;
    }

    public T get() {
        return data;
    }
}
