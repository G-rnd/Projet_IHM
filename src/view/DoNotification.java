package view;

public interface DoNotification {
    /**
     * Définit l'entité à notifier.
     *
     * @param receiver L'entité à notifier.
     */
    void setReceiver(ReceiveNotification receiver);

    /**
     * Envoie une notification de fin de lecture lecture d'occurrences.
     *
     * @param receiver L'entité à notifier.
     */
    void notifyLoadedObis1(ReceiveNotification receiver);

    /**
     * Envoie une notification de fin de lecture lecture d'occurrences.
     *
     * @param receiver L'entité à notifier.
     */
    void notifyLoadedObis2(ReceiveNotification receiver);

    /**
     * Envoie une notification de fin de lecture lecture d'occurrences.
     *
     * @param receiver L'entité à notifier.
     */
    void notifyLoadedObis3(ReceiveNotification receiver);

    /**
     * Envoie une notification de fin de lecture lecture de noms.
     *
     * @param receiver L'entité à notifier.
     */
    void notifyLoadedNames1(ReceiveNotification receiver);

    /**
     * Envoie une notification de fin de lecture lecture de noms.
     *
     * @param receiver L'entité à notifier.
     */
    void notifyLoadedNames2(ReceiveNotification receiver);

    /**
     * Envoie une notification de fin de lecture lecture d'un fichier local.
     *
     * @param receiver L'entité à notifier.
     */
    void notifyLoadedLocal(ReceiveNotification receiver);

    /**
     * Envoie une notification de fin de lecture lecture de détails d'observations.
     *
     * @param receiver L'entité à notifier.
     */
    void notifyLoadedODS(ReceiveNotification receiver);
}
