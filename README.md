# consumer-objection-app
-----------------Install Maven----------------

$ sudo apt-get install maven

-----------------Install PostgreSQL------------

$ sudo apt update && sudo apt install -y postgresql postgresql-client

To set up database
---------------
    $ su postgres
    $ createuser --pwprompt <username>
    $ createdb -O <username> <dbname>

For remote login:
    $ psql --host=<endpoint> --port=<port> --username=<username> --password --dbname=<dbname>


-----------------Install Adminer-----------------

    $ sudo apt-get update
    $ sudo apt update && sudo apt install adminer
    $ /usr/sbin/a2enconf adminer.conf
    $ systemctl reload apache2

Then browser localhost/adminer


To run:
    mvn spring-boot:run


------Registration API-------
    URL (POST): APP_URL/api/register 

    * Sample Request
    {
        "name": "Dummy Name",
        "email": "dummy@d.com",
        "password": "#kjsd#45kj87dd"
    }

* Sample Response:
    {
        "message": "Successfully registered!",
        "status": "OK"
    }

* Failed Response:
    {
        "error": "User is already registered!",
        "status": "EMAIL_EXISTS"
    }


----Login API-------
URL (POST): APP_URL/api/login 

    * Sample Request:
    {
        "email": "dummy@d.com",
        "password": "#kjsd#45kj87dd"
    }

* Sample Response
    {
        "result" : "OK",
        "authentication" : "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkdW1teUBkLmNvbSIsInJvbGVzIjpbXSwiZXhwIjoxNjc0NjYwOTc3fQ.z3KSyGkgTiX3zSmxcfPXxmzH0vJibtlEp_LJ4I_ZIJ7E8tRksO7ROqEF-23BUn5KTnUaCs089QBxQmXJU7sBKQ"
    }

* Sample Failed Response
    {
        "authentication" : "Empty!",
        "status":"NOT_OK"
    }

