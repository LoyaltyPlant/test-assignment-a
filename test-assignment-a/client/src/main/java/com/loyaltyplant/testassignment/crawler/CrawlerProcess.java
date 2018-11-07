package com.loyaltyplant.testassignment.crawler;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface CrawlerProcess<T> {

    /**
     * Возвращает текущий прогресс выполнения задания, значение от 0 до 1
     * @return текущий прогресс
     */
    float getProgress();

    /**
     * Возвращает сообщение о текущем статусе задания ("в процессе", "окончен" и т.п.)
     * @return сообщение
     */
    @Nonnull
    String getStatusMessage();

    /**
     * Возвращает сообщение о текущем статусе задания
     * @return сообщение
     */
    @Nonnull
    default String getProgressMessage() {
        int size = getResult().size();
        String statusMessage = getStatusMessage();
        char[] progressBar = new char[35];
        float progress = getProgress();
        int progressPos = (int) (progressBar.length * progress - .001);
        Arrays.fill(progressBar, ' ');
        Arrays.fill(progressBar, 0, progressPos, '=');
        progressBar[progressPos] = '>';
        return String.format("Progress [%s %3d%%] %s (%d records)",
                             new String(progressBar), (int) (progress * 100f), statusMessage, size);
    }

    /**
     * Останавливает текущий поток до окончания исполнения задания или по достижению таймаута
     */
    boolean waitForCompletion(long timeout, TimeUnit timeUnit);

    boolean isCompleted();

    /**
     * Отмена выполнения задания
     */
    void cancelJob();

    /**
     * Возвращает результат выполнения задания. Если задание ещё не окончено, то возвращает текущий срез
     * загруженных записей.
     *
     * @return список загруженных элементов
     */
    @Nonnull
    List<T> getResult();
}
