Copyright (c) 2017, Contributors

Lob Demo

What Is This?
-------------

This application is intended to provide a working implementation of Lob’s and 
Google Civic’s APIs. The example provided specifically tests sending a letter
using Lob’s API while utilizing Google Civic’s API to fetch the nearest official
address to direct the letter towards. 


How To Run the Example
----------------------

There are a few different ways to interact with the project

1. Since the project is written in Java, a Java IDE such as Eclipse (highly recommended), Netbeans,or BlueJ may be utilized to directly deploy the project on and test within the 
program. Note that the project is implemented with maven for the sake of the 
Lob and JSON parsing dependencies so this may be the easiest option.

2. Run it on Command Line/Terminal. As this is a java project implemented in Maven,
running the project on the command line is slightly different. The program can be 
compiled directly on the command line; however it will require access to the 
latest jar file published by Lob for the API integration. Since this project was implemented with Maven, the dependencies were not provided in the form of jar files.
For the sake of running this project on the command line, the JSON parser jar file
was found and provided into the folder ‘/jars used/json-20140107.jar’ but the most
updated jar file from Lob (version 5.0.0) was not directly found online outside of
maven and could not be provided. However, if this jar file is come across, here are
the steps to run this program on the command line:

Go to the path /LobDemo/src/main/java/LobDemo/LobDemo/ to obtain all .java files. 
Once these files are obtained, go into terminal/Command prompt, and compile these 
files together with the added /jars used/json-20140107.jar file along with the 
additional Lob 5.0.0 api jar file if provided. Afterwards, simply run the program from 
here and it should run the same as it does on Eclipse/Netbeans.
 

3. Read the code. A great deal f effort has gone into making the example code readable,
not only in terms of the code itself, but also the extensive inline comments and documentation blocks.


