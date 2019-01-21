package net.sargue.hibp;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Objects;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.logging.Level.SEVERE;

public class PwnedPasswords {
    private final static Logger log = Logger.getLogger(PwnedPasswords.class.getName());

    private File file;

    public PwnedPasswords(File file) {
        this.file = Objects.requireNonNull(file);
    }

    /**
     *
     * @param hashPrefix the 5 character hash prefix
     * @return lines of the pwned passwords file corresponding to the given prefix
     */
    public String checkHashRange(String hashPrefix) {
        if (hashPrefix == null || hashPrefix.length() != 5)
            throw new IllegalArgumentException("The hashPrefix must be a 5 character string.");

        try ( RandomAccessFile f = new RandomAccessFile(file, "r") ) {
            return checkHashRange(f, hashPrefix.toUpperCase(), 0, f.length());
        } catch (IOException e) {
            log.log(SEVERE, "Error accessing passwords file", e);
            return "";
        }
    }

    private String checkHashRange(RandomAccessFile f, String hashPrefix, long start, long end)
        throws IOException
    {
        long halfway = start + (end - start) / 2;
        if (halfway - start < 44) return ""; // 44 bytes, minimum row length
        long middleRow = startOfRow(f, halfway);
        String currentPrefix = rowPrefix(f, middleRow);
        if (hashPrefix.equals(currentPrefix))
            return fetchRange(f, hashPrefix, middleRow);
        if (hashPrefix.compareTo(currentPrefix) > 0)
            return checkHashRange(f, hashPrefix, middleRow, end);
        return checkHashRange(f, hashPrefix, start, middleRow);
    }

    private long startOfRow(RandomAccessFile f, long position) throws IOException {
        while (position > 0) {
            f.seek(position);
            if (f.read() == '\n') return position + 1;
            position--;
        }
        return 0;
    }

    private String rowPrefix(RandomAccessFile f, long rowStart) throws IOException {
        byte[] buffer = new byte[5];
        f.seek(rowStart);
        f.read(buffer);
        return new String(buffer, US_ASCII);
    }

    private String fetchRange(RandomAccessFile f, String hashPrefix, long rowStart)
        throws IOException
    {
        // 1st, backwards until the start or the first row with this prefix
        long currentRow = rowStart;
        long previousRow = startOfRow(f, currentRow - 44); // 44 bytes, minimum row length
        String previousPrefix = rowPrefix(f, previousRow);
        while (previousRow > 0 && previousPrefix.equals(hashPrefix)) {
            currentRow = previousRow;
            previousRow = startOfRow(f, currentRow - 44);
            previousPrefix = rowPrefix(f, previousRow);
        }

        if (previousRow == 0 && previousPrefix.equals(hashPrefix))
            currentRow = previousRow;

        // 2nd, read lines until the prefix changes or the EOF
        StringBuilder sb = new StringBuilder();
        f.seek(currentRow);
        String line = f.readLine();
        while (line != null && line.startsWith(hashPrefix)) {
            sb.append(line, 5, line.length()).append('\r').append('\n');
            line = f.readLine();
        }
        return sb.toString();
    }
}
