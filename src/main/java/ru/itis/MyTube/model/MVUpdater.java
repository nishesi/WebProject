package ru.itis.MyTube.model;

import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MVUpdater {
    private final Updater updater;
    private final Thread updaterThread;

    public MVUpdater(DataSource dataSource, long period) {
        updater = new Updater(dataSource, period);
        updaterThread = new Thread(updater);
    }

    public void start() {
        updaterThread.start();
    }

    public void end() {
        updater.isRun = false;
    }

    @RequiredArgsConstructor
    private static class Updater implements Runnable {
        private static final String SQL_UPDATE_MV =
                "refresh materialized view channels_inf;" +
                "refresh materialized view videos_inf;";
        private final DataSource dataSource;

        private final long period;
        private boolean isRun = true;
        @Override
        public void run() {
            while (isRun) {
                update();
                try {
                    Thread.sleep(period);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        private void update() {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_MV) ) {

                preparedStatement.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
