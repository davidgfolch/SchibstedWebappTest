

Bon dia David,

Et passo l'enunciat de la prova tècnica. Com t'he comentat normalment donem una setmana per tenir-la. Ja em dirÃ s si creus que podrÃ s tenir-la. Qualsevol aclariment que necessitis, no dubtis a fer-me-arribar.

**Cercem com organitzes el codi, la qualitat dels testos i com gestiones les peticions http.**

 

 


## Directions

Implement a Web Application using the Java language.

This application will have 3 different private pages and a login form. In order to access any of these private pages the user will need to have a session started through the login form and will need to have the right role to be able to access the page.

The application will also have a REST API endpoint exposing the `User` resource. Creating, deleting and modifying users and their permissions will be done through this API. 
 

## Requirements

Functional Requirements

    The necessary roles to access each page are the next ones:
        Page 1: In order to be able to access this page the logged user needs to have the role PAGE_1
        Page 2: In order to be able to access this page the logged user needs to have the role PAGE_2
        Page 3: In order to be able to access this page the logged user needs to have the role PAGE_3
    There's also an ADMIN role that means the user can modify and create other users through the REST API. The other users can only read through it
    Each page will simply show the name of the page the user is accessing and the text "Hello {USER_NAME}". 
    All pages will also have a link/button to close the user session.
    In the case of accessing any of these private pages without a logged session the application will redirect the user to the login form and once a success user login is performed the application will redirect the user to the page it was trying to reach before being redirected to the login form.
    In the case of accessing any of these private pages with a logged session but without the necessary role to access the page the application will not allow the user to see the page returning an appropriate status code indicating that access was denied.
    The user model will have a `username` field, a `roles` field and a `password` field. The password field is write only and will not be exposed on read operations
    We are not looking for â€œprettyâ€ pages so do not waste time on that, we are happy with ugly black and white pages :) Although no blinking tags please.
    There will be a minimum of 3 users, each of them with a different role. There might be users with several roles.
    The user session will expire in 5 minutes from the last user action.
    The REST API will use the same credentials used in the login form, but no session is needed. The authentication will be done using HTTP basic authentication
    All unauthorized, forbidden or resource unknown responses must honor the correct status code(https://httpstatus.es)

 

Technical requirements

    You need to use the com.sun.net.httpserver.HttpServer class to create a server. The use of an application server or servlet container is not allowed.
    You cannot use any framework.
    It is not necessary to have the users and roles in a database, it will be enough having them in memory, a text file or similar (Although the use of an embedded database is ok)
    The execution of the application will be through a runnable jar that will allow us to start it through command line (â€œjava -jar test-web-application.jarâ€) without having to use an application server or container
    The inclusiÃ³n of unit test covering a good percentage of code will be a plus
    The use of design patterns like MVC will be a plus. Code maintainability is very important for us
    Using content negotiation when delivering the API resources will be highly appreciated.
    Using reactive programming when/if it fits will be appreciated
    The application should have a dependency and build management system(gradle, mvn) to allow straightforward compilation and
    Delivery will be done uploading the code in a public git repository system like github or bitbucket.
    The git repository needs to have a clear README file explaining how to build and run the application and its tests along with any other characteristic worth to mention.

 

Suggestions in order to develop technical test:

    JDK 8 (Standard Edition)
    RxJava
    Twig or Mustache for HTML templating
    Jackson or Gson
    JUnit 4
    AssertJ
    Sqlite or H2

 

Salutacions,

Xavier

 

 

	

 
	

                Xavier Venteo
                Head of Development
                Mov. +34 690 28 8068
                xavier.venteo@scmspain.com

 

PlaÃ§a Xavier Cugat, 2 Edif. A
08174 Sant Cugat del VallÃ©s - Barcelona


RESPETEMOS EL MEDIO AMBIENTE: Â¿Necesitas realmente imprimir este email?
CONFIDENCIALIDAD: Este mail es confidencial y para uso exclusivo de su destinatario.

 
