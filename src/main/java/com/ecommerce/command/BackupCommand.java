package com.ecommerce.command;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BackupCommand implements Command {

    private static final String DB_FILE = "shop_management.db";
    private static final String BACKUP_ZIP = "./backup/ecommerce_backup.zip";

    @Override
    public void execute() throws IOException {
        Path source = Paths.get(DB_FILE);
        Path zipPath = Paths.get(BACKUP_ZIP);

        // create backup directory if not exists
        Path parentDir = zipPath.getParent();
        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }

        // create ZIP file
        try (FileOutputStream fos = new FileOutputStream(zipPath.toFile());
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(source.toFile())) {

            // add the database file to the ZIP
            ZipEntry entry = new ZipEntry(source.getFileName().toString());
            zos.putNextEntry(entry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }

            zos.closeEntry();
        }

        System.out.println("Backup ZIP created successfully: " + zipPath.toAbsolutePath());
    }
}
