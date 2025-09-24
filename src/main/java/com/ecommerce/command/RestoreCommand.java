package com.ecommerce.command;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RestoreCommand implements Command {
    private static final String BACKUP_ZIP = "./backup/ecommerce_backup.zip";
    private static final String DB_DIRECTORY = "./restore/";

    @Override
    public void execute() throws IOException {
        Path backupPath = Paths.get(BACKUP_ZIP);

        // Check if backup ZIP exists
        if (!Files.exists(backupPath)) {
            throw new RuntimeException("Backup ZIP not found: " + BACKUP_ZIP);
        }

        // Create database directory if it doesn't exist
        Path dbDirPath = Paths.get(DB_DIRECTORY);
        Files.createDirectories(dbDirPath);

        // Extract the database file from the ZIP
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(backupPath.toFile()))) {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                Path filePath = dbDirPath.resolve(entry.getName());

                // Create parent directories if needed
                if (filePath.getParent() != null) {
                    Files.createDirectories(filePath.getParent());
                }

                try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                }

                zis.closeEntry();
                entry = zis.getNextEntry();
            }
        }
        System.out.println("Database restored successfully to: " + DB_DIRECTORY);
    }
}
