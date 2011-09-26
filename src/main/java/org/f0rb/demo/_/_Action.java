package org.f0rb.demo._;

import com.opensymphony.xwork2.ModelDriven;

/**
 * Created by IntelliJ IDEA.
 * Author: yuanzhen
 * Date: 2010-8-30
 * Time: 15:02:05
 * To change this template use File | Settings | File Templates.
 */
public abstract class _Action<T extends _DTO<?>> implements ModelDriven<T> {
    private T o;
    private _Service<T> service;

    protected _Action(T o) {
        this.o = o;
    }

    /**
     * Method getModel returns the model of this _Action object.
     *
     * @return the model (type T) of this _Action object.
     */
    public T getModel() {
        return o;
    }

    /**
     * Method execute ...
     *
     * @return String
     */
    public String execute(){
        return service.execute(o);
    }

    public void setService(_Service<T> service) {
        this.service = service;
    }
}
