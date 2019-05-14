# Diff Application

This application was built using Gradle and Spring.
To run, `gradle bootRun`.

The application will run in localhost port 8080.

To test it,

### Configuration parameters:

* storage.location: The location of where files will be stored.

### API

The following API are supported:

`GET` `v1/diff/{id}`: Performs a diff on two files with *{id}*.

`GET` `v1/diff/{id}/left` `v1/diff/{id}/rights`: Gets the content of both files (left or right) for *{id}*. The files are Base64 encoded.

`PUT` `v1/diff/{id}/left` `v1/diff/{id}/rights`: Puts the content of both files (left or right) for *{id}*. The files must be Base64 encoded.

`DELETE` `v1/diff/{id}/left` `v1/diff/{id}/rights`: Delete the both files (left or right) for *{id}*. The files must be Base64 encoded.
