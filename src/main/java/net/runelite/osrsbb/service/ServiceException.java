package net.runelite.osrsbb.service;

public class ServiceException extends Exception {
    /**
     * Exception message
     */
    private static final long serialVersionUID = -7803126953457755292L;

    public ServiceException(String message) {
        super(message);
    }
}
