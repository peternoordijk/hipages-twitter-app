
==================================
  HIPAGES TWITTER APP
==================================

Internship Challenge

![alt tag](http://mediumsworld.files.wordpress.com/2012/05/life-changing-experience-ahead.jpg)

By Peter Noordijk


==================================
  TABLE OF CONTENTS
==================================

1. Introduction
2. Functionalities
3. Backend technology
4. Frontend technology
5. Installation


==================================
  1. INTRODUCTION
==================================

This application has been made due to an IT internship which I would 
like to attend at Hipages group. Its goal is to give the user an experience
in which the Twitter activities related to Hipages will be revealed in a 
life changing way. 


==================================
  2. FUNCTIONALITIES
==================================

This application is able to do the very basic functionalities of Twitter. 
You can search for hashtags, @ mentions, and so on. The main focus of this
app is the profile of Hipages Group. By default the statuses from its
profile will be shown.
A known issue are the responses. When you are searching for something using 
this application you will be shown status updates as well as their comments
in one single stream. This might be resolved in future updates.

I'm at this point not entirely sure whether it is required or not for me to
add additional features.


==================================
  3. BACKEND TECHNOLOGY
==================================

For this application I decided to use Java. The backend server is functional 
as a RESTful webservice. It gets requests from the client (webbrowser), 
connects to Twitter, and sends back information in JSON format.

    ________________________
    |                      |
    |        CLIENT        |
    |______________________|
                |
                |
                v
    ________________________
    |                      |
    |   JAVA RESTServer    |
    |______________________|
                |
                |
                v
    ________________________
    |                      |
    |       TWITTER        |
    |______________________|

Everything the Java server really has to do is translate the requests into 
other requests. The server is created using a library called Jersey and the 
object-to-JSON conversion is done using the library GSON. 

The connection from Java to Twitter has been established using a library 
called Twitter4J. This library is specialized in Twitter. This said, you 
will probably understand that the Java layer might actually be able to work 
with only one class with a few methods (one method for each request).

However, I thought that might be a bit too easy and ugly for a challenge like 
this and I decided to make a better design. This application now contains 
five Java packages:
- domain
- controllers
- services
- builders
- tests
The MVC pattern has obviously done its work in here. However, the namegiving 
might be a bit weird: the domain is equal to a model, the controllers are 
actually the views and the services are really just controllers. Right now 
I'm a bit confused about the reason for this as well.
The fourth package, called builders, is something different. This package is 
being used to create the actual connection to Twitter. The Twitter4J library 
has entirely been encapsulated in this package. The rest of the project can 
only use its interfaces. That said, it would be really easy to switch to 
another library if Twitter4J gets deprecated at a certain moment. 
Twitter4J has its own domain model. However, relying on this model would be 
in conflict with the statement above. That's why I created my own domain 
model which contains only the information which will be used by the frontend 
of this application.
Last but not least there is a test package. This package contains some tests
which are used to confirm whether the implementation of the Java layer is 
correct.


==================================
  4. FRONTEND TECHNOLOGY
==================================

The user of this application can access its features using a web browser. I 
decided to use the frontend framework Bootstrap, as it is open-source and 
very usefull for small projects like this. It's also produced by Twitter so 
that makes it even better for this particular project. 
As JavaScript usually tends to become one big dirty fix I tried to implement 
some Object-oriented features. This doesn't take away that it still looks 
quite chaotic. 
As for the rest the frontend is not really very interesting. It makes use 
of Ajax, parses JSON, and so on...


==================================
  5. INSTALLATION
==================================

This application requires Java to run on the computer as well as Tomcat. All 
libraries used in this project are defined using Maven. In the file pom.xml 
you will be able to see all dependencies. To compile this project it is also 
required to have Maven installed. You can start compiling using this command 
in your project directory:

  mvn clean install

After running this code a folder called 'target' will be created in your 
project root directory. This folder contains a .war file which you will need 
to copy into the webapps folder of Tomcat. Now all you have to do is restart 
Tomcat (or just wait for 10 seconds until Tomcat identifies the file). Now 
go to http://localhost:8080/hipages-twitter-app/ and voila!
