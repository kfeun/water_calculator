package com.ajulay.calculate;

import com.ajulay.model.Claster;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.RecursiveTask;


@AllArgsConstructor
public class CalculateTask extends RecursiveTask<Integer> {

    private final List<Claster> clasters;

    private final int start;

    private final int end;

    private final Boolean needToRight;

    private Claster maxClaster;


    @Override
    protected Integer compute() {
        if (clasters.size() < 3) return 0;

        if (maxClaster == null) {
            maxClaster = getMax(start, end);
            if (maxClaster != null) {
                CalculateTask task1 = new CalculateTask(clasters, start, maxClaster.getPosition(), Boolean.FALSE, maxClaster);
                CalculateTask task2 = new CalculateTask(clasters, maxClaster.getPosition(), end, Boolean.TRUE, maxClaster);
                task1.fork();
                return task2.compute() + task1.join();
            }
            return 0;
        } else {
            Claster newMaxClaster = getMax(start, end);

            if (newMaxClaster == null) {
                return 0;
            }

            int sum = calculateInternalValue(maxClaster, newMaxClaster);

            if (needToRight && newMaxClaster.getPosition() < end - 1) {
                CalculateTask task = new CalculateTask(clasters, newMaxClaster.getPosition(), end, Boolean.TRUE, newMaxClaster);
                task.fork();
                return sum + task.join();
            }
            if (!needToRight && newMaxClaster.getPosition() > start - 1) {
                CalculateTask task = new CalculateTask(clasters, start, newMaxClaster.getPosition(), Boolean.FALSE, newMaxClaster);
                task.fork();
                return sum + task.join();
            }

            return sum;
        }
    }

    private int calculate(final int start, final int end) {
        final int lowLevelValue = Math.min(clasters.get(start).getValue(), clasters.get(end).getValue());
        final int allSquare = lowLevelValue * (Math.abs(start - end) - 1);
        int sumValue = 0;
        if (start == maxClaster.getPosition() || maxClaster.getPosition() == end) {
            for (int i = start + 1; i < end; i++) {
                sumValue += clasters.get(i).getValue();
            }
        }
        return allSquare - sumValue;
    }

    private int calculateInternalValue(Claster start, Claster end) {
        if (end == null || start == null) {
            return 0;
        }
        if (start.getPosition() < end.getPosition()) {
            return calculate(start.getPosition(), end.getPosition());
        }
        return calculate(end.getPosition(), start.getPosition());
    }

    private Claster getMax(int start, int end) { //right
        if (maxClaster == null) {
            return clasters.parallelStream().max(Comparator.comparingInt(Claster::getValue)).orElse(null);
        }
        final int maxClasterPosition = maxClaster.getPosition();
        final int range = end - start;
        if (maxClasterPosition == start && maxClasterPosition < end - 1) {
            return clasters.stream().skip(start + 1).limit(range).parallel().max(Comparator.comparingInt(Claster::getValue)).orElse(null);
        }
        if (maxClasterPosition == end && maxClasterPosition > start + 1)
            return clasters.stream().skip(start).limit(range).parallel().max(Comparator.comparingInt(Claster::getValue)).orElse(null);

        return null;
    }

}
