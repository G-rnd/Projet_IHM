package view;

public interface ReceiveNotification {
    /**
     * Action effectuée à la réception d'une notification d'une lecture d'occurrences.
     */
    void receiveNotificationLoadedObis1();

    /**
     * Action effectuée à la réception d'une notification d'une lecture d'occurrences.
     */
    void receiveNotificationLoadedObis2();

    /**
     * Action effectuée à la réception d'une notification d'une lecture d'occurrences.
     */
    void receiveNotificationLoadedObis3();

    /**
     * Action effectuée à la réception d'une notification d'une lecture de noms.
     */
    void receiveNotificationLoadedNames1();

    /**
     * Action effectuée à la réception d'une notification d'une lecture de noms.
     */
    void receiveNotificationLoadedNames2();

    /**
     * Action effectuée à la réception d'une notification d'une lecture d'un fichier local.
     */
    void receiveNotificationLoadedLocal();

    /**
     * Action effectuée à la réception d'une notification d'une lecture de détails d'observations.
     */
    void receiveNotificationLoadedODS();
}
