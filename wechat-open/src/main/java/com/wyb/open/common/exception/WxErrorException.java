package com.wyb.open.common.exception;

/**
 * @author Kunzite
 */
public class WxErrorException extends Exception {

    private static final long serialVersionUID = -7369829024021589841L;
    private WxError error;

    public WxErrorException(WxError error) {
        super();
        this.error = error;
    }

    public WxErrorException(WxError error, Throwable cause) {
        super(error.toString(), cause);
        this.error = error;
    }

    public WxError getError() {
        return this.error;
    }

}
