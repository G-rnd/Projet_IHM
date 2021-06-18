package view;

public interface DoNotification {
    void setReceiver(ReceiveNotification receiver);

    void notifyLoadedObis1(ReceiveNotification receiver);
    void notifyLoadedObis2(ReceiveNotification receiver);
    void notifyLoadedObis3(ReceiveNotification receiver);

    void notifyLoadedNames1(ReceiveNotification receiver);
    void notifyLoadedNames2(ReceiveNotification receiver);

    void notifyLoadedLocal(ReceiveNotification receiver);
    void notifyLoadedODS(ReceiveNotification receiver);
}
