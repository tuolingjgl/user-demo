package org.f0rb.demo._;

/**
 * Class org.f0rb.demo.service description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-7-14
 */
public interface _Service<T extends _DTO<?>> {
    String execute(T t);
}
