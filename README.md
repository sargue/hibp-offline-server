# HIBP Pwned Passwords offline server

This is a very simple implementation of the
[HIBP's passwords API](https://haveibeenpwned.com/API/v2#PwnedPasswords)
as an offline / local server.

It uses the [downloadable password list](https://haveibeenpwned.com/Passwords)
to perform local searches.

## Requirements

Only Java 8 or later is required.

A git client makes the download and installation even easier, but it's
not mandatory.

## Installing the database

Download the passwords database. Specifically the SHA-1 ordered by hash.
You can find direct link and torrent files
[on this page](https://haveibeenpwned.com/Passwords).

After downloading decompress and take note of the path to the *txt* file.

## Running the offline server


    git clone https://github.com/sargue/hibp-offline-server.git
    cd hibp-offline-server
    gradlew run http://localhost:8080 path-to-txt-file

Alternatively you could build changing the last command with

    gradlew installDist

Look into the *build* directory, you will find packages inside the
*distribution* directory. You can also run the built version using
this command:

    build/install/hibp-offline-server/bin/hibp-offline-server http://localhost:8080 path-to-txt-file

## Testing and browser based checking

After the server is running you can point a browser to the listening
location for a simple password checking form. It works more or less
like the [online one](https://haveibeenpwned.com/Passwords).

You can also use the API, for example: http://localhost:8080/range/21DBC
